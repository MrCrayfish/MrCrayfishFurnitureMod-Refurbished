package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.blockentity.IPaintable;
import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class ClientPaintable implements IPaintable
{
    private final Player player;

    public ClientPaintable(Player player)
    {
        this.player = player;
    }

    @Override
    public void setImage(PaletteImage image)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public PaletteImage getImage()
    {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public Player getPainter()
    {
        return this.player;
    }

    @Override
    public void setPainter(@Nullable Player player)
    {
        // Unused
    }

    @Override
    public boolean isEditable()
    {
        return true;
    }

    @Override
    public void setEditable(boolean state) {}
}
