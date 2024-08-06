package com.mrcrayfish.furniture.refurbished.blockentity;

import com.google.common.base.Preconditions;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.ContainerInput;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardCombiningRecipe;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardBlockEntity extends BasicLootBlockEntity
{
    private final RecipeManager.CachedCheck<SingleRecipeInput, ? extends SingleItemRecipe> slicingRecipeCache;
    private final RecipeManager.CachedCheck<ContainerInput, CuttingBoardCombiningRecipe> combiningRecipeCache;
    private final RecipeManager.CachedCheck<SingleRecipeInput, ? extends SingleItemRecipe> outputCache;
    protected final int useableContainerSize;
    protected boolean sync;
    protected boolean canExtract;
    protected boolean placedByPlayer;

    public CuttingBoardBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.CUTTING_BOARD.get(), pos, state, CuttingBoardCombiningRecipe.MAX_INGREDIENTS, ModRecipeTypes.CUTTING_BOARD_SLICING.get(), ModRecipeTypes.CUTTING_BOARD_COMBINING.get());
    }

    public CuttingBoardBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize, RecipeType<? extends SingleItemRecipe> slicingRecipeType, RecipeType<CuttingBoardCombiningRecipe> combiningRecipeType)
    {
        super(type, pos, state, containerSize + 1);
        this.useableContainerSize = containerSize;
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
     * cutting board recipe.
     *
     * @param heldItem the item to place on the cutting board
     * @return True if an item was placed
     */
    public boolean placeItem(ItemStack heldItem)
    {
        int placeIndex = this.getPlaceIndex();
        if(this.canPlaceItem(placeIndex, heldItem))
        {
            ItemStack copy = heldItem.copy();
            copy.setCount(1);
            heldItem.shrink(1);
            if(!this.level.isClientSide())
            {
                this.placedByPlayer = true;
                this.canExtract = false;
                this.playPlaceIngredientSound(1.0F);
            }
            this.setItem(placeIndex, copy);
            return true;
        }
        return false;
    }

    /**
     * Removes the item on the top of the stack and spawns it into the level
     */
    public void removeItem()
    {
        int removeIndex = this.getHeadIndex();
        if(removeIndex >= 0 && !this.getItem(removeIndex).isEmpty())
        {
            ItemStack stack = this.getItem(removeIndex);
            this.spawnItemIntoLevel(this.level, stack);
            this.setItem(removeIndex, ItemStack.EMPTY);
            this.canExtract = false;
        }
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
            if(!this.level.isClientSide())
            {
                level.playSound(null, this.worldPosition, ModSounds.ITEM_KNIFE_CHOP.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                this.spawnSliceParticles(input);
                this.spawnSliceResultFromRecipe(sliceIndex, recipe.get(), spawnIntoLevel);
            }
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

    private void playPlaceIngredientSound(float pitch)
    {
        Level level = Objects.requireNonNull(this.level);
        Vec3 vec = Vec3.atBottomCenterOf(this.worldPosition);
        level.playSound(null, vec.x, vec.y, vec.z, ModSounds.BLOCK_CUTTING_BOARD_PLACED_INGREDIENT.get(), SoundSource.PLAYERS, 1.0F, pitch + 0.05F * (float) level.random.nextGaussian());
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
        for(int i = this.useableContainerSize - 1; i >= 0; i--)
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
        return slotIndex >= 0 && slotIndex < this.useableContainerSize && slotIndex == this.getPlaceIndex() && this.isSlotInsertable(slotIndex) && this.canPlaceOnTop(stack);
    }

    @Override
    public boolean canTakeItem(Container container, int slotIndex, ItemStack stack)
    {
        if(slotIndex >= 0 && slotIndex < this.useableContainerSize && slotIndex == this.getHeadIndex() && this.canExtract)
        {
            return this.outputCache.getRecipeFor(new SingleRecipeInput(stack), Objects.requireNonNull(this.level)).isEmpty();
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
        ItemStack stack = super.removeItem(slotIndex, count);
        if(this.isEmpty())
        {
            this.canExtract = false;
        }
        return stack;
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
        return this.slicingRecipeCache.getRecipeFor(new SingleRecipeInput(stack), Objects.requireNonNull(this.level)).map(RecipeHolder::value);
    }

    /**
     *
     * @return
     */
    private Optional<CuttingBoardCombiningRecipe> getCombiningRecipe()
    {
        return this.combiningRecipeCache.getRecipeFor(new ContainerInput(this), Objects.requireNonNull(this.level)).map(RecipeHolder::value);
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
        if(placeIndex >= this.useableContainerSize)
            return Optional.empty();

        Container container = new SimpleContainer(placeIndex + 1);
        IntStream.range(0, placeIndex + 1).forEach(index -> container.setItem(index, this.getItem(index)));
        container.setItem(container.getContainerSize() - 1, stack);
        return this.combiningRecipeCache.getRecipeFor(new ContainerInput(container), Objects.requireNonNull(this.level)).map(RecipeHolder::value);
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
        ItemStack stack = recipe.assemble(new ContainerInput(this), level.registryAccess());
        List<ItemStack> remainingItems = this.getCraftingRemainingItems();

        this.clearContent();
        this.spawnSliceParticles(stack);
        this.spawnMagicParticles();

        Vec3 center = Vec3.atBottomCenterOf(this.worldPosition);
        level.playSound(null, center.x, center.y, center.z, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);

        if(spawnIntoLevel)
        {
            this.spawnItemIntoLevel(level, stack);
            remainingItems.forEach(item -> this.spawnItemIntoLevel(this.level, item));
            return;
        }

        // Add the result and remaining items back onto the cutting board
        this.setItem(0, stack);
        for(int i = 0; i < remainingItems.size() && i < this.useableContainerSize; i++)
        {
            this.setItem(i + 1, remainingItems.get(i));
        }

        // Finally allow hoppers to extract items
        this.canExtract = true;
    }

    private List<ItemStack> getCraftingRemainingItems()
    {
        // Collect remaining items
        List<ItemStack> remainingItems = new ArrayList<>();
        for(int i = 0; i < this.useableContainerSize; i++)
        {
            ItemStack stack = this.getItem(i);
            if(!stack.isEmpty() && stack.getItem().hasCraftingRemainingItem())
            {
                Item item = stack.getItem().getCraftingRemainingItem();
                if(item != null)
                {
                    remainingItems.add(new ItemStack(item));
                }
            }
        }
        return remainingItems;
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
            BlockEntityHelper.sendCustomUpdate(entity, BlockEntity::getUpdateTag);
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
    public CompoundTag getUpdateTag(HolderLookup.Provider provider)
    {
        return this.saveWithoutMetadata(provider);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.saveAdditional(tag, provider);
        tag.putBoolean("CanExtract", this.canExtract);
        tag.putBoolean("PlacedByPlayer", this.placedByPlayer);
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider)
    {
        super.loadAdditional(compound, provider);
        if(compound.contains("CanExtract", Tag.TAG_BYTE))
        {
            this.canExtract = compound.getBoolean("CanExtract");
        }
        if(compound.contains("PlacedByPlayer", Tag.TAG_BYTE))
        {
            this.placedByPlayer = compound.getBoolean("PlacedByPlayer");
        }
    }
}
