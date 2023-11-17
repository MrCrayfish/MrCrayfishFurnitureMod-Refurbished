package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public interface IPaintable extends Supplier<PaletteImage>
{
    void setImage(PaletteImage image);

    PaletteImage getImage();

    @Nullable
    Player getPainter();

    void setPainter(@Nullable Player player);

    boolean isEditable();

    void setEditable(boolean state);

    @Override
    default PaletteImage get()
    {
        return this.getImage();
    }
}
