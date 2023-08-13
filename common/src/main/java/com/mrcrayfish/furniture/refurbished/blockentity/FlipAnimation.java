package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.util.Mth;

/**
 * Author: MrCrayfish
 */
public class FlipAnimation
{
    public static final int FLIP_ANIMATION_LENGTH = 15;

    private boolean flipping;
    private int animation;

    /**
     * Plays the animation
     */
    public void play()
    {
        this.flipping = true;
        this.animation = 0;
    }

    /**
     * @return True if the animation is currently playing. The animation is playing if it's
     * flipped and the animation time is less than the animation length.
     */
    public boolean isPlaying()
    {
        return this.flipping && this.animation < FLIP_ANIMATION_LENGTH;
    }

    /**
     * @return The current animation time
     */
    public float getTime(float partialTick)
    {
        return Mth.clamp(Mth.lerp(partialTick, this.animation, this.animation + 1), 0, FLIP_ANIMATION_LENGTH) / FLIP_ANIMATION_LENGTH;
    }

    /**
     * Handles updating the animation time and stopping when finished
     */
    public void tick()
    {
        if(this.flipping && this.animation < FLIP_ANIMATION_LENGTH)
        {
            this.animation++;
            if(this.animation == FLIP_ANIMATION_LENGTH)
            {
                this.flipping = false;
            }
        }
    }
}
