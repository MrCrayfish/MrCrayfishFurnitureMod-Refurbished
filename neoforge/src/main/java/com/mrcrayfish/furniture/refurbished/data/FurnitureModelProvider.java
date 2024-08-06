package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import com.mrcrayfish.furniture.refurbished.data.model.ModelTemplate;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedItem;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedVariantBlockState;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class FurnitureModelProvider extends BlockStateProvider
{
    public static final ExistingFileHelper.ResourceType TEXTURE = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures");
    public static final ExistingFileHelper.ResourceType MODEL = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".json", "models");

    private final ExtraModelProvider extraModelProvider;

    public FurnitureModelProvider(PackOutput output, ExistingFileHelper helper)
    {
        super(output, Constants.MOD_ID, helper);
        this.registerExistingResources(helper);
        this.extraModelProvider = new ExtraModelProvider(output, Constants.MOD_ID, helper);
    }

    private void registerExistingResources(ExistingFileHelper helper)
    {
        // Registers existing parent models since they aren't generated
        ModelTemplate.all().forEach(model -> helper.trackGenerated(model, MODEL));

        // Registers a default texture for all blocks in the mod
        Registration.get(Registries.BLOCK).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
            helper.trackGenerated(this.blockTexture((Block) entry.get()), TEXTURE);
        });

        // Registers wood particle textures
        WoodType.values().forEach(type -> {
            helper.trackGenerated(Utils.resource("block/" + type.name() + "_particle"), TEXTURE);
        });

        // Registers coloured particle textures
        Arrays.stream(DyeColor.values()).forEach(type -> {
            helper.trackGenerated(Utils.resource("block/" + type.getName() + "_particle"), TEXTURE);
        });

        // Registers metal particle textures
        Arrays.stream(MetalType.values()).forEach(type -> {
            helper.trackGenerated(Utils.resource("block/" + type.getName() + "_particle"), TEXTURE);
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void registerStatesAndModels()
    {
        new CommonBlockModelProvider(builder -> {
            // Variant block states
            Block block = builder.getBlock();
            VariantBlockStateBuilder blockStateBuilder = this.getVariantBuilder(block);
            builder.getVariants().forEach(entry -> {
                VariantBlockStateBuilder.PartialBlockstate state = blockStateBuilder.partialState();
                for(Map.Entry<Property, Comparable> property : entry.getValueMap().entrySet()) {
                    state = state.with(property.getKey(), property.getValue());
                }
                ConfiguredModel.Builder<?> configuredBuilder = ConfiguredModel.builder();
                PreparedVariantBlockState.Model[] models = entry.getModels();
                for(int i = 0; i < models.length; i++) {
                    PreparedVariantBlockState.Model model = models[i];
                    configuredBuilder
                            .modelFile(this.createModelFileFromVariant(model))
                            .rotationX(model.getXRotation().ordinal() * 90)
                            .rotationY(model.getYRotation().ordinal() * 90);
                    if(i < models.length - 1) {
                        configuredBuilder = configuredBuilder.nextModel();
                    }
                }
                state.setModels(configuredBuilder.build());
            });

            // Generates an item model if the block has an item and the state builder marked a variant for the item model
            Optional.ofNullable(Item.BY_BLOCK.get(block)).ifPresent(item -> {
                Optional.ofNullable(builder.getVariantForItem()).map(PreparedVariantBlockState.Entry::getModels).ifPresent(models -> {
                    if(models.length > 0) {
                        PreparedVariantBlockState.Model model = models[0];
                        ResourceLocation itemName = BuiltInRegistries.ITEM.getKey(item);
                        ResourceLocation modelLocation = Utils.resource("block/" + model.getName());
                        this.itemModels().getBuilder(itemName.toString()).parent(new ModelFile.UncheckedModelFile(modelLocation));
                    }
                });
            });
        }, builder -> {
            // Multipart block states
            Block block = builder.getBlock();
            MultiPartBlockStateBuilder blockStateBuilder = this.getMultipartBuilder(block);
            builder.getParts().forEach(entry -> {
                ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> configuredBuilder = blockStateBuilder.part();

                // Define the model(s) for the part
                PreparedVariantBlockState.Model[] models = entry.getModels();
                for(int i = 0; i < models.length; i++) {
                    PreparedVariantBlockState.Model model = models[i];
                    configuredBuilder.modelFile(this.createModelFileFromVariant(model))
                            .rotationX(model.getXRotation().ordinal() * 90)
                            .rotationY(model.getYRotation().ordinal() * 90);
                    if(i < models.length - 1) {
                        configuredBuilder = configuredBuilder.nextModel();
                    }
                }

                // Create the condition when to apply the part.
                MultiPartBlockStateBuilder.PartBuilder partBuilder = configuredBuilder.addModel();
                partBuilder.useOr = entry.isOrMode();
                entry.getValueMap().forEach((property, comparable) -> {
                    partBuilder.condition(property, comparable);
                });
            });

            // Generates an item model if the block has an item
            Optional.ofNullable(Item.BY_BLOCK.get(block)).ifPresent(item -> {
                Optional.ofNullable(builder.getModelForItem()).ifPresent(model -> {
                    ResourceLocation itemName = BuiltInRegistries.ITEM.getKey(item);
                    ItemModelBuilder modelBuilder = this.itemModels().getBuilder(itemName.toString()).parent(new ModelFile.UncheckedModelFile(model.getModel()));
                    TextureMapping textures = model.getTextures();
                    Arrays.stream(model.getSlots()).forEach(slot -> modelBuilder.texture(slot.getId(), textures.get(slot)));
                });
            });

        }, model -> {
            // Extra models
            BlockModelBuilder modelBuilder = this.extraModelProvider.withExistingParent(model.getName(), model.getModel());
            Arrays.stream(model.getSlots()).forEach(slot -> modelBuilder.texture(slot.getId(), model.getTextures().get(slot)));
        }).run();

        new CommonItemModelProvider(prepared -> {
            Item item = prepared.getItem();
            ResourceLocation itemName = ModelLocationUtils.getModelLocation(item);
            PreparedItem.Model model = prepared.getModel();
            ItemModelBuilder builder = this.itemModels().getBuilder(itemName.toString());
            builder.parent(new ModelFile.UncheckedModelFile(model.getModel()));
            for(TextureSlot slot : model.getSlots())
            {
                builder.texture(slot.getId(), model.getTextures().get(slot));
            }
        }).run();
    }

    private ModelFile createModelFileFromVariant(PreparedVariantBlockState.Model model)
    {
        if(model.isChild())
        {
            BlockModelBuilder modelBuilder = this.models().withExistingParent(model.getName(), model.getModel());
            TextureMapping textures = model.getTextures();
            Arrays.stream(model.getSlots()).forEach(slot -> modelBuilder.texture(slot.getId(), textures.get(slot)));
            return modelBuilder;
        }
        return this.models().getExistingFile(model.getModel());
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        this.extraModelProvider.clear();
        return CompletableFuture.allOf(super.run(cache), this.extraModelProvider.generateAll(cache));
    }
}
