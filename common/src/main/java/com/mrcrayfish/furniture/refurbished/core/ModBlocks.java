package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.block.ChairBlock;
import com.mrcrayfish.furniture.refurbished.block.TableBlock;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModBlocks
{
    public static final RegistryEntry<TableBlock> TABLE_OAK = RegistryEntry.blockWithItem(Utils.resource("oak_table"), () -> new TableBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.OAK_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<TableBlock> TABLE_SPRUCE = RegistryEntry.blockWithItem(Utils.resource("spruce_table"), () -> new TableBlock(WoodType.SPRUCE, BlockBehaviour.Properties.of().mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<TableBlock> TABLE_BIRCH = RegistryEntry.blockWithItem(Utils.resource("birch_table"), () -> new TableBlock(WoodType.BIRCH, BlockBehaviour.Properties.of().mapColor(Blocks.BIRCH_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<TableBlock> TABLE_JUNGLE = RegistryEntry.blockWithItem(Utils.resource("jungle_table"), () -> new TableBlock(WoodType.JUNGLE, BlockBehaviour.Properties.of().mapColor(Blocks.JUNGLE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<TableBlock> TABLE_ACACIA = RegistryEntry.blockWithItem(Utils.resource("acacia_table"), () -> new TableBlock(WoodType.ACACIA, BlockBehaviour.Properties.of().mapColor(Blocks.ACACIA_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<TableBlock> TABLE_DARK_OAK = RegistryEntry.blockWithItem(Utils.resource("dark_oak_table"), () -> new TableBlock(WoodType.DARK_OAK, BlockBehaviour.Properties.of().mapColor(Blocks.DARK_OAK_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<TableBlock> TABLE_MANGROVE = RegistryEntry.blockWithItem(Utils.resource("mangrove_table"), () -> new TableBlock(WoodType.MANGROVE, BlockBehaviour.Properties.of().mapColor(Blocks.MANGROVE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<TableBlock> TABLE_CHERRY = RegistryEntry.blockWithItem(Utils.resource("cherry_table"), () -> new TableBlock(WoodType.CHERRY, BlockBehaviour.Properties.of().mapColor(Blocks.CHERRY_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<TableBlock> TABLE_CRIMSON = RegistryEntry.blockWithItem(Utils.resource("crimson_table"), () -> new TableBlock(WoodType.CRIMSON, BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<TableBlock> TABLE_WARPED = RegistryEntry.blockWithItem(Utils.resource("warped_table"), () -> new TableBlock(WoodType.WARPED, BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));
    public static final RegistryEntry<ChairBlock> CHAIR_OAK = RegistryEntry.blockWithItem(Utils.resource("oak_chair"), () -> new ChairBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.OAK_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryEntry<ChairBlock> CHAIR_SPRUCE = RegistryEntry.blockWithItem(Utils.resource("spruce_chair"), () -> new ChairBlock(WoodType.SPRUCE, BlockBehaviour.Properties.of().mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryEntry<ChairBlock> CHAIR_BIRCH = RegistryEntry.blockWithItem(Utils.resource("birch_chair"), () -> new ChairBlock(WoodType.BIRCH, BlockBehaviour.Properties.of().mapColor(Blocks.BIRCH_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryEntry<ChairBlock> CHAIR_JUNGLE = RegistryEntry.blockWithItem(Utils.resource("jungle_chair"), () -> new ChairBlock(WoodType.JUNGLE, BlockBehaviour.Properties.of().mapColor(Blocks.JUNGLE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryEntry<ChairBlock> CHAIR_ACACIA = RegistryEntry.blockWithItem(Utils.resource("acacia_chair"), () -> new ChairBlock(WoodType.ACACIA, BlockBehaviour.Properties.of().mapColor(Blocks.ACACIA_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryEntry<ChairBlock> CHAIR_DARK_OAK = RegistryEntry.blockWithItem(Utils.resource("dark_oak_chair"), () -> new ChairBlock(WoodType.DARK_OAK, BlockBehaviour.Properties.of().mapColor(Blocks.DARK_OAK_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryEntry<ChairBlock> CHAIR_MANGROVE = RegistryEntry.blockWithItem(Utils.resource("mangrove_chair"), () -> new ChairBlock(WoodType.MANGROVE, BlockBehaviour.Properties.of().mapColor(Blocks.MANGROVE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryEntry<ChairBlock> CHAIR_CHERRY = RegistryEntry.blockWithItem(Utils.resource("cherry_chair"), () -> new ChairBlock(WoodType.CHERRY, BlockBehaviour.Properties.of().mapColor(Blocks.CHERRY_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryEntry<ChairBlock> CHAIR_CRIMSON = RegistryEntry.blockWithItem(Utils.resource("crimson_chair"), () -> new ChairBlock(WoodType.CRIMSON, BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryEntry<ChairBlock> CHAIR_WARPED = RegistryEntry.blockWithItem(Utils.resource("warped_chair"), () -> new ChairBlock(WoodType.WARPED, BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
}
