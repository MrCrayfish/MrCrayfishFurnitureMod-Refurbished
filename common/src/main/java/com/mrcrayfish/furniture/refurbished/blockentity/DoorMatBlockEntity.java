package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModDataComponents;
import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import com.mrcrayfish.furniture.refurbished.inventory.DoorMatMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;
import java.util.BitSet;

/**
 * Author: MrCrayfish
 */
public class DoorMatBlockEntity extends BlockEntity implements MenuProvider, IPaintable
{
    public static final int IMAGE_WIDTH = 14;
    public static final int IMAGE_HEIGHT = 10;

    private @Nullable Player paintingPlayer;
    private @Nullable PaletteImage image;
    private boolean finalised;

    public DoorMatBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.DOOR_MAT.get(), pos, state);
    }

    @Override
    public void setImage(PaletteImage image)
    {
        if(image.getWidth() == IMAGE_WIDTH && image.getHeight() == IMAGE_HEIGHT)
        {
            this.image = image;
            this.setChanged();
            if(this.level != null)
            {
                BlockEntityHelper.sendCustomUpdate(this, BlockEntity::getUpdateTag);
            }
        }
    }

    @Override
    public PaletteImage getImage()
    {
        return this.image;
    }

    @Override
    @Nullable
    public Player getPainter()
    {
        return this.paintingPlayer;
    }

    @Override
    public void setPainter(@Nullable Player player)
    {
        this.paintingPlayer = player;
    }

    @Override
    public boolean isEditable()
    {
        return !this.finalised;
    }

    @Override
    public void setEditable(boolean state)
    {
        // Only allow to be set true
        if(!this.finalised && !state)
        {
            this.finalised = true;
            this.setChanged();
        }
    }

    @Override
    public boolean isValid(Player player)
    {
        return Container.stillValidBlockEntity(this, player) && player.equals(this.getPainter());
    }

    @Override
    public Component getDisplayName()
    {
        return Utils.translation("container", "door_mat");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player)
    {
        return new DoorMatMenu(windowId, playerInventory, this);
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput input)
    {
        super.applyImplicitComponents(input);
        PaletteImage image = input.get(ModDataComponents.PALETTE_IMAGE.get());
        if(image != null)
        {
            this.setImage(image);
            this.finalised = true;
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder)
    {
        super.collectImplicitComponents(builder);
        if(this.image != null)
        {
            builder.set(ModDataComponents.PALETTE_IMAGE.get(), this.image.copy());
        }
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag)
    {
        tag.remove("Image");
        tag.remove("Finalised");
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.loadAdditional(tag, provider);
        if(tag.contains("Finalised", Tag.TAG_BYTE))
        {
            this.finalised = tag.getBoolean("Finalised");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.saveAdditional(tag, provider);
        tag.putBoolean("Finalised", this.finalised);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider)
    {
        return this.saveWithoutMetadata(provider);
    }
}
