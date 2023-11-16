package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class DoorMatBlockEntity extends BlockEntity implements Supplier<PaletteImage>
{
    public static final int IMAGE_WIDTH = 14;
    public static final int IMAGE_HEIGHT = 10;
    private @Nullable PaletteImage image;

    public DoorMatBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.DOOR_MAT.get(), pos, state);
        this.image = new PaletteImage(IMAGE_WIDTH, IMAGE_HEIGHT);
        this.image.set(3, 2, 5);
        this.image.set(10, 2, 5);
        this.image.set(2, 5, 2);
        this.image.set(3, 6, 3);
        this.image.set(4, 7, 4);
        this.image.set(5, 7, 6);
        this.image.set(6, 7, 7);
        this.image.set(7, 7, 8);
        this.image.set(8, 7, 9);
        this.image.set(9, 7, 10);
        this.image.set(10, 6, 11);
        this.image.set(11, 5, 12);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("Image", Tag.TAG_LONG_ARRAY))
        {
            long[] data = tag.getLongArray("Image");
            BitSet bits = BitSet.valueOf(data);
            if(bits.size() == IMAGE_WIDTH * IMAGE_HEIGHT)
            {
                this.image = new PaletteImage(IMAGE_WIDTH, IMAGE_HEIGHT, () -> bits);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        if(this.image != null)
        {
            long[] data = this.image.getData().toLongArray();
            tag.putLongArray("Image", data);
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }

    @Override
    public PaletteImage get()
    {
        return this.image;
    }
}
