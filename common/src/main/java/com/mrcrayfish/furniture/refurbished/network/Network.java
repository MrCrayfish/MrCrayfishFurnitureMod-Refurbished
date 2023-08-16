package com.mrcrayfish.furniture.refurbished.network;

import com.mrcrayfish.framework.api.FrameworkAPI;
import com.mrcrayfish.framework.api.network.FrameworkNetwork;
import com.mrcrayfish.framework.api.network.MessageDirection;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.network.message.MessageFlipAnimation;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSendMail;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSetMailboxName;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncFluid;
import com.mrcrayfish.furniture.refurbished.network.message.MessageUpdateMailboxes;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class Network
{
    public static final FrameworkNetwork PLAY = FrameworkAPI
            .createNetworkBuilder(new ResourceLocation(Constants.MOD_ID, "play"), 1)
            .registerPlayMessage(MessageSyncFluid.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageFlipAnimation.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageSetMailboxName.class, MessageDirection.PLAY_SERVER_BOUND)
            .registerPlayMessage(MessageUpdateMailboxes.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageSendMail.class, MessageDirection.PLAY_SERVER_BOUND)
            .build();

    public static void init() {}

    public static FrameworkNetwork getPlay()
    {
        return PLAY;
    }
}
