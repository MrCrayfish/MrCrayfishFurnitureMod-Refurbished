package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.*;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.data.model.ModelTemplate;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedBlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class CommonBlockModelProvider
{
    private final Consumer<PreparedBlockState> consumer;

    public CommonBlockModelProvider(Consumer<PreparedBlockState> consumer)
    {
        this.consumer = consumer;
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

    private void table(TableBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, false).parentModel(ModelTemplate.TABLE.stateModel(type).setTextures(textures)).markAsItem();
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, false).parentModel(ModelTemplate.TABLE_NORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, false).parentModel(ModelTemplate.TABLE_NORTH_EAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, false).parentModel(ModelTemplate.TABLE_NORTH_EAST_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, false).parentModel(ModelTemplate.TABLE_EAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, false).parentModel(ModelTemplate.TABLE_EAST_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, true).parentModel(ModelTemplate.TABLE_EAST_SOUTH_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, false).parentModel(ModelTemplate.TABLE_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, true).parentModel(ModelTemplate.TABLE_SOUTH_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, true).parentModel(ModelTemplate.TABLE_SOUTH_WEST_NORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, true).parentModel(ModelTemplate.TABLE_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, true).parentModel(ModelTemplate.TABLE_WEST_NORTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, true).parentModel(ModelTemplate.TABLE_WEST_NORTH_EAST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, false).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, false).parentModel(ModelTemplate.TABLE_NORTH_SOUTH.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, false).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, false).prop(TableBlock.WEST, true).parentModel(ModelTemplate.TABLE_EAST_WEST.stateModel(type).setTextures(textures));
        state.createVariant().prop(TableBlock.NORTH, true).prop(TableBlock.EAST, true).prop(TableBlock.SOUTH, true).prop(TableBlock.WEST, true).parentModel(ModelTemplate.TABLE_NORTH_EAST_SOUTH_WEST.stateModel(type).setTextures(textures));
        this.consumer.accept(state);
    }

    private void chair(ChairBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.NORTH).prop(ChairBlock.TUCKED, false).parentModel(ModelTemplate.CHAIR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.EAST).prop(ChairBlock.TUCKED, false).parentModel(ModelTemplate.CHAIR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.SOUTH).prop(ChairBlock.TUCKED, false).parentModel(ModelTemplate.CHAIR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.WEST).prop(ChairBlock.TUCKED, false).parentModel(ModelTemplate.CHAIR.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.NORTH).prop(ChairBlock.TUCKED, true).parentModel(ModelTemplate.CHAIR_TUCKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.EAST).prop(ChairBlock.TUCKED, true).parentModel(ModelTemplate.CHAIR_TUCKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.SOUTH).prop(ChairBlock.TUCKED, true).parentModel(ModelTemplate.CHAIR_TUCKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ChairBlock.DIRECTION, Direction.WEST).prop(ChairBlock.TUCKED, true).parentModel(ModelTemplate.CHAIR_TUCKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void desk(DeskBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.NORTH).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, false).parentModel(ModelTemplate.DESK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.EAST).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, false).parentModel(ModelTemplate.DESK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.SOUTH).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, false).parentModel(ModelTemplate.DESK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.WEST).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, false).parentModel(ModelTemplate.DESK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.NORTH).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, true).parentModel(ModelTemplate.DESK_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.EAST).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, true).parentModel(ModelTemplate.DESK_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.SOUTH).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, true).parentModel(ModelTemplate.DESK_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.WEST).prop(DeskBlock.LEFT, false).prop(DeskBlock.RIGHT, true).parentModel(ModelTemplate.DESK_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.NORTH).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, false).parentModel(ModelTemplate.DESK_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.EAST).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, false).parentModel(ModelTemplate.DESK_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.SOUTH).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, false).parentModel(ModelTemplate.DESK_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.WEST).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, false).parentModel(ModelTemplate.DESK_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.NORTH).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, true).parentModel(ModelTemplate.DESK_MIDDLE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.EAST).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, true).parentModel(ModelTemplate.DESK_MIDDLE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.SOUTH).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, true).parentModel(ModelTemplate.DESK_MIDDLE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DeskBlock.DIRECTION, Direction.WEST).prop(DeskBlock.LEFT, true).prop(DeskBlock.RIGHT, true).parentModel(ModelTemplate.DESK_MIDDLE.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void drawer(DrawerBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_RIGHT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_RIGHT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_RIGHT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_RIGHT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_LEFT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_LEFT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_LEFT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_LEFT_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_MIDDLE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_MIDDLE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_MIDDLE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.DRAWER_MIDDLE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_RIGHT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_RIGHT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_RIGHT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, false).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_RIGHT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_LEFT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_LEFT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_LEFT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, false).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_LEFT_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_MIDDLE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_MIDDLE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_MIDDLE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.LEFT, true).prop(DrawerBlock.RIGHT, true).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.DRAWER_MIDDLE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void crate(CrateBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(CrateBlock.OPEN, false).parentModel(ModelTemplate.CRATE_CLOSED.stateModel(type).setTextures(textures)).markAsItem();
        state.createVariant().prop(CrateBlock.OPEN, true).parentModel(ModelTemplate.CRATE_OPEN.stateModel(type).setTextures(textures));
        this.consumer.accept(state);
    }

    private void woodenKitchenCabinetry(WoodenKitchenCabinetryBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).parentModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).parentModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).parentModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).parentModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void woodenKitchenDrawer(WoodenKitchenDrawerBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void woodenKitchenSink(WoodenKitchenSinkBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).parentModel(ModelTemplate.KITCHEN_SINK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).parentModel(ModelTemplate.KITCHEN_SINK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).parentModel(ModelTemplate.KITCHEN_SINK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).parentModel(ModelTemplate.KITCHEN_SINK.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void colouredKitchenCabinetry(ColouredKitchenCabinetryBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).parentModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.NORTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).parentModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.EAST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).parentModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.SOUTH).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.DEFAULT).parentModel(ModelTemplate.KITCHEN_CABINETRY_DEFAULT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.INSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_LEFT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(WoodenKitchenCabinetryBlock.DIRECTION, Direction.WEST).prop(WoodenKitchenCabinetryBlock.SHAPE, KitchenCabinetryBlock.Shape.OUTSIDE_CORNER_RIGHT).parentModel(ModelTemplate.KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void colouredKitchenDrawer(ColouredKitchenDrawerBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.OPEN, false).parentModel(ModelTemplate.KITCHEN_DRAWER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.OPEN, true).parentModel(ModelTemplate.KITCHEN_DRAWER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void colouredKitchenSink(ColouredKitchenSinkBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.NORTH).parentModel(ModelTemplate.KITCHEN_SINK.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.EAST).parentModel(ModelTemplate.KITCHEN_SINK.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.SOUTH).parentModel(ModelTemplate.KITCHEN_SINK.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DrawerBlock.DIRECTION, Direction.WEST).parentModel(ModelTemplate.KITCHEN_SINK.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void grill(GrillBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(GrillBlock.DIRECTION, Direction.NORTH).parentModel(ModelTemplate.GRILL.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(GrillBlock.DIRECTION, Direction.EAST).parentModel(ModelTemplate.GRILL.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(GrillBlock.DIRECTION, Direction.SOUTH).parentModel(ModelTemplate.GRILL.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(GrillBlock.DIRECTION, Direction.WEST).parentModel(ModelTemplate.GRILL.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void cooler(CoolerBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.NORTH).prop(CoolerBlock.OPEN, false).parentModel(ModelTemplate.COOLER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.EAST).prop(CoolerBlock.OPEN, false).parentModel(ModelTemplate.COOLER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.SOUTH).prop(CoolerBlock.OPEN, false).parentModel(ModelTemplate.COOLER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.WEST).prop(CoolerBlock.OPEN, false).parentModel(ModelTemplate.COOLER_CLOSED.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.NORTH).prop(CoolerBlock.OPEN, true).parentModel(ModelTemplate.COOLER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.EAST).prop(CoolerBlock.OPEN, true).parentModel(ModelTemplate.COOLER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.SOUTH).prop(CoolerBlock.OPEN, true).parentModel(ModelTemplate.COOLER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CoolerBlock.DIRECTION, Direction.WEST).prop(CoolerBlock.OPEN, true).parentModel(ModelTemplate.COOLER_OPEN.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }
    
    private void fridge(FridgeBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.NORTH).prop(FridgeBlock.OPEN, false).parentModel(ModelTemplate.FRIDGE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.EAST).prop(FridgeBlock.OPEN, false).parentModel(ModelTemplate.FRIDGE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.SOUTH).prop(FridgeBlock.OPEN, false).parentModel(ModelTemplate.FRIDGE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.WEST).prop(FridgeBlock.OPEN, false).parentModel(ModelTemplate.FRIDGE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.NORTH).prop(FridgeBlock.OPEN, true).parentModel(ModelTemplate.FRIDGE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.EAST).prop(FridgeBlock.OPEN, true).parentModel(ModelTemplate.FRIDGE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.SOUTH).prop(FridgeBlock.OPEN, true).parentModel(ModelTemplate.FRIDGE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FridgeBlock.DIRECTION, Direction.WEST).prop(FridgeBlock.OPEN, true).parentModel(ModelTemplate.FRIDGE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void freezer(FreezerBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block.getFridge().get()));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.NORTH).prop(FreezerBlock.OPEN, false).parentModel(ModelTemplate.FREEZER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.EAST).prop(FreezerBlock.OPEN, false).parentModel(ModelTemplate.FREEZER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.SOUTH).prop(FreezerBlock.OPEN, false).parentModel(ModelTemplate.FREEZER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.WEST).prop(FreezerBlock.OPEN, false).parentModel(ModelTemplate.FREEZER_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.NORTH).prop(FreezerBlock.OPEN, true).parentModel(ModelTemplate.FREEZER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.EAST).prop(FreezerBlock.OPEN, true).parentModel(ModelTemplate.FREEZER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.SOUTH).prop(FreezerBlock.OPEN, true).parentModel(ModelTemplate.FREEZER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FreezerBlock.DIRECTION, Direction.WEST).prop(FreezerBlock.OPEN, true).parentModel(ModelTemplate.FREEZER_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void toaster(ToasterBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.NORTH).prop(ToasterBlock.POWERED, false).parentModel(ModelTemplate.TOASTER.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.EAST).prop(ToasterBlock.POWERED, false).parentModel(ModelTemplate.TOASTER.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.SOUTH).prop(ToasterBlock.POWERED, false).parentModel(ModelTemplate.TOASTER.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.WEST).prop(ToasterBlock.POWERED, false).parentModel(ModelTemplate.TOASTER.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.NORTH).prop(ToasterBlock.POWERED, true).parentModel(ModelTemplate.TOASTER_COOKING.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.EAST).prop(ToasterBlock.POWERED, true).parentModel(ModelTemplate.TOASTER_COOKING.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.SOUTH).prop(ToasterBlock.POWERED, true).parentModel(ModelTemplate.TOASTER_COOKING.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(ToasterBlock.DIRECTION, Direction.WEST).prop(ToasterBlock.POWERED, true).parentModel(ModelTemplate.TOASTER_COOKING.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void microwave(MicrowaveBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.NORTH).prop(MicrowaveBlock.OPEN, false).parentModel(ModelTemplate.MICROWAVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.EAST).prop(MicrowaveBlock.OPEN, false).parentModel(ModelTemplate.MICROWAVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.SOUTH).prop(MicrowaveBlock.OPEN, false).parentModel(ModelTemplate.MICROWAVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.WEST).prop(MicrowaveBlock.OPEN, false).parentModel(ModelTemplate.MICROWAVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.NORTH).prop(MicrowaveBlock.OPEN, true).parentModel(ModelTemplate.MICROWAVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.EAST).prop(MicrowaveBlock.OPEN, true).parentModel(ModelTemplate.MICROWAVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.SOUTH).prop(MicrowaveBlock.OPEN, true).parentModel(ModelTemplate.MICROWAVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(MicrowaveBlock.DIRECTION, Direction.WEST).prop(MicrowaveBlock.OPEN, true).parentModel(ModelTemplate.MICROWAVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void stove(StoveBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.NORTH).prop(StoveBlock.OPEN, false).parentModel(ModelTemplate.STOVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.EAST).prop(StoveBlock.OPEN, false).parentModel(ModelTemplate.STOVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.SOUTH).prop(StoveBlock.OPEN, false).parentModel(ModelTemplate.STOVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.WEST).prop(StoveBlock.OPEN, false).parentModel(ModelTemplate.STOVE_CLOSED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.NORTH).prop(StoveBlock.OPEN, true).parentModel(ModelTemplate.STOVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.EAST).prop(StoveBlock.OPEN, true).parentModel(ModelTemplate.STOVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.SOUTH).prop(StoveBlock.OPEN, true).parentModel(ModelTemplate.STOVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(StoveBlock.DIRECTION, Direction.WEST).prop(StoveBlock.OPEN, true).parentModel(ModelTemplate.STOVE_OPEN.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void cuttingBoard(CuttingBoardBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(CuttingBoardBlock.DIRECTION, Direction.NORTH).parentModel(ModelTemplate.CUTTING_BOARD.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(CuttingBoardBlock.DIRECTION, Direction.EAST).parentModel(ModelTemplate.CUTTING_BOARD.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CuttingBoardBlock.DIRECTION, Direction.SOUTH).parentModel(ModelTemplate.CUTTING_BOARD.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CuttingBoardBlock.DIRECTION, Direction.WEST).parentModel(ModelTemplate.CUTTING_BOARD.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void fryingPan(FryingPanBlock block)
    {
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(MetalType.LIGHT));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(FryingPanBlock.DIRECTION, Direction.NORTH).existingModel(ModelTemplate.FRYING_PAN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(FryingPanBlock.DIRECTION, Direction.EAST).existingModel(ModelTemplate.FRYING_PAN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(FryingPanBlock.DIRECTION, Direction.SOUTH).existingModel(ModelTemplate.FRYING_PAN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(FryingPanBlock.DIRECTION, Direction.WEST).existingModel(ModelTemplate.FRYING_PAN.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void mailbox(MailboxBlock block)
    {
        WoodType type = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.woodParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.NORTH).prop(MailboxBlock.ENABLED, false).parentModel(ModelTemplate.MAIL_BOX.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.ENABLED, false).parentModel(ModelTemplate.MAIL_BOX.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.ENABLED, false).parentModel(ModelTemplate.MAIL_BOX.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.ENABLED, false).parentModel(ModelTemplate.MAIL_BOX.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.NORTH).prop(DrawerBlock.ENABLED, true).parentModel(ModelTemplate.MAIL_BOX_UNCHECKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.EAST).prop(DrawerBlock.ENABLED, true).parentModel(ModelTemplate.MAIL_BOX_UNCHECKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.SOUTH).prop(DrawerBlock.ENABLED, true).parentModel(ModelTemplate.MAIL_BOX_UNCHECKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(MailboxBlock.DIRECTION, Direction.WEST).prop(DrawerBlock.ENABLED, true).parentModel(ModelTemplate.MAIL_BOX_UNCHECKED.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void postBox(PostBoxBlock block)
    {
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(MetalType.LIGHT));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(PostBoxBlock.DIRECTION, Direction.NORTH).existingModel(ModelTemplate.POST_BOX.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(PostBoxBlock.DIRECTION, Direction.EAST).existingModel(ModelTemplate.POST_BOX.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(PostBoxBlock.DIRECTION, Direction.SOUTH).existingModel(ModelTemplate.POST_BOX.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(PostBoxBlock.DIRECTION, Direction.WEST).existingModel(ModelTemplate.POST_BOX.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void sofa(SofaBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.colourParticle(color));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.DEFAULT).parentModel(ModelTemplate.SOFA.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.DEFAULT).parentModel(ModelTemplate.SOFA.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.DEFAULT).parentModel(ModelTemplate.SOFA.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.DEFAULT).parentModel(ModelTemplate.SOFA.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.LEFT).parentModel(ModelTemplate.SOFA_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.LEFT).parentModel(ModelTemplate.SOFA_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.LEFT).parentModel(ModelTemplate.SOFA_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.LEFT).parentModel(ModelTemplate.SOFA_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.RIGHT).parentModel(ModelTemplate.SOFA_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.RIGHT).parentModel(ModelTemplate.SOFA_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.RIGHT).parentModel(ModelTemplate.SOFA_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.RIGHT).parentModel(ModelTemplate.SOFA_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.MIDDLE).parentModel(ModelTemplate.SOFA_MIDDLE.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.MIDDLE).parentModel(ModelTemplate.SOFA_MIDDLE.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.MIDDLE).parentModel(ModelTemplate.SOFA_MIDDLE.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.MIDDLE).parentModel(ModelTemplate.SOFA_MIDDLE.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_LEFT).parentModel(ModelTemplate.SOFA_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_LEFT).parentModel(ModelTemplate.SOFA_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_LEFT).parentModel(ModelTemplate.SOFA_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_LEFT).parentModel(ModelTemplate.SOFA_CORNER_LEFT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.NORTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_RIGHT).parentModel(ModelTemplate.SOFA_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.EAST).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_RIGHT).parentModel(ModelTemplate.SOFA_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.SOUTH).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_RIGHT).parentModel(ModelTemplate.SOFA_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(SofaBlock.DIRECTION, Direction.WEST).prop(SofaBlock.SHAPE, SofaBlock.Shape.CORNER_RIGHT).parentModel(ModelTemplate.SOFA_CORNER_RIGHT.stateModel(color).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void doorbell(DoorbellBlock block)
    {
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(MetalType.LIGHT));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.NORTH).prop(DoorbellBlock.ENABLED, false).existingModel(ModelTemplate.DOORBELL.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0)).markAsItem();
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.EAST).prop(DoorbellBlock.ENABLED, false).existingModel(ModelTemplate.DOORBELL.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.SOUTH).prop(DoorbellBlock.ENABLED, false).existingModel(ModelTemplate.DOORBELL.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.WEST).prop(DoorbellBlock.ENABLED, false).existingModel(ModelTemplate.DOORBELL.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.NORTH).prop(DoorbellBlock.ENABLED, true).existingModel(ModelTemplate.DOORBELL_PRESSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.EAST).prop(DoorbellBlock.ENABLED, true).existingModel(ModelTemplate.DOORBELL_PRESSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.SOUTH).prop(DoorbellBlock.ENABLED, true).existingModel(ModelTemplate.DOORBELL_PRESSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(DoorbellBlock.DIRECTION, Direction.WEST).prop(DoorbellBlock.ENABLED, true).existingModel(ModelTemplate.DOORBELL_PRESSED.stateModel().setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void lightswitch(LightswitchBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180)).markAsItem();
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.POWERED, false).parentModel(ModelTemplate.LIGHTSWITCH_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.WALL).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.FLOOR).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.NORTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.EAST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.SOUTH).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(LightswitchBlock.FACING, Direction.WEST).prop(LightswitchBlock.FACE, AttachFace.CEILING).prop(LightswitchBlock.POWERED, true).parentModel(ModelTemplate.LIGHTSWITCH_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }

    private void ceilingLight(CeilingLightBlock block)
    {
        MetalType type = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.PARTICLE, this.metalParticle(type));
        textures.put(TextureSlot.TEXTURE, this.blockTexture(block));
        PreparedBlockState state = new PreparedBlockState(block);
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180)).markAsItem();
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, false).parentModel(ModelTemplate.CEILING_LIGHT_OFF.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.WALL).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.FLOOR).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R90));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.NORTH).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R0).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.EAST).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R90).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.SOUTH).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R180).setXRotation(VariantProperties.Rotation.R270));
        state.createVariant().prop(CeilingLightBlock.FACING, Direction.WEST).prop(CeilingLightBlock.FACE, AttachFace.CEILING).prop(CeilingLightBlock.POWERED, true).parentModel(ModelTemplate.CEILING_LIGHT_ON.stateModel(type).setTextures(textures).setYRotation(VariantProperties.Rotation.R270).setXRotation(VariantProperties.Rotation.R270));
        this.consumer.accept(state);
    }
}
