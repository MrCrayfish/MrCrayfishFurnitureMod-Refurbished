package com.mrcrayfish.furniture.refurbished.image;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.BitSet;
import java.util.function.Supplier;

/**
 * Represents an image made up of 16 colour palette in RGB format. Only the index of the colour is
 * stored for each pixel instead of the colour itself. Designed for low overhead when sending image
 * over a network.
 * <p>
 * Author: MrCrayfish
 */
public class PaletteImage
{
    public static final int[] COLOURS = {0x00000000, 0xFF100A0A, 0xFF585E53, 0xFFA5A589, 0xFFEAE5D1, 0xFFDEC666, 0xFF97AB50, 0xFF516B38, 0xFF25150F, 0xFF522732, 0xFF9C323C, 0xFFC4663D, 0xFFE48D80, 0xFF6392AF, 0xFF2C3C6A, 0xFF2C1D34};
    private static final int BITS_PER_INDEX = 4;
    public static final Codec<PaletteImage> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(PaletteImage::getId),
            Codec.INT.fieldOf("width").validate(val -> {
                if(val >= 1 && val <= 128)
                    return DataResult.success(val);
                return DataResult.error(() -> "Width must be between 1 and 128 (inclusive)");
            }).forGetter(PaletteImage::getWidth),
            Codec.INT.fieldOf("height").validate(val -> {
                if(val >= 1 && val <= 128)
                    return DataResult.success(val);
                return DataResult.error(() -> "Height must be between 1 and 128 (inclusive)");
            }).forGetter(PaletteImage::getHeight),
            ExtraCodecs.BIT_SET.fieldOf("data").forGetter(PaletteImage::getData)
        ).apply(builder, PaletteImage::new);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, PaletteImage> STREAM_CODEC = StreamCodec.of((buf, image) -> {
        buf.writeByte((byte) image.width);
        buf.writeByte((byte) image.height);
        buf.writeLongArray(image.bits.toLongArray());
    }, buf -> {
        int width = buf.readByte();
        int height = buf.readByte();
        long[] data = buf.readLongArray();
        return new PaletteImage(width, height, () -> BitSet.valueOf(data));
    });

    protected final ResourceLocation id;
    protected final int width;
    protected final int height;
    protected final BitSet bits;

    public PaletteImage(int width, int height)
    {
        this(width, height, () -> new BitSet(width * height));
    }

    private PaletteImage(ResourceLocation id, int width, int height, BitSet data)
    {
        this.id = id;
        this.width = width;
        this.height = height;
        this.bits = BitSet.valueOf(data.toLongArray());
    }

    public PaletteImage(int width, int height, Supplier<BitSet> supplier)
    {
        Preconditions.checkArgument(width >= 1 && width <= 128, "Width must be between 1 and 128 (inclusive)");
        Preconditions.checkArgument(height >= 1 && height <= 128, "Height must be between 1 and 128 (inclusive)");
        this.width = width;
        this.height = height;
        BitSet bits = new BitSet(width * height);
        bits.or(supplier.get());
        this.bits = bits;
        this.id = this.createImageId(bits);
    }

    /**
     * @return The id of this palette image
     */
    public ResourceLocation getId()
    {
        return this.id;
    }

    /**
     * @return The width of this palette image
     */
    public int getWidth()
    {
        return this.width;
    }

    /**
     * @return The height of this palette image
     */
    public int getHeight()
    {
        return this.height;
    }

    /**
     * @return The raw colour index data of this palette image
     */
    public BitSet getData()
    {
        return this.bits;
    }

    /**
     * Sets the colour index of the pixel at the given x and y position to the provided colour index.
     * An exception will be thrown if the x and y position is not within the bounds of the image or
     * if the colourIndex does not reference a colour in {@link #COLOURS}.
     *
     * @param x           the x position of the pixel
     * @param y           the y position of the pixel
     * @param colourIndex the index of a palette colour. See {@link #COLOURS}
     */
    @SuppressWarnings("PointlessArithmeticExpression")
    public void set(int x, int y, int colourIndex)
    {
        Preconditions.checkPositionIndex((y * this.width + x), this.width * this.height - 1);
        Preconditions.checkPositionIndex(colourIndex, COLOURS.length - 1);
        int bitIndex = (y * this.width + x) * BITS_PER_INDEX;
        this.bits.set(bitIndex + 0, (colourIndex & 1) != 0);
        this.bits.set(bitIndex + 1, (colourIndex & 2) != 0);
        this.bits.set(bitIndex + 2, (colourIndex & 4) != 0);
        this.bits.set(bitIndex + 3, (colourIndex & 8) != 0);
    }

    /**
     * Gets the colour index of the pixel at the given x and y position. An exception will be thrown
     * if the x and y position is not within the bounds of the image.
     *
     * @param x the x position of the pixel
     * @param y the y position of the pixel
     * @return The index of the colour in {@link #COLOURS}
     */
    @SuppressWarnings("PointlessArithmeticExpression")
    public int get(int x, int y)
    {
        Preconditions.checkArgument((y * this.width + x) < this.width * this.height);
        int bitIndex = (y * this.width + x) * BITS_PER_INDEX;
        int value = 0;
        value = setBit(value, 0, this.bits.get(bitIndex + 0));
        value = setBit(value, 1, this.bits.get(bitIndex + 1));
        value = setBit(value, 2, this.bits.get(bitIndex + 2));
        value = setBit(value, 3, this.bits.get(bitIndex + 3));
        return value;
    }

    /**
     * Utility method to change the state of the bit at the given offset in the provided value
     *
     * @param value  the integer value to modify
     * @param offset the offset of the bit
     * @param state  the new state of the bit; true for 1, false for 0
     * @return the new value
     */
    private static int setBit(int value, int offset, boolean state)
    {
        if(state) value |= (1 << offset);
        return value;
    }

    /**
     * Creates an id (as a resource location) to identify a palette image. This method accepts the
     * raw contents of a palette image to generate an identifier. Palette images with the same contents
     * will create the same id. This is used when loading as a dynamic texture.
     *
     * @param set the bit set contents of the palette image
     * @return a new resource location to identify the contents
     */
    private ResourceLocation createImageId(BitSet set)
    {
        return Utils.resource("palette_image_" + Util.sanitizeName(Integer.toHexString(set.hashCode()), ResourceLocation::validPathChar));
    }

    /**
     * @return Creates a copy of this palette image
     */
    public PaletteImage copy()
    {
        return new PaletteImage(this.width, this.height, () -> BitSet.valueOf(this.bits.toLongArray()));
    }
}
