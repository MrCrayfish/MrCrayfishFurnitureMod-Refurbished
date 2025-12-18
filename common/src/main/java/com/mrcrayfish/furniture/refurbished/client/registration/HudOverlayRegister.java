package com.mrcrayfish.furniture.refurbished.client.registration;

import com.mrcrayfish.furniture.refurbished.client.gui.overlay.IHudOverlay;
import net.minecraft.resources.Identifier;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface HudOverlayRegister
{
    void apply(Identifier id, IHudOverlay overlay);
}
