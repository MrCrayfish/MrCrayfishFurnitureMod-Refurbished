package com.mrcrayfish.furniture.refurbished.image;

import com.mojang.blaze3d.platform.NativeImage;
import com.mrcrayfish.furniture.refurbished.blockentity.IPaintable;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

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

    private final Map<ResourceLocation, Entry> entries = new HashMap<>();

    private TextureCache() {}

    /**
     * Ticks the texture cache. This handles removing and releasing expired dynamic textures
     */
    public void tick()
    {
        // Removes any cached images that have expired and releases the texture from memory
        this.entries.entrySet().removeIf(e -> {
            TextureCache.Entry entry = e.getValue();
            if(entry.isExpired()) {
                entry.release();
                return true;
            }
            return false;
        });
    }

    /**
     * Gets the texture identifier of the supplied palette image or if palette image has not been
     * registered, it will be converted into a dynamic texture and registered into the texture
     * manager before returning the identifier.
     *
     * @param paintable a supplier returning a palette image
     * @return a texture id linking to a registered dynamic texture
     */
    @Nullable
    public ResourceLocation getOrCacheImage(IPaintable paintable)
    {
        PaletteImage image = paintable.getImage();
        if(image != null)
        {
            Entry entry = this.entries.get(image.getId());
            if(entry == null || entry.isExpired())
            {
                if(entry != null)
                {
                    entry.release();
                }
                entry = new Entry(image);
                this.entries.put(image.getId(), entry);
            }
            entry.ping();
            return image.getId();
        }
        return null;
    }

    /**
     * Gets the RenderType used for drawing the provided paintable to a buffer. The render
     * type automatically has the texture applied, just simply call {@link net.minecraft.client.renderer.MultiBufferSource#getBuffer(RenderType)}
     * to get the vertex consumer. This method will also register the paintable
     * if it doesn't exist in the texture cache. If the paintable doesn't have an
     * image, this method will simply return null.
     *
     * @param paintable a paintable instance with an image
     * @return the render type for the paintable or null if paintable is missing image
     */
    @Nullable
    public RenderType getRenderType(IPaintable paintable)
    {
        ResourceLocation id = this.getOrCacheImage(paintable);
        Entry entry = this.entries.get(id);
        if(entry != null && !entry.isExpired())
        {
            return entry.getRenderType();
        }
        return null;
    }

    /**
     * Gets the abstract texture for an image in the cache with the given id.
     *
     * @param id the id of the entry
     * @return the abstract texture of the cached entry or missing texture if not found
     */
    public AbstractTexture getTexture(ResourceLocation id)
    {
        Entry entry = this.entries.get(id);
        if(entry != null && !entry.isExpired())
        {
            return entry.texture;
        }
        // Otherwise return missing texture
        return Minecraft.getInstance().getTextureManager().getTexture(MissingTextureAtlasSprite.getLocation());
    }

    private static class Entry
    {
        private final ResourceLocation id;
        private final DynamicTexture texture;
        private final RenderType renderType;
        private long lastDrawTime;
        private boolean expired;

        public Entry(PaletteImage image)
        {
            this.id = image.getId();
            this.texture = new DynamicTexture(convertToNativeImage(image));
            Minecraft.getInstance().getTextureManager().register(image.getId(), this.texture);
            this.lastDrawTime = Util.getMillis();
            this.renderType = ClientServices.PLATFORM.createPaletteImageRenderType(this.id);
        }

        /**
         * @return The identifier of this entry, based on the contents of the palette image
         */
        public ResourceLocation getId()
        {
            return this.id;
        }

        /**
         * @return The render type to use to draw this image to a buffer
         */
        public RenderType getRenderType()
        {
            return this.renderType;
        }

        /**
         * Updates the last draw time to the current time.
         */
        public void ping()
        {
            this.lastDrawTime = Util.getMillis();
        }

        /**
         * @return True if this entry last draw time is greater than {@link #EXPIRE_TIME}
         */
        public boolean isExpired()
        {
            return this.expired || Util.getMillis() - this.lastDrawTime > EXPIRE_TIME;
        }

        public void release()
        {
            if(!this.expired)
            {
                Minecraft.getInstance().getTextureManager().release(this.id);
                this.expired = true;
            }
        }
    }

    /**
     * Converts a PaletteImage into a NativeImage. This allows it to be loaded into a
     * {@link DynamicTexture} for custom rendering.
     *
     * @param image the palette image to convert
     * @return a native image of the palette image
     */
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

    /**
     * Converts an ARGB format int to an ABGR format int
     *
     * @param value an argb integer
     * @return the argb integer in abgr format
     */
    private static int convertARGBToABGR(int value)
    {
        int alpha = FastColor.ARGB32.alpha(value);
        int red = FastColor.ARGB32.red(value);
        int green = FastColor.ARGB32.green(value);
        int blue = FastColor.ARGB32.blue(value);
        return FastColor.ARGB32.color(alpha, blue, green, red);
    }
}
