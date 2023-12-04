package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.util.LazyValue;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModSounds
{
    public static final RegistryEntry<SoundEvent> BLOCK_CHAIR_SLIDE = RegistryEntry.soundEvent(Utils.resource("block.chair.slide"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_DOORBELL_CHIME = RegistryEntry.soundEvent(Utils.resource("block.doorbell.chime"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_ELECTRICITY_GENERATOR_ENGINE = RegistryEntry.soundEvent(Utils.resource("block.electricity_generator.engine"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_STORAGE_JAR_INSERT_ITEM = RegistryEntry.soundEvent(Utils.resource("block.storage_jar.insert_item"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_RECYCLE_BIN_ENGINE = RegistryEntry.soundEvent(Utils.resource("block.recycle_bin.engine"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_CEILING_FAN_SPIN = RegistryEntry.soundEvent(Utils.resource("block.ceiling_fan.spin"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_LIGHTSWITCH_FLICK = RegistryEntry.soundEvent(Utils.resource("block.lightswitch.flick"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TRAMPOLINE_BOUNCE = RegistryEntry.soundEvent(Utils.resource("block.trampoline.bounce"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TRAMPOLINE_SUPER_BOUNCE = RegistryEntry.soundEvent(Utils.resource("block.trampoline.super_bounce"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_COLOUR_TEST = RegistryEntry.soundEvent(Utils.resource("block.television.channel.colour_test"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_WHITE_NOISE = RegistryEntry.soundEvent(Utils.resource("block.television.channel.white_noise"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_DANCE_MUSIC = RegistryEntry.soundEvent(Utils.resource("block.television.channel.dance_music"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_VILLAGER_NEWS = RegistryEntry.soundEvent(Utils.resource("block.television.channel.villager_news"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_CHIRP_SONG = RegistryEntry.soundEvent(Utils.resource("block.television.channel.chirp_song"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_OCEAN_SUNSET = RegistryEntry.soundEvent(Utils.resource("block.television.channel.ocean_sunset"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_BLOCKY_GAME = RegistryEntry.soundEvent(Utils.resource("block.television.channel.blocky_game"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TELEVISION_CHANNEL_RETRO_SONG = RegistryEntry.soundEvent(Utils.resource("block.television.channel.retro_song"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_PLACE_INGREDIENT = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.place_ingredient"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_BREAK = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.break"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_HIT = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.hit"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_STEP = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.step"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_PLACE = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.place"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_FRYING_PAN_SIZZLING = RegistryEntry.soundEvent(Utils.resource("block.frying_pan.sizzling"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TOASTER_DOWN = RegistryEntry.soundEvent(Utils.resource("block.toaster.down"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TOASTER_POP = RegistryEntry.soundEvent(Utils.resource("block.toaster.pop"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_TOASTER_INSERT = RegistryEntry.soundEvent(Utils.resource("block.toaster.insert"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_COOLER_OPEN = RegistryEntry.soundEvent(Utils.resource("block.cooler.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_COOLER_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.cooler.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_MICROWAVE_OPEN = RegistryEntry.soundEvent(Utils.resource("block.microwave.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> BLOCK_MICROWAVE_CLOSE = RegistryEntry.soundEvent(Utils.resource("block.microwave.close"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_PACKAGE_OPEN = RegistryEntry.soundEvent(Utils.resource("item.package.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_SELECTED_NODE = RegistryEntry.soundEvent(Utils.resource("item.wrench.selected_node"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_REMOVE_LINK = RegistryEntry.soundEvent(Utils.resource("item.wrench.remove_link"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_CONNECTED_LINK = RegistryEntry.soundEvent(Utils.resource("item.wrench.connected_link"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_KNIFE_CHOP = RegistryEntry.soundEvent(Utils.resource("item.knife.chop"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_SPATULA_SCOOP = RegistryEntry.soundEvent(Utils.resource("item.spatula.scoop"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_CLICK = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_click"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_HIT = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_hit"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_SUCCESS = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_success"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_FAIL = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_fail"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_WIN = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_win"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> UI_PADDLE_BALL_RETRO_LOSE = RegistryEntry.soundEvent(Utils.resource("ui.paddle_ball.retro_lose"), id -> () -> SoundEvent.createVariableRangeEvent(id));

    public static final LazyValue<SoundType> SOUND_TYPE_FRYING_PAN = new LazyValue<>(() -> {
        return new SoundType(1.0F, 1.0F, ModSounds.BLOCK_FRYING_PAN_BREAK.get(), ModSounds.BLOCK_FRYING_PAN_STEP.get(), ModSounds.BLOCK_FRYING_PAN_PLACE.get(), ModSounds.BLOCK_FRYING_PAN_HIT.get(), ModSounds.BLOCK_FRYING_PAN_PLACE.get());
    });
}
