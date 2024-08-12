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
    public static final RegistryEntry<SoundEvent> BLOCK_CHAIR_SLIDE = RegistryEntry.soundEvent(Utils.resource("block.chair.slide"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_DOORBELL_CHIME = RegistryEntry.soundEvent(Utils.resource("block.doorbell.chime"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_ELECTRICITY_GENERATOR_ENGINE = RegistryEntry.soundEvent(Utils.resource("block.electricity_generator.engine"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_STORAGE_JAR_INSERT_ITEM = RegistryEntry.soundEvent(Utils.resource("block.storage_jar.insert_item"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_RECYCLE_BIN_ENGINE = RegistryEntry.soundEvent(Utils.resource("block.recycle_bin.engine"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_CEILING_FAN_SPIN = RegistryEntry.soundEvent(Utils.resource("block.ceiling_fan.spin"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_LIGHTSWITCH_FLICK = RegistryEntry.soundEvent(Utils.resource("block.lightswitch.flick"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TRAMPOLINE_BOUNCE = RegistryEntry.soundEvent(Utils.resource("block.trampoline.bounce"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TRAMPOLINE_SUPER_BOUNCE = RegistryEntry.soundEvent(Utils.resource("block.trampoline.super_bounce"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_COLOUR_TEST = RegistryEntry.soundEvent(Utils.resource("block.television.channel.colour_test"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_WHITE_NOISE = RegistryEntry.soundEvent(Utils.resource("block.television.channel.white_noise"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_DANCE_MUSIC = RegistryEntry.soundEvent(Utils.resource("block.television.channel.dance_music"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_VILLAGER_NEWS = RegistryEntry.soundEvent(Utils.resource("block.television.channel.villager_news"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_CHIRP_SONG = RegistryEntry.soundEvent(Utils.resource("block.television.channel.chirp_song"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_OCEAN_SUNSET = RegistryEntry.soundEvent(Utils.resource("block.television.channel.ocean_sunset"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_BLOCKY_GAME = RegistryEntry.soundEvent(Utils.resource("block.television.channel.blocky_game"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_RETRO_SONG = RegistryEntry.soundEvent(Utils.resource("block.television.channel.retro_song"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_PLACE_INGREDIENT = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.place_ingredient"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_BREAK = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.break"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_HIT = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.hit"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_STEP = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.step"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_PLACE = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.place"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_SIZZLING = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.sizzling"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TOASTER_DOWN = RegistryEntry.soundEvent(Utils.resource("block.toaster.down"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TOASTER_POP = RegistryEntry.soundEvent(Utils.resource("block.toaster.pop"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TOASTER_INSERT = RegistryEntry.soundEvent(Utils.resource("block.toaster.insert"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_COOLER_OPEN = RegistryEntry.soundEvent(Utils.resource("block.cooler.open"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_COOLER_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.cooler.close"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_MICROWAVE_OPEN = RegistryEntry.soundEvent(Utils.resource("block.microwave.open"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_MICROWAVE_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.microwave.close"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_MICROWAVE_FAN = RegistryEntry.soundEvent(Utils.resource("block.microwave.fan"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_KITCHEN_DRAWER_OPEN = RegistryEntry.soundEvent(Utils.resource("block.kitchen_drawer.open"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_KITCHEN_DRAWER_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.kitchen_drawer.close"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_KITCHEN_SINK_FILL = RegistryEntry.soundEvent(Utils.resource("block.kitchen_sink.fill"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_CUTTING_BOARD_PLACED_INGREDIENT = RegistryEntry.soundEvent(Utils.resource("block.cutting_board.place_ingredient"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRIDGE_OPEN = RegistryEntry.soundEvent(Utils.resource("block.fridge.open"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRIDGE_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.fridge.close"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FREEZER_OPEN = RegistryEntry.soundEvent(Utils.resource("block.freezer.open"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FREEZER_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.freezer.close"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_STOVE_OPEN = RegistryEntry.soundEvent(Utils.resource("block.stove.open"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_STOVE_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.stove.close"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_DRAWER_OPEN = RegistryEntry.soundEvent(Utils.resource("block.drawer.open"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_DRAWER_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.drawer.close"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_CABINET_OPEN = RegistryEntry.soundEvent(Utils.resource("block.cabinet.open"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_CABINET_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.cabinet.close"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_WORKBENCH_CRAFT = RegistryEntry.soundEvent(Utils.resource("block.workbench.craft"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_PACKAGE_OPEN = RegistryEntry.soundEvent(Utils.resource("item.package.open"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_SELECTED_NODE = RegistryEntry.soundEvent(Utils.resource("item.wrench.selected_node"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_REMOVE_LINK = RegistryEntry.soundEvent(Utils.resource("item.wrench.remove_link"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_CONNECTED_LINK = RegistryEntry.soundEvent(Utils.resource("item.wrench.connected_link"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_HOVER_LINK = RegistryEntry.soundEvent(Utils.resource("item.wrench.hover_link"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_KNIFE_CHOP = RegistryEntry.soundEvent(Utils.resource("item.knife.chop"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_SPATULA_SCOOP = RegistryEntry.soundEvent(Utils.resource("item.spatula.scoop"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_CLICK = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_click"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_HIT = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_hit"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_SUCCESS = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_success"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_FAIL = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_fail"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_WIN = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_win"), id -> () -> new SoundEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_LOSE = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_lose"), id -> () -> new SoundEvent(id));

    public static final Supplier<SoundType> SOUND_TYPE_FRYING_PAN = Suppliers.memoize(() -> {
        return new SoundType(1.0F, 1.0F, ModSounds.BLOCK_FRYING_PAN_BREAK.get(), ModSounds.BLOCK_FRYING_PAN_STEP.get(), ModSounds.BLOCK_FRYING_PAN_PLACE.get(), ModSounds.BLOCK_FRYING_PAN_HIT.get(), ModSounds.BLOCK_FRYING_PAN_PLACE.get());
    });
}
