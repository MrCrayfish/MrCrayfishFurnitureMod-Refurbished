package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.api.datagen.FrameworkGenerator;
import com.mrcrayfish.furniture.refurbished.block.*;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.data.model.ModelDefinitions;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.data.models.blockstates.BlockStateGenerator;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("UnstableApiUsage")
public class CommonBlockStatesGenerator extends FrameworkGenerator
{
    public CommonBlockStatesGenerator(Map<Block, BlockStateGenerator> generators, Map<Item, ClientItem> items, Map<ResourceLocation, ModelInstance> models)
    {
        super(generators, items, models);
    }

    @Override
    public void generate()
    {
        this.table(ModBlocks.TABLE_OAK.get());
        this.table(ModBlocks.TABLE_SPRUCE.get());
        this.table(ModBlocks.TABLE_BIRCH.get());
        this.table(ModBlocks.TABLE_JUNGLE.get());
        this.table(ModBlocks.TABLE_ACACIA.get());
        this.table(ModBlocks.TABLE_DARK_OAK.get());
        this.table(ModBlocks.TABLE_MANGROVE.get());
        this.table(ModBlocks.TABLE_CHERRY.get());
        this.table(ModBlocks.TABLE_CRIMSON.get());
        this.table(ModBlocks.TABLE_WARPED.get());
        this.chair(ModBlocks.CHAIR_OAK.get());
        this.chair(ModBlocks.CHAIR_SPRUCE.get());
        this.chair(ModBlocks.CHAIR_BIRCH.get());
        this.chair(ModBlocks.CHAIR_JUNGLE.get());
        this.chair(ModBlocks.CHAIR_ACACIA.get());
        this.chair(ModBlocks.CHAIR_DARK_OAK.get());
        this.chair(ModBlocks.CHAIR_MANGROVE.get());
        this.chair(ModBlocks.CHAIR_CHERRY.get());
        this.chair(ModBlocks.CHAIR_CRIMSON.get());
        this.chair(ModBlocks.CHAIR_WARPED.get());
        this.desk(ModBlocks.DESK_OAK.get());
        this.desk(ModBlocks.DESK_SPRUCE.get());
        this.desk(ModBlocks.DESK_BIRCH.get());
        this.desk(ModBlocks.DESK_JUNGLE.get());
        this.desk(ModBlocks.DESK_ACACIA.get());
        this.desk(ModBlocks.DESK_DARK_OAK.get());
        this.desk(ModBlocks.DESK_MANGROVE.get());
        this.desk(ModBlocks.DESK_CHERRY.get());
        this.desk(ModBlocks.DESK_CRIMSON.get());
        this.desk(ModBlocks.DESK_WARPED.get());
        this.drawer(ModBlocks.DRAWER_OAK.get());
        this.drawer(ModBlocks.DRAWER_SPRUCE.get());
        this.drawer(ModBlocks.DRAWER_BIRCH.get());
        this.drawer(ModBlocks.DRAWER_JUNGLE.get());
        this.drawer(ModBlocks.DRAWER_ACACIA.get());
        this.drawer(ModBlocks.DRAWER_DARK_OAK.get());
        this.drawer(ModBlocks.DRAWER_MANGROVE.get());
        this.drawer(ModBlocks.DRAWER_CHERRY.get());
        this.drawer(ModBlocks.DRAWER_CRIMSON.get());
        this.drawer(ModBlocks.DRAWER_WARPED.get());
        this.crate(ModBlocks.CRATE_OAK.get());
        this.crate(ModBlocks.CRATE_SPRUCE.get());
        this.crate(ModBlocks.CRATE_BIRCH.get());
        this.crate(ModBlocks.CRATE_JUNGLE.get());
        this.crate(ModBlocks.CRATE_ACACIA.get());
        this.crate(ModBlocks.CRATE_DARK_OAK.get());
        this.crate(ModBlocks.CRATE_MANGROVE.get());
        this.crate(ModBlocks.CRATE_CHERRY.get());
        this.crate(ModBlocks.CRATE_CRIMSON.get());
        this.crate(ModBlocks.CRATE_WARPED.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_OAK.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_SPRUCE.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_BIRCH.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_JUNGLE.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_ACACIA.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_DARK_OAK.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_MANGROVE.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_CHERRY.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_CRIMSON.get());
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_WARPED.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_OAK.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_SPRUCE.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_BIRCH.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_JUNGLE.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_ACACIA.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_DARK_OAK.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_MANGROVE.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_CHERRY.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_CRIMSON.get());
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_WARPED.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_OAK.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_SPRUCE.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_BIRCH.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_JUNGLE.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_ACACIA.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_DARK_OAK.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_MANGROVE.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_CHERRY.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_CRIMSON.get());
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_WARPED.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_WHITE.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_ORANGE.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_MAGENTA.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_LIGHT_BLUE.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_YELLOW.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_LIME.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_PINK.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_GRAY.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_LIGHT_GRAY.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_CYAN.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_PURPLE.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_BLUE.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_BROWN.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_GREEN.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_RED.get());
        this.colouredKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_BLACK.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_WHITE.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_ORANGE.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_MAGENTA.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_YELLOW.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_LIME.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_PINK.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_GRAY.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_CYAN.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_PURPLE.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_BLUE.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_BROWN.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_GREEN.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_RED.get());
        this.colouredKitchenDrawer(ModBlocks.KITCHEN_DRAWER_BLACK.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_WHITE.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_ORANGE.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_MAGENTA.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_LIGHT_BLUE.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_YELLOW.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_LIME.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_PINK.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_GRAY.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_LIGHT_GRAY.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_CYAN.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_PURPLE.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_BLUE.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_BROWN.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_GREEN.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_RED.get());
        this.colouredKitchenSink(ModBlocks.KITCHEN_SINK_BLACK.get());
        this.grill(ModBlocks.GRILL_WHITE.get());
        this.grill(ModBlocks.GRILL_ORANGE.get());
        this.grill(ModBlocks.GRILL_MAGENTA.get());
        this.grill(ModBlocks.GRILL_LIGHT_BLUE.get());
        this.grill(ModBlocks.GRILL_YELLOW.get());
        this.grill(ModBlocks.GRILL_LIME.get());
        this.grill(ModBlocks.GRILL_PINK.get());
        this.grill(ModBlocks.GRILL_GRAY.get());
        this.grill(ModBlocks.GRILL_LIGHT_GRAY.get());
        this.grill(ModBlocks.GRILL_CYAN.get());
        this.grill(ModBlocks.GRILL_PURPLE.get());
        this.grill(ModBlocks.GRILL_BLUE.get());
        this.grill(ModBlocks.GRILL_BROWN.get());
        this.grill(ModBlocks.GRILL_GREEN.get());
        this.grill(ModBlocks.GRILL_RED.get());
        this.grill(ModBlocks.GRILL_BLACK.get());
        this.cooler(ModBlocks.COOLER_WHITE.get());
        this.cooler(ModBlocks.COOLER_ORANGE.get());
        this.cooler(ModBlocks.COOLER_MAGENTA.get());
        this.cooler(ModBlocks.COOLER_LIGHT_BLUE.get());
        this.cooler(ModBlocks.COOLER_YELLOW.get());
        this.cooler(ModBlocks.COOLER_LIME.get());
        this.cooler(ModBlocks.COOLER_PINK.get());
        this.cooler(ModBlocks.COOLER_GRAY.get());
        this.cooler(ModBlocks.COOLER_LIGHT_GRAY.get());
        this.cooler(ModBlocks.COOLER_CYAN.get());
        this.cooler(ModBlocks.COOLER_PURPLE.get());
        this.cooler(ModBlocks.COOLER_BLUE.get());
        this.cooler(ModBlocks.COOLER_BROWN.get());
        this.cooler(ModBlocks.COOLER_GREEN.get());
        this.cooler(ModBlocks.COOLER_RED.get());
        this.cooler(ModBlocks.COOLER_BLACK.get());
        this.fridge(ModBlocks.FRIDGE_LIGHT.get(), ModBlocks.FREEZER_LIGHT.get());
        this.fridge(ModBlocks.FRIDGE_DARK.get(), ModBlocks.FREEZER_DARK.get());
        this.toaster(ModBlocks.TOASTER_LIGHT.get());
        this.toaster(ModBlocks.TOASTER_DARK.get());
        this.microwave(ModBlocks.MICROWAVE_LIGHT.get());
        this.microwave(ModBlocks.MICROWAVE_DARK.get());
        this.stove(ModBlocks.STOVE_LIGHT.get());
        this.stove(ModBlocks.STOVE_DARK.get());
        this.rangeHood(ModBlocks.RANGE_HOOD_LIGHT.get());
        this.rangeHood(ModBlocks.RANGE_HOOD_DARK.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_OAK.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_SPRUCE.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_BIRCH.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_JUNGLE.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_ACACIA.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_DARK_OAK.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_MANGROVE.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_CHERRY.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_CRIMSON.get());
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_WARPED.get());
        this.fryingPan(ModBlocks.FRYING_PAN.get());
        this.mailbox(ModBlocks.MAIL_BOX_OAK.get());
        this.mailbox(ModBlocks.MAIL_BOX_SPRUCE.get());
        this.mailbox(ModBlocks.MAIL_BOX_BIRCH.get());
        this.mailbox(ModBlocks.MAIL_BOX_JUNGLE.get());
        this.mailbox(ModBlocks.MAIL_BOX_ACACIA.get());
        this.mailbox(ModBlocks.MAIL_BOX_DARK_OAK.get());
        this.mailbox(ModBlocks.MAIL_BOX_MANGROVE.get());
        this.mailbox(ModBlocks.MAIL_BOX_CHERRY.get());
        this.mailbox(ModBlocks.MAIL_BOX_CRIMSON.get());
        this.mailbox(ModBlocks.MAIL_BOX_WARPED.get());
        this.postBox(ModBlocks.POST_BOX.get());
        this.sofa(ModBlocks.SOFA_WHITE.get());
        this.sofa(ModBlocks.SOFA_ORANGE.get());
        this.sofa(ModBlocks.SOFA_MAGENTA.get());
        this.sofa(ModBlocks.SOFA_LIGHT_BLUE.get());
        this.sofa(ModBlocks.SOFA_YELLOW.get());
        this.sofa(ModBlocks.SOFA_LIME.get());
        this.sofa(ModBlocks.SOFA_PINK.get());
        this.sofa(ModBlocks.SOFA_GRAY.get());
        this.sofa(ModBlocks.SOFA_LIGHT_GRAY.get());
        this.sofa(ModBlocks.SOFA_CYAN.get());
        this.sofa(ModBlocks.SOFA_PURPLE.get());
        this.sofa(ModBlocks.SOFA_BLUE.get());
        this.sofa(ModBlocks.SOFA_BROWN.get());
        this.sofa(ModBlocks.SOFA_GREEN.get());
        this.sofa(ModBlocks.SOFA_RED.get());
        this.sofa(ModBlocks.SOFA_BLACK.get());
        this.doorbell(ModBlocks.DOORBELL.get());
        this.lightswitch(ModBlocks.LIGHTSWITCH_LIGHT.get());
        this.lightswitch(ModBlocks.LIGHTSWITCH_DARK.get());
        this.ceilingLight(ModBlocks.CEILING_LIGHT_LIGHT.get());
        this.ceilingLight(ModBlocks.CEILING_LIGHT_DARK.get());
        this.electricityGenerator(ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get());
        this.electricityGenerator(ModBlocks.ELECTRICITY_GENERATOR_DARK.get());
        this.storageJar(ModBlocks.STORAGE_JAR_OAK.get());
        this.storageJar(ModBlocks.STORAGE_JAR_SPRUCE.get());
        this.storageJar(ModBlocks.STORAGE_JAR_BIRCH.get());
        this.storageJar(ModBlocks.STORAGE_JAR_JUNGLE.get());
        this.storageJar(ModBlocks.STORAGE_JAR_ACACIA.get());
        this.storageJar(ModBlocks.STORAGE_JAR_DARK_OAK.get());
        this.storageJar(ModBlocks.STORAGE_JAR_MANGROVE.get());
        this.storageJar(ModBlocks.STORAGE_JAR_CHERRY.get());
        this.storageJar(ModBlocks.STORAGE_JAR_CRIMSON.get());
        this.storageJar(ModBlocks.STORAGE_JAR_WARPED.get());
        this.recycleBin(ModBlocks.RECYCLE_BIN.get());
        this.lamp(ModBlocks.LAMP_WHITE.get());
        this.lamp(ModBlocks.LAMP_ORANGE.get());
        this.lamp(ModBlocks.LAMP_MAGENTA.get());
        this.lamp(ModBlocks.LAMP_LIGHT_BLUE.get());
        this.lamp(ModBlocks.LAMP_YELLOW.get());
        this.lamp(ModBlocks.LAMP_LIME.get());
        this.lamp(ModBlocks.LAMP_PINK.get());
        this.lamp(ModBlocks.LAMP_GRAY.get());
        this.lamp(ModBlocks.LAMP_LIGHT_GRAY.get());
        this.lamp(ModBlocks.LAMP_CYAN.get());
        this.lamp(ModBlocks.LAMP_PURPLE.get());
        this.lamp(ModBlocks.LAMP_BLUE.get());
        this.lamp(ModBlocks.LAMP_BROWN.get());
        this.lamp(ModBlocks.LAMP_GREEN.get());
        this.lamp(ModBlocks.LAMP_RED.get());
        this.lamp(ModBlocks.LAMP_BLACK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_OAK_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_SPRUCE_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_BIRCH_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_JUNGLE_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_ACACIA_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_DARK_OAK_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_MANGROVE_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_CHERRY_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_CRIMSON_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_WARPED_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_OAK_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_SPRUCE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_BIRCH_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_JUNGLE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_ACACIA_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_DARK_OAK_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_MANGROVE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_CHERRY_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_CRIMSON_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_WARPED_DARK.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_OAK.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_SPRUCE.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_BIRCH.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_JUNGLE.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_ACACIA.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_DARK_OAK.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_MANGROVE.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_CHERRY.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_CRIMSON.get());
        this.storageCabinet(ModBlocks.STORAGE_CABINET_WARPED.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_OAK.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_SPRUCE.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_BIRCH.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_JUNGLE.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_ACACIA.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_DARK_OAK.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_MANGROVE.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_CHERRY.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_CRIMSON.get());
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_WARPED.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_WHITE.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_ORANGE.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_MAGENTA.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_LIGHT_BLUE.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_YELLOW.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_LIME.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_PINK.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_GRAY.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_LIGHT_GRAY.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_CYAN.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_PURPLE.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_BLUE.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_BROWN.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_GREEN.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_RED.get());
        this.colouredKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_BLACK.get());
        this.trampoline(ModBlocks.TRAMPOLINE_WHITE.get());
        this.trampoline(ModBlocks.TRAMPOLINE_ORANGE.get());
        this.trampoline(ModBlocks.TRAMPOLINE_MAGENTA.get());
        this.trampoline(ModBlocks.TRAMPOLINE_LIGHT_BLUE.get());
        this.trampoline(ModBlocks.TRAMPOLINE_YELLOW.get());
        this.trampoline(ModBlocks.TRAMPOLINE_LIME.get());
        this.trampoline(ModBlocks.TRAMPOLINE_PINK.get());
        this.trampoline(ModBlocks.TRAMPOLINE_GRAY.get());
        this.trampoline(ModBlocks.TRAMPOLINE_LIGHT_GRAY.get());
        this.trampoline(ModBlocks.TRAMPOLINE_CYAN.get());
        this.trampoline(ModBlocks.TRAMPOLINE_PURPLE.get());
        this.trampoline(ModBlocks.TRAMPOLINE_BLUE.get());
        this.trampoline(ModBlocks.TRAMPOLINE_BROWN.get());
        this.trampoline(ModBlocks.TRAMPOLINE_GREEN.get());
        this.trampoline(ModBlocks.TRAMPOLINE_RED.get());
        this.trampoline(ModBlocks.TRAMPOLINE_BLACK.get());
        this.plate(ModBlocks.PLATE.get());
        this.stool(ModBlocks.STOOL_WHITE.get());
        this.stool(ModBlocks.STOOL_ORANGE.get());
        this.stool(ModBlocks.STOOL_MAGENTA.get());
        this.stool(ModBlocks.STOOL_LIGHT_BLUE.get());
        this.stool(ModBlocks.STOOL_YELLOW.get());
        this.stool(ModBlocks.STOOL_LIME.get());
        this.stool(ModBlocks.STOOL_PINK.get());
        this.stool(ModBlocks.STOOL_GRAY.get());
        this.stool(ModBlocks.STOOL_LIGHT_GRAY.get());
        this.stool(ModBlocks.STOOL_CYAN.get());
        this.stool(ModBlocks.STOOL_PURPLE.get());
        this.stool(ModBlocks.STOOL_BLUE.get());
        this.stool(ModBlocks.STOOL_BROWN.get());
        this.stool(ModBlocks.STOOL_GREEN.get());
        this.stool(ModBlocks.STOOL_RED.get());
        this.stool(ModBlocks.STOOL_BLACK.get());
        this.hedge(ModBlocks.HEDGE_OAK.get(), 0xFF48B518);
        this.hedge(ModBlocks.HEDGE_SPRUCE.get(), 0xFF619961);
        this.hedge(ModBlocks.HEDGE_BIRCH.get(), 0xFF80A755);
        this.hedge(ModBlocks.HEDGE_JUNGLE.get(), 0xFF48B518);
        this.hedge(ModBlocks.HEDGE_ACACIA.get(), 0xFF48B518);
        this.hedge(ModBlocks.HEDGE_DARK_OAK.get(), 0xFF48B518);
        this.hedge(ModBlocks.HEDGE_MANGROVE.get(), 0xFF92C648);
        this.hedge(ModBlocks.HEDGE_CHERRY.get(), -1);
        this.hedge(ModBlocks.HEDGE_AZALEA.get(), -1);
        this.steppingStones(ModBlocks.STEPPING_STONES_STONE.get());
        this.steppingStones(ModBlocks.STEPPING_STONES_GRANITE.get());
        this.steppingStones(ModBlocks.STEPPING_STONES_DIORITE.get());
        this.steppingStones(ModBlocks.STEPPING_STONES_ANDESITE.get());
        this.steppingStones(ModBlocks.STEPPING_STONES_DEEPSLATE.get());
        this.woodenToilet(ModBlocks.TOILET_OAK.get());
        this.woodenToilet(ModBlocks.TOILET_SPRUCE.get());
        this.woodenToilet(ModBlocks.TOILET_BIRCH.get());
        this.woodenToilet(ModBlocks.TOILET_JUNGLE.get());
        this.woodenToilet(ModBlocks.TOILET_ACACIA.get());
        this.woodenToilet(ModBlocks.TOILET_DARK_OAK.get());
        this.woodenToilet(ModBlocks.TOILET_MANGROVE.get());
        this.woodenToilet(ModBlocks.TOILET_CHERRY.get());
        this.woodenToilet(ModBlocks.TOILET_CRIMSON.get());
        this.woodenToilet(ModBlocks.TOILET_WARPED.get());
        this.colouredToilet(ModBlocks.TOILET_WHITE.get());
        this.colouredToilet(ModBlocks.TOILET_ORANGE.get());
        this.colouredToilet(ModBlocks.TOILET_MAGENTA.get());
        this.colouredToilet(ModBlocks.TOILET_LIGHT_BLUE.get());
        this.colouredToilet(ModBlocks.TOILET_YELLOW.get());
        this.colouredToilet(ModBlocks.TOILET_LIME.get());
        this.colouredToilet(ModBlocks.TOILET_PINK.get());
        this.colouredToilet(ModBlocks.TOILET_GRAY.get());
        this.colouredToilet(ModBlocks.TOILET_LIGHT_GRAY.get());
        this.colouredToilet(ModBlocks.TOILET_CYAN.get());
        this.colouredToilet(ModBlocks.TOILET_PURPLE.get());
        this.colouredToilet(ModBlocks.TOILET_BLUE.get());
        this.colouredToilet(ModBlocks.TOILET_BROWN.get());
        this.colouredToilet(ModBlocks.TOILET_GREEN.get());
        this.colouredToilet(ModBlocks.TOILET_RED.get());
        this.colouredToilet(ModBlocks.TOILET_BLACK.get());
        this.woodenBasin(ModBlocks.BASIN_OAK.get());
        this.woodenBasin(ModBlocks.BASIN_SPRUCE.get());
        this.woodenBasin(ModBlocks.BASIN_BIRCH.get());
        this.woodenBasin(ModBlocks.BASIN_JUNGLE.get());
        this.woodenBasin(ModBlocks.BASIN_ACACIA.get());
        this.woodenBasin(ModBlocks.BASIN_DARK_OAK.get());
        this.woodenBasin(ModBlocks.BASIN_MANGROVE.get());
        this.woodenBasin(ModBlocks.BASIN_CHERRY.get());
        this.woodenBasin(ModBlocks.BASIN_CRIMSON.get());
        this.woodenBasin(ModBlocks.BASIN_WARPED.get());
        this.colouredBasin(ModBlocks.BASIN_WHITE.get());
        this.colouredBasin(ModBlocks.BASIN_ORANGE.get());
        this.colouredBasin(ModBlocks.BASIN_MAGENTA.get());
        this.colouredBasin(ModBlocks.BASIN_LIGHT_BLUE.get());
        this.colouredBasin(ModBlocks.BASIN_YELLOW.get());
        this.colouredBasin(ModBlocks.BASIN_LIME.get());
        this.colouredBasin(ModBlocks.BASIN_PINK.get());
        this.colouredBasin(ModBlocks.BASIN_GRAY.get());
        this.colouredBasin(ModBlocks.BASIN_LIGHT_GRAY.get());
        this.colouredBasin(ModBlocks.BASIN_CYAN.get());
        this.colouredBasin(ModBlocks.BASIN_PURPLE.get());
        this.colouredBasin(ModBlocks.BASIN_BLUE.get());
        this.colouredBasin(ModBlocks.BASIN_BROWN.get());
        this.colouredBasin(ModBlocks.BASIN_GREEN.get());
        this.colouredBasin(ModBlocks.BASIN_RED.get());
        this.colouredBasin(ModBlocks.BASIN_BLACK.get());
        this.woodenBath(ModBlocks.BATH_OAK.get());
        this.woodenBath(ModBlocks.BATH_SPRUCE.get());
        this.woodenBath(ModBlocks.BATH_BIRCH.get());
        this.woodenBath(ModBlocks.BATH_JUNGLE.get());
        this.woodenBath(ModBlocks.BATH_ACACIA.get());
        this.woodenBath(ModBlocks.BATH_DARK_OAK.get());
        this.woodenBath(ModBlocks.BATH_MANGROVE.get());
        this.woodenBath(ModBlocks.BATH_CHERRY.get());
        this.woodenBath(ModBlocks.BATH_CRIMSON.get());
        this.woodenBath(ModBlocks.BATH_WARPED.get());
        this.colouredBath(ModBlocks.BATH_WHITE.get());
        this.colouredBath(ModBlocks.BATH_ORANGE.get());
        this.colouredBath(ModBlocks.BATH_MAGENTA.get());
        this.colouredBath(ModBlocks.BATH_LIGHT_BLUE.get());
        this.colouredBath(ModBlocks.BATH_YELLOW.get());
        this.colouredBath(ModBlocks.BATH_LIME.get());
        this.colouredBath(ModBlocks.BATH_PINK.get());
        this.colouredBath(ModBlocks.BATH_GRAY.get());
        this.colouredBath(ModBlocks.BATH_LIGHT_GRAY.get());
        this.colouredBath(ModBlocks.BATH_CYAN.get());
        this.colouredBath(ModBlocks.BATH_PURPLE.get());
        this.colouredBath(ModBlocks.BATH_BLUE.get());
        this.colouredBath(ModBlocks.BATH_BROWN.get());
        this.colouredBath(ModBlocks.BATH_GREEN.get());
        this.colouredBath(ModBlocks.BATH_RED.get());
        this.colouredBath(ModBlocks.BATH_BLACK.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_OAK.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_SPRUCE.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_BIRCH.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_JUNGLE.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_ACACIA.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_DARK_OAK.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_MANGROVE.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_CHERRY.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_CRIMSON.get());
        this.latticeFence(ModBlocks.LATTICE_FENCE_WARPED.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_OAK.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_SPRUCE.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_BIRCH.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_JUNGLE.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_ACACIA.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_DARK_OAK.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_MANGROVE.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_CHERRY.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_CRIMSON.get());
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_WARPED.get());
        this.television(ModBlocks.TELEVISION.get());
        this.computer(ModBlocks.COMPUTER.get());
        this.doorMat(ModBlocks.DOOR_MAT.get());
        this.workbench(ModBlocks.WORKBENCH.get());
    }

    private ResourceLocation blockTexture(Block block)
    {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "block/" + name.getPath());
    }

    private ResourceLocation woodParticle(WoodType type)
    {
        return Utils.resource("block/" + type.name() + "_particle");
    }

    private ResourceLocation colourParticle(DyeColor color)
    {
        return Utils.resource("block/" + color.getName() + "_particle");
    }

    private ResourceLocation metalParticle(MetalType type)
    {
        return Utils.resource("block/" + type.getName() + "_particle");
    }

    private ResourceLocation leafTexture(LeafType type)
    {
        return ResourceLocation.withDefaultNamespace("block/" + type.getName() + "_leaves");
    }

    private ResourceLocation stoneTexture(StoneType type)
    {
        return ResourceLocation.withDefaultNamespace("block/" + type.getName());
    }

    private void registerItemWithModel(Block block, ResourceLocation location)
    {
        this.items.put(block.asItem(), this.createClientItem(ItemModelUtils.plainModel(location)));
    }

    private void registerItemWithModel(Item item, ResourceLocation location)
    {
        this.items.put(item, this.createClientItem(ItemModelUtils.plainModel(location)));
    }

    private void table(TableBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation baseTableModel = ModelDefinitions.TABLE.create(block, textures, this.models::put);
        this.registerItemWithModel(block, baseTableModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(TableBlock.NORTH, TableBlock.EAST, TableBlock.SOUTH, TableBlock.WEST)
                .select(false, false, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, baseTableModel))
                .select(true, false, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_NORTH.create(block, textures, this.models::put)))
                .select(true, true, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_NORTH_EAST.create(block, textures, this.models::put)))
                .select(true, true, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_NORTH_EAST_SOUTH.create(block, textures, this.models::put)))
                .select(false, true, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_EAST.create(block, textures, this.models::put)))
                .select(false, true, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_EAST_SOUTH.create(block, textures, this.models::put)))
                .select(false, true, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_EAST_SOUTH_WEST.create(block, textures, this.models::put)))
                .select(false, false, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_SOUTH.create(block, textures, this.models::put)))
                .select(false, false, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_SOUTH_WEST.create(block, textures, this.models::put)))
                .select(true, false, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_SOUTH_WEST_NORTH.create(block, textures, this.models::put)))
                .select(false, false, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_WEST.create(block, textures, this.models::put)))
                .select(true, false, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_WEST_NORTH.create(block, textures, this.models::put)))
                .select(true, true, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_WEST_NORTH_EAST.create(block, textures, this.models::put)))
                .select(true, false, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_NORTH_SOUTH.create(block, textures, this.models::put)))
                .select(false, true, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_EAST_WEST.create(block, textures, this.models::put)))
                .select(true, true, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ModelDefinitions.TABLE_NORTH_EAST_SOUTH_WEST.create(block, textures, this.models::put)))));
    }

    private void chair(ChairBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation baseChairModel = ModelDefinitions.CHAIR.create(block, textures, this.models::put);
        ResourceLocation tuckedChairModel = ModelDefinitions.CHAIR_TUCKED.create(block, textures, this.models::put);
        this.registerItemWithModel(block, baseChairModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(ChairBlock.DIRECTION, ChairBlock.TUCKED)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, baseChairModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, baseChairModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, baseChairModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, baseChairModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, tuckedChairModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, tuckedChairModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, tuckedChairModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, tuckedChairModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void desk(DeskBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation baseDeskModel = ModelDefinitions.DESK.create(block, textures, this.models::put);
        ResourceLocation deskLeftModel = ModelDefinitions.DESK_LEFT.create(block, textures, this.models::put);
        ResourceLocation deskMiddleModel = ModelDefinitions.DESK_MIDDLE.create(block, textures, this.models::put);
        ResourceLocation deskRightModel = ModelDefinitions.DESK_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, baseDeskModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(DeskBlock.DIRECTION, DeskBlock.LEFT, DeskBlock.RIGHT)
                .select(Direction.NORTH, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, baseDeskModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, baseDeskModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, baseDeskModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, baseDeskModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, deskLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, deskLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, deskLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, deskLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, deskRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, deskRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, deskRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, deskRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, deskMiddleModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, deskMiddleModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, deskMiddleModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, deskMiddleModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void drawer(DrawerBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation drawerClosedModel = ModelDefinitions.DRAWER_CLOSED.create(block, textures, this.models::put);
        ResourceLocation drawerOpenModel = ModelDefinitions.DRAWER_OPEN.create(block, textures, this.models::put);
        ResourceLocation drawerLeftClosedModel = ModelDefinitions.DRAWER_LEFT_CLOSED.create(block, textures, this.models::put);
        ResourceLocation drawerLeftOpenModel = ModelDefinitions.DRAWER_LEFT_OPEN.create(block, textures, this.models::put);
        ResourceLocation drawerRightClosedModel = ModelDefinitions.DRAWER_RIGHT_CLOSED.create(block, textures, this.models::put);
        ResourceLocation drawerRightOpenModel = ModelDefinitions.DRAWER_RIGHT_OPEN.create(block, textures, this.models::put);
        ResourceLocation drawerMiddleClosedModel = ModelDefinitions.DRAWER_MIDDLE_CLOSED.create(block, textures, this.models::put);
        ResourceLocation drawerMiddleOpenModel = ModelDefinitions.DRAWER_MIDDLE_OPEN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, drawerClosedModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(DrawerBlock.DIRECTION, DrawerBlock.LEFT, DrawerBlock.RIGHT, DrawerBlock.OPEN)
                .select(Direction.NORTH, false, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerLeftClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerLeftClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerLeftClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerLeftClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, false, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerRightClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerRightClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerRightClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerRightClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerMiddleClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerMiddleClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerMiddleClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerMiddleClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, false, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerLeftOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerLeftOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerLeftOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerLeftOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, false, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerRightOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerRightOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerRightOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerRightOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerMiddleOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerMiddleOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerMiddleOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerMiddleOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void crate(CrateBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation closedCrateModel = ModelDefinitions.CRATE_CLOSED.create(block, textures, this.models::put);
        ResourceLocation openCrateModel = ModelDefinitions.CRATE_OPEN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, closedCrateModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(CrateBlock.OPEN)
                .select(false, Variant.variant()
                    .with(VariantProperties.MODEL, closedCrateModel))
                .select(true, Variant.variant()
                    .with(VariantProperties.MODEL, openCrateModel))));
    }

    private void woodenKitchenCabinetry(WoodenKitchenCabinetryBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation defaultCabinetryModel = ModelDefinitions.KITCHEN_CABINETRY_DEFAULT.create(block, textures, this.models::put);
        ResourceLocation cabinetryInsideCornerLeftModel = ModelDefinitions.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.create(block, textures, this.models::put);
        ResourceLocation cabinetryInsideCornerRightModel = ModelDefinitions.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.create(block, textures, this.models::put);
        ResourceLocation cabinetryOutsideCornerLeftModel = ModelDefinitions.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.create(block, textures, this.models::put);
        ResourceLocation cabinetryOutsideCornerRightModel = ModelDefinitions.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, defaultCabinetryModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(WoodenKitchenCabinetryBlock.DIRECTION, WoodenKitchenCabinetryBlock.SHAPE)
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, defaultCabinetryModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, defaultCabinetryModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, defaultCabinetryModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, defaultCabinetryModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void woodenKitchenDrawer(WoodenKitchenDrawerBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation drawerClosedModel = ModelDefinitions.KITCHEN_DRAWER_CLOSED.create(block, textures, this.models::put);
        ResourceLocation drawerOpenModel = ModelDefinitions.KITCHEN_DRAWER_OPEN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, drawerClosedModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(DrawerBlock.DIRECTION, DrawerBlock.OPEN)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void woodenKitchenSink(WoodenKitchenSinkBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation baseKitchenSinkModel = ModelDefinitions.KITCHEN_SINK.create(block, textures, this.models::put);
        this.registerItemWithModel(block, baseKitchenSinkModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(WoodenKitchenSinkBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, baseKitchenSinkModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, baseKitchenSinkModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, baseKitchenSinkModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, baseKitchenSinkModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void colouredKitchenCabinetry(ColouredKitchenCabinetryBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation defaultCabinetryModel = ModelDefinitions.KITCHEN_CABINETRY_DEFAULT.create(block, textures, this.models::put);
        ResourceLocation cabinetryInsideCornerLeftModel = ModelDefinitions.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.create(block, textures, this.models::put);
        ResourceLocation cabinetryInsideCornerRightModel = ModelDefinitions.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.create(block, textures, this.models::put);
        ResourceLocation cabinetryOutsideCornerLeftModel = ModelDefinitions.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.create(block, textures, this.models::put);
        ResourceLocation cabinetryOutsideCornerRightModel = ModelDefinitions.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, defaultCabinetryModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(ColouredKitchenCabinetryBlock.DIRECTION, ColouredKitchenCabinetryBlock.SHAPE)
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, defaultCabinetryModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, defaultCabinetryModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, defaultCabinetryModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, defaultCabinetryModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryInsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, cabinetryOutsideCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void colouredKitchenDrawer(ColouredKitchenDrawerBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation drawerClosedModel = ModelDefinitions.KITCHEN_DRAWER_CLOSED.create(block, textures, this.models::put);
        ResourceLocation drawerOpenModel = ModelDefinitions.KITCHEN_DRAWER_OPEN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, drawerClosedModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(ColouredKitchenDrawerBlock.DIRECTION, ColouredKitchenDrawerBlock.OPEN)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, drawerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, drawerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void colouredKitchenSink(ColouredKitchenSinkBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation baseKitchenSinkModel = ModelDefinitions.KITCHEN_SINK.create(block, textures, this.models::put);
        this.registerItemWithModel(block, baseKitchenSinkModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(WoodenKitchenSinkBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, baseKitchenSinkModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, baseKitchenSinkModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, baseKitchenSinkModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, baseKitchenSinkModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void grill(GrillBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation grillModel = ModelDefinitions.GRILL.create(block, textures, this.models::put);
        this.registerItemWithModel(block, grillModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(GrillBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, grillModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, grillModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, grillModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, grillModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void cooler(CoolerBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation coolerClosedModel = ModelDefinitions.COOLER_CLOSED.create(block, textures, this.models::put);
        ResourceLocation coolerOpenModel = ModelDefinitions.COOLER_OPEN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, coolerClosedModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(CoolerBlock.DIRECTION, CoolerBlock.OPEN)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, coolerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, coolerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, coolerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, coolerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, coolerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, coolerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, coolerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, coolerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void fridge(FridgeBlock fridgeBlock, FreezerBlock freezerBlock)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(fridgeBlock.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(fridgeBlock));

        ResourceLocation fridgeClosedModel = ModelDefinitions.FRIDGE_CLOSED.create(fridgeBlock, textures, this.models::put);
        ResourceLocation fridgeOpenModel = ModelDefinitions.FRIDGE_OPEN.create(fridgeBlock, textures, this.models::put);
        this.generators.put(fridgeBlock, MultiVariantGenerator.multiVariant(fridgeBlock)
            .with(PropertyDispatch.properties(FridgeBlock.DIRECTION, FridgeBlock.OPEN)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, fridgeClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, fridgeClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, fridgeClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, fridgeClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, fridgeOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, fridgeOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, fridgeOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, fridgeOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));

        ResourceLocation freezerClosedModel = ModelDefinitions.FREEZER_CLOSED.create(freezerBlock, textures, this.models::put);
        ResourceLocation freezerOpenModel = ModelDefinitions.FREEZER_OPEN.create(freezerBlock, textures, this.models::put);
        this.generators.put(freezerBlock, MultiVariantGenerator.multiVariant(freezerBlock)
            .with(PropertyDispatch.properties(FreezerBlock.DIRECTION, FreezerBlock.OPEN)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, freezerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, freezerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, freezerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, freezerClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, freezerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, freezerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, freezerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, freezerOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void toaster(ToasterBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation toasterModel = ModelDefinitions.TOASTER.create(block, textures, this.models::put);
        ResourceLocation toasterCookingModel = ModelDefinitions.TOASTER_COOKING.create(block, textures, this.models::put);
        this.registerItemWithModel(block, toasterModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(ToasterBlock.DIRECTION, ToasterBlock.POWERED)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, toasterModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, toasterModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, toasterModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, toasterModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, toasterCookingModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, toasterCookingModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, toasterCookingModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, toasterCookingModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void microwave(MicrowaveBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation microwaveClosedModel = ModelDefinitions.MICROWAVE_CLOSED.create(block, textures, this.models::put);
        ResourceLocation microwaveOpenModel = ModelDefinitions.MICROWAVE_OPEN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, microwaveClosedModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(MicrowaveBlock.DIRECTION, MicrowaveBlock.OPEN)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, microwaveClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, microwaveClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, microwaveClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, microwaveClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, microwaveOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, microwaveOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, microwaveOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, microwaveOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void stove(StoveBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation stoveClosedModel = ModelDefinitions.STOVE_CLOSED.create(block, textures, this.models::put);
        ResourceLocation stoveOpenModel = ModelDefinitions.STOVE_OPEN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, stoveClosedModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(StoveBlock.DIRECTION, StoveBlock.OPEN)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, stoveClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, stoveClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, stoveClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, stoveClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, stoveOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, stoveOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, stoveOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, stoveOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void rangeHood(RangeHoodBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation rangeHoodOffModel = ModelDefinitions.RANGE_HOOD_OFF.create(block, textures, this.models::put);
        ResourceLocation rangeHoodOnModel = ModelDefinitions.RANGE_HOOD_ON.create(block, textures, this.models::put);
        this.registerItemWithModel(block, rangeHoodOffModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(RangeHoodBlock.DIRECTION, RangeHoodBlock.POWERED)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, rangeHoodOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, rangeHoodOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, rangeHoodOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, rangeHoodOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, rangeHoodOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, rangeHoodOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, rangeHoodOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, rangeHoodOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void cuttingBoard(CuttingBoardBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation cuttingBoardModel = ModelDefinitions.CUTTING_BOARD.create(block, textures, this.models::put);
        this.registerItemWithModel(block, cuttingBoardModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(CuttingBoardBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, cuttingBoardModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, cuttingBoardModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, cuttingBoardModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, cuttingBoardModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void fryingPan(FryingPanBlock block)
    {
        ResourceLocation fryingPanModel = ModelLocationUtils.getModelLocation(block);
        ResourceLocation fryingPanHotModel = ModelLocationUtils.getModelLocation(block, "_hot");
        this.registerItemWithModel(block, fryingPanModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(FryingPanBlock.DIRECTION, FryingPanBlock.LIT)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, fryingPanModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, fryingPanModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, fryingPanModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, fryingPanModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, fryingPanHotModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, fryingPanHotModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, fryingPanHotModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, fryingPanHotModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void mailbox(MailboxBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation mailboxModel = ModelDefinitions.MAIL_BOX.create(block, textures, this.models::put);
        ResourceLocation mailboxUncheckedModel = ModelDefinitions.MAIL_BOX_UNCHECKED.create(block, textures, this.models::put);
        this.registerItemWithModel(block, mailboxModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(MailboxBlock.DIRECTION, MailboxBlock.ENABLED)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, mailboxModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, mailboxModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, mailboxModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, mailboxModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, mailboxUncheckedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, mailboxUncheckedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, mailboxUncheckedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, mailboxUncheckedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void postBox(PostBoxBlock block)
    {
        ResourceLocation postboxModel = ModelLocationUtils.getModelLocation(block);
        this.registerItemWithModel(block, postboxModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(PostBoxBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, postboxModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, postboxModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, postboxModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, postboxModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void sofa(SofaBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation sofaModel = ModelDefinitions.SOFA.create(block, textures, this.models::put);
        ResourceLocation sofaLeftModel = ModelDefinitions.SOFA_LEFT.create(block, textures, this.models::put);
        ResourceLocation sofaRightModel = ModelDefinitions.SOFA_RIGHT.create(block, textures, this.models::put);
        ResourceLocation sofaMiddleModel = ModelDefinitions.SOFA_MIDDLE.create(block, textures, this.models::put);
        ResourceLocation sofaCornerLeftModel = ModelDefinitions.SOFA_CORNER_LEFT.create(block, textures, this.models::put);
        ResourceLocation sofaCornerRightModel = ModelDefinitions.SOFA_CORNER_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, sofaModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(SofaBlock.DIRECTION, SofaBlock.SHAPE)
                .select(Direction.NORTH, SofaBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, SofaBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, SofaBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, SofaBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, SofaBlock.Shape.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, SofaBlock.Shape.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, SofaBlock.Shape.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, SofaBlock.Shape.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, SofaBlock.Shape.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, SofaBlock.Shape.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, SofaBlock.Shape.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, SofaBlock.Shape.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, SofaBlock.Shape.MIDDLE, Variant.variant()
                    .with(VariantProperties.MODEL, sofaMiddleModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, SofaBlock.Shape.MIDDLE, Variant.variant()
                    .with(VariantProperties.MODEL, sofaMiddleModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, SofaBlock.Shape.MIDDLE, Variant.variant()
                    .with(VariantProperties.MODEL, sofaMiddleModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, SofaBlock.Shape.MIDDLE, Variant.variant()
                    .with(VariantProperties.MODEL, sofaMiddleModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, SofaBlock.Shape.CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, SofaBlock.Shape.CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, SofaBlock.Shape.CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, SofaBlock.Shape.CORNER_LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaCornerLeftModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, SofaBlock.Shape.CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, SofaBlock.Shape.CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, SofaBlock.Shape.CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, SofaBlock.Shape.CORNER_RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, sofaCornerRightModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void doorbell(DoorbellBlock block)
    {
        ResourceLocation doorbellModel = ModelLocationUtils.getModelLocation(block);
        ResourceLocation doorbellPressedModel = ModelLocationUtils.getModelLocation(block, "_pressed");
        this.registerItemWithModel(block, doorbellModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(DoorbellBlock.DIRECTION, DoorbellBlock.ENABLED)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, doorbellModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, doorbellModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, doorbellModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, doorbellModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, doorbellPressedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, doorbellPressedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, doorbellPressedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, doorbellPressedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));

    }

    private void lightswitch(LightswitchBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation lightswitchOffModel = ModelDefinitions.LIGHTSWITCH_OFF.create(block, textures, this.models::put);
        ResourceLocation lightswitchOnModel = ModelDefinitions.LIGHTSWITCH_ON.create(block, textures, this.models::put);
        this.registerItemWithModel(block, lightswitchOffModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(LightswitchBlock.FACING, LightswitchBlock.FACE, LightswitchBlock.ENABLED, LightswitchBlock.POWERED)
                .select(Direction.NORTH, AttachFace.WALL, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST, AttachFace.WALL, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.SOUTH, AttachFace.WALL, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.WEST, AttachFace.WALL, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.NORTH, AttachFace.FLOOR, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.FLOOR, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.FLOOR, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.FLOOR, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.CEILING, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.CEILING, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.CEILING, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.CEILING, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.WALL, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST, AttachFace.WALL, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.SOUTH, AttachFace.WALL, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.WEST, AttachFace.WALL, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.NORTH, AttachFace.FLOOR, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.FLOOR, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.FLOOR, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.FLOOR, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.CEILING, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.CEILING, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.CEILING, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.CEILING, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.WALL, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST, AttachFace.WALL, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.SOUTH, AttachFace.WALL, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.WEST, AttachFace.WALL, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.NORTH, AttachFace.FLOOR, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.FLOOR, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.FLOOR, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.FLOOR, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.CEILING, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.CEILING, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.CEILING, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.CEILING, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.WALL, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST, AttachFace.WALL, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.SOUTH, AttachFace.WALL, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.WEST, AttachFace.WALL, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.NORTH, AttachFace.FLOOR, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.FLOOR, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.FLOOR, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.FLOOR, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.CEILING, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.CEILING, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.CEILING, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.CEILING, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, lightswitchOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void ceilingLight(CeilingLightBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation ceilingLightOnModel = ModelDefinitions.CEILING_LIGHT_ON.create(block, textures, this.models::put);
        ResourceLocation ceilingLightOffModel = ModelDefinitions.CEILING_LIGHT_OFF.create(block, textures, this.models::put);
        this.registerItemWithModel(block, ceilingLightOffModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(CeilingLightBlock.FACING, CeilingLightBlock.FACE, CeilingLightBlock.POWERED)
                .select(Direction.NORTH, AttachFace.WALL, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST, AttachFace.WALL, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.SOUTH, AttachFace.WALL, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.WEST, AttachFace.WALL, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.NORTH, AttachFace.FLOOR, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.FLOOR, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.FLOOR, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.FLOOR, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.CEILING, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.CEILING, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.CEILING, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.CEILING, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.WALL, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST, AttachFace.WALL, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.SOUTH, AttachFace.WALL, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.WEST, AttachFace.WALL, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.NORTH, AttachFace.FLOOR, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.FLOOR, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.FLOOR, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.FLOOR, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, AttachFace.CEILING, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, AttachFace.CEILING, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, AttachFace.CEILING, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, AttachFace.CEILING, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingLightOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void electricityGenerator(ElectricityGeneratorBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation electricityGeneratorOffModel = ModelDefinitions.ELECTRICITY_GENERATOR_OFF.create(block, textures, this.models::put);
        ResourceLocation electricityGeneratorOnModel = ModelDefinitions.ELECTRICITY_GENERATOR_ON.create(block, textures, this.models::put);
        this.registerItemWithModel(block, electricityGeneratorOffModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(ElectricityGeneratorBlock.DIRECTION, ElectricityGeneratorBlock.POWERED)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, electricityGeneratorOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, electricityGeneratorOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, electricityGeneratorOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, electricityGeneratorOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, electricityGeneratorOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, electricityGeneratorOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, electricityGeneratorOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, electricityGeneratorOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void storageJar(StorageJarBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, ResourceLocation.withDefaultNamespace("block/glass"))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation storageJarModel = ModelDefinitions.STORAGE_JAR.create(block, textures, this.models::put);
        this.registerItemWithModel(block, storageJarModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(StorageJarBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, storageJarModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, storageJarModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, storageJarModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, storageJarModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void recycleBin(RecycleBinBlock block)
    {
        ResourceLocation recycleBinClosedModel = ModelLocationUtils.getModelLocation(block, "_closed");
        ResourceLocation recycleBinOpenModel = ModelLocationUtils.getModelLocation(block, "_open");
        this.registerItemWithModel(block, recycleBinClosedModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(RecycleBinBlock.DIRECTION, RecycleBinBlock.OPEN)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, recycleBinClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, recycleBinClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, recycleBinClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, recycleBinClosedModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, recycleBinOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, recycleBinOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, recycleBinOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, recycleBinOpenModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void lamp(LampBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation lampOffModel = ModelDefinitions.LAMP_OFF.create(block, textures, this.models::put);
        ResourceLocation lampOnModel = ModelDefinitions.LAMP_ON.create(block, textures, this.models::put);
        this.registerItemWithModel(block, lampOffModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(LampBlock.POWERED)
                .select(false, Variant.variant().with(VariantProperties.MODEL, lampOffModel))
                .select(true, Variant.variant().with(VariantProperties.MODEL, lampOnModel))));
    }

    private void ceilingFan(CeilingFanBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation ceilingFanOffModel = ModelDefinitions.CEILING_FAN_BASE_OFF.create(block, textures, this.models::put);
        ResourceLocation ceilingFanOnModel = ModelDefinitions.CEILING_FAN_BASE_ON.create(block, textures, this.models::put);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(CeilingFanBlock.FACING, CeilingFanBlock.POWERED, CeilingFanBlock.LIT)
                .select(Direction.NORTH, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                .select(Direction.EAST, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                .select(Direction.WEST, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.UP, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel))
                .select(Direction.DOWN, false, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.NORTH, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                .select(Direction.EAST, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                .select(Direction.WEST, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.UP, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel))
                .select(Direction.DOWN, false, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.NORTH, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                .select(Direction.EAST, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                .select(Direction.WEST, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.UP, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel))
                .select(Direction.DOWN, true, false, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOffModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.NORTH, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                .select(Direction.EAST, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                .select(Direction.WEST, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.UP, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOnModel))
                .select(Direction.DOWN, true, true, Variant.variant()
                    .with(VariantProperties.MODEL, ceilingFanOnModel)
                    .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))));

        // Custom item
        this.registerItemWithModel(block, ModelDefinitions.CEILING_FAN.create(block.asItem(), textures, this.models::put));

        // Extra models for the ceiling fan blade
        TextureMapping extraTextures = new TextureMapping();
        extraTextures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        String name = "%s_%s_ceiling_fan_blade".formatted(block.getWoodType().name(), block.getMetalType().getName());
        ModelDefinitions.CEILING_FAN_BLADE.create(Utils.resource("extra/" + name), extraTextures, this.models::put);
    }

    private void storageCabinet(WoodenStorageCabinetBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation storageCabinetClosedLeft = ModelDefinitions.CABINET_CLOSED_HINGE_LEFT.create(block, textures, this.models::put);
        ResourceLocation storageCabinetClosedRight = ModelDefinitions.CABINET_CLOSED_HINGE_RIGHT.create(block, textures, this.models::put);
        ResourceLocation storageCabinetOpenLeft = ModelDefinitions.CABINET_OPEN_HINGE_LEFT.create(block, textures, this.models::put);
        ResourceLocation storageCabinetOpenRight = ModelDefinitions.CABINET_OPEN_HINGE_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, storageCabinetClosedLeft);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(WoodenStorageCabinetBlock.DIRECTION, WoodenStorageCabinetBlock.OPEN, WoodenStorageCabinetBlock.HINGE)
                .select(Direction.NORTH, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, storageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));

    }

    private void woodenKitchenCabinet(WoodenKitchenStorageCabinetBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation kitchenStorageCabinetClosedLeft = ModelDefinitions.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.create(block, textures, this.models::put);
        ResourceLocation kitchenStorageCabinetClosedRight = ModelDefinitions.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.create(block, textures, this.models::put);
        ResourceLocation kitchenStorageCabinetOpenLeft = ModelDefinitions.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.create(block, textures, this.models::put);
        ResourceLocation kitchenStorageCabinetOpenRight = ModelDefinitions.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, kitchenStorageCabinetClosedLeft);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(WoodenKitchenStorageCabinetBlock.DIRECTION, WoodenKitchenStorageCabinetBlock.OPEN, WoodenKitchenStorageCabinetBlock.HINGE)
                .select(Direction.NORTH, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void colouredKitchenCabinet(ColouredKitchenStorageCabinetBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation kitchenStorageCabinetClosedLeft = ModelDefinitions.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.create(block, textures, this.models::put);
        ResourceLocation kitchenStorageCabinetClosedRight = ModelDefinitions.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.create(block, textures, this.models::put);
        ResourceLocation kitchenStorageCabinetOpenLeft = ModelDefinitions.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.create(block, textures, this.models::put);
        ResourceLocation kitchenStorageCabinetOpenRight = ModelDefinitions.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, kitchenStorageCabinetClosedLeft);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(ColouredKitchenStorageCabinetBlock.DIRECTION, ColouredKitchenStorageCabinetBlock.OPEN, ColouredKitchenStorageCabinetBlock.HINGE)
                .select(Direction.NORTH, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, DoorHingeSide.LEFT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenLeft)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetClosedRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, DoorHingeSide.RIGHT, Variant.variant()
                    .with(VariantProperties.MODEL, kitchenStorageCabinetOpenRight)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void trampoline(TrampolineBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation trampolineDefaultModel = ModelDefinitions.TRAMPOLINE_DEFAULT.create(block, textures, this.models::put);
        ResourceLocation trampolineNorthModel = ModelDefinitions.TRAMPOLINE_NORTH.create(block, textures, this.models::put);
        ResourceLocation trampolineEastModel = ModelDefinitions.TRAMPOLINE_EAST.create(block, textures, this.models::put);
        ResourceLocation trampolineSouthModel = ModelDefinitions.TRAMPOLINE_SOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineWestModel = ModelDefinitions.TRAMPOLINE_WEST.create(block, textures, this.models::put);
        ResourceLocation trampolineNorthSouthModel = ModelDefinitions.TRAMPOLINE_NORTH_SOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineEastWestModel = ModelDefinitions.TRAMPOLINE_EAST_WEST.create(block, textures, this.models::put);
        ResourceLocation trampolineNorthEastModel = ModelDefinitions.TRAMPOLINE_NORTH_EAST.create(block, textures, this.models::put);
        ResourceLocation trampolineEastSouthModel = ModelDefinitions.TRAMPOLINE_EAST_SOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineSouthWestModel = ModelDefinitions.TRAMPOLINE_SOUTH_WEST.create(block, textures, this.models::put);
        ResourceLocation trampolineWestNorthModel = ModelDefinitions.TRAMPOLINE_WEST_NORTH.create(block, textures, this.models::put);
        ResourceLocation trampolineNorthEastWithLegModel = ModelDefinitions.TRAMPOLINE_NORTH_EAST_WITH_LEG.create(block, textures, this.models::put);
        ResourceLocation trampolineEastSouthWithLegModel = ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WITH_LEG.create(block, textures, this.models::put);
        ResourceLocation trampolineSouthWestWithLegModel = ModelDefinitions.TRAMPOLINE_SOUTH_WEST_WITH_LEG.create(block, textures, this.models::put);
        ResourceLocation trampolineWestNorthWithLegModel = ModelDefinitions.TRAMPOLINE_WEST_NORTH_WITH_LEG.create(block, textures, this.models::put);
        ResourceLocation trampolineNorthEastSouthModel = ModelDefinitions.TRAMPOLINE_NORTH_EAST_SOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineEastSouthWestModel = ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WEST.create(block, textures, this.models::put);
        ResourceLocation trampolineSouthWestNorthModel = ModelDefinitions.TRAMPOLINE_SOUTH_WEST_NORTH.create(block, textures, this.models::put);
        ResourceLocation trampolineWestNorthEastModel = ModelDefinitions.TRAMPOLINE_WEST_NORTH_EAST.create(block, textures, this.models::put);
        ResourceLocation trampolineNorthEastSouthWithLegNorthEastModel = ModelDefinitions.TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST.create(block, textures, this.models::put);
        ResourceLocation trampolineNorthEastSouthWithLegEastSouthModel = ModelDefinitions.TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_EASTSOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineNorthEastSouthWithLegNorthEastEastSouthModel = ModelDefinitions.TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST_EASTSOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineEastSouthWestWithLegEastSouthModel = ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineEastSouthWestWithLegSouthWestModel = ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_SOUTHWEST.create(block, textures, this.models::put);
        ResourceLocation trampolineEastSouthWestWithLegEastSouthSouthWestModel = ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH_SOUTHWEST.create(block, textures, this.models::put);
        ResourceLocation trampolineSouthWestNorthWithLegWestNorthModel = ModelDefinitions.TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH.create(block, textures, this.models::put);
        ResourceLocation trampolineSouthWestNorthWithLegSouthWestModel = ModelDefinitions.TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_SOUTHWEST.create(block, textures, this.models::put);
        ResourceLocation trampolineSouthWestNorthWithLegWestNorthSouthWestModel = ModelDefinitions.TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH_SOUTHWEST.create(block, textures, this.models::put);
        ResourceLocation trampolineWestNorthEastWithLegNorthEastModel = ModelDefinitions.TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_NORTHEAST.create(block, textures, this.models::put);
        ResourceLocation trampolineWestNorthEastWithLegWestNorthModel = ModelDefinitions.TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_WESTNORTH.create(block, textures, this.models::put);
        ResourceLocation trampolineWestNorthEastWithLegNorthEastWestNorthModel = ModelDefinitions.TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_NORTHEAST_WESTNORTH.create(block, textures, this.models::put);
        ResourceLocation trampolineAllModel = ModelDefinitions.TRAMPOLINE_ALL.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegAllModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_ALL.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegNorthEastModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegNorthEastEastSouthModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_EASTSOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegNorthEastEastSouthSouthWestModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_EASTSOUTH_SOUTHWEST.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegEastSouthModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegEastSouthSouthWestModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_SOUTHWEST.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegEastSouthSouthWestWestNorthModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_SOUTHWEST_WESTNORTH.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegSouthWestModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegSouthWestWestNorthModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST_WESTNORTH.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegSouthWestWestNorthNorthEastModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST_WESTNORTH_NORTHEAST.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegWestNorthModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_WESTNORTH.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegWestNorthNorthEastModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_WESTNORTH_NORTHEAST.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegWestNorthNorthEastEastSouthModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_WESTNORTH_NORTHEAST_EASTSOUTH.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegNorthEastSouthWestModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_SOUTHWEST.create(block, textures, this.models::put);
        ResourceLocation trampolineAllWithLegEastSouthWestNorthModel = ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_WESTNORTH.create(block, textures, this.models::put);
        this.registerItemWithModel(block, trampolineDefaultModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(TrampolineBlock.SHAPE)
                .select(TrampolineBlock.Shape.DEFAULT, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineDefaultModel))
                .select(TrampolineBlock.Shape.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineNorthModel))
                .select(TrampolineBlock.Shape.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineEastModel))
                .select(TrampolineBlock.Shape.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineSouthModel))
                .select(TrampolineBlock.Shape.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineWestModel))
                .select(TrampolineBlock.Shape.NORTH_SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineNorthSouthModel))
                .select(TrampolineBlock.Shape.EAST_WEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineEastWestModel))
                .select(TrampolineBlock.Shape.NORTH_EAST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineNorthEastModel))
                .select(TrampolineBlock.Shape.EAST_SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineEastSouthModel))
                .select(TrampolineBlock.Shape.SOUTH_WEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineSouthWestModel))
                .select(TrampolineBlock.Shape.WEST_NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineWestNorthModel))
                .select(TrampolineBlock.Shape.NORTH_EAST_WITH_LEG, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineNorthEastWithLegModel))
                .select(TrampolineBlock.Shape.EAST_SOUTH_WITH_LEG, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineEastSouthWithLegModel))
                .select(TrampolineBlock.Shape.SOUTH_WEST_WITH_LEG, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineSouthWestWithLegModel))
                .select(TrampolineBlock.Shape.WEST_NORTH_WITH_LEG, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineWestNorthWithLegModel))
                .select(TrampolineBlock.Shape.NORTH_EAST_SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineNorthEastSouthModel))
                .select(TrampolineBlock.Shape.EAST_SOUTH_WEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineEastSouthWestModel))
                .select(TrampolineBlock.Shape.SOUTH_WEST_NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineSouthWestNorthModel))
                .select(TrampolineBlock.Shape.WEST_NORTH_EAST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineWestNorthEastModel))
                .select(TrampolineBlock.Shape.NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineNorthEastSouthWithLegNorthEastModel))
                .select(TrampolineBlock.Shape.NORTH_EAST_SOUTH_WITH_LEG_EASTSOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineNorthEastSouthWithLegEastSouthModel))
                .select(TrampolineBlock.Shape.NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST_EASTSOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineNorthEastSouthWithLegNorthEastEastSouthModel))
                .select(TrampolineBlock.Shape.EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineEastSouthWestWithLegEastSouthModel))
                .select(TrampolineBlock.Shape.EAST_SOUTH_WEST_WITH_LEG_SOUTHWEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineEastSouthWestWithLegSouthWestModel))
                .select(TrampolineBlock.Shape.EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH_SOUTHWEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineEastSouthWestWithLegEastSouthSouthWestModel))
                .select(TrampolineBlock.Shape.SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineSouthWestNorthWithLegWestNorthModel))
                .select(TrampolineBlock.Shape.SOUTH_WEST_NORTH_WITH_LEG_SOUTHWEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineSouthWestNorthWithLegSouthWestModel))
                .select(TrampolineBlock.Shape.SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH_SOUTHWEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineSouthWestNorthWithLegWestNorthSouthWestModel))
                .select(TrampolineBlock.Shape.WEST_NORTH_EAST_WITH_LEG_NORTHEAST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineWestNorthEastWithLegNorthEastModel))
                .select(TrampolineBlock.Shape.WEST_NORTH_EAST_WITH_LEG_WESTNORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineWestNorthEastWithLegWestNorthModel))
                .select(TrampolineBlock.Shape.WEST_NORTH_EAST_WITH_LEG_NORTHEAST_WESTNORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineWestNorthEastWithLegNorthEastWestNorthModel))
                .select(TrampolineBlock.Shape.ALL, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_ALL, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegAllModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegNorthEastModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST_EASTSOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegNorthEastEastSouthModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST_EASTSOUTH_SOUTHWEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegNorthEastEastSouthSouthWestModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegEastSouthModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH_SOUTHWEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegEastSouthSouthWestModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH_SOUTHWEST_WESTNORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegEastSouthSouthWestWestNorthModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_SOUTHWEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegSouthWestModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_SOUTHWEST_WESTNORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegSouthWestWestNorthModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_SOUTHWEST_WESTNORTH_NORTHEAST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegSouthWestWestNorthNorthEastModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_WESTNORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegWestNorthModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_WESTNORTH_NORTHEAST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegWestNorthNorthEastModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_WESTNORTH_NORTHEAST_EASTSOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegWestNorthNorthEastEastSouthModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST_SOUTHWEST, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegNorthEastSouthWestModel))
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH_WESTNORTH, Variant.variant()
                    .with(VariantProperties.MODEL, trampolineAllWithLegEastSouthWestNorthModel))));
    }

    private void plate(PlateBlock block)
    {
        this.registerItemWithModel(block, Utils.resource("block/apricity_plate"));
        this.generators.put(block, MultiVariantGenerator.multiVariant(block,
            Variant.variant().with(VariantProperties.MODEL, Utils.resource("block/apricity_plate")),
            Variant.variant().with(VariantProperties.MODEL, Utils.resource("block/stardust_plate")),
            Variant.variant().with(VariantProperties.MODEL, Utils.resource("block/cerulean_plate")),
            Variant.variant().with(VariantProperties.MODEL, Utils.resource("block/tuscan_plate"))
        ));
    }

    private void stool(StoolBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation stoolModel =  ModelDefinitions.STOOL.create(block, textures, this.models::put);
        this.registerItemWithModel(block, stoolModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block,
            Variant.variant().with(VariantProperties.MODEL, stoolModel)));
    }

    private void hedge(HedgeBlock block, int tint)
    {
        LeafType type = block.getLeafType();
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.leafTexture(type))
            .put(TextureSlot.TEXTURE, this.leafTexture(type));
        ResourceLocation hedgeCenterSideModel = ModelDefinitions.HEDGE_CENTER_SIDE.create(block, textures, this.models::put);
        ResourceLocation hedgeConnectionStyleOneModel = ModelDefinitions.HEDGE_CONNECTION_STYLE_1.create(block, textures, this.models::put);
        ResourceLocation hedgeConnectionStyleTwoModel = ModelDefinitions.HEDGE_CONNECTION_STYLE_2.create(block, textures, this.models::put);
        ResourceLocation hedgeConnectionStyleThreeModel = ModelDefinitions.HEDGE_CONNECTION_STYLE_3.create(block, textures, this.models::put);
        this.generators.put(block, MultiPartGenerator.multiPart(block)
            .with(List.of(
                Variant.variant().with(VariantProperties.MODEL, ModelDefinitions.HEDGE_CENTER_STYLE_1.create(block, textures, this.models::put)),
                Variant.variant().with(VariantProperties.MODEL, ModelDefinitions.HEDGE_CENTER_STYLE_2.create(block, textures, this.models::put)),
                Variant.variant().with(VariantProperties.MODEL, ModelDefinitions.HEDGE_CENTER_STYLE_3.create(block, textures, this.models::put))
            ))
            .with(Condition.condition().term(HedgeBlock.NORTH, false),
                Variant.variant().with(VariantProperties.MODEL, hedgeCenterSideModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
            .with(Condition.condition().term(HedgeBlock.EAST, false),
                Variant.variant().with(VariantProperties.MODEL, hedgeCenterSideModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
            .with(Condition.condition().term(HedgeBlock.SOUTH, false),
                Variant.variant().with(VariantProperties.MODEL, hedgeCenterSideModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
            .with(Condition.condition().term(HedgeBlock.WEST, false),
                Variant.variant().with(VariantProperties.MODEL, hedgeCenterSideModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
            .with(Condition.condition().term(HedgeBlock.NORTH, true),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleOneModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleTwoModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleThreeModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
            )
            .with(Condition.condition().term(HedgeBlock.EAST, true),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleOneModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleTwoModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleThreeModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
            )
            .with(Condition.condition().term(HedgeBlock.SOUTH, true),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleOneModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleTwoModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleThreeModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0)
            )
            .with(Condition.condition().term(HedgeBlock.WEST, true),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleOneModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleTwoModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
                Variant.variant().with(VariantProperties.MODEL, hedgeConnectionStyleThreeModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            )
        );

        ResourceLocation hedgeItemModel = ModelDefinitions.HEDGE.create(block.asItem(), textures, this.models::put);
        if(tint != -1)
        {
            this.items.put(block.asItem(), this.createClientItem(ItemModelUtils.tintedModel(hedgeItemModel, ItemModelUtils.constantTint(tint))));
        }
        else
        {
            this.registerItemWithModel(block, hedgeItemModel);
        }
    }

    private void steppingStones(SteppingStoneBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.stoneTexture(block.getStoneType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation steppingStonesStyleOneModel = ModelDefinitions.STEPPING_STONES_STYLE_1.create(block, textures, this.models::put);
        ResourceLocation steppingStonesStyleTwoModel = ModelDefinitions.STEPPING_STONES_STYLE_2.create(block, textures, this.models::put);
        ResourceLocation steppingStonesStyleThreeModel = ModelDefinitions.STEPPING_STONES_STYLE_3.create(block, textures, this.models::put);
        ResourceLocation steppingStonesStyleFourModel = ModelDefinitions.STEPPING_STONES_STYLE_4.create(block, textures, this.models::put);
        this.registerItemWithModel(block, steppingStonesStyleOneModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(SteppingStoneBlock.DIRECTION)
                .select(Direction.NORTH, List.of(
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleOneModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleTwoModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleThreeModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleFourModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0)
                ))
                .select(Direction.EAST, List.of(
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleOneModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleTwoModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleThreeModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleFourModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                ))
                .select(Direction.SOUTH, List.of(
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleOneModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleTwoModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleThreeModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleFourModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                ))
                .select(Direction.WEST, List.of(
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleOneModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleTwoModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleThreeModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270),
                    Variant.variant().with(VariantProperties.MODEL, steppingStonesStyleFourModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                ))
            )
        );
    }

    private void woodenToilet(WoodenToiletBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation toiletModel = ModelDefinitions.TOILET.create(block, textures, this.models::put);
        this.registerItemWithModel(block, toiletModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(ToiletBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, toiletModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, toiletModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, toiletModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, toiletModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void colouredToilet(ColouredToiletBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation toiletModel = ModelDefinitions.TOILET.create(block, textures, this.models::put);
        this.registerItemWithModel(block, toiletModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(ToiletBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, toiletModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, toiletModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, toiletModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, toiletModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void woodenBasin(WoodenBasinBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation basinModel = ModelDefinitions.BASIN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, basinModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(BasinBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, basinModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, basinModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, basinModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, basinModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void colouredBasin(ColouredBasinBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation basinModel = ModelDefinitions.BASIN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, basinModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(BasinBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, basinModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, basinModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, basinModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, basinModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void woodenBath(WoodenBathBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation bathHeadModel = ModelDefinitions.BATH_HEAD.create(block, textures, this.models::put);
        ResourceLocation bathBottomModel = ModelDefinitions.BATH_BOTTOM.create(block, textures, this.models::put);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(BathBlock.DIRECTION, BathBlock.TYPE)
                .select(Direction.NORTH, BathBlock.Type.HEAD, Variant.variant()
                    .with(VariantProperties.MODEL, bathHeadModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, BathBlock.Type.HEAD, Variant.variant()
                    .with(VariantProperties.MODEL, bathHeadModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, BathBlock.Type.HEAD, Variant.variant()
                    .with(VariantProperties.MODEL, bathHeadModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, BathBlock.Type.HEAD, Variant.variant()
                    .with(VariantProperties.MODEL, bathHeadModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, BathBlock.Type.BOTTOM, Variant.variant()
                    .with(VariantProperties.MODEL, bathBottomModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, BathBlock.Type.BOTTOM, Variant.variant()
                    .with(VariantProperties.MODEL, bathBottomModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, BathBlock.Type.BOTTOM, Variant.variant()
                    .with(VariantProperties.MODEL, bathBottomModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, BathBlock.Type.BOTTOM, Variant.variant()
                    .with(VariantProperties.MODEL, bathBottomModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
        this.registerItemWithModel(block, ModelDefinitions.BATH.create(block.asItem(), textures, this.models::put));
    }

    private void colouredBath(ColouredBathBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation bathHeadModel = ModelDefinitions.BATH_HEAD.create(block, textures, this.models::put);
        ResourceLocation bathBottomModel = ModelDefinitions.BATH_BOTTOM.create(block, textures, this.models::put);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(BathBlock.DIRECTION, BathBlock.TYPE)
                .select(Direction.NORTH, BathBlock.Type.HEAD, Variant.variant()
                    .with(VariantProperties.MODEL, bathHeadModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, BathBlock.Type.HEAD, Variant.variant()
                    .with(VariantProperties.MODEL, bathHeadModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, BathBlock.Type.HEAD, Variant.variant()
                    .with(VariantProperties.MODEL, bathHeadModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, BathBlock.Type.HEAD, Variant.variant()
                    .with(VariantProperties.MODEL, bathHeadModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, BathBlock.Type.BOTTOM, Variant.variant()
                    .with(VariantProperties.MODEL, bathBottomModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, BathBlock.Type.BOTTOM, Variant.variant()
                    .with(VariantProperties.MODEL, bathBottomModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, BathBlock.Type.BOTTOM, Variant.variant()
                    .with(VariantProperties.MODEL, bathBottomModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, BathBlock.Type.BOTTOM, Variant.variant()
                    .with(VariantProperties.MODEL, bathBottomModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
        this.registerItemWithModel(block, ModelDefinitions.BATH.create(block, textures, this.models::put));
    }

    private void latticeFence(LatticeFenceBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation latticeFenceCenterModel = ModelDefinitions.LATTICE_FENCE_CENTER.create(block, textures, this.models::put);
        ResourceLocation latticeFenceConnectionModel = ModelDefinitions.LATTICE_FENCE_CONNECTION.create(block, textures, this.models::put);
        this.generators.put(block, MultiPartGenerator.multiPart(block)
            .with(Variant.variant().with(VariantProperties.MODEL, latticeFenceCenterModel))
            .with(Condition.condition().term(LatticeFenceBlock.NORTH, true), Variant.variant()
                .with(VariantProperties.MODEL, latticeFenceConnectionModel)
                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
            .with(Condition.condition().term(LatticeFenceBlock.EAST, true), Variant.variant()
                .with(VariantProperties.MODEL, latticeFenceConnectionModel)
                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
            .with(Condition.condition().term(LatticeFenceBlock.SOUTH, true), Variant.variant()
                .with(VariantProperties.MODEL, latticeFenceConnectionModel)
                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
            .with(Condition.condition().term(LatticeFenceBlock.WEST, true), Variant.variant()
                .with(VariantProperties.MODEL, latticeFenceConnectionModel)
                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)));
        this.registerItemWithModel(block, ModelDefinitions.LATTICE_FENCE.create(block.asItem(), textures, this.models::put));
    }

    private void latticeFenceGate(LatticeFenceGateBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        ResourceLocation latticeFenceGateClosedModel = ModelDefinitions.LATTICE_FENCE_GATE_CLOSED.create(block, textures, this.models::put);
        ResourceLocation latticeFenceGateOpenModel = ModelDefinitions.LATTICE_FENCE_GATE_OPEN.create(block, textures, this.models::put);
        this.registerItemWithModel(block, latticeFenceGateClosedModel);
        this.generators.put(block, MultiPartGenerator.multiPart(block)
            .with(Condition.condition().term(LatticeFenceGateBlock.FACING, Direction.NORTH).term(LatticeFenceGateBlock.OPEN, false), Variant.variant().with(VariantProperties.MODEL, latticeFenceGateClosedModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
            .with(Condition.condition().term(LatticeFenceGateBlock.FACING, Direction.EAST).term(LatticeFenceGateBlock.OPEN, false), Variant.variant().with(VariantProperties.MODEL, latticeFenceGateClosedModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
            .with(Condition.condition().term(LatticeFenceGateBlock.FACING, Direction.SOUTH).term(LatticeFenceGateBlock.OPEN, false), Variant.variant().with(VariantProperties.MODEL, latticeFenceGateClosedModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
            .with(Condition.condition().term(LatticeFenceGateBlock.FACING, Direction.WEST).term(LatticeFenceGateBlock.OPEN, false), Variant.variant().with(VariantProperties.MODEL, latticeFenceGateClosedModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
            .with(Condition.condition().term(LatticeFenceGateBlock.FACING, Direction.NORTH).term(LatticeFenceGateBlock.OPEN, true), Variant.variant().with(VariantProperties.MODEL, latticeFenceGateOpenModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
            .with(Condition.condition().term(LatticeFenceGateBlock.FACING, Direction.EAST).term(LatticeFenceGateBlock.OPEN, true), Variant.variant().with(VariantProperties.MODEL, latticeFenceGateOpenModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
            .with(Condition.condition().term(LatticeFenceGateBlock.FACING, Direction.SOUTH).term(LatticeFenceGateBlock.OPEN, true), Variant.variant().with(VariantProperties.MODEL, latticeFenceGateOpenModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
            .with(Condition.condition().term(LatticeFenceGateBlock.FACING, Direction.WEST).term(LatticeFenceGateBlock.OPEN, true), Variant.variant().with(VariantProperties.MODEL, latticeFenceGateOpenModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
        );
    }

    private void television(TelevisionBlock block)
    {
        ResourceLocation televisionOffModel = Utils.resource("block/television_off");
        ResourceLocation televisionOnModel = Utils.resource("block/television_on");
        this.registerItemWithModel(block, televisionOffModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(TelevisionBlock.DIRECTION, TelevisionBlock.POWERED)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, televisionOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, televisionOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, televisionOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, televisionOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, televisionOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, televisionOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, televisionOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, televisionOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void computer(ComputerBlock block)
    {
        ResourceLocation computerOffModel = Utils.resource("block/computer_off");
        ResourceLocation computerOnModel = Utils.resource("block/computer_on");
        this.registerItemWithModel(block, computerOffModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(ComputerBlock.DIRECTION, ComputerBlock.POWERED)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, computerOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, computerOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, computerOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, computerOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, computerOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, computerOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, computerOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, computerOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void doorMat(DoorMatBlock block)
    {
        ResourceLocation doorMatModel = Utils.resource("block/door_mat");
        this.registerItemWithModel(block, doorMatModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(DoorMatBlock.DIRECTION)
                .select(Direction.NORTH, Variant.variant()
                    .with(VariantProperties.MODEL, doorMatModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, Variant.variant()
                    .with(VariantProperties.MODEL, doorMatModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                    .with(VariantProperties.MODEL, doorMatModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                    .with(VariantProperties.MODEL, doorMatModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    private void workbench(WorkbenchBlock block)
    {
        ResourceLocation workbenchOffModel = Utils.resource("block/workbench_off");
        ResourceLocation workbenchOnModel = Utils.resource("block/workbench_on");
        this.registerItemWithModel(block, workbenchOnModel);
        this.generators.put(block, MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.properties(WorkbenchBlock.DIRECTION, WorkbenchBlock.POWERED)
                .select(Direction.NORTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, workbenchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, false, Variant.variant()
                    .with(VariantProperties.MODEL, workbenchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, false, Variant.variant()
                    .with(VariantProperties.MODEL, workbenchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant()
                    .with(VariantProperties.MODEL, workbenchOffModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, workbenchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.EAST, true, Variant.variant()
                    .with(VariantProperties.MODEL, workbenchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, true, Variant.variant()
                    .with(VariantProperties.MODEL, workbenchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, true, Variant.variant()
                    .with(VariantProperties.MODEL, workbenchOnModel)
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }
}
