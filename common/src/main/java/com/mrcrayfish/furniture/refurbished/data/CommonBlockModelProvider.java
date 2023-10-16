package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.*;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.data.model.ExtraModel;
import com.mrcrayfish.furniture.refurbished.data.model.ModelTemplate;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedMultiPartBlockState;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedVariantBlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class CommonBlockModelProvider
{
    private final Consumer<PreparedVariantBlockState> variantStateConsumer;
    private final Consumer<PreparedMultiPartBlockState> multiPartStateConsumer;
    private final Consumer<ExtraModel> extraModelConsumer;

    public CommonBlockModelProvider(Consumer<PreparedVariantBlockState> variantStateConsumer, Consumer<PreparedMultiPartBlockState> multiPartStateConsumer, Consumer<ExtraModel> extraModelConsumer)
    {
        this.variantStateConsumer = variantStateConsumer;
        this.multiPartStateConsumer = multiPartStateConsumer;
        this.extraModelConsumer = extraModelConsumer;
    }

    public void run()
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
        this.fridge(ModBlocks.FRIDGE_LIGHT.get());
        this.fridge(ModBlocks.FRIDGE_DARK.get());
        this.freezer(ModBlocks.FREEZER_LIGHT.get());
        this.freezer(ModBlocks.FREEZER_DARK.get());
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
        this.hedge(ModBlocks.HEDGE_OAK.get());
        this.hedge(ModBlocks.HEDGE_SPRUCE.get());
        this.hedge(ModBlocks.HEDGE_BIRCH.get());
        this.hedge(ModBlocks.HEDGE_JUNGLE.get());
        this.hedge(ModBlocks.HEDGE_ACACIA.get());
        this.hedge(ModBlocks.HEDGE_DARK_OAK.get());
        this.hedge(ModBlocks.HEDGE_MANGROVE.get());
        this.hedge(ModBlocks.HEDGE_CHERRY.get());
        this.hedge(ModBlocks.HEDGE_AZALEA.get());
    }

    private ResourceLocation blockTexture(Block block)
    {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
        return new ResourceLocation(name.getNamespace(), "block/" + name.getPath());
    }

    private ResourceLocation woodParticle(WoodType type)
    {
        return new ResourceLocation(Constants.MOD_ID, "block/" + type.name() + "_particle");
    }

    private ResourceLocation colourParticle(DyeColor color)
    {
        return new ResourceLocation(Constants.MOD_ID, "block/" + color.getName() + "_particle");
    }

    private ResourceLocation metalParticle(MetalType type)
    {
        return new ResourceLocation(Constants.MOD_ID, "block/" + type.getName() + "_particle");
    }

    private ResourceLocation leafTexture(LeafType type)
    {
        return new ResourceLocation("block/" + type.getName() + "_leaves");
    }

    private void table(TableBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, false).addTexturedModel(ModelTemplate.TABLE.stateModel(type).setTextures(textures)).markAsItem();
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, false).addTexturedModel(ModelTemplate.TABLE_NORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, false).addTexturedModel(ModelTemplate.TABLE_NORTH_EAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, false).addTexturedModel(ModelTemplate.TABLE_NORTH_EAST_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, false).addTexturedModel(ModelTemplate.TABLE_EAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, false).addTexturedModel(ModelTemplate.TABLE_EAST_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, true).addTexturedModel(ModelTemplate.TABLE_EAST_SOUTH_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, false).addTexturedModel(ModelTemplate.TABLE_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, true).addTexturedModel(ModelTemplate.TABLE_SOUTH_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, true).addTexturedModel(ModelTemplate.TABLE_SOUTH_WEST_NORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, true).addTexturedModel(ModelTemplate.TABLE_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, true).addTexturedModel(ModelTemplate.TABLE_WEST_NORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, true).addTexturedModel(ModelTemplate.TABLE_WEST_NORTH_EAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, false).addTexturedModel(ModelTemplate.TABLE_NORTH_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, true).addTexturedModel(ModelTemplate.TABLE_EAST_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, true).addTexturedModel(ModelTemplate.TABLE_NORTH_EAST_SOUTH_WEST.stateModel(type).setTextures(textures));
        this.variantStateConsumer.accept(state);
    }

    private void chair(ChairBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.NORTH).prop(ChairBlock.TUCKED, false).addTexturedModel(ModelTemplate.CHAIR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.EAST).prop(ChairBlock.TUCKED, false).addTexturedModel(ModelTemplate.CHAIR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.SOUTH).prop(ChairBlock.TUCKED, false).addTexturedModel(ModelTemplate.CHAIR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.WEST).prop(ChairBlock.TUCKED, false).addTexturedModel(ModelTemplate.CHAIR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.NORTH).prop(ChairBlock.TUCKED, true).addTexturedModel(ModelTemplate.CHAIR_TUCKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.EAST).prop(ChairBlock.TUCKED, true).addTexturedModel(ModelTemplate.CHAIR_TUCKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.SOUTH).prop(ChairBlock.TUCKED, true).addTexturedModel(ModelTemplate.CHAIR_TUCKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.WEST).prop(ChairBlock.TUCKED, true).addTexturedModel(ModelTemplate.CHAIR_TUCKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void desk(DeskBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.NORTH).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, false).addTexturedModel(ModelTemplate.DESK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.EAST).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, false).addTexturedModel(ModelTemplate.DESK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.SOUTH).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, false).addTexturedModel(ModelTemplate.DESK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.WEST).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, false).addTexturedModel(ModelTemplate.DESK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.NORTH).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, true).addTexturedModel(ModelTemplate.DESK_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.EAST).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, true).addTexturedModel(ModelTemplate.DESK_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.SOUTH).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, true).addTexturedModel(ModelTemplate.DESK_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.WEST).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, true).addTexturedModel(ModelTemplate.DESK_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.NORTH).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, false).addTexturedModel(ModelTemplate.DESK_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.EAST).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, false).addTexturedModel(ModelTemplate.DESK_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.SOUTH).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, false).addTexturedModel(ModelTemplate.DESK_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.WEST).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, false).addTexturedModel(ModelTemplate.DESK_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.NORTH).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, true).addTexturedModel(ModelTemplate.DESK_MIDDLE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.EAST).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, true).addTexturedModel(ModelTemplate.DESK_MIDDLE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.SOUTH).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, true).addTexturedModel(ModelTemplate.DESK_MIDDLE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.WEST).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, true).addTexturedModel(ModelTemplate.DESK_MIDDLE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void drawer(DrawerBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_RIGHT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_RIGHT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_RIGHT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_RIGHT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_LEFT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_LEFT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_LEFT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_LEFT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_MIDDLE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_MIDDLE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_MIDDLE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.DRAWER_MIDDLE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_RIGHT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_RIGHT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_RIGHT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_RIGHT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_LEFT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_LEFT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_LEFT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_LEFT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_MIDDLE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_MIDDLE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_MIDDLE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.DRAWER_MIDDLE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void crate(CrateBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(CrateBlock.OPEN, false).addTexturedModel(ModelTemplate.CRATE_CLOSED.stateModel(type).setTextures(textures)).markAsItem();
        state.createVariant().prop(CrateBlock.OPEN, true).addTexturedModel(ModelTemplate.CRATE_OPEN.stateModel(type).setTextures(textures));
        this.variantStateConsumer.accept(state);
    }

    private void woodenKitchenCabinetry(WoodenKitchenCabinetryBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void woodenKitchenDrawer(WoodenKitchenDrawerBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void woodenKitchenSink(WoodenKitchenSinkBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).addTexturedModel(ModelTemplate.KITCHEN_SINK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).addTexturedModel(ModelTemplate.KITCHEN_SINK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).addTexturedModel(ModelTemplate.KITCHEN_SINK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).addTexturedModel(ModelTemplate.KITCHEN_SINK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void colouredKitchenCabinetry(ColouredKitchenCabinetryBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).addTexturedModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void colouredKitchenDrawer(ColouredKitchenDrawerBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.OPEN, false).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.OPEN, true).addTexturedModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void colouredKitchenSink(ColouredKitchenSinkBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).addTexturedModel(ModelTemplate.KITCHEN_SINK.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).addTexturedModel(ModelTemplate.KITCHEN_SINK.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).addTexturedModel(ModelTemplate.KITCHEN_SINK.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).addTexturedModel(ModelTemplate.KITCHEN_SINK.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void grill(GrillBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(GrillBlock.DIRECTION, Direction.NORTH).addTexturedModel(ModelTemplate.GRILL.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(GrillBlock.DIRECTION, Direction.EAST).addTexturedModel(ModelTemplate.GRILL.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(GrillBlock.DIRECTION, Direction.SOUTH).addTexturedModel(ModelTemplate.GRILL.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(GrillBlock.DIRECTION, Direction.WEST).addTexturedModel(ModelTemplate.GRILL.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void cooler(CoolerBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.NORTH).prop(CoolerBlock.OPEN, false).addTexturedModel(ModelTemplate.COOLER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.EAST).prop(CoolerBlock.OPEN, false).addTexturedModel(ModelTemplate.COOLER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.SOUTH).prop(CoolerBlock.OPEN, false).addTexturedModel(ModelTemplate.COOLER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.WEST).prop(CoolerBlock.OPEN, false).addTexturedModel(ModelTemplate.COOLER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.NORTH).prop(CoolerBlock.OPEN, true).addTexturedModel(ModelTemplate.COOLER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.EAST).prop(CoolerBlock.OPEN, true).addTexturedModel(ModelTemplate.COOLER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.SOUTH).prop(CoolerBlock.OPEN, true).addTexturedModel(ModelTemplate.COOLER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.WEST).prop(CoolerBlock.OPEN, true).addTexturedModel(ModelTemplate.COOLER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }
    
    private void fridge(FridgeBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.NORTH).prop(FridgeBlock.OPEN, false).addTexturedModel(ModelTemplate.FRIDGE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.EAST).prop(FridgeBlock.OPEN, false).addTexturedModel(ModelTemplate.FRIDGE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.SOUTH).prop(FridgeBlock.OPEN, false).addTexturedModel(ModelTemplate.FRIDGE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.WEST).prop(FridgeBlock.OPEN, false).addTexturedModel(ModelTemplate.FRIDGE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.NORTH).prop(FridgeBlock.OPEN, true).addTexturedModel(ModelTemplate.FRIDGE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.EAST).prop(FridgeBlock.OPEN, true).addTexturedModel(ModelTemplate.FRIDGE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.SOUTH).prop(FridgeBlock.OPEN, true).addTexturedModel(ModelTemplate.FRIDGE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.WEST).prop(FridgeBlock.OPEN, true).addTexturedModel(ModelTemplate.FRIDGE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void freezer(FreezerBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block.getFridge().get()));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.NORTH).prop(FreezerBlock.OPEN, false).addTexturedModel(ModelTemplate.FREEZER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.EAST).prop(FreezerBlock.OPEN, false).addTexturedModel(ModelTemplate.FREEZER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.SOUTH).prop(FreezerBlock.OPEN, false).addTexturedModel(ModelTemplate.FREEZER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.WEST).prop(FreezerBlock.OPEN, false).addTexturedModel(ModelTemplate.FREEZER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.NORTH).prop(FreezerBlock.OPEN, true).addTexturedModel(ModelTemplate.FREEZER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.EAST).prop(FreezerBlock.OPEN, true).addTexturedModel(ModelTemplate.FREEZER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.SOUTH).prop(FreezerBlock.OPEN, true).addTexturedModel(ModelTemplate.FREEZER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.WEST).prop(FreezerBlock.OPEN, true).addTexturedModel(ModelTemplate.FREEZER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void toaster(ToasterBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.NORTH).prop(ToasterBlock.POWERED, false).addTexturedModel(ModelTemplate.TOASTER.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.EAST).prop(ToasterBlock.POWERED, false).addTexturedModel(ModelTemplate.TOASTER.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.SOUTH).prop(ToasterBlock.POWERED, false).addTexturedModel(ModelTemplate.TOASTER.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.WEST).prop(ToasterBlock.POWERED, false).addTexturedModel(ModelTemplate.TOASTER.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.NORTH).prop(ToasterBlock.POWERED, true).addTexturedModel(ModelTemplate.TOASTER_COOKING.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.EAST).prop(ToasterBlock.POWERED, true).addTexturedModel(ModelTemplate.TOASTER_COOKING.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.SOUTH).prop(ToasterBlock.POWERED, true).addTexturedModel(ModelTemplate.TOASTER_COOKING.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.WEST).prop(ToasterBlock.POWERED, true).addTexturedModel(ModelTemplate.TOASTER_COOKING.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void microwave(MicrowaveBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.NORTH).prop(MicrowaveBlock.OPEN, false).addTexturedModel(ModelTemplate.MICROWAVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.EAST).prop(MicrowaveBlock.OPEN, false).addTexturedModel(ModelTemplate.MICROWAVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.SOUTH).prop(MicrowaveBlock.OPEN, false).addTexturedModel(ModelTemplate.MICROWAVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.WEST).prop(MicrowaveBlock.OPEN, false).addTexturedModel(ModelTemplate.MICROWAVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.NORTH).prop(MicrowaveBlock.OPEN, true).addTexturedModel(ModelTemplate.MICROWAVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.EAST).prop(MicrowaveBlock.OPEN, true).addTexturedModel(ModelTemplate.MICROWAVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.SOUTH).prop(MicrowaveBlock.OPEN, true).addTexturedModel(ModelTemplate.MICROWAVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.WEST).prop(MicrowaveBlock.OPEN, true).addTexturedModel(ModelTemplate.MICROWAVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void stove(StoveBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.NORTH).prop(StoveBlock.OPEN, false).addTexturedModel(ModelTemplate.STOVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.EAST).prop(StoveBlock.OPEN, false).addTexturedModel(ModelTemplate.STOVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.SOUTH).prop(StoveBlock.OPEN, false).addTexturedModel(ModelTemplate.STOVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.WEST).prop(StoveBlock.OPEN, false).addTexturedModel(ModelTemplate.STOVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.NORTH).prop(StoveBlock.OPEN, true).addTexturedModel(ModelTemplate.STOVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.EAST).prop(StoveBlock.OPEN, true).addTexturedModel(ModelTemplate.STOVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.SOUTH).prop(StoveBlock.OPEN, true).addTexturedModel(ModelTemplate.STOVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.WEST).prop(StoveBlock.OPEN, true).addTexturedModel(ModelTemplate.STOVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void rangeHood(RangeHoodBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(RangeHoodBlock.DIRECTION, Direction.NORTH).prop(RangeHoodBlock.POWERED, false).addTexturedModel(ModelTemplate.RANGE_HOOD_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(RangeHoodBlock.DIRECTION, Direction.EAST).prop(RangeHoodBlock.POWERED, false).addTexturedModel(ModelTemplate.RANGE_HOOD_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(RangeHoodBlock.DIRECTION, Direction.SOUTH).prop(RangeHoodBlock.POWERED, false).addTexturedModel(ModelTemplate.RANGE_HOOD_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(RangeHoodBlock.DIRECTION, Direction.WEST).prop(RangeHoodBlock.POWERED, false).addTexturedModel(ModelTemplate.RANGE_HOOD_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(RangeHoodBlock.DIRECTION, Direction.NORTH).prop(RangeHoodBlock.POWERED, true).addTexturedModel(ModelTemplate.RANGE_HOOD_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(RangeHoodBlock.DIRECTION, Direction.EAST).prop(RangeHoodBlock.POWERED, true).addTexturedModel(ModelTemplate.RANGE_HOOD_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(RangeHoodBlock.DIRECTION, Direction.SOUTH).prop(RangeHoodBlock.POWERED, true).addTexturedModel(ModelTemplate.RANGE_HOOD_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(RangeHoodBlock.DIRECTION, Direction.WEST).prop(RangeHoodBlock.POWERED, true).addTexturedModel(ModelTemplate.RANGE_HOOD_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void cuttingBoard(CuttingBoardBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(CuttingBoardBlock.DIRECTION, Direction.NORTH).addTexturedModel(ModelTemplate.CUTTING_BOARD.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(CuttingBoardBlock.DIRECTION, Direction.EAST).addTexturedModel(ModelTemplate.CUTTING_BOARD.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CuttingBoardBlock.DIRECTION, Direction.SOUTH).addTexturedModel(ModelTemplate.CUTTING_BOARD.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CuttingBoardBlock.DIRECTION, Direction.WEST).addTexturedModel(ModelTemplate.CUTTING_BOARD.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void fryingPan(FryingPanBlock block)
    {
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(MetalType.LIGHT));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(FryingPanBlock.DIRECTION, Direction.NORTH).addExistingModel(ModelTemplate.FRYING_PAN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(FryingPanBlock.DIRECTION, Direction.EAST).addExistingModel(ModelTemplate.FRYING_PAN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FryingPanBlock.DIRECTION, Direction.SOUTH).addExistingModel(ModelTemplate.FRYING_PAN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FryingPanBlock.DIRECTION, Direction.WEST).addExistingModel(ModelTemplate.FRYING_PAN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void mailbox(MailboxBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.NORTH).prop(MailboxBlock.ENABLED, false).addTexturedModel(ModelTemplate.MAIL_BOX.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.ENABLED, false).addTexturedModel(ModelTemplate.MAIL_BOX.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.ENABLED, false).addTexturedModel(ModelTemplate.MAIL_BOX.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.ENABLED, false).addTexturedModel(ModelTemplate.MAIL_BOX.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.ENABLED, true).addTexturedModel(ModelTemplate.MAIL_BOX_UNCHECKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.ENABLED, true).addTexturedModel(ModelTemplate.MAIL_BOX_UNCHECKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.ENABLED, true).addTexturedModel(ModelTemplate.MAIL_BOX_UNCHECKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.ENABLED, true).addTexturedModel(ModelTemplate.MAIL_BOX_UNCHECKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void postBox(PostBoxBlock block)
    {
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(MetalType.LIGHT));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(PostBoxBlock.DIRECTION, Direction.NORTH).addExistingModel(ModelTemplate.POST_BOX.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(PostBoxBlock.DIRECTION, Direction.EAST).addExistingModel(ModelTemplate.POST_BOX.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(PostBoxBlock.DIRECTION, Direction.SOUTH).addExistingModel(ModelTemplate.POST_BOX.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(PostBoxBlock.DIRECTION, Direction.WEST).addExistingModel(ModelTemplate.POST_BOX.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void sofa(SofaBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.SOFA.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.SOFA.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.SOFA.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.SOFA.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.LEFT).addTexturedModel(ModelTemplate.SOFA_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.LEFT).addTexturedModel(ModelTemplate.SOFA_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.LEFT).addTexturedModel(ModelTemplate.SOFA_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.LEFT).addTexturedModel(ModelTemplate.SOFA_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.RIGHT).addTexturedModel(ModelTemplate.SOFA_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.RIGHT).addTexturedModel(ModelTemplate.SOFA_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.RIGHT).addTexturedModel(ModelTemplate.SOFA_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.RIGHT).addTexturedModel(ModelTemplate.SOFA_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.MIDDLE).addTexturedModel(ModelTemplate.SOFA_MIDDLE.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.MIDDLE).addTexturedModel(ModelTemplate.SOFA_MIDDLE.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.MIDDLE).addTexturedModel(ModelTemplate.SOFA_MIDDLE.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.MIDDLE).addTexturedModel(ModelTemplate.SOFA_MIDDLE.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_LEFT).addTexturedModel(ModelTemplate.SOFA_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_LEFT).addTexturedModel(ModelTemplate.SOFA_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_LEFT).addTexturedModel(ModelTemplate.SOFA_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_LEFT).addTexturedModel(ModelTemplate.SOFA_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_RIGHT).addTexturedModel(ModelTemplate.SOFA_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_RIGHT).addTexturedModel(ModelTemplate.SOFA_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_RIGHT).addTexturedModel(ModelTemplate.SOFA_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_RIGHT).addTexturedModel(ModelTemplate.SOFA_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void doorbell(DoorbellBlock block)
    {
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(MetalType.LIGHT));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.NORTH).prop(DoorbellBlock.ENABLED, false).addExistingModel(ModelTemplate.DOORBELL.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.EAST).prop(DoorbellBlock.ENABLED, false).addExistingModel(ModelTemplate.DOORBELL.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.SOUTH).prop(DoorbellBlock.ENABLED, false).addExistingModel(ModelTemplate.DOORBELL.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.WEST).prop(DoorbellBlock.ENABLED, false).addExistingModel(ModelTemplate.DOORBELL.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.NORTH).prop(DoorbellBlock.ENABLED, true).addExistingModel(ModelTemplate.DOORBELL_PRESSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.EAST).prop(DoorbellBlock.ENABLED, true).addExistingModel(ModelTemplate.DOORBELL_PRESSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.SOUTH).prop(DoorbellBlock.ENABLED, true).addExistingModel(ModelTemplate.DOORBELL_PRESSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.WEST).prop(DoorbellBlock.ENABLED, true).addExistingModel(ModelTemplate.DOORBELL_PRESSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void lightswitch(LightswitchBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180)).markAsItem();
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, false).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, false).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.ENABLED, true).prop(LightswitchBlock.POWERED, true).addTexturedModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void ceilingLight(CeilingLightBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180)).markAsItem();
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, false).addTexturedModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, true).addTexturedModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void electricityGenerator(ElectricityGeneratorBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(ElectricityGeneratorBlock.DIRECTION, Direction.NORTH).prop(ElectricityGeneratorBlock.POWERED, false).addTexturedModel(ModelTemplate.ELECTRICITY_GENERATOR_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(ElectricityGeneratorBlock.DIRECTION, Direction.EAST).prop(ElectricityGeneratorBlock.POWERED, false).addTexturedModel(ModelTemplate.ELECTRICITY_GENERATOR_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ElectricityGeneratorBlock.DIRECTION, Direction.SOUTH).prop(ElectricityGeneratorBlock.POWERED, false).addTexturedModel(ModelTemplate.ELECTRICITY_GENERATOR_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ElectricityGeneratorBlock.DIRECTION, Direction.WEST).prop(ElectricityGeneratorBlock.POWERED, false).addTexturedModel(ModelTemplate.ELECTRICITY_GENERATOR_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(ElectricityGeneratorBlock.DIRECTION, Direction.NORTH).prop(ElectricityGeneratorBlock.POWERED, true).addTexturedModel(ModelTemplate.ELECTRICITY_GENERATOR_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(ElectricityGeneratorBlock.DIRECTION, Direction.EAST).prop(ElectricityGeneratorBlock.POWERED, true).addTexturedModel(ModelTemplate.ELECTRICITY_GENERATOR_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ElectricityGeneratorBlock.DIRECTION, Direction.SOUTH).prop(ElectricityGeneratorBlock.POWERED, true).addTexturedModel(ModelTemplate.ELECTRICITY_GENERATOR_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ElectricityGeneratorBlock.DIRECTION, Direction.WEST).prop(ElectricityGeneratorBlock.POWERED, true).addTexturedModel(ModelTemplate.ELECTRICITY_GENERATOR_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void storageJar(StorageJarBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, new ResourceLocation("block/glass"));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(StorageJarBlock.DIRECTION, Direction.NORTH).addTexturedModel(ModelTemplate.STORAGE_JAR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(StorageJarBlock.DIRECTION, Direction.EAST).addTexturedModel(ModelTemplate.STORAGE_JAR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageJarBlock.DIRECTION, Direction.SOUTH).addTexturedModel(ModelTemplate.STORAGE_JAR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageJarBlock.DIRECTION, Direction.WEST).addTexturedModel(ModelTemplate.STORAGE_JAR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void recycleBin(RecycleBinBlock block)
    {
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(MetalType.LIGHT));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(RecycleBinBlock.DIRECTION, Direction.NORTH).prop(RecycleBinBlock.OPEN, false).addExistingModel(ModelTemplate.RECYCLE_BIN_CLOSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(RecycleBinBlock.DIRECTION, Direction.EAST).prop(RecycleBinBlock.OPEN, false).addExistingModel(ModelTemplate.RECYCLE_BIN_CLOSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(RecycleBinBlock.DIRECTION, Direction.SOUTH).prop(RecycleBinBlock.OPEN, false).addExistingModel(ModelTemplate.RECYCLE_BIN_CLOSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(RecycleBinBlock.DIRECTION, Direction.WEST).prop(RecycleBinBlock.OPEN, false).addExistingModel(ModelTemplate.RECYCLE_BIN_CLOSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(RecycleBinBlock.DIRECTION, Direction.NORTH).prop(RecycleBinBlock.OPEN, true).addExistingModel(ModelTemplate.RECYCLE_BIN_OPEN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(RecycleBinBlock.DIRECTION, Direction.EAST).prop(RecycleBinBlock.OPEN, true).addExistingModel(ModelTemplate.RECYCLE_BIN_OPEN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(RecycleBinBlock.DIRECTION, Direction.SOUTH).prop(RecycleBinBlock.OPEN, true).addExistingModel(ModelTemplate.RECYCLE_BIN_OPEN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(RecycleBinBlock.DIRECTION, Direction.WEST).prop(RecycleBinBlock.OPEN, true).addExistingModel(ModelTemplate.RECYCLE_BIN_OPEN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void lamp(LampBlock block)
    {
        DyeColor type = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(LampBlock.POWERED, false).addTexturedModel(ModelTemplate.LAMP_OFF.stateModel(type).setTextures(textures)).markAsItem();
        state.createVariant().prop(LampBlock.POWERED, true).addTexturedModel(ModelTemplate.LAMP_ON.stateModel(type).setTextures(textures));
        this.variantStateConsumer.accept(state);
    }

    private void ceilingFan(CeilingFanBlock block)
    {
        WoodType woodType = block.getWoodType();
        MetalType metalType = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(metalType));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.NORTH).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.EAST).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R90).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.SOUTH).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.WEST).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R270).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.UP).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.DOWN).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.NORTH).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.EAST).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R90).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.SOUTH).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.WEST).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R270).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.UP).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.DOWN).prop(CeilingFanBlock.POWERED, false).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.NORTH).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.EAST).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R90).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.SOUTH).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.WEST).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R270).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.UP).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.DOWN).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, false).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_OFF.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.NORTH).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_ON.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.EAST).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_ON.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R90).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.SOUTH).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_ON.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.WEST).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_ON.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R270).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.UP).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_ON.stateModel(woodType, metalType).setTextures(textures));
        state.createVariant().prop(CeilingFanBlock.FACING, Direction.DOWN).prop(CeilingFanBlock.POWERED, true).prop(CeilingFanBlock.LIT, true).addTexturedModel(ModelTemplate.CEILING_FAN_BASE_ON.stateModel(woodType, metalType).setTextures(textures).setXRotation(VariantProperties.Rotation.R180));
        this.variantStateConsumer.accept(state);

        // Creates the extra models used for animating the blades
        TextureMapping extraTextures = new TextureMapping();
        extraTextures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        this.extraModelConsumer.accept(ModelTemplate.CEILING_FAN_BLADE.extraModel(woodType, metalType).setTextures(extraTextures));
    }

    private void storageCabinet(WoodenStorageCabinetBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void woodenKitchenCabinet(WoodenKitchenStorageCabinetBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void colouredKitchenCabinet(ColouredKitchenStorageCabinetBlock block)
    {
        DyeColor type = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.LEFT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, false).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.NORTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.EAST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.SOUTH).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StorageCabinetBlock.DIRECTION, Direction.WEST).prop(StorageCabinetBlock.OPEN, true).prop(StorageCabinetBlock.HINGE, DoorHingeSide.RIGHT).addTexturedModel(ModelTemplate.KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.variantStateConsumer.accept(state);
    }

    private void trampoline(TrampolineBlock block)
    {
        DyeColor type = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.DEFAULT).addTexturedModel(ModelTemplate.TRAMPOLINE_DEFAULT.stateModel(type).setTextures(textures)).markAsItem();
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.NORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_NORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.EAST).addTexturedModel(ModelTemplate.TRAMPOLINE_EAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.SOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.WEST).addTexturedModel(ModelTemplate.TRAMPOLINE_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.NORTH_SOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_NORTH_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.EAST_WEST).addTexturedModel(ModelTemplate.TRAMPOLINE_EAST_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.NORTH_EAST).addTexturedModel(ModelTemplate.TRAMPOLINE_NORTH_EAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.EAST_SOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_EAST_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.SOUTH_WEST).addTexturedModel(ModelTemplate.TRAMPOLINE_SOUTH_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.WEST_NORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_WEST_NORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.NORTH_EAST_WITH_LEG).addTexturedModel(ModelTemplate.TRAMPOLINE_NORTH_EAST_WITH_LEG.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.EAST_SOUTH_WITH_LEG).addTexturedModel(ModelTemplate.TRAMPOLINE_EAST_SOUTH_WITH_LEG.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.SOUTH_WEST_WITH_LEG).addTexturedModel(ModelTemplate.TRAMPOLINE_SOUTH_WEST_WITH_LEG.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.WEST_NORTH_WITH_LEG).addTexturedModel(ModelTemplate.TRAMPOLINE_WEST_NORTH_WITH_LEG.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.NORTH_EAST_SOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_NORTH_EAST_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.EAST_SOUTH_WEST).addTexturedModel(ModelTemplate.TRAMPOLINE_EAST_SOUTH_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.SOUTH_WEST_NORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_SOUTH_WEST_NORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.WEST_NORTH_EAST).addTexturedModel(ModelTemplate.TRAMPOLINE_WEST_NORTH_EAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST).addTexturedModel(ModelTemplate.TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.NORTH_EAST_SOUTH_WITH_LEG_EASTSOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_EASTSOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST_EASTSOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST_EASTSOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.EAST_SOUTH_WEST_WITH_LEG_SOUTHWEST).addTexturedModel(ModelTemplate.TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_SOUTHWEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH_SOUTHWEST).addTexturedModel(ModelTemplate.TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH_SOUTHWEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.SOUTH_WEST_NORTH_WITH_LEG_SOUTHWEST).addTexturedModel(ModelTemplate.TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_SOUTHWEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH_SOUTHWEST).addTexturedModel(ModelTemplate.TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH_SOUTHWEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.WEST_NORTH_EAST_WITH_LEG_NORTHEAST).addTexturedModel(ModelTemplate.TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_NORTHEAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.WEST_NORTH_EAST_WITH_LEG_WESTNORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_WESTNORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.WEST_NORTH_EAST_WITH_LEG_NORTHEAST_WESTNORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_NORTHEAST_WESTNORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_ALL).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_ALL.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST_EASTSOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_EASTSOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST_EASTSOUTH_SOUTHWEST).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_EASTSOUTH_SOUTHWEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH_SOUTHWEST).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_SOUTHWEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH_SOUTHWEST_WESTNORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_SOUTHWEST_WESTNORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_SOUTHWEST).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_SOUTHWEST_WESTNORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST_WESTNORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_SOUTHWEST_WESTNORTH_NORTHEAST).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST_WESTNORTH_NORTHEAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_WESTNORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_WESTNORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_WESTNORTH_NORTHEAST).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_WESTNORTH_NORTHEAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_WESTNORTH_NORTHEAST_EASTSOUTH).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_WESTNORTH_NORTHEAST_EASTSOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_NORTHEAST_SOUTHWEST).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_SOUTHWEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TrampolineBlock.SHAPE, TrampolineBlock.Shape.ALL_WITH_LEG_EASTSOUTH_WESTNORTH).addTexturedModel(ModelTemplate.TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_WESTNORTH.stateModel(type).setTextures(textures));
        this.variantStateConsumer.accept(state);
    }

    private void plate(PlateBlock block)
    {
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant()
                .addExistingModel(ModelTemplate.APRICITY_PLATE.stateModel())
                .addExistingModel(ModelTemplate.STARDUST_PLATE.stateModel())
                .addExistingModel(ModelTemplate.CERULEAN_PLATE.stateModel())
                .addExistingModel(ModelTemplate.TUSCAN_PLATE.stateModel())
                .markAsItem();
        this.variantStateConsumer.accept(state);
    }

    private void stool(StoolBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedVariantBlockState state = new PreparedVariantBlockState(block);
        state.createVariant().addTexturedModel(ModelTemplate.STOOL.stateModel(color).setTextures(textures)).markAsItem();
        this.variantStateConsumer.accept(state);
    }

    private void hedge(HedgeBlock block)
    {
        LeafType type = block.getLeafType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.leafTexture(type));
        textures.put(TextureSlot.TEXTURE, this.leafTexture(type));
        PreparedMultiPartBlockState state = new PreparedMultiPartBlockState(block);
        state.setItemModel(ModelTemplate.HEDGE.stateModel(type).setTextures(textures).markAsChild());
        state.createPart()
                .addTexturedModel(ModelTemplate.HEDGE_CENTER_STYLE_1.stateModel(type).setTextures(textures))
                .addTexturedModel(ModelTemplate.HEDGE_CENTER_STYLE_2.stateModel(type).setTextures(textures))
                .addTexturedModel(ModelTemplate.HEDGE_CENTER_STYLE_3.stateModel(type).setTextures(textures));
        state.createPart().prop(HedgeBlock.NORTH, false).addTexturedModel(ModelTemplate.HEDGE_CENTER_SIDE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createPart().prop(HedgeBlock.EAST, false).addTexturedModel(ModelTemplate.HEDGE_CENTER_SIDE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createPart().prop(HedgeBlock.SOUTH, false).addTexturedModel(ModelTemplate.HEDGE_CENTER_SIDE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createPart().prop(HedgeBlock.WEST, false).addTexturedModel(ModelTemplate.HEDGE_CENTER_SIDE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createPart().prop(HedgeBlock.NORTH, true)
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_1.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180))
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_2.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180))
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_3.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createPart().prop(HedgeBlock.EAST, true)
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_1.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270))
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_2.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270))
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_3.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createPart().prop(HedgeBlock.SOUTH, true)
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_1.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0))
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_2.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0))
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_3.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createPart().prop(HedgeBlock.WEST, true)
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_1.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90))
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_2.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90))
                .addTexturedModel(ModelTemplate.HEDGE_CONNECTION_STYLE_3.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        this.multiPartStateConsumer.accept(state);
    }
}
