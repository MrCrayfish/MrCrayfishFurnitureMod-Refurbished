package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.api.datagen.FrameworkGenerator;
import com.mrcrayfish.furniture.refurbished.block.*;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.data.model.ModelDefinitions;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.multipart.CombinedCondition;
import net.minecraft.client.renderer.block.dispatch.multipart.Condition;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("UnstableApiUsage")
public class CommonBlockStatesGenerator extends FrameworkGenerator
{
    public CommonBlockStatesGenerator(Map<Block, BlockModelDefinitionGenerator> generators, Map<Item, ClientItem> items, Map<Identifier, ModelInstance> models)
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
        this.table(ModBlocks.TABLE_PALE_OAK.get());
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
        this.chair(ModBlocks.CHAIR_PALE_OAK.get());
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
        this.desk(ModBlocks.DESK_PALE_OAK.get());
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
        this.drawer(ModBlocks.DRAWER_PALE_OAK.get());
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
        this.crate(ModBlocks.CRATE_PALE_OAK.get());
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
        this.woodenKitchenCabinetry(ModBlocks.KITCHEN_CABINETRY_PALE_OAK.get());
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
        this.woodenKitchenDrawer(ModBlocks.KITCHEN_DRAWER_PALE_OAK.get());
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
        this.woodenKitchenSink(ModBlocks.KITCHEN_SINK_PALE_OAK.get());
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
        this.cuttingBoard(ModBlocks.CUTTING_BOARD_PALE_OAK.get());
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
        this.mailbox(ModBlocks.MAIL_BOX_PALE_OAK.get());
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
        this.storageJar(ModBlocks.STORAGE_JAR_PALE_OAK.get());
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
        this.ceilingFan(ModBlocks.CEILING_FAN_PALE_OAK_LIGHT.get());
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
        this.ceilingFan(ModBlocks.CEILING_FAN_PALE_OAK_DARK.get());
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
        this.storageCabinet(ModBlocks.STORAGE_CABINET_PALE_OAK.get());
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
        this.woodenKitchenCabinet(ModBlocks.KITCHEN_STORAGE_CABINET_PALE_OAK.get());
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
        this.hedge(ModBlocks.HEDGE_PALE_OAK.get(), -1); // TODO needs tint?
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
        this.woodenToilet(ModBlocks.TOILET_PALE_OAK.get());
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
        this.woodenBasin(ModBlocks.BASIN_PALE_OAK.get());
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
        this.woodenBath(ModBlocks.BATH_PALE_OAK.get());
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
        this.latticeFence(ModBlocks.LATTICE_FENCE_PALE_OAK.get());
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
        this.latticeFenceGate(ModBlocks.LATTICE_FENCE_GATE_PALE_OAK.get());
        this.television(ModBlocks.TELEVISION.get());
        this.computer(ModBlocks.COMPUTER.get());
        this.doorMat(ModBlocks.DOOR_MAT.get());
        this.workbench(ModBlocks.WORKBENCH.get());
    }

    private Material blockTexture(Block block)
    {
        Identifier name = BuiltInRegistries.BLOCK.getKey(block);
        return new Material(Identifier.fromNamespaceAndPath(name.getNamespace(), "block/" + name.getPath()));
    }

    private Material woodParticle(WoodType type)
    {
        return new Material(Utils.id("block/" + type.name() + "_particle"));
    }

    private Material colourParticle(DyeColor color)
    {
        return new Material(Utils.id("block/" + color.getName() + "_particle"));
    }

    private Material metalParticle(MetalType type)
    {
        return new Material(Utils.id("block/" + type.getName() + "_particle"));
    }

    private Material leafTexture(LeafType type)
    {
        return new Material(Identifier.withDefaultNamespace("block/" + type.getName() + "_leaves"));
    }

    private Material stoneTexture(StoneType type)
    {
        return new Material(Identifier.withDefaultNamespace("block/" + type.getName()));
    }

    private void registerItemWithModel(Block block, Identifier location)
    {
        this.items.put(block.asItem(), this.createClientItem(ItemModelUtils.plainModel(location)));
    }

    private void registerItemWithModelFromVariant(Block block, Variant variant)
    {
        Identifier location = variant.modelLocation();
        this.items.put(block.asItem(), this.createClientItem(ItemModelUtils.plainModel(location)));
    }

    private void registerItemWithModelFromMultiVariant(Block block, MultiVariant variant)
    {
        Identifier location = variant.variants().unwrap().getFirst().value().modelLocation();
        this.items.put(block.asItem(), this.createClientItem(ItemModelUtils.plainModel(location)));
    }

    private void registerItemWithModel(Item item, Identifier location)
    {
        this.items.put(item, this.createClientItem(ItemModelUtils.plainModel(location)));
    }

    private Variant plainModel(Identifier location)
    {
        return new Variant(location);
    }

    private MultiVariant variant(Variant variant)
    {
        return new MultiVariant(WeightedList.of(variant));
    }

    private MultiVariant variants(Variant... variants)
    {
        return new MultiVariant(WeightedList.of(Arrays.stream(variants).map(variant -> new Weighted<>(variant, 1)).toList()));
    }

    private MultiVariant plainVariant(Identifier location)
    {
        return this.variant(this.plainModel(location));
    }

    private ConditionBuilder condition()
    {
        return new ConditionBuilder();
    }

    private Condition or(ConditionBuilder... builders)
    {
        return new CombinedCondition(CombinedCondition.Operation.OR, Stream.of(builders).map(ConditionBuilder::build).toList());
    }

    private void table(TableBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant baseTableVariant = this.plainVariant(ModelDefinitions.TABLE.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, baseTableVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(TableBlock.NORTH, TableBlock.EAST, TableBlock.SOUTH, TableBlock.WEST)
                .select(false, false, false, false, baseTableVariant)
                .select(true, false, false, false, this.plainVariant(ModelDefinitions.TABLE_NORTH.create(block, textures, this.models::put)))
                .select(true, true, false, false, this.plainVariant(ModelDefinitions.TABLE_NORTH_EAST.create(block, textures, this.models::put)))
                .select(true, true, true, false, this.plainVariant(ModelDefinitions.TABLE_NORTH_EAST_SOUTH.create(block, textures, this.models::put)))
                .select(false, true, false, false, this.plainVariant(ModelDefinitions.TABLE_EAST.create(block, textures, this.models::put)))
                .select(false, true, true, false, this.plainVariant(ModelDefinitions.TABLE_EAST_SOUTH.create(block, textures, this.models::put)))
                .select(false, true, true, true, this.plainVariant(ModelDefinitions.TABLE_EAST_SOUTH_WEST.create(block, textures, this.models::put)))
                .select(false, false, true, false, this.plainVariant(ModelDefinitions.TABLE_SOUTH.create(block, textures, this.models::put)))
                .select(false, false, true, true, this.plainVariant(ModelDefinitions.TABLE_SOUTH_WEST.create(block, textures, this.models::put)))
                .select(true, false, true, true, this.plainVariant(ModelDefinitions.TABLE_SOUTH_WEST_NORTH.create(block, textures, this.models::put)))
                .select(false, false, false, true, this.plainVariant(ModelDefinitions.TABLE_WEST.create(block, textures, this.models::put)))
                .select(true, false, false, true, this.plainVariant(ModelDefinitions.TABLE_WEST_NORTH.create(block, textures, this.models::put)))
                .select(true, true, false, true, this.plainVariant(ModelDefinitions.TABLE_WEST_NORTH_EAST.create(block, textures, this.models::put)))
                .select(true, false, true, false, this.plainVariant(ModelDefinitions.TABLE_NORTH_SOUTH.create(block, textures, this.models::put)))
                .select(false, true, false, true, this.plainVariant(ModelDefinitions.TABLE_EAST_WEST.create(block, textures, this.models::put)))
                .select(true, true, true, true, this.plainVariant(ModelDefinitions.TABLE_NORTH_EAST_SOUTH_WEST.create(block, textures, this.models::put)))
            )
        );
    }

    private void chair(ChairBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant baseChairVariant = this.plainVariant(ModelDefinitions.CHAIR.create(block, textures, this.models::put));
        MultiVariant tuckedChairVariant = this.plainVariant(ModelDefinitions.CHAIR_TUCKED.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, baseChairVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(ChairBlock.DIRECTION, ChairBlock.TUCKED)
                .select(Direction.NORTH, false, baseChairVariant)
                .select(Direction.EAST, false, baseChairVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, baseChairVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, baseChairVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, tuckedChairVariant)
                .select(Direction.EAST, true, tuckedChairVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, tuckedChairVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, tuckedChairVariant.with(Y_ROT_270))));
    }

    private void desk(DeskBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant baseDeskVariant = this.plainVariant(ModelDefinitions.DESK.create(block, textures, this.models::put));
        MultiVariant deskLeftVariant = this.plainVariant(ModelDefinitions.DESK_LEFT.create(block, textures, this.models::put));
        MultiVariant deskMiddleVariant = this.plainVariant(ModelDefinitions.DESK_MIDDLE.create(block, textures, this.models::put));
        MultiVariant deskRightVariant = this.plainVariant(ModelDefinitions.DESK_RIGHT.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, baseDeskVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(DeskBlock.DIRECTION, DeskBlock.LEFT, DeskBlock.RIGHT)
                .select(Direction.NORTH, false, false, baseDeskVariant)
                .select(Direction.EAST, false, false, baseDeskVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, false, baseDeskVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, false, baseDeskVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, false, deskLeftVariant)
                .select(Direction.EAST, true, false, deskLeftVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, false, deskLeftVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, false, deskLeftVariant.with(Y_ROT_270))
                .select(Direction.NORTH, false, true, deskRightVariant)
                .select(Direction.EAST, false, true, deskRightVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, true, deskRightVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, true, deskRightVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, true, deskMiddleVariant)
                .select(Direction.EAST, true, true, deskMiddleVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, true, deskMiddleVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, true, deskMiddleVariant.with(Y_ROT_270))));
    }

    private void drawer(DrawerBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant drawerClosedVariant = this.plainVariant(ModelDefinitions.DRAWER_CLOSED.create(block, textures, this.models::put));
        MultiVariant drawerOpenVariant = this.plainVariant(ModelDefinitions.DRAWER_OPEN.create(block, textures, this.models::put));
        MultiVariant drawerLeftClosedVariant = this.plainVariant(ModelDefinitions.DRAWER_LEFT_CLOSED.create(block, textures, this.models::put));
        MultiVariant drawerLeftOpenVariant = this.plainVariant(ModelDefinitions.DRAWER_LEFT_OPEN.create(block, textures, this.models::put));
        MultiVariant drawerRightClosedVariant = this.plainVariant(ModelDefinitions.DRAWER_RIGHT_CLOSED.create(block, textures, this.models::put));
        MultiVariant drawerRightOpenVariant = this.plainVariant(ModelDefinitions.DRAWER_RIGHT_OPEN.create(block, textures, this.models::put));
        MultiVariant drawerMiddleClosedVariant = this.plainVariant(ModelDefinitions.DRAWER_MIDDLE_CLOSED.create(block, textures, this.models::put));
        MultiVariant drawerMiddleOpenVariant = this.plainVariant(ModelDefinitions.DRAWER_MIDDLE_OPEN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, drawerClosedVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(DrawerBlock.DIRECTION, DrawerBlock.LEFT, DrawerBlock.RIGHT, DrawerBlock.OPEN)
                .select(Direction.NORTH, false, false, false, drawerClosedVariant)
                .select(Direction.EAST, false, false, false, drawerClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, false, false, drawerClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, false, false, drawerClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, false, false, drawerLeftClosedVariant)
                .select(Direction.EAST, true, false, false, drawerLeftClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, false, false, drawerLeftClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, false, false, drawerLeftClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, false, true, false, drawerRightClosedVariant)
                .select(Direction.EAST, false, true, false, drawerRightClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, true, false, drawerRightClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, true, false, drawerRightClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, true, false, drawerMiddleClosedVariant)
                .select(Direction.EAST, true, true, false, drawerMiddleClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, true, false, drawerMiddleClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, true, false, drawerMiddleClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, false, false, true, drawerOpenVariant)
                .select(Direction.EAST, false, false, true, drawerOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, false, true, drawerOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, false, true, drawerOpenVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, false, true, drawerLeftOpenVariant)
                .select(Direction.EAST, true, false, true, drawerLeftOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, false, true, drawerLeftOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, false, true, drawerLeftOpenVariant.with(Y_ROT_270))
                .select(Direction.NORTH, false, true, true, drawerRightOpenVariant)
                .select(Direction.EAST, false, true, true, drawerRightOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, true, true, drawerRightOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, true, true, drawerRightOpenVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, true, true, drawerMiddleOpenVariant)
                .select(Direction.EAST, true, true, true, drawerMiddleOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, true, true, drawerMiddleOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, true, true, drawerMiddleOpenVariant.with(Y_ROT_270))));
    }

    private void crate(CrateBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant closedCrateVariant = this.plainVariant(ModelDefinitions.CRATE_CLOSED.create(block, textures, this.models::put));
        MultiVariant openCrateVariant = this.plainVariant(ModelDefinitions.CRATE_OPEN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, closedCrateVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(CrateBlock.OPEN)
                .select(false, closedCrateVariant)
                .select(true, openCrateVariant)));
    }

    private void woodenKitchenCabinetry(WoodenKitchenCabinetryBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant defaultCabinetryVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_DEFAULT.create(block, textures, this.models::put));
        MultiVariant cabinetryInsideCornerLeftVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.create(block, textures, this.models::put));
        MultiVariant cabinetryInsideCornerRightVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.create(block, textures, this.models::put));
        MultiVariant cabinetryOutsideCornerLeftVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.create(block, textures, this.models::put));
        MultiVariant cabinetryOutsideCornerRightVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, defaultCabinetryVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(WoodenKitchenCabinetryBlock.DIRECTION, WoodenKitchenCabinetryBlock.SHAPE)
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.DEFAULT, defaultCabinetryVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.DEFAULT, defaultCabinetryVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.DEFAULT, defaultCabinetryVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.DEFAULT, defaultCabinetryVariant.with(Y_ROT_270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, cabinetryInsideCornerLeftVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, cabinetryInsideCornerLeftVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, cabinetryInsideCornerLeftVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, cabinetryInsideCornerLeftVariant.with(Y_ROT_270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, cabinetryInsideCornerRightVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, cabinetryInsideCornerRightVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, cabinetryInsideCornerRightVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, cabinetryInsideCornerRightVariant.with(Y_ROT_270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, cabinetryOutsideCornerLeftVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, cabinetryOutsideCornerLeftVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, cabinetryOutsideCornerLeftVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, cabinetryOutsideCornerLeftVariant.with(Y_ROT_270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, cabinetryOutsideCornerRightVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, cabinetryOutsideCornerRightVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, cabinetryOutsideCornerRightVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, cabinetryOutsideCornerRightVariant.with(Y_ROT_270))));
    }

    private void woodenKitchenDrawer(WoodenKitchenDrawerBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant drawerClosedVariant = this.plainVariant(ModelDefinitions.KITCHEN_DRAWER_CLOSED.create(block, textures, this.models::put));
        MultiVariant drawerOpenVariant = this.plainVariant(ModelDefinitions.KITCHEN_DRAWER_OPEN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, drawerClosedVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(DrawerBlock.DIRECTION, DrawerBlock.OPEN)
                .select(Direction.NORTH, false, drawerClosedVariant)
                .select(Direction.EAST, false, drawerClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, drawerClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, drawerClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, drawerOpenVariant)
                .select(Direction.EAST, true, drawerOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, drawerOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, drawerOpenVariant.with(Y_ROT_270))));
    }

    private void woodenKitchenSink(WoodenKitchenSinkBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant baseKitchenSinkVariant = this.plainVariant(ModelDefinitions.KITCHEN_SINK.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, baseKitchenSinkVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(WoodenKitchenSinkBlock.DIRECTION)
                .select(Direction.NORTH, baseKitchenSinkVariant)
                .select(Direction.EAST, baseKitchenSinkVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, baseKitchenSinkVariant.with(Y_ROT_180))
                .select(Direction.WEST, baseKitchenSinkVariant.with(Y_ROT_270))));
    }

    private void colouredKitchenCabinetry(ColouredKitchenCabinetryBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant defaultCabinetryVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_DEFAULT.create(block, textures, this.models::put));
        MultiVariant cabinetryInsideCornerLeftVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.create(block, textures, this.models::put));
        MultiVariant cabinetryInsideCornerRightVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.create(block, textures, this.models::put));
        MultiVariant cabinetryOutsideCornerLeftVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.create(block, textures, this.models::put));
        MultiVariant cabinetryOutsideCornerRightVariant = this.plainVariant(ModelDefinitions.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, defaultCabinetryVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(ColouredKitchenCabinetryBlock.DIRECTION, ColouredKitchenCabinetryBlock.SHAPE)
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.DEFAULT, defaultCabinetryVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.DEFAULT, defaultCabinetryVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.DEFAULT, defaultCabinetryVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.DEFAULT, defaultCabinetryVariant.with(Y_ROT_270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, cabinetryInsideCornerLeftVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, cabinetryInsideCornerLeftVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, cabinetryInsideCornerLeftVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT, cabinetryInsideCornerLeftVariant.with(Y_ROT_270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, cabinetryInsideCornerRightVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, cabinetryInsideCornerRightVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, cabinetryInsideCornerRightVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT, cabinetryInsideCornerRightVariant.with(Y_ROT_270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, cabinetryOutsideCornerLeftVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, cabinetryOutsideCornerLeftVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, cabinetryOutsideCornerLeftVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT, cabinetryOutsideCornerLeftVariant.with(Y_ROT_270))
                .select(Direction.NORTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, cabinetryOutsideCornerRightVariant)
                .select(Direction.EAST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, cabinetryOutsideCornerRightVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, cabinetryOutsideCornerRightVariant.with(Y_ROT_180))
                .select(Direction.WEST, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT, cabinetryOutsideCornerRightVariant.with(Y_ROT_270))));
    }

    private void colouredKitchenDrawer(ColouredKitchenDrawerBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant drawerClosedVariant = this.plainVariant(ModelDefinitions.KITCHEN_DRAWER_CLOSED.create(block, textures, this.models::put));
        MultiVariant drawerOpenVariant = this.plainVariant(ModelDefinitions.KITCHEN_DRAWER_OPEN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, drawerClosedVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(ColouredKitchenDrawerBlock.DIRECTION, ColouredKitchenDrawerBlock.OPEN)
                .select(Direction.NORTH, false, drawerClosedVariant)
                .select(Direction.EAST, false, drawerClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, drawerClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, drawerClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, drawerOpenVariant)
                .select(Direction.EAST, true, drawerOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, drawerOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, drawerOpenVariant.with(Y_ROT_270))));
    }

    private void colouredKitchenSink(ColouredKitchenSinkBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant baseKitchenSinkVariant = this.plainVariant(ModelDefinitions.KITCHEN_SINK.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, baseKitchenSinkVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(WoodenKitchenSinkBlock.DIRECTION)
                .select(Direction.NORTH, baseKitchenSinkVariant)
                .select(Direction.EAST, baseKitchenSinkVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, baseKitchenSinkVariant.with(Y_ROT_180))
                .select(Direction.WEST, baseKitchenSinkVariant.with(Y_ROT_270))));
    }

    private void grill(GrillBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant grillVariant = this.plainVariant(ModelDefinitions.GRILL.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, grillVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(GrillBlock.DIRECTION)
                .select(Direction.NORTH, grillVariant)
                .select(Direction.EAST, grillVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, grillVariant.with(Y_ROT_180))
                .select(Direction.WEST, grillVariant.with(Y_ROT_270))));
    }

    private void cooler(CoolerBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant coolerClosedVariant = this.plainVariant(ModelDefinitions.COOLER_CLOSED.create(block, textures, this.models::put));
        MultiVariant coolerOpenVariant = this.plainVariant(ModelDefinitions.COOLER_OPEN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, coolerClosedVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(CoolerBlock.DIRECTION, CoolerBlock.OPEN)
                .select(Direction.NORTH, false, coolerClosedVariant)
                .select(Direction.EAST, false, coolerClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, coolerClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, coolerClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, coolerOpenVariant)
                .select(Direction.EAST, true, coolerOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, coolerOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, coolerOpenVariant.with(Y_ROT_270))));
    }

    private void fridge(FridgeBlock fridgeBlock, FreezerBlock freezerBlock)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(fridgeBlock.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(fridgeBlock));

        MultiVariant fridgeClosedVariant = this.plainVariant(ModelDefinitions.FRIDGE_CLOSED.create(fridgeBlock, textures, this.models::put));
        MultiVariant fridgeOpenVariant = this.plainVariant(ModelDefinitions.FRIDGE_OPEN.create(fridgeBlock, textures, this.models::put));
        this.generators.put(fridgeBlock, MultiVariantGenerator.dispatch(fridgeBlock)
            .with(PropertyDispatch.initial(FridgeBlock.DIRECTION, FridgeBlock.OPEN)
                .select(Direction.NORTH, false, fridgeClosedVariant)
                .select(Direction.EAST, false, fridgeClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, fridgeClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, fridgeClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, fridgeOpenVariant)
                .select(Direction.EAST, true, fridgeOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, fridgeOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, fridgeOpenVariant.with(Y_ROT_270))));

        MultiVariant freezerClosedVariant = this.plainVariant(ModelDefinitions.FREEZER_CLOSED.create(freezerBlock, textures, this.models::put));
        MultiVariant freezerOpenVariant = this.plainVariant(ModelDefinitions.FREEZER_OPEN.create(freezerBlock, textures, this.models::put));
        this.generators.put(freezerBlock, MultiVariantGenerator.dispatch(freezerBlock)
            .with(PropertyDispatch.initial(FreezerBlock.DIRECTION, FreezerBlock.OPEN)
                .select(Direction.NORTH, false, freezerClosedVariant)
                .select(Direction.EAST, false, freezerClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, freezerClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, freezerClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, freezerOpenVariant)
                .select(Direction.EAST, true, freezerOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, freezerOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, freezerOpenVariant.with(Y_ROT_270))));
    }

    private void toaster(ToasterBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant toasterVariant = this.plainVariant(ModelDefinitions.TOASTER.create(block, textures, this.models::put));
        MultiVariant toasterCookingVariant = this.plainVariant(ModelDefinitions.TOASTER_COOKING.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, toasterVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(ToasterBlock.DIRECTION, ToasterBlock.POWERED)
                .select(Direction.NORTH, false, toasterVariant)
                .select(Direction.EAST, false, toasterVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, toasterVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, toasterVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, toasterCookingVariant)
                .select(Direction.EAST, true, toasterCookingVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, toasterCookingVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, toasterCookingVariant.with(Y_ROT_270))));
    }

    private void microwave(MicrowaveBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant microwaveClosedVariant = this.plainVariant(ModelDefinitions.MICROWAVE_CLOSED.create(block, textures, this.models::put));
        MultiVariant microwaveOpenVariant = this.plainVariant(ModelDefinitions.MICROWAVE_OPEN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, microwaveClosedVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(MicrowaveBlock.DIRECTION, MicrowaveBlock.OPEN)
                .select(Direction.NORTH, false, microwaveClosedVariant)
                .select(Direction.EAST, false, microwaveClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, microwaveClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, microwaveClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, microwaveOpenVariant)
                .select(Direction.EAST, true, microwaveOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, microwaveOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, microwaveOpenVariant.with(Y_ROT_270))));
    }

    private void stove(StoveBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant stoveClosedVariant = this.plainVariant(ModelDefinitions.STOVE_CLOSED.create(block, textures, this.models::put));
        MultiVariant stoveOpenVariant = this.plainVariant(ModelDefinitions.STOVE_OPEN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, stoveClosedVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(StoveBlock.DIRECTION, StoveBlock.OPEN)
                .select(Direction.NORTH, false, stoveClosedVariant)
                .select(Direction.EAST, false, stoveClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, stoveClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, stoveClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, stoveOpenVariant)
                .select(Direction.EAST, true, stoveOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, stoveOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, stoveOpenVariant.with(Y_ROT_270))));
    }

    private void rangeHood(RangeHoodBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant rangeHoodOffVariant = this.plainVariant(ModelDefinitions.RANGE_HOOD_OFF.create(block, textures, this.models::put));
        MultiVariant rangeHoodOnVariant = this.plainVariant(ModelDefinitions.RANGE_HOOD_ON.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, rangeHoodOffVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(RangeHoodBlock.DIRECTION, RangeHoodBlock.POWERED)
                .select(Direction.NORTH, false, rangeHoodOffVariant)
                .select(Direction.EAST, false, rangeHoodOffVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, rangeHoodOffVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, rangeHoodOffVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, rangeHoodOnVariant)
                .select(Direction.EAST, true, rangeHoodOnVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, rangeHoodOnVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, rangeHoodOnVariant.with(Y_ROT_270))));
    }

    private void cuttingBoard(CuttingBoardBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant cuttingBoardVariant = this.plainVariant(ModelDefinitions.CUTTING_BOARD.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, cuttingBoardVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(CuttingBoardBlock.DIRECTION)
                .select(Direction.NORTH, cuttingBoardVariant)
                .select(Direction.EAST, cuttingBoardVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, cuttingBoardVariant.with(Y_ROT_180))
                .select(Direction.WEST, cuttingBoardVariant.with(Y_ROT_270))));
    }

    private void fryingPan(FryingPanBlock block)
    {
        MultiVariant fryingPanVariant = this.plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiVariant fryingPanHotVariant = this.plainVariant(ModelLocationUtils.getModelLocation(block, "_hot"));
        this.registerItemWithModelFromMultiVariant(block, fryingPanVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(FryingPanBlock.DIRECTION, FryingPanBlock.LIT)
                .select(Direction.NORTH, false, fryingPanVariant)
                .select(Direction.EAST, false, fryingPanVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, fryingPanVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, fryingPanVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, fryingPanHotVariant)
                .select(Direction.EAST, true, fryingPanHotVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, fryingPanHotVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, fryingPanHotVariant.with(Y_ROT_270))));
    }

    private void mailbox(MailboxBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant mailboxVariant = this.plainVariant(ModelDefinitions.MAIL_BOX.create(block, textures, this.models::put));
        MultiVariant mailboxUncheckedVariant = this.plainVariant(ModelDefinitions.MAIL_BOX_UNCHECKED.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, mailboxVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(MailboxBlock.DIRECTION, MailboxBlock.ENABLED)
                .select(Direction.NORTH, false, mailboxVariant)
                .select(Direction.EAST, false, mailboxVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, mailboxVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, mailboxVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, mailboxUncheckedVariant)
                .select(Direction.EAST, true, mailboxUncheckedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, mailboxUncheckedVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, mailboxUncheckedVariant.with(Y_ROT_270))));
    }

    private void postBox(PostBoxBlock block)
    {
        MultiVariant postboxVariant = this.plainVariant(ModelLocationUtils.getModelLocation(block));
        this.registerItemWithModelFromMultiVariant(block, postboxVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(PostBoxBlock.DIRECTION)
                .select(Direction.NORTH, postboxVariant)
                .select(Direction.EAST, postboxVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, postboxVariant.with(Y_ROT_180))
                .select(Direction.WEST, postboxVariant.with(Y_ROT_270))));
    }

    private void sofa(SofaBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant sofaVariant = this.plainVariant(ModelDefinitions.SOFA.create(block, textures, this.models::put));
        MultiVariant sofaLeftVariant = this.plainVariant(ModelDefinitions.SOFA_LEFT.create(block, textures, this.models::put));
        MultiVariant sofaRightVariant = this.plainVariant(ModelDefinitions.SOFA_RIGHT.create(block, textures, this.models::put));
        MultiVariant sofaMiddleVariant = this.plainVariant(ModelDefinitions.SOFA_MIDDLE.create(block, textures, this.models::put));
        MultiVariant sofaCornerLeftVariant = this.plainVariant(ModelDefinitions.SOFA_CORNER_LEFT.create(block, textures, this.models::put));
        MultiVariant sofaCornerRightVariant = this.plainVariant(ModelDefinitions.SOFA_CORNER_RIGHT.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, sofaVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(SofaBlock.DIRECTION, SofaBlock.SHAPE)
                .select(Direction.NORTH, SofaBlock.Shape.DEFAULT, sofaVariant)
                .select(Direction.EAST, SofaBlock.Shape.DEFAULT, sofaVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, SofaBlock.Shape.DEFAULT, sofaVariant.with(Y_ROT_180))
                .select(Direction.WEST, SofaBlock.Shape.DEFAULT, sofaVariant.with(Y_ROT_270))
                .select(Direction.NORTH, SofaBlock.Shape.LEFT, sofaLeftVariant)
                .select(Direction.EAST, SofaBlock.Shape.LEFT, sofaLeftVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, SofaBlock.Shape.LEFT, sofaLeftVariant.with(Y_ROT_180))
                .select(Direction.WEST, SofaBlock.Shape.LEFT, sofaLeftVariant.with(Y_ROT_270))
                .select(Direction.NORTH, SofaBlock.Shape.RIGHT, sofaRightVariant)
                .select(Direction.EAST, SofaBlock.Shape.RIGHT, sofaRightVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, SofaBlock.Shape.RIGHT, sofaRightVariant.with(Y_ROT_180))
                .select(Direction.WEST, SofaBlock.Shape.RIGHT, sofaRightVariant.with(Y_ROT_270))
                .select(Direction.NORTH, SofaBlock.Shape.MIDDLE, sofaMiddleVariant)
                .select(Direction.EAST, SofaBlock.Shape.MIDDLE, sofaMiddleVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, SofaBlock.Shape.MIDDLE, sofaMiddleVariant.with(Y_ROT_180))
                .select(Direction.WEST, SofaBlock.Shape.MIDDLE, sofaMiddleVariant.with(Y_ROT_270))
                .select(Direction.NORTH, SofaBlock.Shape.CORNER_LEFT, sofaCornerLeftVariant)
                .select(Direction.EAST, SofaBlock.Shape.CORNER_LEFT, sofaCornerLeftVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, SofaBlock.Shape.CORNER_LEFT, sofaCornerLeftVariant.with(Y_ROT_180))
                .select(Direction.WEST, SofaBlock.Shape.CORNER_LEFT, sofaCornerLeftVariant.with(Y_ROT_270))
                .select(Direction.NORTH, SofaBlock.Shape.CORNER_RIGHT, sofaCornerRightVariant)
                .select(Direction.EAST, SofaBlock.Shape.CORNER_RIGHT, sofaCornerRightVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, SofaBlock.Shape.CORNER_RIGHT, sofaCornerRightVariant.with(Y_ROT_180))
                .select(Direction.WEST, SofaBlock.Shape.CORNER_RIGHT, sofaCornerRightVariant.with(Y_ROT_270))));
    }

    private void doorbell(DoorbellBlock block)
    {
        MultiVariant doorbellVariant = this.plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiVariant doorbellPressedVariant = this.plainVariant(ModelLocationUtils.getModelLocation(block, "_pressed"));
        this.registerItemWithModelFromMultiVariant(block, doorbellVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(DoorbellBlock.DIRECTION, DoorbellBlock.ENABLED)
                .select(Direction.NORTH, false, doorbellVariant)
                .select(Direction.EAST, false, doorbellVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, doorbellVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, doorbellVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, doorbellPressedVariant)
                .select(Direction.EAST, true, doorbellPressedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, doorbellPressedVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, doorbellPressedVariant.with(Y_ROT_270))));

    }

    private void lightswitch(LightswitchBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant lightswitchOffVariant = this.plainVariant(ModelDefinitions.LIGHTSWITCH_OFF.create(block, textures, this.models::put));
        MultiVariant lightswitchOnVariant = this.plainVariant(ModelDefinitions.LIGHTSWITCH_ON.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, lightswitchOffVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(LightswitchBlock.FACING, LightswitchBlock.FACE, LightswitchBlock.ENABLED, LightswitchBlock.POWERED)
                .select(Direction.NORTH, AttachFace.WALL, false, false, lightswitchOffVariant.with(Y_ROT_180))
                .select(Direction.EAST, AttachFace.WALL, false, false, lightswitchOffVariant.with(Y_ROT_270))
                .select(Direction.SOUTH, AttachFace.WALL, false, false, lightswitchOffVariant)
                .select(Direction.WEST, AttachFace.WALL, false, false, lightswitchOffVariant.with(Y_ROT_90))
                .select(Direction.NORTH, AttachFace.FLOOR, false, false, lightswitchOffVariant.with(X_ROT_90))
                .select(Direction.EAST, AttachFace.FLOOR, false, false, lightswitchOffVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.FLOOR, false, false, lightswitchOffVariant.with(X_ROT_90).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.FLOOR, false, false, lightswitchOffVariant.with(X_ROT_90).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.CEILING, false, false, lightswitchOffVariant.with(X_ROT_270))
                .select(Direction.EAST, AttachFace.CEILING, false, false, lightswitchOffVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.CEILING, false, false, lightswitchOffVariant.with(X_ROT_270).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.CEILING, false, false, lightswitchOffVariant.with(X_ROT_270).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.WALL, false, true, lightswitchOffVariant.with(Y_ROT_180))
                .select(Direction.EAST, AttachFace.WALL, false, true, lightswitchOffVariant.with(Y_ROT_270))
                .select(Direction.SOUTH, AttachFace.WALL, false, true, lightswitchOffVariant)
                .select(Direction.WEST, AttachFace.WALL, false, true, lightswitchOffVariant.with(Y_ROT_90))
                .select(Direction.NORTH, AttachFace.FLOOR, false, true, lightswitchOffVariant.with(X_ROT_90))
                .select(Direction.EAST, AttachFace.FLOOR, false, true, lightswitchOffVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.FLOOR, false, true, lightswitchOffVariant.with(X_ROT_90).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.FLOOR, false, true, lightswitchOffVariant.with(X_ROT_90).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.CEILING, false, true, lightswitchOffVariant.with(X_ROT_270))
                .select(Direction.EAST, AttachFace.CEILING, false, true, lightswitchOffVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.CEILING, false, true, lightswitchOffVariant.with(X_ROT_270).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.CEILING, false, true, lightswitchOffVariant.with(X_ROT_270).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.WALL, true, false, lightswitchOnVariant.with(Y_ROT_180))
                .select(Direction.EAST, AttachFace.WALL, true, false, lightswitchOnVariant.with(Y_ROT_270))
                .select(Direction.SOUTH, AttachFace.WALL, true, false, lightswitchOnVariant)
                .select(Direction.WEST, AttachFace.WALL, true, false, lightswitchOnVariant.with(Y_ROT_90))
                .select(Direction.NORTH, AttachFace.FLOOR, true, false, lightswitchOnVariant.with(X_ROT_90))
                .select(Direction.EAST, AttachFace.FLOOR, true, false, lightswitchOnVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.FLOOR, true, false, lightswitchOnVariant.with(X_ROT_90).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.FLOOR, true, false, lightswitchOnVariant.with(X_ROT_90).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.CEILING, true, false, lightswitchOnVariant.with(X_ROT_270))
                .select(Direction.EAST, AttachFace.CEILING, true, false, lightswitchOnVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.CEILING, true, false, lightswitchOnVariant.with(X_ROT_270).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.CEILING, true, false, lightswitchOnVariant.with(X_ROT_270).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.WALL, true, true, lightswitchOnVariant.with(Y_ROT_180))
                .select(Direction.EAST, AttachFace.WALL, true, true, lightswitchOnVariant.with(Y_ROT_270))
                .select(Direction.SOUTH, AttachFace.WALL, true, true, lightswitchOnVariant)
                .select(Direction.WEST, AttachFace.WALL, true, true, lightswitchOnVariant.with(Y_ROT_90))
                .select(Direction.NORTH, AttachFace.FLOOR, true, true, lightswitchOnVariant.with(X_ROT_90))
                .select(Direction.EAST, AttachFace.FLOOR, true, true, lightswitchOnVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.FLOOR, true, true, lightswitchOnVariant.with(X_ROT_90).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.FLOOR, true, true, lightswitchOnVariant.with(X_ROT_90).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.CEILING, true, true, lightswitchOnVariant.with(X_ROT_270))
                .select(Direction.EAST, AttachFace.CEILING, true, true, lightswitchOnVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.CEILING, true, true, lightswitchOnVariant.with(X_ROT_270).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.CEILING, true, true, lightswitchOnVariant.with(X_ROT_270).with(Y_ROT_270))));
    }

    private void ceilingLight(CeilingLightBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant ceilingLightOnVariant = this.plainVariant(ModelDefinitions.CEILING_LIGHT_ON.create(block, textures, this.models::put));
        MultiVariant ceilingLightOffVariant = this.plainVariant(ModelDefinitions.CEILING_LIGHT_OFF.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, ceilingLightOffVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(CeilingLightBlock.FACING, CeilingLightBlock.FACE, CeilingLightBlock.POWERED)
                .select(Direction.NORTH, AttachFace.WALL, false, ceilingLightOffVariant.with(Y_ROT_180))
                .select(Direction.EAST, AttachFace.WALL, false, ceilingLightOffVariant.with(Y_ROT_270))
                .select(Direction.SOUTH, AttachFace.WALL, false, ceilingLightOffVariant)
                .select(Direction.WEST, AttachFace.WALL, false, ceilingLightOffVariant.with(Y_ROT_90))
                .select(Direction.NORTH, AttachFace.FLOOR, false, ceilingLightOffVariant.with(X_ROT_90))
                .select(Direction.EAST, AttachFace.FLOOR, false, ceilingLightOffVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.FLOOR, false, ceilingLightOffVariant.with(X_ROT_90).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.FLOOR, false, ceilingLightOffVariant.with(X_ROT_90).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.CEILING, false, ceilingLightOffVariant.with(X_ROT_270))
                .select(Direction.EAST, AttachFace.CEILING, false, ceilingLightOffVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.CEILING, false, ceilingLightOffVariant.with(X_ROT_270).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.CEILING, false, ceilingLightOffVariant.with(X_ROT_270).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.WALL, true, ceilingLightOnVariant.with(Y_ROT_180))
                .select(Direction.EAST, AttachFace.WALL, true, ceilingLightOnVariant.with(Y_ROT_270))
                .select(Direction.SOUTH, AttachFace.WALL, true, ceilingLightOnVariant)
                .select(Direction.WEST, AttachFace.WALL, true, ceilingLightOnVariant.with(Y_ROT_90))
                .select(Direction.NORTH, AttachFace.FLOOR, true, ceilingLightOnVariant.with(X_ROT_90))
                .select(Direction.EAST, AttachFace.FLOOR, true, ceilingLightOnVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.FLOOR, true, ceilingLightOnVariant.with(X_ROT_90).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.FLOOR, true, ceilingLightOnVariant.with(X_ROT_90).with(Y_ROT_270))
                .select(Direction.NORTH, AttachFace.CEILING, true, ceilingLightOnVariant.with(X_ROT_270))
                .select(Direction.EAST, AttachFace.CEILING, true, ceilingLightOnVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.SOUTH, AttachFace.CEILING, true, ceilingLightOnVariant.with(X_ROT_270).with(Y_ROT_180))
                .select(Direction.WEST, AttachFace.CEILING, true, ceilingLightOnVariant.with(X_ROT_270).with(Y_ROT_270))));
    }

    private void electricityGenerator(ElectricityGeneratorBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant electricityGeneratorOffVariant = this.plainVariant(ModelDefinitions.ELECTRICITY_GENERATOR_OFF.create(block, textures, this.models::put));
        MultiVariant electricityGeneratorOnVariant = this.plainVariant(ModelDefinitions.ELECTRICITY_GENERATOR_ON.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, electricityGeneratorOffVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(ElectricityGeneratorBlock.DIRECTION, ElectricityGeneratorBlock.POWERED)
                .select(Direction.NORTH, false, electricityGeneratorOffVariant)
                .select(Direction.EAST, false, electricityGeneratorOffVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, electricityGeneratorOffVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, electricityGeneratorOffVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, electricityGeneratorOnVariant)
                .select(Direction.EAST, true, electricityGeneratorOnVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, electricityGeneratorOnVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, electricityGeneratorOnVariant.with(Y_ROT_270))));
    }

    private void storageJar(StorageJarBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, new Material(Identifier.withDefaultNamespace("block/glass")))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant storageJarVariant = this.plainVariant(ModelDefinitions.STORAGE_JAR.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, storageJarVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(StorageJarBlock.DIRECTION)
                .select(Direction.NORTH, storageJarVariant)
                .select(Direction.EAST, storageJarVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, storageJarVariant.with(Y_ROT_180))
                .select(Direction.WEST, storageJarVariant.with(Y_ROT_270))));
    }

    private void recycleBin(RecycleBinBlock block)
    {
        MultiVariant recycleBinClosedVariant = this.plainVariant(ModelLocationUtils.getModelLocation(block, "_closed"));
        MultiVariant recycleBinOpenVariant = this.plainVariant(ModelLocationUtils.getModelLocation(block, "_open"));
        this.registerItemWithModelFromMultiVariant(block, recycleBinClosedVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(RecycleBinBlock.DIRECTION, RecycleBinBlock.OPEN)
                .select(Direction.NORTH, false, recycleBinClosedVariant)
                .select(Direction.EAST, false, recycleBinClosedVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, recycleBinClosedVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, recycleBinClosedVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, recycleBinOpenVariant)
                .select(Direction.EAST, true, recycleBinOpenVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, recycleBinOpenVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, recycleBinOpenVariant.with(Y_ROT_270))));
    }

    private void lamp(LampBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant lampOffVariant = this.plainVariant(ModelDefinitions.LAMP_OFF.create(block, textures, this.models::put));
        MultiVariant lampOnVariant = this.plainVariant(ModelDefinitions.LAMP_ON.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, lampOffVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(LampBlock.POWERED)
                .select(false, lampOffVariant)
                .select(true, lampOnVariant)));
    }

    private void ceilingFan(CeilingFanBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.metalParticle(block.getMetalType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant ceilingFanOffVariant = this.plainVariant(ModelDefinitions.CEILING_FAN_BASE_OFF.create(block, textures, this.models::put));
        MultiVariant ceilingFanOnVariant = this.plainVariant(ModelDefinitions.CEILING_FAN_BASE_ON.create(block, textures, this.models::put));
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(CeilingFanBlock.FACING, CeilingFanBlock.POWERED, CeilingFanBlock.LIT)
                .select(Direction.NORTH, false, false, ceilingFanOffVariant.with(X_ROT_90))
                .select(Direction.EAST, false, false, ceilingFanOffVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, false, false, ceilingFanOffVariant.with(X_ROT_270))
                .select(Direction.WEST, false, false, ceilingFanOffVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.UP, false, false, ceilingFanOffVariant)
                .select(Direction.DOWN, false, false, ceilingFanOffVariant.with(X_ROT_180))
                .select(Direction.NORTH, false, true, ceilingFanOffVariant.with(X_ROT_90))
                .select(Direction.EAST, false, true, ceilingFanOffVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, false, true, ceilingFanOffVariant.with(X_ROT_270))
                .select(Direction.WEST, false, true, ceilingFanOffVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.UP, false, true, ceilingFanOffVariant)
                .select(Direction.DOWN, false, true, ceilingFanOffVariant.with(X_ROT_180))
                .select(Direction.NORTH, true, false, ceilingFanOffVariant.with(X_ROT_90))
                .select(Direction.EAST, true, false, ceilingFanOffVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, true, false, ceilingFanOffVariant.with(X_ROT_270))
                .select(Direction.WEST, true, false, ceilingFanOffVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.UP, true, false, ceilingFanOffVariant)
                .select(Direction.DOWN, true, false, ceilingFanOffVariant.with(X_ROT_180))
                .select(Direction.NORTH, true, true, ceilingFanOnVariant.with(X_ROT_90))
                .select(Direction.EAST, true, true, ceilingFanOnVariant.with(X_ROT_90).with(Y_ROT_90))
                .select(Direction.SOUTH, true, true, ceilingFanOnVariant.with(X_ROT_270))
                .select(Direction.WEST, true, true, ceilingFanOnVariant.with(X_ROT_270).with(Y_ROT_90))
                .select(Direction.UP, true, true, ceilingFanOnVariant)
                .select(Direction.DOWN, true, true, ceilingFanOnVariant.with(X_ROT_180))));

        // Custom item
        this.registerItemWithModel(block, ModelDefinitions.CEILING_FAN.create(block.asItem(), textures, this.models::put));

        // Extra models for the ceiling fan blade
        TextureMapping extraTextures = new TextureMapping();
        extraTextures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        String name = "%s_%s_ceiling_fan_blade".formatted(block.getWoodType().name(), block.getMetalType().getName());
        ModelDefinitions.CEILING_FAN_BLADE.create(Utils.id("extra/" + name), extraTextures, this.models::put);
    }

    private void storageCabinet(WoodenStorageCabinetBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        Identifier storageCabinetClosedLeft = ModelDefinitions.CABINET_CLOSED_HINGE_LEFT.create(block, textures, this.models::put);
        Identifier storageCabinetClosedRight = ModelDefinitions.CABINET_CLOSED_HINGE_RIGHT.create(block, textures, this.models::put);
        Identifier storageCabinetOpenLeft = ModelDefinitions.CABINET_OPEN_HINGE_LEFT.create(block, textures, this.models::put);
        Identifier storageCabinetOpenRight = ModelDefinitions.CABINET_OPEN_HINGE_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, storageCabinetClosedLeft);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(WoodenStorageCabinetBlock.DIRECTION, WoodenStorageCabinetBlock.OPEN, WoodenStorageCabinetBlock.HINGE)
                .select(Direction.NORTH, false, DoorHingeSide.LEFT, this.plainVariant(storageCabinetClosedLeft))
                .select(Direction.EAST, false, DoorHingeSide.LEFT, this.plainVariant(storageCabinetClosedLeft).with(Y_ROT_90))
                .select(Direction.SOUTH, false, DoorHingeSide.LEFT, this.plainVariant(storageCabinetClosedLeft).with(Y_ROT_180))
                .select(Direction.WEST, false, DoorHingeSide.LEFT, this.plainVariant(storageCabinetClosedLeft).with(Y_ROT_270))
                .select(Direction.NORTH, true, DoorHingeSide.LEFT, this.plainVariant(storageCabinetOpenLeft))
                .select(Direction.EAST, true, DoorHingeSide.LEFT, this.plainVariant(storageCabinetOpenLeft).with(Y_ROT_90))
                .select(Direction.SOUTH, true, DoorHingeSide.LEFT, this.plainVariant(storageCabinetOpenLeft).with(Y_ROT_180))
                .select(Direction.WEST, true, DoorHingeSide.LEFT, this.plainVariant(storageCabinetOpenLeft).with(Y_ROT_270))
                .select(Direction.NORTH, false, DoorHingeSide.RIGHT, this.plainVariant(storageCabinetClosedRight))
                .select(Direction.EAST, false, DoorHingeSide.RIGHT, this.plainVariant(storageCabinetClosedRight).with(Y_ROT_90))
                .select(Direction.SOUTH, false, DoorHingeSide.RIGHT, this.plainVariant(storageCabinetClosedRight).with(Y_ROT_180))
                .select(Direction.WEST, false, DoorHingeSide.RIGHT, this.plainVariant(storageCabinetClosedRight).with(Y_ROT_270))
                .select(Direction.NORTH, true, DoorHingeSide.RIGHT, this.plainVariant(storageCabinetOpenRight))
                .select(Direction.EAST, true, DoorHingeSide.RIGHT, this.plainVariant(storageCabinetOpenRight).with(Y_ROT_90))
                .select(Direction.SOUTH, true, DoorHingeSide.RIGHT, this.plainVariant(storageCabinetOpenRight).with(Y_ROT_180))
                .select(Direction.WEST, true, DoorHingeSide.RIGHT, this.plainVariant(storageCabinetOpenRight).with(Y_ROT_270))));

    }

    private void woodenKitchenCabinet(WoodenKitchenStorageCabinetBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        Identifier kitchenStorageCabinetClosedLeft = ModelDefinitions.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.create(block, textures, this.models::put);
        Identifier kitchenStorageCabinetClosedRight = ModelDefinitions.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.create(block, textures, this.models::put);
        Identifier kitchenStorageCabinetOpenLeft = ModelDefinitions.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.create(block, textures, this.models::put);
        Identifier kitchenStorageCabinetOpenRight = ModelDefinitions.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, kitchenStorageCabinetClosedLeft);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(WoodenKitchenStorageCabinetBlock.DIRECTION, WoodenKitchenStorageCabinetBlock.OPEN, WoodenKitchenStorageCabinetBlock.HINGE)
                .select(Direction.NORTH, false, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetClosedLeft))
                .select(Direction.EAST, false, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetClosedLeft).with(Y_ROT_90))
                .select(Direction.SOUTH, false, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetClosedLeft).with(Y_ROT_180))
                .select(Direction.WEST, false, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetClosedLeft).with(Y_ROT_270))
                .select(Direction.NORTH, true, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetOpenLeft))
                .select(Direction.EAST, true, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetOpenLeft).with(Y_ROT_90))
                .select(Direction.SOUTH, true, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetOpenLeft).with(Y_ROT_180))
                .select(Direction.WEST, true, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetOpenLeft).with(Y_ROT_270))
                .select(Direction.NORTH, false, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetClosedRight))
                .select(Direction.EAST, false, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetClosedRight).with(Y_ROT_90))
                .select(Direction.SOUTH, false, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetClosedRight).with(Y_ROT_180))
                .select(Direction.WEST, false, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetClosedRight).with(Y_ROT_270))
                .select(Direction.NORTH, true, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetOpenRight))
                .select(Direction.EAST, true, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetOpenRight).with(Y_ROT_90))
                .select(Direction.SOUTH, true, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetOpenRight).with(Y_ROT_180))
                .select(Direction.WEST, true, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetOpenRight).with(Y_ROT_270))));
    }

    private void colouredKitchenCabinet(ColouredKitchenStorageCabinetBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        Identifier kitchenStorageCabinetClosedLeft = ModelDefinitions.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.create(block, textures, this.models::put);
        Identifier kitchenStorageCabinetClosedRight = ModelDefinitions.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.create(block, textures, this.models::put);
        Identifier kitchenStorageCabinetOpenLeft = ModelDefinitions.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.create(block, textures, this.models::put);
        Identifier kitchenStorageCabinetOpenRight = ModelDefinitions.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.create(block, textures, this.models::put);
        this.registerItemWithModel(block, kitchenStorageCabinetClosedLeft);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(ColouredKitchenStorageCabinetBlock.DIRECTION, ColouredKitchenStorageCabinetBlock.OPEN, ColouredKitchenStorageCabinetBlock.HINGE)
                .select(Direction.NORTH, false, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetClosedLeft))
                .select(Direction.EAST, false, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetClosedLeft).with(Y_ROT_90))
                .select(Direction.SOUTH, false, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetClosedLeft).with(Y_ROT_180))
                .select(Direction.WEST, false, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetClosedLeft).with(Y_ROT_270))
                .select(Direction.NORTH, true, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetOpenLeft))
                .select(Direction.EAST, true, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetOpenLeft).with(Y_ROT_90))
                .select(Direction.SOUTH, true, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetOpenLeft).with(Y_ROT_180))
                .select(Direction.WEST, true, DoorHingeSide.LEFT, this.plainVariant(kitchenStorageCabinetOpenLeft).with(Y_ROT_270))
                .select(Direction.NORTH, false, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetClosedRight))
                .select(Direction.EAST, false, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetClosedRight).with(Y_ROT_90))
                .select(Direction.SOUTH, false, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetClosedRight).with(Y_ROT_180))
                .select(Direction.WEST, false, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetClosedRight).with(Y_ROT_270))
                .select(Direction.NORTH, true, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetOpenRight))
                .select(Direction.EAST, true, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetOpenRight).with(Y_ROT_90))
                .select(Direction.SOUTH, true, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetOpenRight).with(Y_ROT_180))
                .select(Direction.WEST, true, DoorHingeSide.RIGHT, this.plainVariant(kitchenStorageCabinetOpenRight).with(Y_ROT_270))));
    }

    private void trampoline(TrampolineBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant trampolineDefaultVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_DEFAULT.create(block, textures, this.models::put));
        MultiVariant trampolineNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_NORTH.create(block, textures, this.models::put));
        MultiVariant trampolineEastVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_EAST.create(block, textures, this.models::put));
        MultiVariant trampolineSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_SOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_WEST.create(block, textures, this.models::put));
        MultiVariant trampolineNorthSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_NORTH_SOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineEastWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_EAST_WEST.create(block, textures, this.models::put));
        MultiVariant trampolineNorthEastVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_NORTH_EAST.create(block, textures, this.models::put));
        MultiVariant trampolineEastSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_EAST_SOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_SOUTH_WEST.create(block, textures, this.models::put));
        MultiVariant trampolineWestNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_WEST_NORTH.create(block, textures, this.models::put));
        MultiVariant trampolineNorthEastWithLegVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_NORTH_EAST_WITH_LEG.create(block, textures, this.models::put));
        MultiVariant trampolineEastSouthWithLegVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WITH_LEG.create(block, textures, this.models::put));
        MultiVariant trampolineSouthWestWithLegVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_SOUTH_WEST_WITH_LEG.create(block, textures, this.models::put));
        MultiVariant trampolineWestNorthWithLegVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_WEST_NORTH_WITH_LEG.create(block, textures, this.models::put));
        MultiVariant trampolineNorthEastSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_NORTH_EAST_SOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineEastSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WEST.create(block, textures, this.models::put));
        MultiVariant trampolineSouthWestNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_SOUTH_WEST_NORTH.create(block, textures, this.models::put));
        MultiVariant trampolineWestNorthEastVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_WEST_NORTH_EAST.create(block, textures, this.models::put));
        MultiVariant trampolineNorthEastSouthWithLegNorthEastVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST.create(block, textures, this.models::put));
        MultiVariant trampolineNorthEastSouthWithLegEastSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_EASTSOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineNorthEastSouthWithLegNorthEastEastSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST_EASTSOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineEastSouthWestWithLegEastSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineEastSouthWestWithLegSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_SOUTHWEST.create(block, textures, this.models::put));
        MultiVariant trampolineEastSouthWestWithLegEastSouthSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH_SOUTHWEST.create(block, textures, this.models::put));
        MultiVariant trampolineSouthWestNorthWithLegWestNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH.create(block, textures, this.models::put));
        MultiVariant trampolineSouthWestNorthWithLegSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_SOUTHWEST.create(block, textures, this.models::put));
        MultiVariant trampolineSouthWestNorthWithLegWestNorthSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH_SOUTHWEST.create(block, textures, this.models::put));
        MultiVariant trampolineWestNorthEastWithLegNorthEastVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_NORTHEAST.create(block, textures, this.models::put));
        MultiVariant trampolineWestNorthEastWithLegWestNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_WESTNORTH.create(block, textures, this.models::put));
        MultiVariant trampolineWestNorthEastWithLegNorthEastWestNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_NORTHEAST_WESTNORTH.create(block, textures, this.models::put));
        MultiVariant trampolineAllVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegAllVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_ALL.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegNorthEastVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegNorthEastEastSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_EASTSOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegNorthEastEastSouthSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_EASTSOUTH_SOUTHWEST.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegEastSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegEastSouthSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_SOUTHWEST.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegEastSouthSouthWestWestNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_SOUTHWEST_WESTNORTH.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegSouthWestWestNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST_WESTNORTH.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegSouthWestWestNorthNorthEastVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST_WESTNORTH_NORTHEAST.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegWestNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_WESTNORTH.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegWestNorthNorthEastVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_WESTNORTH_NORTHEAST.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegWestNorthNorthEastEastSouthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_WESTNORTH_NORTHEAST_EASTSOUTH.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegNorthEastSouthWestVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_SOUTHWEST.create(block, textures, this.models::put));
        MultiVariant trampolineAllWithLegEastSouthWestNorthVariant = this.plainVariant(ModelDefinitions.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_WESTNORTH.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, trampolineDefaultVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(TrampolineBlock.SHAPE)
                .select(TrampolineBlock.Shape.DEFAULT, trampolineDefaultVariant)
                .select(TrampolineBlock.Shape.NORTH, trampolineNorthVariant)
                .select(TrampolineBlock.Shape.EAST, trampolineEastVariant)
                .select(TrampolineBlock.Shape.SOUTH, trampolineSouthVariant)
                .select(TrampolineBlock.Shape.WEST, trampolineWestVariant)
                .select(TrampolineBlock.Shape.NORTH_SOUTH, trampolineNorthSouthVariant)
                .select(TrampolineBlock.Shape.EAST_WEST, trampolineEastWestVariant)
                .select(TrampolineBlock.Shape.NORTH_EAST, trampolineNorthEastVariant)
                .select(TrampolineBlock.Shape.EAST_SOUTH, trampolineEastSouthVariant)
                .select(TrampolineBlock.Shape.SOUTH_WEST, trampolineSouthWestVariant)
                .select(TrampolineBlock.Shape.WEST_NORTH, trampolineWestNorthVariant)
                .select(TrampolineBlock.Shape.NORTH_EAST_WITH_LEG, trampolineNorthEastWithLegVariant)
                .select(TrampolineBlock.Shape.EAST_SOUTH_WITH_LEG, trampolineEastSouthWithLegVariant)
                .select(TrampolineBlock.Shape.SOUTH_WEST_WITH_LEG, trampolineSouthWestWithLegVariant)
                .select(TrampolineBlock.Shape.WEST_NORTH_WITH_LEG, trampolineWestNorthWithLegVariant)
                .select(TrampolineBlock.Shape.NORTH_EAST_SOUTH, trampolineNorthEastSouthVariant)
                .select(TrampolineBlock.Shape.EAST_SOUTH_WEST, trampolineEastSouthWestVariant)
                .select(TrampolineBlock.Shape.SOUTH_WEST_NORTH, trampolineSouthWestNorthVariant)
                .select(TrampolineBlock.Shape.WEST_NORTH_EAST, trampolineWestNorthEastVariant)
                .select(TrampolineBlock.Shape.NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST, trampolineNorthEastSouthWithLegNorthEastVariant)
                .select(TrampolineBlock.Shape.NORTH_EAST_SOUTH_WITH_LEG_EASTSOUTH, trampolineNorthEastSouthWithLegEastSouthVariant)
                .select(TrampolineBlock.Shape.NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST_EASTSOUTH, trampolineNorthEastSouthWithLegNorthEastEastSouthVariant)
                .select(TrampolineBlock.Shape.EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH, trampolineEastSouthWestWithLegEastSouthVariant)
                .select(TrampolineBlock.Shape.EAST_SOUTH_WEST_WITH_LEG_SOUTHWEST, trampolineEastSouthWestWithLegSouthWestVariant)
                .select(TrampolineBlock.Shape.EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH_SOUTHWEST, trampolineEastSouthWestWithLegEastSouthSouthWestVariant)
                .select(TrampolineBlock.Shape.SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH, trampolineSouthWestNorthWithLegWestNorthVariant)
                .select(TrampolineBlock.Shape.SOUTH_WEST_NORTH_WITH_LEG_SOUTHWEST, trampolineSouthWestNorthWithLegSouthWestVariant)
                .select(TrampolineBlock.Shape.SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH_SOUTHWEST, trampolineSouthWestNorthWithLegWestNorthSouthWestVariant)
                .select(TrampolineBlock.Shape.WEST_NORTH_EAST_WITH_LEG_NORTHEAST, trampolineWestNorthEastWithLegNorthEastVariant)
                .select(TrampolineBlock.Shape.WEST_NORTH_EAST_WITH_LEG_WESTNORTH, trampolineWestNorthEastWithLegWestNorthVariant)
                .select(TrampolineBlock.Shape.WEST_NORTH_EAST_WITH_LEG_NORTHEAST_WESTNORTH, trampolineWestNorthEastWithLegNorthEastWestNorthVariant)
                .select(TrampolineBlock.Shape.ALL, trampolineAllVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_ALL, trampolineAllWithLegAllVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST, trampolineAllWithLegNorthEastVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST_EASTSOUTH, trampolineAllWithLegNorthEastEastSouthVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST_EASTSOUTH_SOUTHWEST, trampolineAllWithLegNorthEastEastSouthSouthWestVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH, trampolineAllWithLegEastSouthVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH_SOUTHWEST, trampolineAllWithLegEastSouthSouthWestVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH_SOUTHWEST_WESTNORTH, trampolineAllWithLegEastSouthSouthWestWestNorthVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_SOUTHWEST, trampolineAllWithLegSouthWestVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_SOUTHWEST_WESTNORTH, trampolineAllWithLegSouthWestWestNorthVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_SOUTHWEST_WESTNORTH_NORTHEAST, trampolineAllWithLegSouthWestWestNorthNorthEastVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_WESTNORTH, trampolineAllWithLegWestNorthVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_WESTNORTH_NORTHEAST, trampolineAllWithLegWestNorthNorthEastVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_WESTNORTH_NORTHEAST_EASTSOUTH, trampolineAllWithLegWestNorthNorthEastEastSouthVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST_SOUTHWEST, trampolineAllWithLegNorthEastSouthWestVariant)
                .select(TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH_WESTNORTH, trampolineAllWithLegEastSouthWestNorthVariant)));
    }

    private void plate(PlateBlock block)
    {
        this.registerItemWithModel(block, Utils.id("block/apricity_plate"));
        this.generators.put(block, MultiVariantGenerator.dispatch(block, this.variants(
           this.plainModel(Utils.id("block/apricity_plate")),
           this.plainModel(Utils.id("block/stardust_plate")),
           this.plainModel(Utils.id("block/cerulean_plate")),
           this.plainModel(Utils.id("block/tuscan_plate"))
        )));
    }

    private void stool(StoolBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant stoolVariant = this.plainVariant( ModelDefinitions.STOOL.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, stoolVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block, stoolVariant));
    }

    private void hedge(HedgeBlock block, int tint)
    {
        LeafType type = block.getLeafType();
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.leafTexture(type))
            .put(TextureSlot.TEXTURE, this.leafTexture(type));
        MultiVariant hedgeCenterSideVariant = this.plainVariant(ModelDefinitions.HEDGE_CENTER_SIDE.create(block, textures, this.models::put));
        Variant hedgeConnectionStyleOneVariant = this.plainModel(ModelDefinitions.HEDGE_CONNECTION_STYLE_1.create(block, textures, this.models::put));
        Variant hedgeConnectionStyleTwoVariant = this.plainModel(ModelDefinitions.HEDGE_CONNECTION_STYLE_2.create(block, textures, this.models::put));
        Variant hedgeConnectionStyleThreeVariant = this.plainModel(ModelDefinitions.HEDGE_CONNECTION_STYLE_3.create(block, textures, this.models::put));
        Variant hedgeCenterStyleOneVariant = this.plainModel(ModelDefinitions.HEDGE_CENTER_STYLE_1.create(block, textures, this.models::put));
        Variant hedgeCenterStyleTwoVariant = this.plainModel(ModelDefinitions.HEDGE_CENTER_STYLE_2.create(block, textures, this.models::put));
        Variant hedgeCenterStyleThreeVariant = this.plainModel(ModelDefinitions.HEDGE_CENTER_STYLE_3.create(block, textures, this.models::put));
        this.generators.put(block, MultiPartGenerator.multiPart(block)
            .with(this.variants(
                hedgeCenterStyleOneVariant,
                hedgeCenterStyleTwoVariant,
                hedgeCenterStyleThreeVariant
            ))
            .with(this.condition().term(HedgeBlock.NORTH, false), hedgeCenterSideVariant.with(Y_ROT_180))
            .with(this.condition().term(HedgeBlock.EAST, false), hedgeCenterSideVariant.with(Y_ROT_270))
            .with(this.condition().term(HedgeBlock.SOUTH, false), hedgeCenterSideVariant)
            .with(this.condition().term(HedgeBlock.WEST, false), hedgeCenterSideVariant.with(Y_ROT_90))
            .with(this.condition().term(HedgeBlock.NORTH, true), this.variants(
                hedgeConnectionStyleOneVariant.with(Y_ROT_180),
                hedgeConnectionStyleTwoVariant.with(Y_ROT_180),
                hedgeConnectionStyleThreeVariant.with(Y_ROT_180)
            ))
            .with(this.condition().term(HedgeBlock.EAST, true), this.variants(
                hedgeConnectionStyleOneVariant.with(Y_ROT_270),
                hedgeConnectionStyleTwoVariant.with(Y_ROT_270),
                hedgeConnectionStyleThreeVariant.with(Y_ROT_270)
            ))
            .with(this.condition().term(HedgeBlock.SOUTH, true), this.variants(
                hedgeConnectionStyleOneVariant,
                hedgeConnectionStyleTwoVariant,
                hedgeConnectionStyleThreeVariant
            ))
            .with(this.condition().term(HedgeBlock.WEST, true), this.variants(
                hedgeConnectionStyleOneVariant.with(Y_ROT_90),
                hedgeConnectionStyleTwoVariant.with(Y_ROT_90),
                hedgeConnectionStyleThreeVariant.with(Y_ROT_90)
            ))
        );

        Identifier hedgeItemModel = ModelDefinitions.HEDGE.create(block.asItem(), textures, this.models::put);
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
        Variant steppingStonesStyleOneVariant = this.plainModel(ModelDefinitions.STEPPING_STONES_STYLE_1.create(block, textures, this.models::put));
        Variant steppingStonesStyleTwoVariant = this.plainModel(ModelDefinitions.STEPPING_STONES_STYLE_2.create(block, textures, this.models::put));
        Variant steppingStonesStyleThreeVariant = this.plainModel(ModelDefinitions.STEPPING_STONES_STYLE_3.create(block, textures, this.models::put));
        Variant steppingStonesStyleFourVariant = this.plainModel(ModelDefinitions.STEPPING_STONES_STYLE_4.create(block, textures, this.models::put));
        this.registerItemWithModelFromVariant(block, steppingStonesStyleOneVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(SteppingStoneBlock.DIRECTION)
                .select(Direction.NORTH, this.variants(
                    steppingStonesStyleOneVariant,
                    steppingStonesStyleTwoVariant,
                    steppingStonesStyleThreeVariant,
                    steppingStonesStyleFourVariant
                ))
                .select(Direction.EAST, this.variants(
                    steppingStonesStyleOneVariant.with(Y_ROT_90),
                    steppingStonesStyleTwoVariant.with(Y_ROT_90),
                    steppingStonesStyleThreeVariant.with(Y_ROT_90),
                    steppingStonesStyleFourVariant.with(Y_ROT_90)
                ))
                .select(Direction.SOUTH, this.variants(
                    steppingStonesStyleOneVariant.with(Y_ROT_180),
                    steppingStonesStyleTwoVariant.with(Y_ROT_180),
                    steppingStonesStyleThreeVariant.with(Y_ROT_180),
                    steppingStonesStyleFourVariant.with(Y_ROT_180)
                ))
                .select(Direction.WEST, this.variants(
                    steppingStonesStyleOneVariant.with(Y_ROT_270),
                    steppingStonesStyleTwoVariant.with(Y_ROT_270),
                    steppingStonesStyleThreeVariant.with(Y_ROT_270),
                    steppingStonesStyleFourVariant.with(Y_ROT_270)
                ))
            )
        );
    }

    private void woodenToilet(WoodenToiletBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant toiletVariant = this.plainVariant(ModelDefinitions.TOILET.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, toiletVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(ToiletBlock.DIRECTION)
                .select(Direction.NORTH, toiletVariant)
                .select(Direction.EAST, toiletVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, toiletVariant.with(Y_ROT_180))
                .select(Direction.WEST, toiletVariant.with(Y_ROT_270))));
    }

    private void colouredToilet(ColouredToiletBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant toiletVariant = this.plainVariant(ModelDefinitions.TOILET.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, toiletVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(ToiletBlock.DIRECTION)
                .select(Direction.NORTH, toiletVariant)
                .select(Direction.EAST, toiletVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, toiletVariant.with(Y_ROT_180))
                .select(Direction.WEST, toiletVariant.with(Y_ROT_270))));
    }

    private void woodenBasin(WoodenBasinBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant basinVariant = this.plainVariant(ModelDefinitions.BASIN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, basinVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(BasinBlock.DIRECTION)
                .select(Direction.NORTH, basinVariant)
                .select(Direction.EAST, basinVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, basinVariant.with(Y_ROT_180))
                .select(Direction.WEST, basinVariant.with(Y_ROT_270))));
    }

    private void colouredBasin(ColouredBasinBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant basinVariant = this.plainVariant(ModelDefinitions.BASIN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, basinVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(BasinBlock.DIRECTION)
                .select(Direction.NORTH, basinVariant)
                .select(Direction.EAST, basinVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, basinVariant.with(Y_ROT_180))
                .select(Direction.WEST, basinVariant.with(Y_ROT_270))));
    }

    private void woodenBath(WoodenBathBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant bathHeadVariant = this.plainVariant(ModelDefinitions.BATH_HEAD.create(block, textures, this.models::put));
        MultiVariant bathBottomVariant = this.plainVariant(ModelDefinitions.BATH_BOTTOM.create(block, textures, this.models::put));
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(BathBlock.DIRECTION, BathBlock.TYPE)
                .select(Direction.NORTH, BathBlock.Type.HEAD, bathHeadVariant)
                .select(Direction.EAST, BathBlock.Type.HEAD, bathHeadVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, BathBlock.Type.HEAD, bathHeadVariant.with(Y_ROT_180))
                .select(Direction.WEST, BathBlock.Type.HEAD, bathHeadVariant.with(Y_ROT_270))
                .select(Direction.NORTH, BathBlock.Type.BOTTOM, bathBottomVariant)
                .select(Direction.EAST, BathBlock.Type.BOTTOM, bathBottomVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, BathBlock.Type.BOTTOM, bathBottomVariant.with(Y_ROT_180))
                .select(Direction.WEST, BathBlock.Type.BOTTOM, bathBottomVariant.with(Y_ROT_270))));
        this.registerItemWithModel(block, ModelDefinitions.BATH.create(block.asItem(), textures, this.models::put));
    }

    private void colouredBath(ColouredBathBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.colourParticle(block.getDyeColor()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant bathHeadVariant = this.plainVariant(ModelDefinitions.BATH_HEAD.create(block, textures, this.models::put));
        MultiVariant bathBottomVariant = this.plainVariant(ModelDefinitions.BATH_BOTTOM.create(block, textures, this.models::put));
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(BathBlock.DIRECTION, BathBlock.TYPE)
                .select(Direction.NORTH, BathBlock.Type.HEAD, bathHeadVariant)
                .select(Direction.EAST, BathBlock.Type.HEAD, bathHeadVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, BathBlock.Type.HEAD, bathHeadVariant.with(Y_ROT_180))
                .select(Direction.WEST, BathBlock.Type.HEAD, bathHeadVariant.with(Y_ROT_270))
                .select(Direction.NORTH, BathBlock.Type.BOTTOM, bathBottomVariant)
                .select(Direction.EAST, BathBlock.Type.BOTTOM, bathBottomVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, BathBlock.Type.BOTTOM, bathBottomVariant.with(Y_ROT_180))
                .select(Direction.WEST, BathBlock.Type.BOTTOM, bathBottomVariant.with(Y_ROT_270))));
        this.registerItemWithModel(block, ModelDefinitions.BATH.create(block.asItem(), textures, this.models::put));
    }

    private void latticeFence(LatticeFenceBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant latticeFenceCenterVariant = this.plainVariant(ModelDefinitions.LATTICE_FENCE_CENTER.create(block, textures, this.models::put));
        MultiVariant latticeFenceConnectionVariant = this.plainVariant(ModelDefinitions.LATTICE_FENCE_CONNECTION.create(block, textures, this.models::put));
        this.generators.put(block, MultiPartGenerator.multiPart(block)
            .with(latticeFenceCenterVariant)
            .with(this.condition().term(LatticeFenceBlock.NORTH, true), latticeFenceConnectionVariant.with(Y_ROT_180))
            .with(this.condition().term(LatticeFenceBlock.EAST, true), latticeFenceConnectionVariant.with(Y_ROT_270))
            .with(this.condition().term(LatticeFenceBlock.SOUTH, true), latticeFenceConnectionVariant)
            .with(this.condition().term(LatticeFenceBlock.WEST, true), latticeFenceConnectionVariant.with(Y_ROT_90)));
        this.registerItemWithModel(block, ModelDefinitions.LATTICE_FENCE.create(block.asItem(), textures, this.models::put));
    }

    private void latticeFenceGate(LatticeFenceGateBlock block)
    {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.PARTICLE, this.woodParticle(block.getWoodType()))
            .put(TextureSlot.TEXTURE, this.blockTexture(block));
        MultiVariant latticeFenceGateClosedVariant = this.plainVariant(ModelDefinitions.LATTICE_FENCE_GATE_CLOSED.create(block, textures, this.models::put));
        MultiVariant latticeFenceGateOpenVariant = this.plainVariant(ModelDefinitions.LATTICE_FENCE_GATE_OPEN.create(block, textures, this.models::put));
        this.registerItemWithModelFromMultiVariant(block, latticeFenceGateClosedVariant);
        this.generators.put(block, MultiPartGenerator.multiPart(block)
            .with(this.condition().term(LatticeFenceGateBlock.FACING, Direction.NORTH).term(LatticeFenceGateBlock.OPEN, false), latticeFenceGateClosedVariant)
            .with(this.condition().term(LatticeFenceGateBlock.FACING, Direction.EAST).term(LatticeFenceGateBlock.OPEN, false), latticeFenceGateClosedVariant.with(Y_ROT_90))
            .with(this.condition().term(LatticeFenceGateBlock.FACING, Direction.SOUTH).term(LatticeFenceGateBlock.OPEN, false), latticeFenceGateClosedVariant.with(Y_ROT_180))
            .with(this.condition().term(LatticeFenceGateBlock.FACING, Direction.WEST).term(LatticeFenceGateBlock.OPEN, false), latticeFenceGateClosedVariant.with(Y_ROT_270))
            .with(this.condition().term(LatticeFenceGateBlock.FACING, Direction.NORTH).term(LatticeFenceGateBlock.OPEN, true), latticeFenceGateOpenVariant)
            .with(this.condition().term(LatticeFenceGateBlock.FACING, Direction.EAST).term(LatticeFenceGateBlock.OPEN, true), latticeFenceGateOpenVariant.with(Y_ROT_90))
            .with(this.condition().term(LatticeFenceGateBlock.FACING, Direction.SOUTH).term(LatticeFenceGateBlock.OPEN, true), latticeFenceGateOpenVariant.with(Y_ROT_180))
            .with(this.condition().term(LatticeFenceGateBlock.FACING, Direction.WEST).term(LatticeFenceGateBlock.OPEN, true), latticeFenceGateOpenVariant.with(Y_ROT_270))
        );
    }

    private void television(TelevisionBlock block)
    {
        MultiVariant televisionOffVariant = this.plainVariant(Utils.id("block/television_off"));
        MultiVariant televisionOnVariant = this.plainVariant(Utils.id("block/television_on"));
        this.registerItemWithModelFromMultiVariant(block, televisionOffVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(TelevisionBlock.DIRECTION, TelevisionBlock.POWERED)
                .select(Direction.NORTH, false, televisionOffVariant)
                .select(Direction.EAST, false, televisionOffVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, televisionOffVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, televisionOffVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, televisionOnVariant)
                .select(Direction.EAST, true, televisionOnVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, televisionOnVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, televisionOnVariant.with(Y_ROT_270))));
    }

    private void computer(ComputerBlock block)
    {
        MultiVariant computerOffVariant = this.plainVariant(Utils.id("block/computer_off"));
        MultiVariant computerOnVariant = this.plainVariant(Utils.id("block/computer_on"));
        this.registerItemWithModelFromMultiVariant(block, computerOffVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(ComputerBlock.DIRECTION, ComputerBlock.POWERED)
                .select(Direction.NORTH, false, computerOffVariant)
                .select(Direction.EAST, false, computerOffVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, computerOffVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, computerOffVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, computerOnVariant)
                .select(Direction.EAST, true, computerOnVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, computerOnVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, computerOnVariant.with(Y_ROT_270))));
    }

    private void doorMat(DoorMatBlock block)
    {
        MultiVariant doorMatVariant = this.plainVariant(Utils.id("block/door_mat"));
        this.registerItemWithModelFromMultiVariant(block, doorMatVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(DoorMatBlock.DIRECTION)
                .select(Direction.NORTH, doorMatVariant)
                .select(Direction.EAST, doorMatVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, doorMatVariant.with(Y_ROT_180))
                .select(Direction.WEST, doorMatVariant.with(Y_ROT_270))));
    }

    private void workbench(WorkbenchBlock block)
    {
        MultiVariant workbenchOffVariant = this.plainVariant(Utils.id("block/workbench_off"));
        MultiVariant workbenchOnVariant = this.plainVariant(Utils.id("block/workbench_on"));
        this.registerItemWithModelFromMultiVariant(block, workbenchOnVariant);
        this.generators.put(block, MultiVariantGenerator.dispatch(block)
            .with(PropertyDispatch.initial(WorkbenchBlock.DIRECTION, WorkbenchBlock.POWERED)
                .select(Direction.NORTH, false, workbenchOffVariant)
                .select(Direction.EAST, false, workbenchOffVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, false, workbenchOffVariant.with(Y_ROT_180))
                .select(Direction.WEST, false, workbenchOffVariant.with(Y_ROT_270))
                .select(Direction.NORTH, true, workbenchOnVariant)
                .select(Direction.EAST, true, workbenchOnVariant.with(Y_ROT_90))
                .select(Direction.SOUTH, true, workbenchOnVariant.with(Y_ROT_180))
                .select(Direction.WEST, true, workbenchOnVariant.with(Y_ROT_270))));
    }
}
