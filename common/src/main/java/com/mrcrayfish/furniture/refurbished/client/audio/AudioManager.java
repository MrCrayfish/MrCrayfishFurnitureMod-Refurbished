package com.mrcrayfish.furniture.refurbished.client.audio;

import com.mrcrayfish.furniture.refurbished.blockentity.ILevelAudio;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

import java.lang.ref.WeakReference;
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

    private final Map<ILevelAudio, WeakReference<AudioWorldSound>> playingSounds = new Object2ObjectOpenCustomHashMap<>(new ILevelAudio.Strategy());

    private AudioManager() {}

    public void update()
    {
        // Remove invalid or stopped sounds
        this.playingSounds.entrySet().removeIf(entry -> {
            WeakReference<AudioWorldSound> soundRef = entry.getValue();
            AudioWorldSound sound = soundRef.get();
            return sound == null || sound.isStopped();
        });
    }

    /**
     * @return True if the given audio is currently playing
     */
    private boolean isPlaying(ILevelAudio audio)
    {
        WeakReference<AudioWorldSound> soundRef = this.playingSounds.get(audio);
        if(soundRef != null)
        {
            AudioWorldSound sound = soundRef.get();
            return sound != null && !sound.isStopped();
        }
        return false;
    }

    /**
     * Attempts to start playing the given level audio. The audio will not play if
     * {@link ILevelAudio#getSound()} returns a null sound event, if {@link ILevelAudio#canPlayAudio()}
     * returns false, if the position of audio is further than it's maximum radius according to
     * {@link ILevelAudio#getAudioRadiusSqr()}, or if it's already playing.
     *
     * @param audio the audio to play
     */
    public void playLevelAudio(ILevelAudio audio)
    {
        if(audio.getSound() == null)
            return;

        if(!audio.canPlayAudio())
            return;

        if(this.isPlaying(audio))
            return;

        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && mc.level != null)
        {
            Vec3 pos = audio.getAudioPosition();
            Vec3 eye = mc.player.getEyePosition();
            if(eye.distanceToSqr(pos) > audio.getAudioRadiusSqr())
                return;

            AudioWorldSound sound = new AudioWorldSound(audio);
            mc.getSoundManager().play(sound);
            this.playingSounds.put(audio, new WeakReference<>(sound));
        }
    }

    /**
     * Resets and stops all playing level audio sounds
     */
    public void resetSounds()
    {
        this.playingSounds.forEach((audio, soundRef) -> {
            AudioWorldSound sound = soundRef.get();
            if(sound != null) {
                sound.cancel();
            }
        });
        this.playingSounds.clear();
    }

    /**
     * Updates the level audio with a new sound instance. The audio must already
     * be playing for it be updated.
     *
     * @param audio the level audio to update
     * @param sound the new sound instance
     */
    public void updateSound(ILevelAudio audio, AudioWorldSound sound)
    {
        if(this.playingSounds.containsKey(audio))
        {
            this.playingSounds.put(audio, new WeakReference<>(sound));
        }
    }
}
