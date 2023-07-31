package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class FreezerBlockEntity extends ProcessingBlockEntity
{
    public static final int[] INPUT_SLOTS = new int[]{0};
    public static final int[] OUTPUT_SLOTS = new int[]{1};
    public static final int DATA_PROCESS_TIME = 0;
    public static final int DATA_MAX_PROCESS_TIME = 1;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_PROCESS_TIME, () -> processTime, value -> processTime = value);
        builder.add(DATA_MAX_PROCESS_TIME, () -> maxProcessTime, value -> maxProcessTime = value);
    });

    public FreezerBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.FREEZER.get(), pos, state, 2, ModRecipeTypes.FREEZER_SOLIDIFYING.get());
    }

    public FreezerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 2, ModRecipeTypes.FREEZER_SOLIDIFYING.get());
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "freezer");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new FreezerMenu(ModMenuTypes.FREEZER.get(), windowId, playerInventory, this, this.data);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof FreezerMenu freezer && freezer.getContainer() == this;
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return direction == Direction.DOWN ? OUTPUT_SLOTS : INPUT_SLOTS;
    }

    @Override
    public int[] getInputSlots()
    {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots()
    {
        return OUTPUT_SLOTS;
    }

    @Override
    public int[] getEnergySlots()
    {
        return NO_SLOTS;
    }
}
