package com.mrcrayfish.furniture.refurbished.client.audio;

import com.mrcrayfish.furniture.refurbished.blockentity.ElectricityGeneratorBlockEntity;
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

    public void playElectricityGeneratorSound(BlockPos pos)
    {
        WeakReference<TickableSoundInstance> soundRef = this.playingSounds.get(pos);
        if(soundRef != null)
        {
            TickableSoundInstance sound = soundRef.get();
            if(sound != null && !sound.isStopped())
            {
                return;
            }
        }

        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && mc.level != null)
        {
            Vec3 center = pos.getCenter();
            Vec3 eye = mc.player.getEyePosition();
            if(eye.distanceToSqr(center) > ElectricityGeneratorSound.MAX_DISTANCE)
                return;

            if(mc.level.getBlockEntity(pos) instanceof ElectricityGeneratorBlockEntity generator)
            {
                TickableSoundInstance sound = new ElectricityGeneratorSound(pos, generator);
                mc.getSoundManager().play(sound);
                this.playingSounds.put(pos, new WeakReference<>(sound));
            }
        }
    }

    public void resetSounds()
    {
        this.playingSounds.clear();
    }
}
