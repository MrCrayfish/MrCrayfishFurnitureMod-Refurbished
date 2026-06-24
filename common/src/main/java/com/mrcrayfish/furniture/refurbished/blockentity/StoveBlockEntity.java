package com.mrcrayfish.furniture.refurbished.blockentity;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mrcrayfish.furniture.refurbished.block.StoveBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.IContainerHolder;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Util;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.Nameable;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("UnstableApiUsage")
public class StoveBlockEntity extends ElectricityModuleLootBlockEntity implements IProcessingBlock, IHeatingSource, IPowerSwitch, IHomeControlDevice, Nameable, StackedContentsCompatible
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

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_POWERED, () -> this.isNodePowered() ? 1 : 0, value -> {});
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
        if(!this.isNodePowered() || !this.enabled) return false;
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
        stove.processTick();
        stove.spaces.forEach(IProcessingBlock::processTick);
        if(stove.sync)
        {
            BlockEntityHelper.sendCustomUpdate(stove, BlockEntity::getUpdateTag);
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
        return Services.MENU.createStoveMenu(windowId, playerInventory, this, this.data);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof IContainerHolder holder && holder.container() == this;
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        Vec3 center = Vec3.atCenterOf(this.worldPosition).relative(state.getValue(StoveBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, center.x, center.y, center.z, ModSounds.BLOCK_STOVE_OPEN.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.getRandom().nextFloat());
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        Vec3 center = Vec3.atCenterOf(this.worldPosition).relative(state.getValue(StoveBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, center.x, center.y, center.z, ModSounds.BLOCK_STOVE_CLOSE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.getRandom().nextFloat());
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
    public CompoundTag getUpdateTag(HolderLookup.Provider provider)
    {
        return this.saveWithoutMetadata(provider);
    }

    @Override
    public void loadAdditional(ValueInput input)
    {
        super.loadAdditional(input);
        input.read("Processing", Codec.BOOL).ifPresent(value -> this.processing = value);
        input.getInt("ProcessingTime").ifPresent(value -> this.processingTime = value);
        input.getInt("TotalProcessingTime").ifPresent(value -> this.totalProcessingTime = value);
        input.read("Enabled", Codec.BOOL).ifPresent(value -> this.enabled = value);
        this.readCookingSpaces(input);
    }

    @Override
    protected void saveAdditional(ValueOutput output)
    {
        super.saveAdditional(output);
        output.store("Processing", Codec.BOOL, this.processing);
        output.putInt("TotalProcessingTime", this.totalProcessingTime);
        output.putInt("ProcessingTime", this.processingTime);
        output.store("Enabled", Codec.BOOL, this.enabled);
        this.writeCookingSpaces(output);
    }

    @Override
    public boolean isNodePowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(StoveBlock.POWERED) && state.getValue(StoveBlock.POWERED);
    }

    @Override
    public void setNodePowered(boolean powered)
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
    public void togglePower()
    {
        this.enabled = !this.enabled;
        this.level.setBlock(this.worldPosition, this.getBlockState().setValue(StoveBlock.LIT, this.isNodePowered() && this.enabled), Block.UPDATE_ALL);
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
        BlockEntityHelper.sendCustomUpdate(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void setDeviceState(boolean enabled)
    {
        this.enabled = enabled;
        this.setChanged();
        BlockEntityHelper.sendCustomUpdate(this, BlockEntity::getUpdateTag);
    }

    @Override
    public Component getDeviceName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomName();
        }
        return this.getDefaultName();
    }

    /**
     * Writes the Cooking Spaces to a ValueOutput.
     *
     * @param output the value output to save the data to
     */
    private void writeCookingSpaces(ValueOutput output)
    {
        ValueOutput.ValueOutputList list = output.childrenList("CookingSpaces");
        for(int i = 0; i < this.spaces.size(); i++)
        {
            ValueOutput spaceOutput = list.addChild();
            spaceOutput.putInt("Position", i);
            this.spaces.get(i).writeToOutput(spaceOutput);
        }
        if(list.isEmpty())
        {
            output.discard("CookingSpaces");
        }
    }

    /**
     * Reads the Cooking Spaces from a ValueInput. This method has been designed to accept partial data.
     * This means it can read the data from one cooking space and not reset/affect the other spaces.
     * This use case is used for reading sync data, while still being a general method to read all
     * the cooking spaces.
     *
     * @param input the value input to read data from
     */
    private void readCookingSpaces(ValueInput input)
    {
        input.childrenList("CookingSpaces").ifPresent(list -> {
            list.forEach(spaceInput -> {
                int position = spaceInput.getIntOr("Position", -1);
                if(position >= 0 && position < this.spaces.size()) {
                    this.spaces.get(position).readFromInput(spaceInput);
                }
            });
        });
    }

    @Override
    public void fillStackedContents(StackedItemContents contents)
    {
        for(ItemStack stack : this.items)
        {
            contents.accountStack(stack);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state)
    {
        super.preRemoveSideEffects(pos, state);
        this.onDestroyed(pos);
    }

    protected class CookingSpace implements IProcessingBlock
    {
        private final int inputIndex;
        private final int outputIndex;
        private final RecipeManager.CachedCheck<SingleRecipeInput, ? extends ProcessingRecipe> inputRecipeCache;
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
                time = Math.max(time, optional.get().getTime());
            }
            if(this.totalBakingTime != time)
            {
                this.totalBakingTime = time;
                StoveBlockEntity.this.setChanged();
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
            StoveBlockEntity.this.setChanged();
        }

        @Override
        public void onCompleteProcess()
        {
            ItemStack stack = StoveBlockEntity.this.getItem(this.inputIndex);
            if(!stack.isEmpty())
            {
                Optional<? extends ProcessingRecipe> optional = this.getRecipe();
                ItemStack result = optional.map(recipe -> recipe.assemble(new SingleRecipeInput(stack))).orElse(ItemStack.EMPTY);
                stack.shrink(1);
                if(!result.isEmpty())
                {
                    ItemStack copy = result.copy();
                    ItemStack outputStack = StoveBlockEntity.this.getItem(this.outputIndex);
                    if(outputStack.isEmpty())
                    {
                        StoveBlockEntity.this.setItem(this.outputIndex, copy);
                    }
                    else if(ItemStack.isSameItemSameComponents(copy, outputStack) && outputStack.getCount() + copy.getCount() <= outputStack.getMaxStackSize())
                    {
                        outputStack.grow(copy.getCount());
                        StoveBlockEntity.this.setChanged();
                    }
                    ItemStackTemplate remainder = stack.getMaxStackSize() == 1 ? stack.getItem().getCraftingRemainder() : null;
                    if(remainder != null)
                    {
                        if(stack.isEmpty())
                        {
                            StoveBlockEntity.this.setItem(this.inputIndex, remainder.create());
                        }
                        else
                        {
                            // Fallback and drop the item into the world
                            Vec3 pos = Vec3.atCenterOf(StoveBlockEntity.this.getBlockPos()).add(0, 0.5, 0);
                            Containers.dropItemStack(StoveBlockEntity.this.level, pos.x, pos.y, pos.z, remainder.create());
                        }
                    }
                }
            }
        }

        @Override
        public boolean canProcess()
        {
            if(!StoveBlockEntity.this.isNodePowered() || !StoveBlockEntity.this.enabled)
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
                ItemStack result = optional.get().assemble(new SingleRecipeInput(stack));
                return this.canOutput(result);
            }
            return false;
        }

        private boolean canOutput(ItemStack result)
        {
            if(result.isEmpty())
                return false;
            ItemStack stack = StoveBlockEntity.this.getItem(this.outputIndex);
            return stack.isEmpty() || ItemStack.isSameItemSameComponents(result, stack) && stack.getCount() + result.getCount() <= stack.getMaxStackSize();
        }

        private Optional<? extends ProcessingRecipe> getRecipe()
        {
            ItemStack stack = StoveBlockEntity.this.getItem(this.inputIndex);
            if(!stack.isEmpty() && StoveBlockEntity.this.getLevel() instanceof ServerLevel serverLevel)
            {
                return this.inputRecipeCache.getRecipeFor(new SingleRecipeInput(stack), serverLevel).map(RecipeHolder::value);
            }
            return Optional.empty();
        }

        public void writeToOutput(ValueOutput output)
        {
            output.putInt("CookingTime", this.bakingTime);
            output.putInt("TotalCookingTime", this.totalBakingTime);
        }

        public void readFromInput(ValueInput input)
        {
            input.getInt("CookingTime").ifPresent(value -> this.bakingTime = value);
            input.getInt("TotalCookingTime").ifPresent(value -> this.totalBakingTime = value);
        }
    }
}
