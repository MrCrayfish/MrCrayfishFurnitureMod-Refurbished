package com.mrcrayfish.furniture.refurbished.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedBlockState;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Selector;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class FurnitureModelProvider extends FabricModelProvider
{
    private final FabricDataOutput output;

    public FurnitureModelProvider(FabricDataOutput output)
    {
        super(output);
        this.output = output;
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators)
    {
        Set<ResourceLocation> createdModels = new HashSet<>();
        new CommonBlockModelProvider(builder -> {
            Block block = builder.getBlock();

            // We do our own handling of item models, so skip auto generation
            generators.skipAutoItemBlock(block);

            // Generates the blockstate and block models
            MultiVariantGenerator generator = MultiVariantGenerator.multiVariant(block);
            DynamicPropertyDispatch dispatch = DynamicPropertyDispatch.of(block);
            builder.getVariants().forEach(entry -> {
                // Generates the blockstate
                PreparedBlockState.Model model = Objects.requireNonNull(entry.getModel());
                ResourceLocation modelLocation = entry.hasParentModel() ? new ResourceLocation(this.output.getModId(), "block/" + model.getName()) : model.getModel();
                Variant variant = Variant.variant().with(VariantProperties.MODEL, modelLocation);
                if(model.getXRotation() != VariantProperties.Rotation.R0) {
                    variant.with(VariantProperties.X_ROT, model.getXRotation());
                }
                if(model.getYRotation() != VariantProperties.Rotation.R0) {
                    variant.with(VariantProperties.Y_ROT, model.getYRotation());
                }
                dispatch.register(entry.getValueMap(), variant);

                // Creates and registers the block model into the model output if a child model
                if(entry.hasParentModel() && !createdModels.contains(modelLocation))
                {
                    model.asTemplate().create(modelLocation, model.getTextures(), generators.modelOutput);
                    createdModels.add(modelLocation);
                }
            });
            generator.with(dispatch);
            generators.blockStateOutput.accept(generator);

            // Generates an item model if the block has an item and the state builder marked a variant for the item model
            Optional.ofNullable(Item.BY_BLOCK.get(block)).ifPresent(item -> {
                Optional.ofNullable(builder.getVariantForItem()).map(PreparedBlockState.Entry::getModel).ifPresent(model -> {
                    ResourceLocation itemName = ModelLocationUtils.getModelLocation(item);
                    ResourceLocation modelLocation = new ResourceLocation(this.output.getModId(), "block/" + model.getName());
                    generators.modelOutput.accept(itemName, new DelegatedModel(modelLocation));
                });
            });
        }).run();
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators)
    {
        new CommonItemModelProvider(builder -> {
            Item item = builder.getItem();
            ResourceLocation itemName = ModelLocationUtils.getModelLocation(item);
            generators.output.accept(itemName, builder.getModel());
        }).run();
    }

    /**
     * A custom property dispatch that can accept a dynamic amount of properties based on the block.
     */
    private static class DynamicPropertyDispatch extends PropertyDispatch
    {
        private final ImmutableList<Property<?>> properties;

        private DynamicPropertyDispatch(Block block)
        {
            this.properties = ImmutableList.copyOf(block.getStateDefinition().getProperties());
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        public DynamicPropertyDispatch register(Map<Property, Comparable> valueMap, Variant variant)
        {
            Preconditions.checkArgument(this.properties.containsAll(valueMap.keySet()), "Invalid value map. It must match all the properties from the block");
            List<Property.Value<?>> values = new ArrayList<>();
            valueMap.forEach((property, comparable) -> values.add(property.value(comparable)));
            Selector selector = Selector.of(values.toArray(Property.Value[]::new));
            this.putValue(selector, Collections.singletonList(variant));
            return this;
        }

        @Override
        public List<Property<?>> getDefinedProperties()
        {
            return this.properties;
        }

        private static DynamicPropertyDispatch of(Block block)
        {
            return new DynamicPropertyDispatch(block);
        }
    }
}
