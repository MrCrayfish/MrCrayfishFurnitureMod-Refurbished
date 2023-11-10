package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nullable;

// TODO create pong game on computer. you can play against players in the server
// TODO create app to control lightswitches connected in the power network
// TODO create amazon like app to buy items and send to mailboxes

/**
 * Author: MrCrayfish
 */
public class ComputerBlockEntity extends ElectricityModuleBlockEntity implements MenuProvider
{
    public ComputerBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.COMPUTER.get(), pos, state);
    }

    public ComputerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public boolean isPowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(BlockStateProperties.POWERED) && state.getValue(BlockStateProperties.POWERED);
    }

    @Override
    public void setPowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(BlockStateProperties.POWERED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(BlockStateProperties.POWERED, powered), Block.UPDATE_ALL);
        }
    }

    @Override
    public Component getDisplayName()
    {
        return Utils.translation("container", "computer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player)
    {
        return null;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ComputerBlockEntity computer)
    {
        ElectricityModuleBlockEntity.serverTick(level, pos, state, computer);
    }
}
