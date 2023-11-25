package com.mrcrayfish.furniture.refurbished.blockentity;

/**
 * Author: MrCrayfish
 */
public interface IHeatingSource
{
    /**
     * @return True if this heating source is currently processing the above cooking block
     */
    boolean isProcessing();

    /**
     * @return True if this heating source is outputting heat
     */
    boolean isHeating();
}
