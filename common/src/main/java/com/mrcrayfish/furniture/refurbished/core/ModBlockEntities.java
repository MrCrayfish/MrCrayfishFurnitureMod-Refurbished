package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.blockentity.CrateBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.DrawerBlockEntity;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModBlockEntities
{
    public static final RegistryEntry<BlockEntityType<DrawerBlockEntity>> DRAWER = RegistryEntry.blockEntity(Utils.resource("drawer"), DrawerBlockEntity::new, () -> new Block[]{
            // Valid blocks TODO maybe make dynamic?
            ModBlocks.DRAWER_OAK.get(),
            ModBlocks.DRAWER_SPRUCE.get(),
            ModBlocks.DRAWER_BIRCH.get(),
            ModBlocks.DRAWER_JUNGLE.get(),
            ModBlocks.DRAWER_ACACIA.get(),
            ModBlocks.DRAWER_DARK_OAK.get(),
            ModBlocks.DRAWER_MANGROVE.get(),
            ModBlocks.DRAWER_CHERRY.get(),
            ModBlocks.DRAWER_CRIMSON.get(),
            ModBlocks.DRAWER_WARPED.get()
    });

    public static final RegistryEntry<BlockEntityType<CrateBlockEntity>> CRATE = RegistryEntry.blockEntity(Utils.resource("crate"), CrateBlockEntity::new, () -> new Block[]{
            ModBlocks.CRATE_OAK.get(),
            ModBlocks.CRATE_SPRUCE.get(),
            ModBlocks.CRATE_BIRCH.get(),
            ModBlocks.CRATE_JUNGLE.get(),
            ModBlocks.CRATE_ACACIA.get(),
            ModBlocks.CRATE_DARK_OAK.get(),
            ModBlocks.CRATE_MANGROVE.get(),
            ModBlocks.CRATE_CHERRY.get(),
            ModBlocks.CRATE_CRIMSON.get(),
            ModBlocks.CRATE_WARPED.get()
    });
}
