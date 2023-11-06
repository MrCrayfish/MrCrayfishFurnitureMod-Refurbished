package com.mrcrayfish.furniture.refurbished.client.audio;

import com.mrcrayfish.furniture.refurbished.blockentity.IAudioBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.lang.ref.WeakReference;

/**
 * Author: MrCrayfish
 */
public class AudioBlockSound extends AbstractTickableSoundInstance
{
    private final WeakReference<IAudioBlock> audioBlockRef;
    private final SoundEvent playingSound;
    private boolean switchSound;

    public AudioBlockSound(IAudioBlock block)
    {
        super(block.getSound(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.playingSound = block.getSound();
        this.audioBlockRef = new WeakReference<>(block);
        this.volume = block.getAudioVolume();
        this.pitch = block.getAudioPitch();
        this.looping = true;
        this.delay = 0;
        BlockPos pos = block.getAudioPosition();
        Vec3 offset = block.getAudioPositionOffset();
        this.x = pos.getX() + 0.5 + offset.x;
        this.y = pos.getY() + 0.5 + offset.y;
        this.z = pos.getZ() + 0.5 + offset.z;
    }

    @Override
    public boolean canStartSilent()
    {
        return true;
    }

    @Override
    public void tick()
    {
        IAudioBlock block = this.audioBlockRef.get();
        if(block == null || !block.canPlayAudio())
        {
            this.stop();
            return;
        }

        // If the sound changes, stop playing and queue new sound
        SoundEvent currentSound = block.getSound();
        if(this.playingSound != currentSound && !this.switchSound)
        {
            if(currentSound != null)
            {
                TickableSoundInstance sound = new AudioBlockSound(block);
                Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                AudioManager.get().updateSound(block.getAudioPosition(), sound);
            }
            this.switchSound = true;
        }

        this.pitch = block.getAudioPitch();

        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && !this.switchSound)
        {
            double distanceSquared = mc.player.getEyePosition().distanceToSqr(this.x, this.y, this.z);
            if(distanceSquared > block.getAudioRadiusSqr())
            {
                this.stop();
                return;
            }
            this.volume = 1.0F - (float) Mth.clamp(distanceSquared / block.getAudioRadiusSqr(), 0.0, 1.0);
            this.volume *= block.getAudioVolume();
        }
        else
        {
            this.stop();
        }
    }
}
