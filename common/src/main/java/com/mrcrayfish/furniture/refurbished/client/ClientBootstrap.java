package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.framework.api.event.ClientConnectionEvents;
import com.mrcrayfish.framework.api.event.PlayerEvents;
import com.mrcrayfish.framework.api.event.TickEvents;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.ElectricityGeneratorScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.FreezerScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.MicrowaveScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.PostBoxScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.RecyclingBinScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.StoveScreen;
import com.mrcrayfish.furniture.refurbished.client.particle.BounceParticle;
import com.mrcrayfish.furniture.refurbished.client.particle.SteamParticle;
import com.mrcrayfish.furniture.refurbished.client.particle.SuperBounceParticle;
import com.mrcrayfish.furniture.refurbished.client.registration.BlockColorsRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.BlockEntityRendererRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.EntityRendererRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ItemColorsRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ParticleProviderRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.RenderTypeRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.*;
import com.mrcrayfish.furniture.refurbished.client.renderer.entity.SeatRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModEntities;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class ClientBootstrap
{
    public static void init()
    {
        CreativeFilters.get();
        TickEvents.START_RENDER.register(partialTick -> {
            LinkHandler.get().beforeRender(partialTick);
            ElectricBlockEntityRenderer.clearDrawn();
        });
        ClientConnectionEvents.LOGGING_OUT.register(connection -> {
            AudioManager.get().resetSounds();
        });
        PlayerEvents.CHANGE_DIMENSION.register((player, oldDimension, newDimension) -> {
            AudioManager.get().resetSounds();
        });
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_OAK_LIGHT.get(), ExtraModels.OAK_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_SPRUCE_LIGHT.get(), ExtraModels.SPRUCE_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_BIRCH_LIGHT.get(), ExtraModels.BIRCH_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_JUNGLE_LIGHT.get(), ExtraModels.JUNGLE_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_ACACIA_LIGHT.get(), ExtraModels.ACACIA_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_DARK_OAK_LIGHT.get(), ExtraModels.DARK_OAK_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_CHERRY_LIGHT.get(), ExtraModels.CHERRY_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_MANGROVE_LIGHT.get(), ExtraModels.MANGROVE_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_CRIMSON_LIGHT.get(), ExtraModels.CRIMSON_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_WARPED_LIGHT.get(), ExtraModels.WARPED_LIGHT_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_OAK_DARK.get(), ExtraModels.OAK_DARK_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_SPRUCE_DARK.get(), ExtraModels.SPRUCE_DARK_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_BIRCH_DARK.get(), ExtraModels.BIRCH_DARK_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_JUNGLE_DARK.get(), ExtraModels.JUNGLE_DARK_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_ACACIA_DARK.get(), ExtraModels.ACACIA_DARK_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_DARK_OAK_DARK.get(), ExtraModels.DARK_OAK_DARK_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_CHERRY_DARK.get(), ExtraModels.CHERRY_DARK_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_MANGROVE_DARK.get(), ExtraModels.MANGROVE_DARK_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_CRIMSON_DARK.get(), ExtraModels.CRIMSON_DARK_CEILING_FAN_BLADE::getModel);
        CeilingFanBlockEntityRenderer.registerFanBlade(ModBlocks.CEILING_FAN_WARPED_DARK.get(), ExtraModels.WARPED_DARK_CEILING_FAN_BLADE::getModel);
    }

    public static void registerScreens(ScreenRegister register)
    {
        register.apply(ModMenuTypes.FREEZER.get(), FreezerScreen::new);
        register.apply(ModMenuTypes.MICROWAVE.get(), MicrowaveScreen::new);
        register.apply(ModMenuTypes.STOVE.get(), StoveScreen::new);
        register.apply(ModMenuTypes.POST_BOX.get(), PostBoxScreen::new);
        register.apply(ModMenuTypes.ELECTRICITY_GENERATOR.get(), ElectricityGeneratorScreen::new);
        register.apply(ModMenuTypes.RECYCLE_BIN.get(), RecyclingBinScreen::new);
    }

    public static void registerBlockEntityRenderers(BlockEntityRendererRegister register)
    {
        register.apply(ModBlockEntities.KITCHEN_SINK.get(), KitchenSinkBlockEntityRenderer::new);
        register.apply(ModBlockEntities.GRILL.get(), GrillBlockEntityRenderer::new);
        register.apply(ModBlockEntities.TOASTER.get(), ToasterBlockEntityRenderer::new);
        register.apply(ModBlockEntities.CUTTING_BOARD.get(), CuttingBoardBlockEntityRenderer::new);
        register.apply(ModBlockEntities.FRYING_PAN.get(), FryingPanBlockEntityRenderer::new);
        register.apply(ModBlockEntities.LIGHTSWITCH.get(), ElectricBlockEntityRenderer::new);
        register.apply(ModBlockEntities.LIGHTING.get(), ElectricBlockEntityRenderer::new);
        register.apply(ModBlockEntities.ELECTRICITY_GENERATOR.get(), ElectricBlockEntityRenderer::new);
        register.apply(ModBlockEntities.DOORBELL.get(), ElectricBlockEntityRenderer::new);
        register.apply(ModBlockEntities.FREEZER.get(), ElectricBlockEntityRenderer::new);
        register.apply(ModBlockEntities.MICROWAVE.get(), ElectricBlockEntityRenderer::new);
        register.apply(ModBlockEntities.STORAGE_JAR.get(), StorageJarRenderer::new);
        register.apply(ModBlockEntities.RECYCLE_BIN.get(), ElectricBlockEntityRenderer::new);
        register.apply(ModBlockEntities.CEILING_FAN.get(), CeilingFanBlockEntityRenderer::new);
        register.apply(ModBlockEntities.RANGE_HOOD.get(), ElectricBlockEntityRenderer::new);
        register.apply(ModBlockEntities.PLATE.get(), PlateBlockEntityRenderer::new);
        register.apply(ModBlockEntities.BASIN.get(), BasinBlockEntityRenderer::new);
        register.apply(ModBlockEntities.TOILET.get(), ToiletBlockEntityRenderer::new);
        register.apply(ModBlockEntities.BATH.get(), BathBlockEntityRenderer::new);
        register.apply(ModBlockEntities.TELEVISION.get(), TelevisionBlockEntityRenderer::new);
        register.apply(ModBlockEntities.COMPUTER.get(), ComputerBlockEntityRenderer::new);
    }

    public static void registerEntityRenderers(EntityRendererRegister register)
    {
        register.apply(ModEntities.SEAT.get(), SeatRenderer::new);
    }
    
    public static void registerRenderTypes(RenderTypeRegister register)
    {
        register.apply(ModBlocks.GRILL_WHITE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_ORANGE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_MAGENTA.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_LIGHT_BLUE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_YELLOW.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_LIME.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_PINK.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_GRAY.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_LIGHT_GRAY.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_CYAN.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_PURPLE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_BLUE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_BROWN.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_GREEN.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_RED.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_BLACK.get(), RenderType.cutout());
        register.apply(ModBlocks.MICROWAVE_LIGHT.get(), RenderType.cutout());
        register.apply(ModBlocks.MICROWAVE_DARK.get(), RenderType.cutout());
        register.apply(ModBlocks.STOVE_LIGHT.get(), RenderType.cutout());
        register.apply(ModBlocks.STOVE_DARK.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_SPRUCE.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_BIRCH.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_JUNGLE.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_ACACIA.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_DARK_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_MANGROVE.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_CHERRY.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_CRIMSON.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_WARPED.get(), RenderType.cutout());
        register.apply(ModBlocks.CEILING_LIGHT_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_LIGHT_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_WHITE.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_ORANGE.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_MAGENTA.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_LIGHT_BLUE.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_YELLOW.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_LIME.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_PINK.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_GRAY.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_LIGHT_GRAY.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_CYAN.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_PURPLE.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_BLUE.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_BROWN.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_GREEN.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_RED.get(), RenderType.translucent());
        register.apply(ModBlocks.LAMP_BLACK.get(), RenderType.translucent());
        register.apply(ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get(), RenderType.cutout());
        register.apply(ModBlocks.ELECTRICITY_GENERATOR_DARK.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_SPRUCE.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_BIRCH.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_JUNGLE.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_ACACIA.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_DARK_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_MANGROVE.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_CHERRY.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_CRIMSON.get(), RenderType.cutout());
        register.apply(ModBlocks.STORAGE_JAR_WARPED.get(), RenderType.cutout());
        register.apply(ModBlocks.CEILING_FAN_OAK_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_SPRUCE_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_BIRCH_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_JUNGLE_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_ACACIA_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_DARK_OAK_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_MANGROVE_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_CHERRY_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_CRIMSON_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_WARPED_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_OAK_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_SPRUCE_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_BIRCH_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_JUNGLE_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_ACACIA_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_DARK_OAK_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_MANGROVE_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_CHERRY_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_CRIMSON_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_FAN_WARPED_DARK.get(), RenderType.translucent());
        register.apply(ModBlocks.TRAMPOLINE_WHITE.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_ORANGE.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_MAGENTA.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_LIGHT_BLUE.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_YELLOW.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_LIME.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_PINK.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_GRAY.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_LIGHT_GRAY.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_CYAN.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_PURPLE.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_BLUE.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_BROWN.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_GREEN.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_RED.get(), RenderType.cutout());
        register.apply(ModBlocks.TRAMPOLINE_BLACK.get(), RenderType.cutout());
        register.apply(ModBlocks.RANGE_HOOD_LIGHT.get(), RenderType.cutout());
        register.apply(ModBlocks.RANGE_HOOD_DARK.get(), RenderType.cutout());
        register.apply(ModBlocks.HEDGE_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.HEDGE_SPRUCE.get(), RenderType.cutout());
        register.apply(ModBlocks.HEDGE_BIRCH.get(), RenderType.cutout());
        register.apply(ModBlocks.HEDGE_JUNGLE.get(), RenderType.cutout());
        register.apply(ModBlocks.HEDGE_ACACIA.get(), RenderType.cutout());
        register.apply(ModBlocks.HEDGE_DARK_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.HEDGE_MANGROVE.get(), RenderType.cutout());
        register.apply(ModBlocks.HEDGE_CHERRY.get(), RenderType.cutout());
        register.apply(ModBlocks.HEDGE_AZALEA.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_SPRUCE.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_BIRCH.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_JUNGLE.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_ACACIA.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_DARK_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_MANGROVE.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_CHERRY.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_CRIMSON.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_WARPED.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_WHITE.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_ORANGE.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_MAGENTA.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_LIGHT_BLUE.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_YELLOW.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_LIME.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_PINK.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_GRAY.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_LIGHT_GRAY.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_CYAN.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_PURPLE.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_BLUE.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_BROWN.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_GREEN.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_RED.get(), RenderType.cutout());
        register.apply(ModBlocks.TOILET_BLACK.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_SPRUCE.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_BIRCH.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_JUNGLE.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_ACACIA.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_DARK_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_MANGROVE.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_CHERRY.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_CRIMSON.get(), RenderType.cutout());
        register.apply(ModBlocks.LATTICE_FENCE_WARPED.get(), RenderType.cutout());
        register.apply(ModBlocks.TELEVISION.get(), RenderType.cutout());
    }

    public static void registerParticleProviders(ParticleProviderRegister register)
    {
        register.apply(ModParticleTypes.BOUNCE.get(), BounceParticle.Provider::new);
        register.apply(ModParticleTypes.SUPER_BOUNCE.get(), SuperBounceParticle.Provider::new);
        register.apply(ModParticleTypes.STEAM.get(), SteamParticle.Provider::new);
    }

    public static void registerBlockColors(BlockColorsRegister register)
    {
        register.apply((state, reader, pos, index) -> {
            return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.getDefaultColor();
        }, ModBlocks.HEDGE_OAK.get(), ModBlocks.HEDGE_JUNGLE.get(), ModBlocks.HEDGE_ACACIA.get(), ModBlocks.HEDGE_DARK_OAK.get());

        register.apply((state, reader, pos, i) -> {
            return FoliageColor.getEvergreenColor();
        }, ModBlocks.HEDGE_SPRUCE.get());

        register.apply((state, reader, pos, i) -> {
            return FoliageColor.getBirchColor();
        }, ModBlocks.HEDGE_BIRCH.get());

        register.apply((state, reader, pos, i) -> {
            return FoliageColor.getMangroveColor();
        }, ModBlocks.HEDGE_MANGROVE.get());
    }

    public static void registerItemColors(ItemColorsRegister register)
    {
        register.apply((stack, index) -> {
            BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
            return Minecraft.getInstance().getBlockColors().getColor(state, null, null, index);
        }, ModBlocks.HEDGE_OAK.get(), ModBlocks.HEDGE_SPRUCE.get(), ModBlocks.HEDGE_BIRCH.get(), ModBlocks.HEDGE_JUNGLE.get(), ModBlocks.HEDGE_ACACIA.get(), ModBlocks.HEDGE_DARK_OAK.get(), ModBlocks.HEDGE_MANGROVE.get());
    }
}
