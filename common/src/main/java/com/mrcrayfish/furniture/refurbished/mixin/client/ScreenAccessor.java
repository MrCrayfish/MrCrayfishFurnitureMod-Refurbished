package com.mrcrayfish.furniture.refurbished.mixin.client;

import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

/**
 * Author: MrCrayfish
 */
@Mixin(Screen.class)
public interface ScreenAccessor
{
    @Accessor(value = "children")
    List<GuiEventListener> getChildren();

    @Accessor(value = "narratables")
    List<NarratableEntry> getNarratables();
}
