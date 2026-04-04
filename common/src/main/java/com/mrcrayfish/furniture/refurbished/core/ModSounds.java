package com.mrcrayfish.furniture.refurbished.core;

import com.google.common.base.Suppliers;
import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModSounds
{
    public static final RegistryEntry<SoundEvent> BLOCK_CHAIR_SLIDE = RegistryEntry.soundEvent(Utils.id("block.chair.slide"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_DOORBELL_CHIME = RegistryEntry.soundEvent(Utils.id("block.doorbell.chime"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_ELECTRICITY_GENERATOR_ENGINE = RegistryEntry.soundEvent(Utils.id("block.electricity_generator.engine"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_STORAGE_JAR_INSERT_ITEM = RegistryEntry.soundEvent(Utils.id("block.storage_jar.insert_item"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_RECYCLE_BIN_ENGINE = RegistryEntry.soundEvent(Utils.id("block.recycle_bin.engine"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_CEILING_FAN_SPIN = RegistryEntry.soundEvent(Utils.id("block.ceiling_fan.spin"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_LIGHTSWITCH_FLICK = RegistryEntry.soundEvent(Utils.id("block.lightswitch.flick"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TRAMPOLINE_BOUNCE = RegistryEntry.soundEvent(Utils.id("block.trampoline.bounce"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TRAMPOLINE_SUPER_BOUNCE = RegistryEntry.soundEvent(Utils.id("block.trampoline.super_bounce"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_COLOUR_TEST = RegistryEntry.soundEvent(Utils.id("block.television.channel.colour_test"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_WHITE_NOISE = RegistryEntry.soundEvent(Utils.id("block.television.channel.white_noise"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_DANCE_MUSIC = RegistryEntry.soundEvent(Utils.id("block.television.channel.dance_music"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_VILLAGER_NEWS = RegistryEntry.soundEvent(Utils.id("block.television.channel.villager_news"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_CHIRP_SONG = RegistryEntry.soundEvent(Utils.id("block.television.channel.chirp_song"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_OCEAN_SUNSET = RegistryEntry.soundEvent(Utils.id("block.television.channel.ocean_sunset"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_BLOCKY_GAME = RegistryEntry.soundEvent(Utils.id("block.television.channel.blocky_game"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_RETRO_SONG = RegistryEntry.soundEvent(Utils.id("block.television.channel.retro_song"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_PLACE_INGREDIENT = RegistryEntry.soundEvent(Utils.id("block.frying_pan.place_ingredient"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_BREAK = RegistryEntry.soundEvent(Utils.id("block.frying_pan.break"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_HIT = RegistryEntry.soundEvent(Utils.id("block.frying_pan.hit"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_STEP = RegistryEntry.soundEvent(Utils.id("block.frying_pan.step"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_PLACE = RegistryEntry.soundEvent(Utils.id("block.frying_pan.place"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_SIZZLING = RegistryEntry.soundEvent(Utils.id("block.frying_pan.sizzling"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TOASTER_DOWN = RegistryEntry.soundEvent(Utils.id("block.toaster.down"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TOASTER_POP = RegistryEntry.soundEvent(Utils.id("block.toaster.pop"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TOASTER_INSERT = RegistryEntry.soundEvent(Utils.id("block.toaster.insert"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_COOLER_OPEN = RegistryEntry.soundEvent(Utils.id("block.cooler.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_COOLER_CLOSE = RegistryEntry.soundEvent(Utils.id("block.cooler.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_MICROWAVE_OPEN = RegistryEntry.soundEvent(Utils.id("block.microwave.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_MICROWAVE_CLOSE = RegistryEntry.soundEvent(Utils.id("block.microwave.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_MICROWAVE_FAN = RegistryEntry.soundEvent(Utils.id("block.microwave.fan"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_KITCHEN_DRAWER_OPEN = RegistryEntry.soundEvent(Utils.id("block.kitchen_drawer.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_KITCHEN_DRAWER_CLOSE = RegistryEntry.soundEvent(Utils.id("block.kitchen_drawer.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_KITCHEN_SINK_FILL = RegistryEntry.soundEvent(Utils.id("block.kitchen_sink.fill"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_CUTTING_BOARD_PLACED_INGREDIENT = RegistryEntry.soundEvent(Utils.id("block.cutting_board.place_ingredient"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRIDGE_OPEN = RegistryEntry.soundEvent(Utils.id("block.fridge.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRIDGE_CLOSE = RegistryEntry.soundEvent(Utils.id("block.fridge.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FREEZER_OPEN = RegistryEntry.soundEvent(Utils.id("block.freezer.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FREEZER_CLOSE = RegistryEntry.soundEvent(Utils.id("block.freezer.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_STOVE_OPEN = RegistryEntry.soundEvent(Utils.id("block.stove.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_STOVE_CLOSE = RegistryEntry.soundEvent(Utils.id("block.stove.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_DRAWER_OPEN = RegistryEntry.soundEvent(Utils.id("block.drawer.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_DRAWER_CLOSE = RegistryEntry.soundEvent(Utils.id("block.drawer.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_CABINET_OPEN = RegistryEntry.soundEvent(Utils.id("block.cabinet.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_CABINET_CLOSE = RegistryEntry.soundEvent(Utils.id("block.cabinet.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_WORKBENCH_CRAFT = RegistryEntry.soundEvent(Utils.id("block.workbench.craft"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_PACKAGE_OPEN = RegistryEntry.soundEvent(Utils.id("item.package.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_SELECTED_NODE = RegistryEntry.soundEvent(Utils.id("item.wrench.selected_node"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_REMOVE_LINK = RegistryEntry.soundEvent(Utils.id("item.wrench.remove_link"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_CONNECTED_LINK = RegistryEntry.soundEvent(Utils.id("item.wrench.connected_link"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_HOVER_LINK = RegistryEntry.soundEvent(Utils.id("item.wrench.hover_link"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_KNIFE_CHOP = RegistryEntry.soundEvent(Utils.id("item.knife.chop"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_SPATULA_SCOOP = RegistryEntry.soundEvent(Utils.id("item.spatula.scoop"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_CLICK = RegistryEntry.soundEvent(Utils.id("ui.paddle_ball.retro_click"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_HIT = RegistryEntry.soundEvent(Utils.id("ui.paddle_ball.retro_hit"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_SUCCESS = RegistryEntry.soundEvent(Utils.id("ui.paddle_ball.retro_success"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_FAIL = RegistryEntry.soundEvent(Utils.id("ui.paddle_ball.retro_fail"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_WIN = RegistryEntry.soundEvent(Utils.id("ui.paddle_ball.retro_win"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_LOSE = RegistryEntry.soundEvent(Utils.id("ui.paddle_ball.retro_lose"), id -> () -> SoundEvent.createVariableRangeEvent(id));

    public static final Supplier<SoundType> SOUND_TYPE_FRYING_PAN = Suppliers.memoize(() -> {
        return new SoundType(1.0F, 1.0F, ModSounds.BLOCK_FRYING_PAN_BREAK.get(), ModSounds.BLOCK_FRYING_PAN_STEP.get(), ModSounds.BLOCK_FRYING_PAN_PLACE.get(), ModSounds.BLOCK_FRYING_PAN_HIT.get(), ModSounds.BLOCK_FRYING_PAN_PLACE.get());
    });
}
