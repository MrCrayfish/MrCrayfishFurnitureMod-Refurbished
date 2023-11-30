package com.mrcrayfish.furniture.refurbished.client.registration;

import com.mrcrayfish.furniture.refurbished.client.gui.overlay.IHudOverlay;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface HudOverlayRegister
{
    void apply(ResourceLocation id, IHudOverlay overlay);
}
