package com.mrcrayfish.furniture.refurbished.electric;

import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
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

    private final Map<UUID, BlockPos> playerToNode = new HashMap<>();

    @Override
    public CompoundTag save(CompoundTag tag)
    {
        return new CompoundTag();
    }

    public void onNodeInteract(Level level, Player player, IElectricNode node, Vec3 location, BlockPos pos)
    {
        if(!this.playerToNode.containsKey(player.getUUID()))
        {
            this.playerToNode.put(player.getUUID(), pos);
            return;
        }

        BlockPos previousPos = this.playerToNode.remove(player.getUUID());
        IElectricNode firstNode = level.getBlockEntity(previousPos) instanceof IElectricNode n1 ? n1 : null;
        IElectricNode secondNode = level.getBlockEntity(pos) instanceof IElectricNode n2 ? n2 : null;
        if(firstNode != null && secondNode != null)
        {
            firstNode.connectTo(secondNode);
        }
    }
}
