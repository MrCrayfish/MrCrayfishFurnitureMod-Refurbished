package com.mrcrayfish.furniture.refurbished.client.audio;

import com.mrcrayfish.furniture.refurbished.blockentity.IAudioBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

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
        this.x = pos.getX() + 0.5;
        this.y = pos.getY() + 0.5;
        this.z = pos.getZ() + 0.5;
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
        if(this.playingSound != currentSound)
        {
            if(currentSound != null)
            {
                Minecraft.getInstance().getSoundManager().queueTickingSound(new AudioBlockSound(block));
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
