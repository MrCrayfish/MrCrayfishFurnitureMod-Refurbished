package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.api.datagen.FrameworkGenerator;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.model.ModelDefinitions;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("UnstableApiUsage")
public class CommonItemModelsGenerator extends FrameworkGenerator
{
    public CommonItemModelsGenerator(Map<Block, BlockModelDefinitionGenerator> generators, Map<Item, ClientItem> items, Map<ResourceLocation, ModelInstance> models)
    {
        super(generators, items, models);
    }

    @Override
    public void generate()
    {
        this.items.put(ModItems.FRIDGE_LIGHT.get(), this.createClientItem(ItemModelUtils.plainModel(ModelDefinitions.FRIDGE.create(ModItems.FRIDGE_LIGHT.get(), new TextureMapping().put(TextureSlot.TEXTURE, this.blockTexture(ModBlocks.FRIDGE_LIGHT.get())), this.models::put))));
        this.items.put(ModItems.FRIDGE_DARK.get(), this.createClientItem(ItemModelUtils.plainModel(ModelDefinitions.FRIDGE.create(ModItems.FRIDGE_DARK.get(), new TextureMapping().put(TextureSlot.TEXTURE, this.blockTexture(ModBlocks.FRIDGE_DARK.get())), this.models::put))));
        this.flatHandheldItemModel(ModItems.SPATULA.get());
        this.flatHandheldRodItemModel(ModItems.KNIFE.get());
        this.flatItemModel(ModItems.PACKAGE.get());
        this.flatHandheldItemModel(ModItems.WRENCH.get());
        this.flatItemModel(ModItems.BREAD_SLICE.get());
        this.flatItemModel(ModItems.TOAST.get());
        this.flatItemModel(ModItems.SWEET_BERRY_JAM.get());
        this.flatItemModel(ModItems.SWEET_BERRY_JAM_TOAST.get());
        this.flatItemModel(ModItems.GLOW_BERRY_JAM.get());
        this.flatItemModel(ModItems.GLOW_BERRY_JAM_TOAST.get());
        this.flatItemModel(ModItems.SEA_SALT.get());
        this.flatItemModel(ModItems.WHEAT_FLOUR.get());
        this.flatItemModel(ModItems.DOUGH.get());
        this.flatItemModel(ModItems.CHEESE.get());
        this.flatItemModel(ModItems.CHEESE_SANDWICH.get());
        this.flatItemModel(ModItems.CHEESE_TOASTIE.get());
        this.flatItemModel(ModItems.RAW_VEGETABLE_PIZZA.get());
        this.flatItemModel(ModItems.COOKED_VEGETABLE_PIZZA.get());
        this.flatItemModel(ModItems.VEGETABLE_PIZZA_SLICE.get());
        this.flatItemModel(ModItems.RAW_MEATLOVERS_PIZZA.get());
        this.flatItemModel(ModItems.COOKED_MEATLOVERS_PIZZA.get());
        this.flatItemModel(ModItems.MEATLOVERS_PIZZA_SLICE.get());
        this.flatHandheldItemModel(ModItems.TELEVISION_REMOTE.get());
    }

    private ResourceLocation blockTexture(Block block)
    {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "block/" + name.getPath());
    }

    private void flatHandheldItemModel(Item item)
    {
        this.items.put(item, this.createClientItem(ItemModelUtils.plainModel(ModelTemplates.FLAT_HANDHELD_ITEM.create(item, TextureMapping.layer0(item), this.models::put))));
    }

    private void flatHandheldRodItemModel(Item item)
    {
        this.items.put(item, this.createClientItem(ItemModelUtils.plainModel(ModelTemplates.FLAT_HANDHELD_ROD_ITEM.create(item, TextureMapping.layer0(item), this.models::put))));
    }
}
