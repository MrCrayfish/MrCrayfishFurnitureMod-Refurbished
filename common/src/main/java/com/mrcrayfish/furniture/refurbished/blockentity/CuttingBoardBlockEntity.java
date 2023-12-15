package com.mrcrayfish.furniture.refurbished.blockentity;

import com.google.common.base.Preconditions;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardCombiningRecipe;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardBlockEntity extends BasicLootBlockEntity
{
    private final RecipeManager.CachedCheck<Container, ? extends SingleItemRecipe> slicingRecipeCache;
    private final RecipeManager.CachedCheck<Container, CuttingBoardCombiningRecipe> combiningRecipeCache;
    private final RecipeManager.CachedCheck<Container, ? extends SingleItemRecipe> outputCache;
    protected boolean sync;
    protected boolean canExtract;
    protected boolean placedByPlayer;

    public CuttingBoardBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.CUTTING_BOARD.get(), pos, state, CuttingBoardCombiningRecipe.MAX_INGREDIENTS, ModRecipeTypes.CUTTING_BOARD_SLICING.get(), ModRecipeTypes.CUTTING_BOARD_COMBINING.get());
    }

    public CuttingBoardBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize, RecipeType<? extends SingleItemRecipe> slicingRecipeType, RecipeType<CuttingBoardCombiningRecipe> combiningRecipeType)
    {
        super(type, pos, state, containerSize);
        this.slicingRecipeCache = RecipeManager.createCheck(slicingRecipeType);
        this.combiningRecipeCache = RecipeManager.createCheck(combiningRecipeType);
        this.outputCache = RecipeManager.createCheck(slicingRecipeType);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return false;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "cutting_board");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        // Cutting board has no gui
        return null;
    }

    /**
     * Places the given item onto the cutting board. The item will only be placed if it has a
     * cutting board recipe. If an item is already placed on the cutting board, that item will be
     * removed instead. The given input item will not be placed onto the cutting board in the same
     * action of removing an existing item on the board.
     *
     * @param heldItem the item to place on the cutting board
     * @return True if an item was placed or removed from the board
     */
    public boolean placeItem(ItemStack heldItem)
    {
        int placeIndex = this.getPlaceIndex();
        if(this.canPlaceItem(placeIndex, heldItem))
        {
            ItemStack copy = heldItem.copy();
            copy.setCount(1);
            heldItem.shrink(1);
            this.placedByPlayer = true;
            this.canExtract = false;
            this.setItem(placeIndex, copy);
            return true;
        }
        int removeIndex = this.getHeadIndex();
        if(removeIndex >= 0 && !this.getItem(removeIndex).isEmpty())
        {
            ItemStack stack = this.getItem(removeIndex);
            this.spawnItemIntoLevel(this.level, stack);
            this.setItem(removeIndex, ItemStack.EMPTY);
            this.canExtract = false;
            return true;
        }
        return false;
    }

    /**
     * @return True if an item was sliced
     */
    public boolean sliceItem(Level level, boolean spawnIntoLevel)
    {
        int sliceIndex = this.getHeadIndex();
        if(sliceIndex != 0) // Head index must be the first item
            return false;
        ItemStack input = this.getItem(sliceIndex);
        Optional<? extends SingleItemRecipe> recipe = this.getSlicingRecipe(input);
        if(recipe.isPresent())
        {
            level.playSound(null, this.worldPosition, ModSounds.ITEM_KNIFE_CHOP.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            this.spawnSliceParticles(input);
            this.spawnSliceResultFromRecipe(sliceIndex, recipe.get(), spawnIntoLevel);
            return true;
        }
        return false;
    }

    /**
     * Spawns particles of the given item at the position of the cutting board to indicate it
     * has been sliced.
     *
     * @param stack the item to pull the textures from
     */
    private void spawnSliceParticles(ItemStack stack)
    {
        if(this.level instanceof ServerLevel serverLevel)
        {
            BlockPos pos = this.worldPosition;
            RandomSource rand = serverLevel.getRandom();
            for(int i = 0; i < 8; i++)
            {
                serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, 1, rand.nextGaussian() * 0.15, rand.nextDouble() * 0.2, rand.nextGaussian() * 0.15, 0);
            }
        }
    }

    private void spawnMagicParticles()
    {
        if(this.level instanceof ServerLevel serverLevel)
        {
            BlockPos pos = this.worldPosition;
            RandomSource rand = serverLevel.getRandom();
            for(int i = 0; i < 8; i++)
            {
                double x = pos.getX() + 0.5 + 0.3 * rand.nextGaussian();
                double y = pos.getY() + 0.1;
                double z = pos.getZ() + 0.5 + 0.3 * rand.nextGaussian();
                serverLevel.sendParticles(ParticleTypes.COMPOSTER, x, y, z, 1, rand.nextGaussian() * 0.02, rand.nextGaussian() * 0.02, rand.nextGaussian() * 0.02, 0);
            }
        }
    }

    /**
     * Spawns the result item stack from the given recipe, either into the level or placed back on
     * the cutting board. To spawn into the level, mark spawnIntoLevel as true.
     *
     * @param sliceIndex
     * @param recipe         the recipe to get the result item
     * @param spawnIntoLevel if the item should spawn into the level or remain on the cutting board
     */
    private void spawnSliceResultFromRecipe(int sliceIndex, SingleItemRecipe recipe, boolean spawnIntoLevel)
    {
        Preconditions.checkNotNull(this.level);
        ItemStack result = recipe.getResultItem(this.level.registryAccess());
        if(spawnIntoLevel)
        {
            this.spawnItemIntoLevel(this.level, result);
            this.setItem(sliceIndex, ItemStack.EMPTY);
            return;
        }
        this.setItem(sliceIndex, result.copy());
        this.canExtract = true;
    }

    /**
     * Spawns an item into the level at this position of this cutting board
     *
     * @param level the level to spawn the item entity
     * @param stack the stack that is going to be spawned
     */
    private void spawnItemIntoLevel(Level level, ItemStack stack)
    {
        BlockPos pos = this.worldPosition;
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.0625, pos.getZ() + 0.5, stack.copy());
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(new Vec3(0, 0.15, 0));
        level.addFreshEntity(entity);
    }

    /**
     * The index where to next item will be placed. May return a value greater than the container
     * size, so ensure this is checked.
     *
     * @return index to place the next item
     */
    public int getPlaceIndex()
    {
        return this.getHeadIndex() + 1;
    }

    /**
     * @return The index of the item highest on the cutting board or -1 if empty
     */
    public int getHeadIndex()
    {
        for(int i = this.containerSize - 1; i >= 0; i--)
        {
            if(!this.getItem(i).isEmpty())
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        return slotIndex >= 0 && slotIndex < this.containerSize && slotIndex == this.getPlaceIndex() && this.isSlotInsertable(slotIndex) && this.canPlaceOnTop(stack);
    }

    @Override
    public boolean canTakeItem(Container container, int slotIndex, ItemStack stack)
    {
        if(slotIndex >= 0 && slotIndex < this.containerSize && slotIndex == this.getHeadIndex() && this.canExtract)
        {
            return this.outputCache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level)).isEmpty();
        }
        return false;
    }

    @Override
    public void setItem(int slotIndex, ItemStack stack)
    {
        super.setItem(slotIndex, stack);
        if(!stack.isEmpty() && this.getHeadIndex() >= 0 && !Objects.requireNonNull(this.level).isClientSide())
        {
            this.craftCombiningRecipe(this.placedByPlayer);
        }
    }

    @Override
    public ItemStack removeItem(int slotIndex, int count)
    {
        this.canExtract = false;
        return super.removeItem(slotIndex, count);
    }

    @Override
    protected boolean isSlotInsertable(int slotIndex)
    {
        ItemStack target = this.getItem(slotIndex);
        return target.isEmpty() || target.getCount() < target.getMaxStackSize() && target.getCount() < 1;
    }

    /**
     * A utility method to checks if the given ItemStack is a valid recipe for this processing block.
     * @param stack the item stack to check
     * @return True if it matches a recipe
     */
    public boolean canPlaceOnTop(ItemStack stack)
    {
        return this.getNextCombiningRecipe(stack).isPresent() || this.getHeadIndex() == -1 && this.getSlicingRecipe(stack).isPresent();
    }

    /**
     * Gets the cutting board recipe from the input item. If no recipe exists for the given input
     * item stack, then this method will simply return an empty optional.
     *
     * @param stack the input item
     * @return the recipe for the input or empty optional if no recipe exists.
     */
    private Optional<? extends SingleItemRecipe> getSlicingRecipe(ItemStack stack)
    {
        return this.slicingRecipeCache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level));
    }

    /**
     *
     * @return
     */
    private Optional<CuttingBoardCombiningRecipe> getCombiningRecipe()
    {
        return this.combiningRecipeCache.getRecipeFor(this, Objects.requireNonNull(this.level));
    }

    /**
     * Gets the next possible combining recipe based on the current items on the cutting item board
     * and the given item stack. The given item stack is assumed to be placed on top. The returned
     * recipe is not necessarily complete. A complete match will require call to
     * {@link CuttingBoardCombiningRecipe#completelyMatches(Container)}.
     *
     * @param stack the next item stack to be placed on the cutting board
     * @return An optional containing the recipe or empty
     */
    private Optional<CuttingBoardCombiningRecipe> getNextCombiningRecipe(ItemStack stack)
    {
        int placeIndex = this.getPlaceIndex();
        if(placeIndex >= this.containerSize)
            return Optional.empty();

        Container container = new SimpleContainer(placeIndex + 1);
        IntStream.range(0, placeIndex + 1).forEach(index -> container.setItem(index, this.getItem(index)));
        container.setItem(container.getContainerSize() - 1, stack);
        return this.combiningRecipeCache.getRecipeFor(container, Objects.requireNonNull(this.level));
    }

    /**
     * Attempts to use the items currently placed on the cutting board and combine them into a new item
     *
     * @param spawnIntoLevel if the result should spawn into the level or stay on the cutting board
     */
    private void craftCombiningRecipe(boolean spawnIntoLevel)
    {
        this.placedByPlayer = false;

        Optional<CuttingBoardCombiningRecipe> optional = this.getCombiningRecipe();
        if(optional.isEmpty())
            return;

        CuttingBoardCombiningRecipe recipe = optional.get();
        if(!recipe.completelyMatches(this))
            return;

        Level level = Objects.requireNonNull(this.level);
        ItemStack stack = recipe.assemble(this, level.registryAccess());
        this.clearContent();
        this.spawnSliceParticles(stack);
        this.spawnMagicParticles();

        if(spawnIntoLevel)
        {
            this.spawnItemIntoLevel(level, stack);
            return;
        }

        this.setItem(0, stack);
        this.canExtract = true;
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        if(!this.level.isClientSide())
        {
            this.sync();
        }
    }

    /**
     * Marks the toaster as needing to sync data to tracking clients
     */
    protected void sync()
    {
        this.sync = true;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CuttingBoardBlockEntity entity)
    {
        if(entity.sync)
        {
            BlockEntityHelper.sendCustomUpdate(entity, entity.getUpdateTag());
            entity.sync = false;
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }
}
