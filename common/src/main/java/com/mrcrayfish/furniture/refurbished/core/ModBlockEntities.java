package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.blockentity.CrateBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.DrawerBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenDrawerBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.platform.Services;
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

    public static final RegistryEntry<BlockEntityType<KitchenDrawerBlockEntity>> KITCHEN_DRAWER = RegistryEntry.blockEntity(Utils.resource("kitchen_drawer"), KitchenDrawerBlockEntity::new, () -> new Block[]{
            ModBlocks.KITCHEN_DRAWER_OAK.get(),
            ModBlocks.KITCHEN_DRAWER_SPRUCE.get(),
            ModBlocks.KITCHEN_DRAWER_BIRCH.get(),
            ModBlocks.KITCHEN_DRAWER_JUNGLE.get(),
            ModBlocks.KITCHEN_DRAWER_ACACIA.get(),
            ModBlocks.KITCHEN_DRAWER_DARK_OAK.get(),
            ModBlocks.KITCHEN_DRAWER_MANGROVE.get(),
            ModBlocks.KITCHEN_DRAWER_CHERRY.get(),
            ModBlocks.KITCHEN_DRAWER_CRIMSON.get(),
            ModBlocks.KITCHEN_DRAWER_WARPED.get(),
            ModBlocks.KITCHEN_DRAWER_WHITE.get(),
            ModBlocks.KITCHEN_DRAWER_ORANGE.get(),
            ModBlocks.KITCHEN_DRAWER_MAGENTA.get(),
            ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE.get(),
            ModBlocks.KITCHEN_DRAWER_YELLOW.get(),
            ModBlocks.KITCHEN_DRAWER_LIME.get(),
            ModBlocks.KITCHEN_DRAWER_PINK.get(),
            ModBlocks.KITCHEN_DRAWER_GRAY.get(),
            ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY.get(),
            ModBlocks.KITCHEN_DRAWER_CYAN.get(),
            ModBlocks.KITCHEN_DRAWER_PURPLE.get(),
            ModBlocks.KITCHEN_DRAWER_BLUE.get(),
            ModBlocks.KITCHEN_DRAWER_BROWN.get(),
            ModBlocks.KITCHEN_DRAWER_GREEN.get(),
            ModBlocks.KITCHEN_DRAWER_RED.get(),
            ModBlocks.KITCHEN_DRAWER_BLACK.get(),
    });

    public static final RegistryEntry<BlockEntityType<KitchenSinkBlockEntity>> KITCHEN_SINK = RegistryEntry.blockEntity(Utils.resource("kitchen_sink"), Services.BLOCK_ENTITY::createKitchenSinkBlockEntity, () -> new Block[]{
            ModBlocks.KITCHEN_SINK_OAK.get(),
            ModBlocks.KITCHEN_SINK_SPRUCE.get(),
            ModBlocks.KITCHEN_SINK_BIRCH.get(),
            ModBlocks.KITCHEN_SINK_JUNGLE.get(),
            ModBlocks.KITCHEN_SINK_ACACIA.get(),
            ModBlocks.KITCHEN_SINK_DARK_OAK.get(),
            ModBlocks.KITCHEN_SINK_MANGROVE.get(),
            ModBlocks.KITCHEN_SINK_CHERRY.get(),
            ModBlocks.KITCHEN_SINK_CRIMSON.get(),
            ModBlocks.KITCHEN_SINK_WARPED.get(),
            ModBlocks.KITCHEN_SINK_WHITE.get(),
            ModBlocks.KITCHEN_SINK_ORANGE.get(),
            ModBlocks.KITCHEN_SINK_MAGENTA.get(),
            ModBlocks.KITCHEN_SINK_LIGHT_BLUE.get(),
            ModBlocks.KITCHEN_SINK_YELLOW.get(),
            ModBlocks.KITCHEN_SINK_LIME.get(),
            ModBlocks.KITCHEN_SINK_PINK.get(),
            ModBlocks.KITCHEN_SINK_GRAY.get(),
            ModBlocks.KITCHEN_SINK_LIGHT_GRAY.get(),
            ModBlocks.KITCHEN_SINK_CYAN.get(),
            ModBlocks.KITCHEN_SINK_PURPLE.get(),
            ModBlocks.KITCHEN_SINK_BLUE.get(),
            ModBlocks.KITCHEN_SINK_BROWN.get(),
            ModBlocks.KITCHEN_SINK_GREEN.get(),
            ModBlocks.KITCHEN_SINK_RED.get(),
            ModBlocks.KITCHEN_SINK_BLACK.get(),
    });

    public static final RegistryEntry<BlockEntityType<GrillBlockEntity>> GRILL = RegistryEntry.blockEntity(Utils.resource("grill"), GrillBlockEntity::new, () -> new Block[]{
            ModBlocks.GRILL_WHITE.get(),
            ModBlocks.GRILL_ORANGE.get(),
            ModBlocks.GRILL_MAGENTA.get(),
            ModBlocks.GRILL_LIGHT_BLUE.get(),
            ModBlocks.GRILL_YELLOW.get(),
            ModBlocks.GRILL_LIME.get(),
            ModBlocks.GRILL_PINK.get(),
            ModBlocks.GRILL_GRAY.get(),
            ModBlocks.GRILL_LIGHT_GRAY.get(),
            ModBlocks.GRILL_CYAN.get(),
            ModBlocks.GRILL_PURPLE.get(),
            ModBlocks.GRILL_BLUE.get(),
            ModBlocks.GRILL_BROWN.get(),
            ModBlocks.GRILL_GREEN.get(),
            ModBlocks.GRILL_RED.get(),
            ModBlocks.GRILL_BLACK.get(),
    });
}
