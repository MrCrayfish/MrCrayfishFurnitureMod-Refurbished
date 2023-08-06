package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.platform.services.IEntityHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Author: MrCrayfish
 */
public class ForgeEntityHelper implements IEntityHelper
{
    @Override
    public boolean isFakePlayer(Player player)
    {
        return player instanceof FakePlayer;
    }
}
