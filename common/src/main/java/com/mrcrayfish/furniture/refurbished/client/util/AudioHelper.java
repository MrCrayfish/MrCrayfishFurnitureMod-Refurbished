package com.mrcrayfish.furniture.refurbished.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;

/**
 * Author: MrCrayfish
 */
public class AudioHelper
{
    public static void playUISound(SoundEvent event, float pitch, float volume)
    {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(event, pitch, volume));
    }
}
