package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public interface IPaintable
{
    /**
     * Sets the palette image of this paintable
     *
     * @param image the new palette image
     */
    void setImage(PaletteImage image);

    /**
     * @return The palette image of this paintable or null if no image exists
     */
    @Nullable
    PaletteImage getImage();

    /**
     * @return The player currently editing this paintable or null
     */
    @Nullable
    Player getPainter();

    /**
     * Sets the player that is editing this paintable
     *
     * @param player a player or null
     */
    void setPainter(@Nullable Player player);

    /**
     * @return True if this paintable can be edited
     */
    boolean isEditable();

    /**
     * Sets the editable state of this paintable
     *
     * @param state true to allow editing, false to disallow
     */
    void setEditable(boolean state);

    /**
     * Determines if the doormat is still valid to paint
     *
     * @param player a player instance to test
     * @return True if the player can still paint
     */
    boolean isValid(Player player);
}
