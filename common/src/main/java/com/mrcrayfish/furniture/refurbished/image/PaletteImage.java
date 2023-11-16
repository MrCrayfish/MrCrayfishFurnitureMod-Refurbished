package com.mrcrayfish.furniture.refurbished.image;

import com.google.common.base.Preconditions;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.UUID;
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
    public static final int[] COLOURS = {0x00100A0A, 0xFF333025, 0xFF585E53, 0xFFA5A589, 0xFFEAE5D1, 0xFFDEC666, 0xFF97AB50, 0xFF516B38, 0xFF25150F, 0xFF522732, 0xFF9C323C, 0xFFC4663D, 0xFFE48D80, 0xFF6392AF, 0xFF2C3C6A, 0xFF2C1D34};
    private static final int BITS_PER_INDEX = 4;

    protected final ResourceLocation id;
    protected final int width;
    protected final int height;
    protected final BitSet bits;

    public PaletteImage(int width, int height)
    {
        this(width, height, () -> new BitSet(width * height));
    }

    public PaletteImage(int width, int height, Supplier<BitSet> supplier)
    {
        Preconditions.checkArgument(width >= 1 && width <= 128, "Width must be between 1 and 128 (inclusive)");
        Preconditions.checkArgument(height >= 1 && height <= 128, "Height must be between 1 and 128 (inclusive)");
        this.id = createUniqueId();
        this.width = width;
        this.height = height;
        this.bits = supplier.get();
        if(this.bits.size() < this.width * this.height)
        {
            throw new IllegalArgumentException("BitSet size does not contain enough bits for the image width and height");
        }
    }

    // TODO docs

    public ResourceLocation getId()
    {
        return this.id;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public BitSet getData()
    {
        return this.bits;
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    public void set(int x, int y, int colourIndex)
    {
        Preconditions.checkArgument((y * this.width + x) < this.width * this.height);
        Preconditions.checkArgument(colourIndex >= 0 && colourIndex < COLOURS.length);
        int bitIndex = (y * this.width + x) * BITS_PER_INDEX;
        this.bits.set(bitIndex + 0, (colourIndex & 1) != 0);
        this.bits.set(bitIndex + 1, (colourIndex & 2) != 0);
        this.bits.set(bitIndex + 2, (colourIndex & 4) != 0);
        this.bits.set(bitIndex + 3, (colourIndex & 8) != 0);
    }

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

    private static int setBit(int value, int offset, boolean state)
    {
        if(state) value |= (1 << offset);
        return value;
    }

    public void write(ByteBuffer buffer)
    {
        buffer.put((byte) this.width);
        buffer.put((byte) this.height);
        long[] data = this.bits.toLongArray();
        buffer.putInt(data.length);
        for(long d : data)
        {
            buffer.putLong(d);
        }
    }

    public static PaletteImage read(ByteBuffer buffer)
    {
        int width = buffer.get();
        int height = buffer.get();
        long[] data = new long[buffer.getInt()];
        for(int i = 0; i < data.length; i++)
        {
            data[i] = buffer.getLong();
        }
        return new PaletteImage(width, height, () -> BitSet.valueOf(data));
    }

    public ResourceLocation createUniqueId()
    {
        return new ResourceLocation(Util.sanitizeName(UUID.randomUUID().toString(), ResourceLocation::validPathChar));
    }
}
