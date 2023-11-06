package com.mrcrayfish.furniture.refurbished.client.audio;

import com.mrcrayfish.furniture.refurbished.blockentity.IAudioBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class AudioManager
{
    private static AudioManager instance;

    public static AudioManager get()
    {
        if(instance == null)
        {
            instance = new AudioManager();
        }
        return instance;
    }

    private final Map<BlockPos, WeakReference<TickableSoundInstance>> playingSounds = new HashMap<>();

    private AudioManager() {}

    private boolean isPlayingAtPos(BlockPos pos)
    {
        WeakReference<TickableSoundInstance> soundRef = this.playingSounds.get(pos);
        if(soundRef != null)
        {
            TickableSoundInstance sound = soundRef.get();
            return sound != null && !sound.isStopped();
        }
        return false;
    }

    public void playAudioBlock(IAudioBlock block)
    {
        if(block.getSound() == null)
            return;

        if(!block.canPlayAudio())
            return;

        if(this.isPlayingAtPos(block.getAudioPosition()))
            return;

        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && mc.level != null)
        {
            BlockPos pos = block.getAudioPosition();
            Vec3 center = pos.getCenter();
            Vec3 eye = mc.player.getEyePosition();
            if(eye.distanceToSqr(center) > block.getAudioRadiusSqr())
                return;

            TickableSoundInstance sound = new AudioBlockSound(block);
            mc.getSoundManager().play(sound);
            this.playingSounds.put(pos, new WeakReference<>(sound));
        }
    }

    public void resetSounds()
    {
        this.playingSounds.clear();
    }

    public void updateSound(BlockPos pos, TickableSoundInstance sound)
    {
        this.playingSounds.put(pos, new WeakReference<>(sound));
    }
}
