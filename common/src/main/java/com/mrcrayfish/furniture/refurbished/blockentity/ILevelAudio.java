package com.mrcrayfish.furniture.refurbished.blockentity;

import it.unimi.dsi.fastutil.Hash;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/**
 * An interface to apply to block entities to play looping sounds. E.g. a machine engine sound.
 * It should be noted that these methods are only called on the client side, so consider this in
 * the implementation. It is also important that the methods do not contain any client side only code
 * since it is living in the block entity class. The audio can be played by calling
 * {@link com.mrcrayfish.furniture.refurbished.client.audio.AudioManager#playLevelAudio(ILevelAudio)}
 * <p>
 * Author: MrCrayfish
 */
public interface ILevelAudio
{
    double MAX_DISTANCE = Mth.square(5);

    /**
     * @return The sound event to play
     */
    SoundEvent getSound();

    /**
     * @return The sound source
     */
    SoundSource getSource();

    /**
     * @return The block position of the audio in the level
     */
    Vec3 getAudioPosition();

    /**
     * @return True if this audio can play and can continue to play after it's started
     */
    boolean canPlayAudio();

    /**
     * @return The radius the sound be heard by players. This value is returned as squared
     */
    default double getAudioRadiusSqr()
    {
        return MAX_DISTANCE;
    }

    /**
     * @return The volume of the audio
     */
    default float getAudioVolume()
    {
        return 1.0F;
    }

    /**
     * @return The pitch of the audio
     */
    default float getAudioPitch()
    {
        return 1.0F;
    }

    /**
     * @return The hash of this audio
     */
    int getAudioHash();

    /**
     *
     * @param other
     * @return
     */
    boolean isAudioEqual(ILevelAudio other);

    /**
     *
     */
    final class Strategy implements Hash.Strategy<ILevelAudio>
    {
        @Override
        public int hashCode(ILevelAudio o)
        {
            return o.getAudioHash();
        }

        @Override
        public boolean equals(ILevelAudio a, ILevelAudio b)
        {
            return a.isAudioEqual(b);
        }
    }
}
