package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.WorkbenchBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageWorkbench;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class WorkbenchBlockEntity extends ElectricityModuleLootBlockEntity implements IWorkbench
{
    public static final int DATA_POWERED = 0;
    public static final int RESULT_SLOT = 12;

    protected @Nullable Player currentUser;
    protected int updateTimer;
    protected int countsHash;

    protected final DataSlot selectedRecipe = DataSlot.standalone();
    protected final DataSlot searchNeighbours = DataSlot.standalone();
    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_POWERED, () -> this.isNodePowered() ? 1 : 0, value -> {});
    });

    public WorkbenchBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.WORKBENCH.get(), pos, state);
    }

    public WorkbenchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 13);
        this.selectedRecipe.set(-1);
        this.searchNeighbours.set(1);
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
        return new WorkbenchMenu(windowId, playerInventory, this, this.data);
    }

    public void writeMenuData(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(this.selectedRecipe.get());
        buffer.writeVarInt(this.searchNeighbours.get());
        buffer.writeVarInt(this.isNodePowered() ? 1 : 0);
    }

    @Override
    public boolean isNodePowered()
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(WorkbenchBlock.POWERED))
        {
            return state.getValue(WorkbenchBlock.POWERED);
        }
        return false;
    }

    @Override
    public void setNodePowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(WorkbenchBlock.POWERED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(WorkbenchBlock.POWERED, powered), Block.UPDATE_ALL);
        }
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

    @Override
    public boolean canTakeItem(Container container, int slotIndex, ItemStack stack)
    {
        return slotIndex != RESULT_SLOT;
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        return slotIndex != RESULT_SLOT;
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

    public static void sendCountsToUser(Level level, BlockPos pos, BlockState state, WorkbenchBlockEntity workbench)
    {
        ElectricityModuleLootBlockEntity.serverTick(level, pos, state, workbench);
        workbench.sendCountsToUser(false);
    }

    public void sendCountsToUser(boolean force)
    {
        Player player = this.getUser();
        if(player != null && (this.updateTimer++ % 4 == 0 || force)) // Only check every four ticks
        {
            Map<Item, Integer> newCounts = Utils.countItems(true, this.getSupplyContainers());
            int hash = newCounts.hashCode();
            if(hash == this.countsHash && !force) // Don't send update if same
                return;
            this.countsHash = hash;
            Map<Integer, Integer> map = new Int2IntOpenHashMap();
            for(Map.Entry<Item, Integer> entry : newCounts.entrySet())
            {
                map.put(Item.getId(entry.getKey()), entry.getValue());
            }
            // TODO only send changed and removed items
            ((WorkbenchMenu) player.containerMenu).updateItemCounts(map);
            Network.getPlay().sendToPlayer(() -> (ServerPlayer) player, new MessageWorkbench.ItemCounts(map));
        }
    }

    @Override
    public void performCraft(WorkbenchContructingRecipe recipe)
    {
        Map<Item, Integer> items = this.getConsumedMaterialItems(recipe);
        if(items != null)
        {
            this.removeItems(items);
        }
    }

    @Override
    public boolean canCraft(WorkbenchContructingRecipe recipe)
    {
        return this.isNodePowered() && this.getConsumedMaterialItems(recipe) != null;
    }

    @Override
    public DataSlot selectedRecipeDataSlot()
    {
        return this.selectedRecipe;
    }

    @Override
    public DataSlot searchNeighboursDataSlot()
    {
        return this.searchNeighbours;
    }

    @Override
    public Container getWorkbenchContainer()
    {
        return this;
    }

    @Override
    public ContainerLevelAccess createLevelAccess()
    {
        return ContainerLevelAccess.create(Objects.requireNonNull(this.level), this.worldPosition);
    }

    protected boolean shouldSearchNeighbours()
    {
        return this.searchNeighbours.get() != 0;
    }

    @Nullable
    private Map<Item, Integer> getConsumedMaterialItems(WorkbenchContructingRecipe recipe)
    {
        // Find the items we are going to consume. This has been made complicated since it accepts tags.
        Map<Item, Integer> counts = Utils.countItems(true, this.getSupplyContainers());
        Map<Item, Integer> materials = new HashMap<>();
        for(StackedIngredient material : recipe.getMaterials())
        {
            int remaining = material.count();
            for(ItemStack stack : material.ingredient().getItems())
            {
                Item item = stack.getItem();
                int count = counts.getOrDefault(item, 0);
                count -= materials.getOrDefault(item, 0);
                if(count > 0)
                {
                    if(count >= remaining)
                    {
                        materials.merge(item, remaining, Integer::sum);
                        remaining = 0;
                        break;
                    }
                    materials.merge(item, count, Integer::sum);
                    remaining -= count;
                }
            }
            if(remaining > 0)
            {
                return null;
            }
        }
        return materials;
    }

    private boolean removeItems(Map<Item, Integer> items)
    {
        List<Pair<Container, Runnable>> transactions = new ArrayList<>();
        for(Pair<Direction, Container> pair : this.getSupplyContainers())
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

    private List<Pair<Direction, Container>> getSupplyContainers()
    {
        List<Pair<Direction, Container>> list = new ArrayList<>();
        list.add(Pair.of(null, this));
        if(this.shouldSearchNeighbours())
        {
            Direction direction = this.getDirection();
            this.getContainer(direction).ifPresent(list::add);
            this.getContainer(direction.getOpposite()).ifPresent(list::add);
            this.getContainer(direction.getCounterClockWise()).ifPresent(list::add);
            this.getContainer(direction.getClockWise()).ifPresent(list::add);
        }
        Optional.ofNullable(this.getUser()).ifPresent(player -> list.add(Pair.of(null, player.getInventory())));
        return list;
    }

    private Optional<Pair<Direction, Container>> getContainer(Direction offset)
    {
        if(this.level != null)
        {
            BlockPos pos = this.worldPosition.relative(offset);
            if(this.level.getBlockEntity(pos) instanceof Container container)
            {
                BlockState state = this.level.getBlockState(pos);
                Block block = state.getBlock();
                if(container instanceof ChestBlockEntity && block instanceof ChestBlock) // TODO find a better way to do this
                {
                    container = ChestBlock.getContainer((ChestBlock) block, state, this.level, pos, true);
                }
                if(container != null)
                {
                    return Optional.of(Pair.of(offset.getOpposite(), container));
                }
            }
        }
        return Optional.empty();
    }

    private Direction getDirection()
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(BlockStateProperties.HORIZONTAL_FACING))
        {
            return state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        }
        return Direction.NORTH;
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("SelectedRecipe", Tag.TAG_INT))
        {
            this.selectedRecipe.set(tag.getInt("SelectedRecipe"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putInt("SelectedRecipe", this.selectedRecipe.get());
    }
}
