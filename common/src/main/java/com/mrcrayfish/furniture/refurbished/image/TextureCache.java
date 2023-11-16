package com.mrcrayfish.furniture.refurbished.image;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// TODO docs

/**
 * Keeps track of currently drawn PaletteImages and manages load/unload from memory
 * <p>
 * Author: MrCrayfish
 */
public class TextureCache
{
    private static final long EXPIRE_TIME = 10000;

    private static TextureCache instance;

    public static TextureCache get()
    {
        if(instance == null)
        {
            instance = new TextureCache();
        }
        return instance;
    }

    private Map<ResourceLocation, Entry> entries = new HashMap<>();

    private TextureCache() {}

    public void tick()
    {
        // Removes any cached images that have expired and releases the texture from memory
        this.entries.entrySet().removeIf(e -> {
            TextureCache.Entry entry = e.getValue();
            if(entry.isExpired()) {
                Minecraft.getInstance().getTextureManager().release(entry.getId());
                return true;
            }
            return false;
        });
    }

    @Nullable
    public ResourceLocation getOrCacheImage(Supplier<PaletteImage> supplier)
    {
        PaletteImage image = supplier.get();
        if(image != null)
        {
            Entry entry = this.entries.get(image.getId());
            if(entry == null)
            {
                entry = new Entry(image);
                this.entries.put(image.getId(), entry);
            }
            entry.ping();
            return image.getId();
        }
        return null;
    }

    private static class Entry
    {
        private final ResourceLocation id;
        private long lastDrawTime;

        public Entry(PaletteImage image)
        {
            this.id = image.getId();
            DynamicTexture texture = new DynamicTexture(convertToNativeImage(image));
            Minecraft.getInstance().getTextureManager().register(image.getId(), texture);
            this.lastDrawTime = Util.getMillis();
        }

        public ResourceLocation getId()
        {
            return this.id;
        }

        public void ping()
        {
            this.lastDrawTime = Util.getMillis();
        }

        public boolean isExpired()
        {
            return Util.getMillis() - this.lastDrawTime > EXPIRE_TIME;
        }
    }

    private static NativeImage convertToNativeImage(PaletteImage image)
    {
        NativeImage nativeImage = new NativeImage(image.width, image.height, false);
        for(int y = 0; y < image.height; y++)
        {
            for(int x = 0; x < image.width; x++)
            {
                int colour = PaletteImage.COLOURS[image.get(x, y)];
                colour = convertARGBToABGR(colour);
                nativeImage.setPixelRGBA(x, y, colour);
            }
        }
        return nativeImage;
    }

    private static int convertARGBToABGR(int value)
    {
        int alpha = FastColor.ARGB32.alpha(value);
        int red = FastColor.ARGB32.red(value);
        int green = FastColor.ARGB32.green(value);
        int blue = FastColor.ARGB32.blue(value);
        return FastColor.ABGR32.color(alpha, blue, green, red);
    }
}
