package com.mrcrayfish.furniture.refurbished.client.audio;

import com.mrcrayfish.furniture.refurbished.blockentity.ILevelAudio;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.lang.ref.WeakReference;

/**
 * Author: MrCrayfish
 */
public class AudioWorldSound extends AbstractTickableSoundInstance
{
    private final WeakReference<ILevelAudio> audioBlockRef;
    private final SoundEvent playingSound;
    private boolean switchSound;
    private boolean cancel;

    public AudioWorldSound(ILevelAudio audio)
    {
        super(audio.getSound(), audio.getSource(), SoundInstance.createUnseededRandom());
        this.playingSound = audio.getSound();
        this.audioBlockRef = new WeakReference<>(audio);
        this.volume = audio.getAudioVolume();
        this.pitch = audio.getAudioPitch();
        this.looping = true;
        this.delay = 0;
        Vec3 pos = audio.getAudioPosition();
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    @Override
    public boolean canStartSilent()
    {
        return true;
    }

    public void cancel()
    {
        this.cancel = true;
    }

    private boolean isOutOfRange()
    {
        ILevelAudio audio = this.audioBlockRef.get();
        if(audio != null)
        {
            Minecraft mc = Minecraft.getInstance();
            if(mc.player != null)
            {
                Vec3 pos = audio.getAudioPosition();
                Vec3 eye = mc.player.getEyePosition();
                return eye.distanceToSqr(pos) > audio.getAudioRadiusSqr();
            }
        }
        return true;
    }

    @Override
    public void tick()
    {
        ILevelAudio audio = this.audioBlockRef.get();
        if(audio == null || !audio.canPlayAudio() || this.isOutOfRange() || this.cancel)
        {
            this.stop();
            return;
        }

        // If the sound changes, stop playing and queue new sound
        SoundEvent currentSound = audio.getSound();
        if(this.playingSound != currentSound && !this.switchSound)
        {
            if(currentSound != null)
            {
                AudioWorldSound sound = new AudioWorldSound(audio);
                Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                AudioManager.get().updateSound(audio, sound);
            }
            this.switchSound = true;
        }

        this.pitch = audio.getAudioPitch();

        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && !this.switchSound)
        {
            double distanceSquared = mc.player.getEyePosition().distanceToSqr(this.x, this.y, this.z);
            if(distanceSquared > audio.getAudioRadiusSqr())
            {
                this.stop();
                return;
            }
            this.volume = 1.0F - (float) Mth.clamp(distanceSquared / audio.getAudioRadiusSqr(), 0.0, 1.0);
            this.volume *= audio.getAudioVolume();
        }
        else
        {
            this.stop();
        }

        // Update the position of the audio
        Vec3 pos = audio.getAudioPosition();
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }
}
