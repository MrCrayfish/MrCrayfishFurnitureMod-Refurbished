package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.computer.Computer;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.electricity.IModuleNode;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageComputerState;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import org.jetbrains.annotations.Nullable;

// TODO create amazon like app to buy items and send to mailboxes
// TODO cmd like app

// TODO add a message to say unpowered

/**
 * Author: MrCrayfish
 */
public class ComputerBlockEntity extends ElectricityModuleBlockEntity implements MenuProvider, IComputer
{
    public static final int DATA_POWERED = 0;
    public static final int DATA_SYSTEM = 1;
    public static final int DATA_PROGRAM_1 = 2;
    public static final int DATA_PROGRAM_2 = 3;

    protected int systemData;
    protected int programData1;
    protected int programData2;
    protected Program currentProgram;
    protected @Nullable Player currentUser;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_POWERED, () -> isNodePowered() ? 1 : 0, value -> {});
        builder.add(DATA_SYSTEM, () -> systemData, value -> {});
        builder.add(DATA_PROGRAM_1, () -> programData1, value -> programData1 = value);
        builder.add(DATA_PROGRAM_2, () -> programData2, value -> programData2 = value);
    });

    public ComputerBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.COMPUTER.get(), pos, state);
    }

    public ComputerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public BlockPos getComputerPos()
    {
        return this.worldPosition;
    }

    @Override
    public void setUser(@Nullable Player player)
    {
        this.currentUser = player;
    }

    @Override
    @Nullable
    public Player getUser()
    {
        if(this.currentUser != null && (!this.currentUser.isAlive() || this.currentUser.isRemoved()))
        {
            this.currentUser = null;
        }
        return this.currentUser;
    }

    @Override
    @Nullable
    public Program getProgram()
    {
        return this.currentProgram;
    }

    @Nullable
    @Override
    public ComputerMenu getMenu()
    {
        Player player = this.getUser();
        if(player != null && player.containerMenu instanceof ComputerMenu menu && menu.getComputer() == this)
        {
            return menu;
        }
        return null;
    }

    @Override
    public boolean isServer()
    {
        return this.level != null && !this.level.isClientSide();
    }

    @Override
    public boolean isValid(Player player)
    {
        return this.isNodePowered() && Container.stillValidBlockEntity(this, player) && player.equals(this.getUser());
    }

    @Override
    public boolean isNodePowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(BlockStateProperties.POWERED) && state.getValue(BlockStateProperties.POWERED);
    }

    @Override
    public void setNodePowered(boolean powered)
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
        return new ComputerMenu(windowId, playerInventory, this.data, this);
    }

    public boolean isBeingUsed()
    {
        return this.currentUser != null && this.currentUser.isAlive() && this.currentUser.containerMenu instanceof ComputerMenu menu && this.currentUser.equals(menu.getComputer().getUser());
    }

    @Override
    public void launchProgram(@Nullable ResourceLocation id)
    {
        // If the id is null, it means to close the program
        if(id == null)
        {
            if(this.currentProgram != null)
            {
                this.currentProgram.onClose(true);
            }
            this.currentProgram = null;
            this.syncStateToCurrentUser();
            return;
        }

        // Don't open the program again if it's the current program
        if(this.currentProgram != null && this.currentProgram.getId().equals(id))
            return;

        // Create the program and sync the state if on the server
        Computer.get().createProgramInstance(id, this).ifPresent(program -> {
            this.currentProgram = program;
            this.syncStateToCurrentUser();
        });
    }

    public void syncStateToCurrentUser()
    {
        if(this.getUser() instanceof ServerPlayer player)
        {
            this.syncStateToPlayer(player);
        }
    }

    public void syncStateToPlayer(Player player)
    {
        ResourceLocation programId = this.currentProgram != null ? this.currentProgram.getId() : null;
        Network.getPlay().sendToPlayer(() -> (ServerPlayer) player, new MessageComputerState(this.worldPosition, programId));
    }

    private void tickProgram()
    {
        if(this.currentProgram != null)
        {
            this.currentProgram.tick();
        }
    }

    @Override
    public void moduleTick(Level level)
    {
        super.moduleTick(level);
        if(!level.isClientSide)
        {
            this.tickProgram();
        }
    }

    private void setPowerState(boolean powered)
    {
        this.systemData |= (powered ? (byte) 1 : (byte) 0);
    }

    private void setStartupTime(int time)
    {
        this.systemData |= (time << 16);
    }
}
