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
    public static final RegistryEntry<SoundEvent> ITEM_PACKAGE_OPEN = RegistryEntry.soundEvent(Utils.resource("item.package.open"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_SELECTED_NODE = RegistryEntry.soundEvent(Utils.resource("item.wrench.selected_node"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_REMOVE_LINK = RegistryEntry.soundEvent(Utils.resource("item.wrench.remove_link"), id -> () -> SoundEvent.createVariableRangeEvent(id));
    public static final RegistryEntry<SoundEvent> ITEM_WRENCH_CONNECTED_LINK = RegistryEntry.soundEvent(Utils.resource("item.wrench.connected_link"), id -> () -> SoundEvent.createVariableRangeEvent(id));
}
