package com.mrcrayfish.furniture.refurbished.network;

import com.mrcrayfish.framework.api.FrameworkAPI;
import com.mrcrayfish.framework.api.network.FrameworkNetwork;
import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class Network
{
    public static final FrameworkNetwork PLAY = FrameworkAPI
            .createNetworkBuilder(new ResourceLocation(Constants.MOD_ID, "play"), 1)
            .build();

    public static void init() {}

    public static FrameworkNetwork getPlay()
    {
        return PLAY;
    }
}
