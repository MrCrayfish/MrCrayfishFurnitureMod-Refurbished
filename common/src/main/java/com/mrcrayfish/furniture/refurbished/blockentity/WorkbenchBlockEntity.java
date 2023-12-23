package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageWorkbench;
import com.mrcrayfish.furniture.refurbished.util.ItemHash;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class WorkbenchBlockEntity extends ElectricityModuleLootBlockEntity implements IWorkbench
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
            this.counts = Utils.countItems(true, List.of(Pair.of(null, this)));
            Map<Integer, Integer> map = new Int2IntOpenHashMap();
            for(Map.Entry<Item, Integer> entry : this.counts.entrySet())
            {
                map.put(Item.getId(entry.getKey()), entry.getValue());
            }
            // TODO only send changed and removed items
            ((WorkbenchMenu) player.containerMenu).updateItemCounts(map);
            Network.getPlay().sendToPlayer(() -> (ServerPlayer) player, new MessageWorkbench.ItemCounts(map));
        }
    }

    @Override
    public void performCraft(WorkbenchCraftingRecipe recipe)
    {
        Map<Item, Integer> items = this.getConsumedMaterialItems(recipe);
        if(items != null)
        {
            this.removeItems(items);
        }
    }

    @Override
    public boolean canCraft(WorkbenchCraftingRecipe recipe)
    {
        return this.getConsumedMaterialItems(recipe) != null;
    }

    @Override
    public Container getWorkbenchContainer()
    {
        return this;
    }

    @Nullable
    private Map<Item, Integer> getConsumedMaterialItems(WorkbenchCraftingRecipe recipe)
    {
        // Find the items we are going to consume. This has been made complicated since it accepts tags.
        Map<Item, Integer> counts = Utils.countItems(true, this.getSourceContainers());
        Map<Item, Integer> materials = new HashMap<>();
        outer: for(StackedIngredient material : recipe.getMaterials())
        {
            for(ItemStack stack : material.ingredient().getItems())
            {
                Integer count = counts.get(stack.getItem());
                if(count != null && count >= material.count())
                {
                    materials.put(stack.getItem(), material.count());
                    continue outer;
                }
            }
            return null;
        }
        return materials;
    }

    private boolean removeItems(Map<Item, Integer> items)
    {
        List<Pair<Container, Runnable>> transactions = new ArrayList<>();
        for(Pair<Direction, Container> pair : this.getSourceContainers())
        {
            Direction direction = pair.first();
            Container container = pair.second();
            Utils.getContainerSlots(container, direction).forEach(slot -> {
                ItemStack stack = container.getItem(slot);
                if(stack.isEmpty() || stack.isDamaged())
                    return;
                if(!Utils.canTakeFromContainer(container, slot, stack, direction))
                    return;
                Item item = stack.getItem();
                Integer count = items.get(item);
                if(count != null) {
                    if(stack.getCount() < count) {
                        count -= stack.getCount();
                        items.put(item, count);
                        transactions.add(Pair.of(container, () -> stack.setCount(0)));
                    } else {
                        Integer finalCount = count;
                        transactions.add(Pair.of(container, () -> stack.shrink(finalCount)));
                        items.remove(item);
                    }
                }
            });
        }

        if(items.isEmpty())
        {
            // We are good to go ahead and delete the items
            transactions.forEach(pair -> {
                pair.right().run();
                pair.left().setChanged();
            });
            return true;
        }
        return false;
    }

    private List<Pair<Direction, Container>> getSourceContainers()
    {
        List<Pair<Direction, Container>> list = new ArrayList<>();
        list.add(Pair.of(null, this));
        // TODO neighbours
        return list;
    }
}
