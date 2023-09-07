package com.mrcrayfish.furniture.refurbished.electric;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncLink;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Class to handle creating links between electric nodes
 * <p>
 * Author: MrCrayfish
 */
public class LinkManager extends SavedData
{
    private static final String STORAGE_ID = Utils.resource("link_manager").toString();

    public static Optional<LinkManager> get(MinecraftServer server)
    {
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        if(level != null)
        {
            return Optional.of(level.getDataStorage().computeIfAbsent(tag -> new LinkManager(), LinkManager::new, STORAGE_ID));
        }
        return Optional.empty();
    }

    private final Map<UUID, BlockPos> lastNodeMap = new HashMap<>();

    @Override
    public CompoundTag save(CompoundTag tag)
    {
        return new CompoundTag();
    }

    @Override
    public boolean isDirty()
    {
        // Ensure this never saves
        return false;
    }

    /**
     * Called when a player interacts with an electric node. This handles creating a link between
     * two different electric nodes. On the first call to this method, it will simply store the
     * block position of the interacted node. On the second call, this method will attempt to
     * retrieve the electric node from block position stored on the first call and connect it to the
     * node just interacted.
     *
     * @param level          the level where the interacted happened
     * @param player         the player interacting with the node
     * @param interactedNode the node that was interacted
     */
    public void onNodeInteract(Level level, Player player, IElectricNode interactedNode)
    {
        // Prevent interaction if reached connection limit
        if(interactedNode.isConnectionLimit())
            return;

        if(!this.lastNodeMap.containsKey(player.getUUID()))
        {
            BlockPos pos = interactedNode.getPosition();
            this.lastNodeMap.put(player.getUUID(), pos);
            level.playSound(null, interactedNode.getPosition(), ModSounds.ITEM_WRENCH_SELECTED_NODE.get(), SoundSource.BLOCKS);
            Network.getPlay().sendToPlayer(() -> (ServerPlayer) player, new MessageSyncLink(pos));
            return;
        }

        // Attempt to connect the two nodes together
        BlockPos previousPos = this.lastNodeMap.remove(player.getUUID());
        IElectricNode lastNode = level.getBlockEntity(previousPos) instanceof IElectricNode node ? node : null;
        if(lastNode != null && lastNode != interactedNode)
        {
            double distance = lastNode.getPosition().getCenter().distanceTo(interactedNode.getPosition().getCenter());
            if(distance <= Config.SERVER.electricity.maximumLinkDistance.get())
            {
                lastNode.connectTo(interactedNode);
                level.playSound(null, interactedNode.getPosition(), ModSounds.ITEM_WRENCH_CONNECTED_LINK.get(), SoundSource.BLOCKS);
            }
        }

        // Update the client that the linking should stop
        Network.getPlay().sendToPlayer(() -> (ServerPlayer) player, new MessageSyncLink(null));
    }

    /**
     * Called at the end of a player tick. This method ensures that half completed links are reset
     * if the player is no longer holding the wrench in their main hand.
     *
     * @param player the ticking player
     */
    public void onPlayerTick(Player player)
    {
        UUID id = player.getUUID();
        if(this.lastNodeMap.containsKey(id) && !player.getMainHandItem().is(ModItems.WRENCH.get()))
        {
            this.lastNodeMap.remove(id);
            Network.getPlay().sendToPlayer(() -> (ServerPlayer) player, new MessageSyncLink(null));
        }
    }

    /**
     * Called when a player logged out of the server. This handler ensure that half completed links
     * are reset if the player is no longer in the server.
     *
     * @param player the player that logged out
     */
    public void onPlayerLoggedOut(Player player)
    {
        this.lastNodeMap.remove(player.getUUID());
    }
}
