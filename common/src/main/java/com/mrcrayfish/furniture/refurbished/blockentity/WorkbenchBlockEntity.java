package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageWorkbench;
import com.mrcrayfish.furniture.refurbished.util.ItemHash;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class WorkbenchBlockEntity extends ElectricityModuleLootBlockEntity
{
    protected @Nullable Player currentUser;
    protected int updateTimer;
    protected Map<Item, Integer> counts = new Object2IntOpenCustomHashMap<>(ItemHash.INSTANCE);

    public WorkbenchBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.WORKBENCH.get(), pos, state);
    }

    public WorkbenchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 9);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof WorkbenchMenu;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "workbench");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new WorkbenchMenu(windowId, playerInventory, this);
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Override
    public void setPowered(boolean powered)
    {

    }

    @Override
    public void startOpen(Player player)
    {
        super.startOpen(player);
        this.setUser(player);
    }

    @Override
    public void stopOpen(Player player)
    {
        super.stopOpen(player);
        this.setUser(null);
    }

    public boolean isOccupied()
    {
        return this.getUser() != null;
    }

    @Nullable
    public Player getUser()
    {
        if(this.currentUser != null && this.currentUser.isAlive())
        {
            if(this.currentUser.containerMenu instanceof WorkbenchMenu menu && menu.getContainer() == this)
            {
                return this.currentUser;
            }
        }
        return null;
    }

    public void setUser(@Nullable Player player)
    {
        if(!this.isOccupied() || player == null)
        {
            this.currentUser = player;
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WorkbenchBlockEntity workbench)
    {
        ElectricityModuleLootBlockEntity.serverTick(level, pos, state, workbench);
        workbench.update();
    }

    private void update()
    {
        Player player = this.getUser();
        if(player != null && this.updateTimer++ % 4 == 0) // Only send every four ticks
        {
            this.counts = Utils.countItems(true, Pair.of(null, this));
            Map<Integer, Integer> map = new Int2IntOpenHashMap();
            for(Map.Entry<Item, Integer> entry : this.counts.entrySet())
            {
                map.put(Item.getId(entry.getKey()), entry.getValue());
            }
            // TODO only send changed and removed items
            Network.getPlay().sendToPlayer(() -> (ServerPlayer) player, new MessageWorkbench.ItemCounts(map));
        }
    }
}
