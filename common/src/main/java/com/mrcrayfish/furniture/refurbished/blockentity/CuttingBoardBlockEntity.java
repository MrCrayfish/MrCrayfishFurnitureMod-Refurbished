package com.mrcrayfish.furniture.refurbished.blockentity;

import com.google.common.base.Preconditions;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
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

/**
 * Author: MrCrayfish
 */
public class CuttingBoardBlockEntity extends BasicLootBlockEntity
{
    private final RecipeManager.CachedCheck<Container, ? extends SingleItemRecipe> inputRecipeCache;
    private final RecipeManager.CachedCheck<Container, ? extends SingleItemRecipe> outputCache;
    protected boolean sync;

    public CuttingBoardBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.CUTTING_BOARD.get(), pos, state, 1, ModRecipeTypes.CUTTING_BOARD_SLICING.get());
    }

    public CuttingBoardBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize, RecipeType<? extends SingleItemRecipe> recipeType)
    {
        super(type, pos, state, containerSize);
        this.inputRecipeCache = RecipeManager.createCheck(recipeType);
        this.outputCache = RecipeManager.createCheck(recipeType);
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
        if(this.canPlaceItem(0, heldItem))
        {
            ItemStack copy = heldItem.copy();
            copy.setCount(1);
            heldItem.shrink(1);
            this.setItem(0, copy);
            return true;
        }
        else if(!this.getItem(0).isEmpty())
        {
            ItemStack stack = this.getItem(0);
            this.spawnItemIntoLevel(this.level, stack);
            this.setItem(0, ItemStack.EMPTY);
            return true;
        }
        return false;
    }

    /**
     * @return True if an item was sliced
     */
    public boolean sliceItem(Level level, boolean spawnIntoLevel)
    {
        ItemStack input = this.getItem(0);
        Optional<? extends SingleItemRecipe> recipe = this.getRecipe(input);
        if(recipe.isPresent())
        {
            level.playSound(null, this.worldPosition, ModSounds.ITEM_KNIFE_CHOP.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            this.spawnSliceParticles(input);
            this.spawnSliceResultFromRecipe(recipe.get(), spawnIntoLevel);
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

    /**
     * Spawns the result item stack from the given recipe, either into the level or placed back on
     * the cutting board. To spawn into the level, mark spawnIntoLevel as true.
     *
     * @param recipe the recipe to get the result item
     * @param spawnIntoLevel if the item should spawn into the level or remain on the cutting board
     */
    private void spawnSliceResultFromRecipe(SingleItemRecipe recipe, boolean spawnIntoLevel)
    {
        Preconditions.checkNotNull(this.level);
        ItemStack result = recipe.getResultItem(this.level.registryAccess());
        if(spawnIntoLevel)
        {
            this.spawnItemIntoLevel(this.level, result);
            this.setItem(0, ItemStack.EMPTY);
            return;
        }
        this.setItem(0, result.copy());
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
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, stack.copy());
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(new Vec3(0, 0.15, 0));
        level.addFreshEntity(entity);
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        return slotIndex == 0 && this.isSlotInsertable(slotIndex) && this.isRecipe(stack);
    }

    @Override
    public boolean canTakeItem(Container container, int slotIndex, ItemStack stack)
    {
        if(slotIndex == 0)
        {
            return this.outputCache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level)).isEmpty();
        }
        return false;
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
    public boolean isRecipe(ItemStack stack)
    {
        return this.getRecipe(stack).isPresent();
    }

    /**
     * Gets the cutting board recipe from the input item. If no recipe exists for the given input
     * item stack, then this method will simply return an empty optional.
     *
     * @param stack the input item
     * @return the recipe for the input or empty optional if no recipe exists.
     */
    public Optional<? extends SingleItemRecipe> getRecipe(ItemStack stack)
    {
        return this.inputRecipeCache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level));
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
