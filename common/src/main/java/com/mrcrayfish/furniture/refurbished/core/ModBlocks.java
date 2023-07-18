package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.block.ChairBlock;
import com.mrcrayfish.furniture.refurbished.block.TableBlock;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModBlocks
{
    public static final RegistryEntry<TableBlock> TABLE_OAK = RegistryEntry.blockWithItem(Utils.resource("oak_table"), () -> new TableBlock(WoodType.OAK, Block.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryEntry<TableBlock> TABLE_SPRUCE = RegistryEntry.blockWithItem(Utils.resource("spruce_table"), () -> new TableBlock(WoodType.SPRUCE, Block.Properties.copy(Blocks.SPRUCE_PLANKS)));
    public static final RegistryEntry<TableBlock> TABLE_BIRCH = RegistryEntry.blockWithItem(Utils.resource("birch_table"), () -> new TableBlock(WoodType.BIRCH, Block.Properties.copy(Blocks.BIRCH_PLANKS)));
    public static final RegistryEntry<TableBlock> TABLE_JUNGLE = RegistryEntry.blockWithItem(Utils.resource("jungle_table"), () -> new TableBlock(WoodType.JUNGLE, Block.Properties.copy(Blocks.JUNGLE_PLANKS)));
    public static final RegistryEntry<TableBlock> TABLE_ACACIA = RegistryEntry.blockWithItem(Utils.resource("acacia_table"), () -> new TableBlock(WoodType.ACACIA, Block.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistryEntry<TableBlock> TABLE_DARK_OAK = RegistryEntry.blockWithItem(Utils.resource("dark_oak_table"), () -> new TableBlock(WoodType.DARK_OAK, Block.Properties.copy(Blocks.DARK_OAK_PLANKS)));
    public static final RegistryEntry<TableBlock> TABLE_CRIMSON = RegistryEntry.blockWithItem(Utils.resource("crimson_table"), () -> new TableBlock(WoodType.CRIMSON, Block.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistryEntry<TableBlock> TABLE_WARPED = RegistryEntry.blockWithItem(Utils.resource("warped_table"), () -> new TableBlock(WoodType.WARPED, Block.Properties.copy(Blocks.WARPED_PLANKS)));
    public static final RegistryEntry<TableBlock> TABLE_MANGROVE = RegistryEntry.blockWithItem(Utils.resource("mangrove_table"), () -> new TableBlock(WoodType.MANGROVE, Block.Properties.copy(Blocks.MANGROVE_PLANKS)));
    public static final RegistryEntry<TableBlock> TABLE_CHERRY = RegistryEntry.blockWithItem(Utils.resource("cherry_table"), () -> new TableBlock(WoodType.CHERRY, Block.Properties.copy(Blocks.MANGROVE_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_OAK = RegistryEntry.blockWithItem(Utils.resource("oak_chair"), () -> new ChairBlock(WoodType.OAK, Block.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_SPRUCE = RegistryEntry.blockWithItem(Utils.resource("spruce_chair"), () -> new ChairBlock(WoodType.SPRUCE, Block.Properties.copy(Blocks.SPRUCE_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_BIRCH = RegistryEntry.blockWithItem(Utils.resource("birch_chair"), () -> new ChairBlock(WoodType.BIRCH, Block.Properties.copy(Blocks.BIRCH_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_JUNGLE = RegistryEntry.blockWithItem(Utils.resource("jungle_chair"), () -> new ChairBlock(WoodType.JUNGLE, Block.Properties.copy(Blocks.JUNGLE_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_ACACIA = RegistryEntry.blockWithItem(Utils.resource("acacia_chair"), () -> new ChairBlock(WoodType.ACACIA, Block.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_DARK_OAK = RegistryEntry.blockWithItem(Utils.resource("dark_oak_chair"), () -> new ChairBlock(WoodType.DARK_OAK, Block.Properties.copy(Blocks.DARK_OAK_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_CRIMSON = RegistryEntry.blockWithItem(Utils.resource("crimson_chair"), () -> new ChairBlock(WoodType.CRIMSON, Block.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_WARPED = RegistryEntry.blockWithItem(Utils.resource("warped_chair"), () -> new ChairBlock(WoodType.WARPED, Block.Properties.copy(Blocks.WARPED_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_MANGROVE = RegistryEntry.blockWithItem(Utils.resource("mangrove_chair"), () -> new ChairBlock(WoodType.MANGROVE, Block.Properties.copy(Blocks.MANGROVE_PLANKS)));
    public static final RegistryEntry<ChairBlock> CHAIR_CHERRY = RegistryEntry.blockWithItem(Utils.resource("cherry_chair"), () -> new ChairBlock(WoodType.CHERRY, Block.Properties.copy(Blocks.MANGROVE_PLANKS)));
}
