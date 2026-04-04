package com.mrcrayfish.furniture.refurbished.client.gui.overlay;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * Author: MrCrayfish
 */
public interface IHudOverlay
{
    void draw(GuiGraphicsExtractor extractor, DeltaTracker deltaTracker);
}
