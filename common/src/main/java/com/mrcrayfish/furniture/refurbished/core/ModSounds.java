package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.sounds.SoundEvent;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModSounds
{
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
    public static final RegistryEntry<SoundEvent> ITEM_PACKAGE_OPEN = RegistryEntry.soundEvent(Utils.resource("item.package.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_SELECTED_NODE = RegistryEntry.soundEvent(Utils.resource("item.wrench.selected_node"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_REMOVE_LINK = RegistryEntry.soundEvent(Utils.resource("item.wrench.remove_link"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_CONNECTED_LINK = RegistryEntry.soundEvent(Utils.resource("item.wrench.connected_link"), id -> () -> SoundEvent.createVariableRangeEvent(id));
}
