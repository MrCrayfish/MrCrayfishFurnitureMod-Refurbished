package com.mrcrayfish.furniture.refurbished.electricity;

import com.mojang.serialization.Codec;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public interface IElectricityNode
{
    /**
     * @return The block position of the node in the level
     */
    BlockPos getNodePosition();

    /**
     * @return The level where the node is located
     */
    Level getNodeLevel();

    /**
     * @return The block entity that owns this node
     */
    BlockEntity getNodeOwner();

    /**
     * @return An AABB box used for interacting
     */
    AABB getNodeInteractBox();

    /**
     * @return True if this node is a source node. See {@link ISourceNode}
     */
    boolean isSourceNode();

    /**
     * @return True if this node is currently powered
     */
    boolean isNodePowered();

    /**
     * Sets the powered state for this node
     *
     * @param powered true for powered, false for unpowered
     */
    void setNodePowered(boolean powered);

    /**
     * @return A set containing all the connections of this node
     */
    Set<Connection> getNodeConnections();

    /**
     * Marks this node as receiving power with the given state. This is only applicable to module nodes.
     * @param state the power state
     */
    void setNodeReceivingPower(boolean state);

    /**
     * Gets the set of the block positions representing the position of the source nodes that are
     * powering this node. This is handled automatically, implementations should just return a
     * hash set.
     *
     * @return A set of the block positions representing the position of the source nodes
     */
    Set<BlockPos> getPowerSources();

    /**
     *
     * @return
     */
    default boolean isNodeInPowerableNetwork()
    {
        return !this.getPowerSources().isEmpty();
    }

    /**
     * @return True if the provided source block position is in the known power sources
     */
    default boolean isNodeInPowerableNetwork(BlockPos source)
    {
        return this.getPowerSources().contains(source);
    }

    /**
     * @return True if this node is receiving power. This is only applicable to module nodes.
     */
    boolean isNodeReceivingPower();

    /**
     * @return True if this node is not removed from the level
     */
    default boolean isNodeValid()
    {
        return !this.getNodeOwner().isRemoved();
    }

    /**
     * Called when the node is removed from the level. E.g. player breaks the block
     */
    default void onNodeDestroyed()
    {
        this.removeAllNodeConnections();
    }

    /**
     * Called when this node connects to another node
     *
     * @param other the node that was just connected to this node
     */
    default void onNodeConnectedTo(IElectricityNode other) {}

    /**
     * @return True if electricity can flow through this node
     */
    default boolean canPowerTraverseNode()
    {
        return true;
    }

    /**
     * @return Updates and returns a set containing all the connections of this node
     */
    default Set<Connection> updateAndGetNodeConnections()
    {
        this.updateNodeConnections();
        return this.getNodeConnections();
    }

    /**
     * Syncs the data of this node to tracking clients
     */
    default void syncDataToTrackingClients()
    {
        BlockEntity entity = this.getNodeOwner();
        BlockEntityHelper.sendCustomUpdate(entity, BlockEntity::getUpdateTag);
    }

    /**
     * Reads the node data from the given tag. Data is only read if the tag contains the specific keys.
     *
     * @param input the ValueInput for reading node data
     */
    default void readNodeNbt(ValueInput input)
    {
        // Hack to offset connections when using clone command. Does not support rotation
        BlockPos offset = BlockPos.ZERO;
        Optional<Long> nodePos = input.getLong("NodePos");
        if(nodePos.isPresent())
        {
            BlockPos current = this.getNodePosition();
            BlockPos previous = BlockPos.of(nodePos.get());
            if(!current.equals(previous))
            {
                offset = current.subtract(previous);
            }
        }

        BlockPos pos = this.getNodePosition();
        Set<Connection> connections = this.getNodeConnections();
        connections.clear();
        for(long node : input.listOrEmpty("Connections", Codec.LONG))
        {
            connections.add(Connection.of(pos, BlockPos.of(node).offset(offset)));
        }
    }

    /**
     * Writes the data of this node to the given compound tag
     *
     * @param output a ValueOutput to append the data to
     */
    default void writeNodeNbt(ValueOutput output)
    {
        Set<Connection> connections = this.getNodeConnections();
        ValueOutput.TypedOutputList<Long> nodes = output.list("Connections", Codec.LONG);
        connections.stream()
            .map(c -> c.getOtherPos(this.getNodePosition()))
            .filter(Objects::nonNull)
            .map(BlockPos::asLong)
            .mapToLong(Long::longValue)
            .forEach(nodes::add);
        output.putLong("NodePos", this.getNodePosition().asLong());
    }

    /**
     * Custom method for saving data to an item. This happens when the player picks the block with
     * nbt enabled. Removes node data that should not be included when writing to the item.
     *
     * @param stack the stack to write the data to
     */
    default void saveNodeNbtToItem(ItemStack stack, HolderLookup.Provider provider)
    {
        ProblemReporter reporter = new ProblemReporter.Collector();
        TagValueOutput output = TagValueOutput.createWithContext(reporter, provider);
        BlockEntity entity = this.getNodeOwner();
        entity.saveWithoutMetadata(output);
        output.discard("Connections"); // Don't include connections as this breaks node limits
        output.discard("NodePos"); // Don't include fix for connections since none are present anyway
        output.discard("Powered"); // Remove the powered property
        output.discard("Overloaded"); // Remove the overloaded property
        BlockItem.setBlockEntityData(stack, entity.getType(), output);
    }

    /**
     * Removes the given connection from the connections of this node. An update will also be
     * sent to tracking clients of the removed connection (in order to stop drawing it).
     *
     * @param connection the connection to remove
     */
    default void removeNodeConnection(Connection connection)
    {
        if(this.getNodeConnections().remove(connection))
        {
            this.syncDataToTrackingClients();
            this.getNodeOwner().setChanged();
        }
    }

    /**
     * Removes all the connections from this node and updates the nodes previously connected to this
     * node to also remove their connection to this node. This method does not sync the data to
     * tracking clients after removal.
     */
    default void removeAllNodeConnections()
    {
        Set<Connection> connections = this.getNodeConnections();
        connections.forEach(c -> {
            IElectricityNode node = c.getOtherNode(this);
            if(node != null && node.isNodeValid()) {
                node.removeNodeConnection(c);
                node.syncDataToTrackingClients();
            }
        });
        connections.clear();
        this.getNodeOwner().setChanged();
    }

    /**
     * Updates the connections of this node and removes connections that are not valid. This method
     * does not sync connections to tracking clients.
     */
    default void updateNodeConnections()
    {
        Level level = this.getNodeLevel();
        this.getNodeConnections().removeIf(c -> {
            if(!c.isConnected(level)) {
                IElectricityNode node = c.getOtherNode(this);
                if(node != null) {
                    node.removeNodeConnection(c);
                }
                return true;
            }
            return false;
        });
    }

    /**
     * @return The maximum links allowed to connect to/from this node
     */
    default int getNodeMaximumConnections()
    {
        return Config.SERVER.electricity.maximumLinksPerElectricityNode.get();
    }

    /**
     * @return True if this node connection count has reached the configured max links per node.
     */
    default boolean isNodeConnectionLimitReached()
    {
        return this.getNodeConnections().size() >= this.getNodeMaximumConnections();
    }

    /**
     * Attempts to connect this node to the given node. If this node is already connected to the
     * given node, it will simply be ignored. On a successful connection, the given node will also
     * be connected to this node and their connections will be synced to tracking clients. This
     * method ignores the configured maximum length allowed for a connection.
     *
     * @param other the node to connect to
     * @return True if successfully connected to the other node
     */
    default boolean connectToNode(IElectricityNode other)
    {
        BlockPos pos = this.getNodePosition();
        Set<Connection> connections = this.getNodeConnections();
        if(connections.add(Connection.of(pos, other.getNodePosition())))
        {
            other.connectToNode(this);
            this.onNodeConnectedTo(other);
            this.syncDataToTrackingClients();
            this.getNodeOwner().setChanged();
            return true;
        }
        return false;
    }

    /**
     * Determines if this node is connected to the given node
     *
     * @param node the node to test
     * @return True if connected
     */
    default boolean isConnectedToNode(IElectricityNode node)
    {
        Set<Connection> connections = this.getNodeConnections();
        return connections.contains(Connection.of(this.getNodePosition(), node.getNodePosition()));
    }

    /**
     * @return The interaction box of this node but offset by the block position
     */
    default AABB getPositionedNodeInteractBox()
    {
        return this.getNodeInteractBox().move(this.getNodePosition());
    }

    /**
     * Called at the start of the level tick, before block entities. Be careful, make sure to check
     * for client side if providing any custom behaviour.
     *
     * @param level the level the node is ticking in
     */
    default void earlyNodeTick(Level level) {}

    /**
     *
     * @param start
     * @return
     */
    static List<IElectricityNode> searchNodes(IElectricityNode start)
    {
        return searchNodes(start, Config.SERVER.electricity.maximumNodesInNetwork.get(), false, node -> true, node -> true);
    }

    /**
     * Performs a breadth first search beginning from the given start node. This search has been
     * modified and requires specifying a maximum depth. This means if the amount of required steps
     * to reach a node from the provided beginning node is more than maxDepth, the node will be ignored
     * and it's connections won't be searched.
     *
     * @param start           the node to begin the search from
     * @param searchLimit     the maximum amount of nodes that can be found
     * @param cancelAtLimit   if searching should stop once searchLimit has been reached
     * @param searchPredicate a predicate determining if a node can be searched
     * @return A list of all nodes that were searched and passed the match predicate
     */
    static List<IElectricityNode> searchNodes(IElectricityNode start, int searchLimit, boolean cancelAtLimit, Predicate<IElectricityNode> searchPredicate, Predicate<IElectricityNode> matchPredicate)
    {
        // TODO create separate version for general use without area limitation
        AABB box = ISourceNode.createPowerableZone(start.getNodeLevel(), start.getNodePosition());
        Set<IElectricityNode> found = new HashSet<>(List.of(start));
        // Creating the queue with an initial size equal to the maximum nodes in a network
        // prevents expensive calls to grow the capacity.
        Queue<IElectricityNode> queue = new ArrayDeque<>(searchLimit);
        queue.add(start);
        search: while(!queue.isEmpty())
        {
            IElectricityNode node = queue.poll();
            for(Connection connection : node.getNodeConnections())
            {
                IElectricityNode other = connection.getOtherNode(node);
                if(other == null || found.contains(other))
                    continue;

                BlockPos pos = other.getNodePosition();
                if(!box.contains(pos.getX(), pos.getY(), pos.getZ()))
                    continue;

                found.add(other);

                if(cancelAtLimit && found.size() >= searchLimit)
                    break search;

                if(!searchPredicate.test(other))
                    continue;

                queue.add(other);
            }
        }
        return found.stream().filter(matchPredicate).collect(Collectors.toList());
    }
}
