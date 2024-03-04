package com.mrcrayfish.furniture.refurbished.blockentity;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.refurbished.block.StoveBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("UnstableApiUsage")
public class StoveBlockEntity extends ElectricityModuleLootBlockEntity implements IProcessingBlock, IHeatingSource, IPowerSwitch, IHomeControlDevice, Nameable
{
    public static final int[] INPUT_SLOTS = new int[]{0, 1, 2};
    public static final int[] OUTPUT_SLOTS = new int[]{3, 4, 5};
    public static final int DATA_POWERED = 0;
    public static final int DATA_ENABLED = 1;
    public static final int DATA_PROGRESS_1 = 2;
    public static final int DATA_PROGRESS_2 = 3;
    public static final int DATA_PROGRESS_3 = 4;
    public static final int DATA_TOTAL_PROGRESS_1 = 5;
    public static final int DATA_TOTAL_PROGRESS_2 = 6;
    public static final int DATA_TOTAL_PROGRESS_3 = 7;

    protected final ImmutableList<CookingSpace> spaces;
    protected boolean enabled;
    protected boolean processing;
    protected int totalProcessingTime;
    protected int processingTime;
    protected WeakReference<ICookingBlock> cookingBlockRef;
    protected boolean sync;
    protected @Nullable StoveContainer container;
    protected @Nullable Component name;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_POWERED, () -> this.isPowered() ? 1 : 0, value -> {});
        builder.add(DATA_ENABLED, () -> enabled ? 1 : 0, value -> {});
        builder.add(DATA_PROGRESS_1, () -> this.getCookingSpaces(0).bakingTime, value -> {});
        builder.add(DATA_PROGRESS_2, () -> this.getCookingSpaces(1).bakingTime, value -> {});
        builder.add(DATA_PROGRESS_3, () -> this.getCookingSpaces(2).bakingTime, value -> {});
        builder.add(DATA_TOTAL_PROGRESS_1, () -> this.getCookingSpaces(0).totalBakingTime, value -> {});
        builder.add(DATA_TOTAL_PROGRESS_2, () -> this.getCookingSpaces(1).totalBakingTime, value -> {});
        builder.add(DATA_TOTAL_PROGRESS_3, () -> this.getCookingSpaces(2).totalBakingTime, value -> {});
    });

    public StoveBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.STOVE.get(), pos, state);
    }

    public StoveBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 6);
        this.spaces = Util.make(() -> {
            ImmutableList.Builder<CookingSpace> builder = ImmutableList.builderWithExpectedSize(3);
            IntStream.range(0, 3).forEach(i -> builder.add(new CookingSpace(i, i + 3, ModRecipeTypes.OVEN_BAKING.get())));
            return builder.build();
        });
    }

    private CookingSpace getCookingSpaces(int index)
    {
        return this.spaces.get(index);
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        if(direction == Direction.DOWN)
        {
            return OUTPUT_SLOTS;
        }
        return INPUT_SLOTS;
    }

    @Override
    public int getEnergy()
    {
        return 0;
    }

    @Override
    public void addEnergy(int energy) {}

    @Override
    public boolean requiresEnergy()
    {
        return false;
    }

    @Override
    public int retrieveEnergy(boolean simulate)
    {
        return 0;
    }

    @Override
    public int updateAndGetTotalProcessingTime()
    {
        int time = 0;
        ICookingBlock block = this.getCookingBlock();
        if(block != null)
        {
            time = block.getTimeToCook();
        }
        if(this.totalProcessingTime != time)
        {
            this.totalProcessingTime = time;
        }
        return this.totalProcessingTime;
    }

    @Override
    public int getTotalProcessingTime()
    {
        return this.totalProcessingTime;
    }

    @Override
    public int getProcessingTime()
    {
        return this.processingTime;
    }

    @Override
    public void setProcessingTime(int time)
    {
        boolean changed = false;
        if(this.processingTime == 0 && time > this.processingTime)
        {
            ICookingBlock block = this.getCookingBlock();
            if(block != null)
            {
                block.onStartCooking();
            }
            this.processing = true;
            this.sync();
            changed = true;
        }
        else if(time == 0 && time < this.processingTime)
        {
            ICookingBlock block = this.getCookingBlock();
            if(block != null)
            {
                block.onStopCooking();
            }
            this.processing = false;
            this.sync();
            changed = true;
        }
        if(this.processingTime != time)
        {
            this.processingTime = time;
            changed = true;
        }
        if(changed)
        {
            this.setChanged();
        }
    }

    @Override
    public void onCompleteProcess()
    {
        ICookingBlock block = this.getCookingBlock();
        if(block != null)
        {
            block.onCompleteCooking();
            this.processing = false;
            this.setChanged();
            this.sync();
        }
    }

    @Override
    public boolean canProcess()
    {
        if(!this.isPowered() || !this.enabled) return false;
        ICookingBlock block = this.getCookingBlock();
        return block != null && block.canCook();
    }

    @Nullable
    public ICookingBlock getCookingBlock()
    {
        if(this.cookingBlockRef != null)
        {
            ICookingBlock block = this.cookingBlockRef.get();
            if(block != null && !block.getBlockEntity().isRemoved())
            {
                return block;
            }
            this.cookingBlockRef = null;
        }

        if(this.level instanceof ServerLevel serverLevel)
        {
            BlockEntity entity = serverLevel.getBlockEntity(this.worldPosition.above());
            if(entity instanceof ICookingBlock cookingBlock)
            {
                this.cookingBlockRef = new WeakReference<>(cookingBlock);
                return cookingBlock;
            }
        }
        return null;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, StoveBlockEntity stove)
    {
        ElectricityModuleLootBlockEntity.serverTick(level, pos, state, stove);
        stove.processTick();
        stove.spaces.forEach(IProcessingBlock::processTick);
        if(stove.sync)
        {
            BlockEntityHelper.sendCustomUpdate(stove, stove.getUpdateTag());
            stove.sync = false;
        }
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "stove");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new StoveMenu(windowId, playerInventory, this, this.data);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof StoveMenu stove && stove.getContainer() == this;
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        Vec3 center = Vec3.atCenterOf(this.worldPosition).relative(state.getValue(StoveBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, center.x, center.y, center.z, ModSounds.BLOCK_STOVE_OPEN.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        Vec3 center = Vec3.atCenterOf(this.worldPosition).relative(state.getValue(StoveBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, center.x, center.y, center.z, ModSounds.BLOCK_STOVE_CLOSE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDoorState(state, false);
    }

    private void setDoorState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(StoveBlock.OPEN, open), Block.UPDATE_ALL);
        }
    }

    @Override
    public boolean isProcessing()
    {
        return this.processing;
    }

    @Override
    public boolean isHeating()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(StoveBlock.LIT) && state.getValue(StoveBlock.LIT);
    }

    /**
     * Callback when the stove is removed from the level and updates the above cooking block, if
     * any, that cooking has stopped.
     *
     * @param pos the block position of the stove
     */
    public void onDestroyed(BlockPos pos)
    {
        if(this.isProcessing())
        {
            Level level = this.getLevel();
            if(level != null && level.getBlockEntity(pos.above()) instanceof ICookingBlock cooking)
            {
                cooking.onStopCooking();
            }
        }
    }

    /**
     * Called when a neighbour block is changed.
     */
    public void onNeighbourChanged()
    {
        this.container = null;
    }

    @Override
    public void setBlockState(BlockState state)
    {
        super.setBlockState(state);
        this.container = null;
    }

    public WorldlyContainer getContainer()
    {
        if(this.container == null)
        {
            ICookingBlock cookingBlock = this.getCookingBlock();
            if(cookingBlock != null && cookingBlock.getBlockEntity() instanceof Container cookingContainer)
            {
                this.container = new StoveContainer(this, cookingBlock, cookingContainer);
            }
        }
        else if(!this.container.isValid())
        {
            this.container = null;
        }
        return this.container != null ? this.container : this;
    }

    /**
     * Marks the frying pan as needing to sync data to tracking clients
     */
    protected void sync()
    {
        this.sync = true;
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

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("Processing", Tag.TAG_BYTE))
        {
            this.processing = tag.getBoolean("Processing");
        }
        if(tag.contains("TotalProcessingTime", Tag.TAG_INT))
        {
            this.totalProcessingTime = tag.getInt("TotalProcessingTime");
        }
        if(tag.contains("ProcessingTime", Tag.TAG_INT))
        {
            this.processingTime = tag.getInt("ProcessingTime");
        }
        if(tag.contains("Enabled", Tag.TAG_BYTE))
        {
            this.enabled = tag.getBoolean("Enabled");
        }
        this.readCookingSpaces(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("Processing", this.processing);
        tag.putInt("TotalProcessingTime", this.totalProcessingTime);
        tag.putInt("ProcessingTime", this.processingTime);
        tag.putBoolean("Enabled", this.enabled);
        this.writeCookingSpaces(tag);
    }

    @Override
    public boolean isPowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(StoveBlock.POWERED) && state.getValue(StoveBlock.POWERED);
    }

    @Override
    public void setPowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(StoveBlock.POWERED))
        {
            state = state.setValue(StoveBlock.POWERED, powered);
        }
        if(state.hasProperty(StoveBlock.LIT))
        {
            state = state.setValue(StoveBlock.LIT, powered && this.enabled);
        }
        this.level.setBlock(this.worldPosition, state, Block.UPDATE_ALL);
    }

    @Override
    public void toggle()
    {
        this.enabled = !this.enabled;
        this.level.setBlock(this.worldPosition, this.getBlockState().setValue(StoveBlock.LIT, this.isPowered() && this.enabled), Block.UPDATE_ALL);
        this.setChanged();
        this.sync();
    }

    @Override
    public BlockPos getDevicePos()
    {
        return this.worldPosition;
    }

    @Override
    public boolean isDeviceEnabled()
    {
        return this.enabled;
    }

    @Override
    public void toggleDeviceState()
    {
        this.enabled = !this.enabled;
        this.setChanged();
        this.syncNodeData();
    }

    @Override
    public void setDeviceState(boolean enabled)
    {
        this.enabled = enabled;
        this.setChanged();
        this.syncNodeData();
    }

    @Override
    public Component getDeviceName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomName();
        }
        return this.getName();
    }

    @Override
    public Component getName()
    {
        return this.getBlockState().getBlock().getName();
    }

    @Override
    public Component getDisplayName()
    {
        return this.name != null ? this.name : this.getName();
    }

    @Nullable
    @Override
    public Component getCustomName()
    {
        return this.name;
    }

    public void setCustomName(@Nullable Component name)
    {
        this.name = name;
    }

    /**
     * Writes the Cooking Spaces to NBT.
     *
     * @param compound the compound tag to save the data to
     * @return the compound tag the data saved to
     */
    private CompoundTag writeCookingSpaces(CompoundTag compound)
    {
        ListTag list = new ListTag();
        for(int i = 0; i < this.spaces.size(); i++)
        {
            CompoundTag tag = new CompoundTag();
            this.spaces.get(i).writeToTag(tag);
            tag.putInt("Position", i);
            list.add(tag);
        }
        compound.put("CookingSpaces", list);
        return compound;
    }

    /**
     * Reads the Cooking Spaces from NBT. This method has been designed to accept partial data.
     * This means it can read the data from one cooking space and not reset/affect the other spaces.
     * This use case is used for reading sync data, while still being a general method to read all
     * the cooking spaces.
     *
     * @param compound the compound tag to read from
     */
    private void readCookingSpaces(CompoundTag compound)
    {
        if(compound.contains("CookingSpaces", Tag.TAG_LIST))
        {
            ListTag list = compound.getList("CookingSpaces", Tag.TAG_COMPOUND);
            list.forEach(nbt -> {
                CompoundTag tag = (CompoundTag) nbt;
                if(tag.contains("Position", Tag.TAG_INT)) {
                    int position = tag.getInt("Position");
                    if(position >= 0 && position < this.spaces.size()) {
                        this.spaces.get(position).readFromTag(tag);
                    }
                }
            });
        }
    }

    protected class CookingSpace implements IProcessingBlock
    {
        private final int inputIndex;
        private final int outputIndex;
        private final RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe> inputRecipeCache;
        private int totalBakingTime;
        private int bakingTime;

        public CookingSpace(int inputIndex, int outputIndex, RecipeType<? extends ProcessingRecipe> recipeType)
        {
            this.inputIndex = inputIndex;
            this.outputIndex = outputIndex;
            this.inputRecipeCache = RecipeManager.createCheck(recipeType);
        }

        @Override
        public int getEnergy()
        {
            return 0;
        }

        @Override
        public void addEnergy(int energy) {}

        @Override
        public boolean requiresEnergy()
        {
            return false;
        }

        @Override
        public int retrieveEnergy(boolean simulate)
        {
            return 0;
        }

        @Override
        public int updateAndGetTotalProcessingTime()
        {
            int time = 0;
            Optional<? extends ProcessingRecipe> optional = this.getRecipe();
            if(optional.isPresent())
            {
                time = Math.max(time, optional.get().getProcessTime());
            }
            if(this.totalBakingTime != time)
            {
                this.totalBakingTime = time;
            }
            return this.totalBakingTime;
        }

        @Override
        public int getTotalProcessingTime()
        {
            return this.totalBakingTime;
        }

        @Override
        public int getProcessingTime()
        {
            return this.bakingTime;
        }

        @Override
        public void setProcessingTime(int time)
        {
            this.bakingTime = time;
        }

        @Override
        public void onCompleteProcess()
        {
            ItemStack stack = StoveBlockEntity.this.getItem(this.inputIndex);
            if(!stack.isEmpty())
            {
                Item remainingItem = stack.getItem().getCraftingRemainingItem();
                Optional<? extends ProcessingRecipe> optional = this.getRecipe();
                Level level = Objects.requireNonNull(StoveBlockEntity.this.getLevel());
                ItemStack result = optional.map(recipe -> recipe.getResultItem(level.registryAccess())).orElse(ItemStack.EMPTY);
                stack.shrink(1);
                if(!result.isEmpty())
                {
                    ItemStack copy = result.copy();
                    ItemStack outputStack = StoveBlockEntity.this.getItem(this.outputIndex);
                    if(outputStack.isEmpty())
                    {
                        StoveBlockEntity.this.setItem(this.outputIndex, copy);
                        return;
                    }
                    else if(ItemStack.matches(copy, outputStack) && outputStack.getCount() < outputStack.getMaxStackSize())
                    {
                        outputStack.grow(1);
                    }
                    if(remainingItem != null)
                    {
                        StoveBlockEntity.this.setItem(this.inputIndex, new ItemStack(remainingItem));
                    }
                }
            }
        }

        @Override
        public boolean canProcess()
        {
            if(!StoveBlockEntity.this.isPowered() || !StoveBlockEntity.this.enabled)
                return false;

            ItemStack stack = StoveBlockEntity.this.getItem(this.inputIndex);
            if(!stack.isEmpty())
            {
                Optional<? extends ProcessingRecipe> optional = this.getRecipe();
                if(optional.isEmpty())
                {
                    return false;
                }

                Level level = Objects.requireNonNull(StoveBlockEntity.this.getLevel());
                ItemStack result = optional.get().getResultItem(level.registryAccess());
                return this.canOutput(result);
            }
            return false;
        }

        private boolean canOutput(ItemStack result)
        {
            if(result.isEmpty())
                return false;
            ItemStack stack = StoveBlockEntity.this.getItem(this.outputIndex);
            return stack.isEmpty() || ItemStack.isSameItemSameTags(result, stack) && stack.getCount() < stack.getMaxStackSize();
        }

        private Optional<? extends ProcessingRecipe> getRecipe()
        {
            ItemStack stack = StoveBlockEntity.this.getItem(this.inputIndex);
            if(!stack.isEmpty())
            {
                Level level = StoveBlockEntity.this.getLevel();
                return this.inputRecipeCache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(level)).map(RecipeHolder::value);
            }
            return Optional.empty();
        }

        public void writeToTag(CompoundTag tag)
        {
            tag.putInt("CookingTime", this.bakingTime);
            tag.putInt("TotalCookingTime", this.totalBakingTime);
        }

        public void readFromTag(CompoundTag tag)
        {
            if(tag.contains("CookingTime", Tag.TAG_INT))
            {
                this.bakingTime = tag.getInt("CookingTime");
            }
            if(tag.contains("TotalCookingTime", Tag.TAG_INT))
            {
                this.totalBakingTime = tag.getInt("TotalCookingTime");
            }
        }
    }
}
