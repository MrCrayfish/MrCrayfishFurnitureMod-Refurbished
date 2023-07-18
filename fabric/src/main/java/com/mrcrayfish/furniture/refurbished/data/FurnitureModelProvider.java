package com.mrcrayfish.furniture.refurbished.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedBlockState;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedStateModel;
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
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
        new CommonModelProvider(builder -> {
            Block block = builder.getBlock();

            // We do our own handling of item models, so skip auto generation
            generators.skipAutoItemBlock(block);

            // Generates the blockstate and block models
            MultiVariantGenerator generator = MultiVariantGenerator.multiVariant(block);
            DynamicPropertyDispatch dispatch = DynamicPropertyDispatch.of(block);
            builder.getVariants().forEach(entry -> {
                // Generates the blockstate
                PreparedStateModel preparedModel = Objects.requireNonNull(entry.getPreparedModel());
                ResourceLocation model = new ResourceLocation(this.output.getModId(), "block/" + preparedModel.getName());
                Variant variant = Variant.variant().with(VariantProperties.MODEL, model);
                if(preparedModel.getXRotation() != VariantProperties.Rotation.R0) {
                    variant.with(VariantProperties.X_ROT, preparedModel.getXRotation());
                }
                if(preparedModel.getYRotation() != VariantProperties.Rotation.R0) {
                    variant.with(VariantProperties.Y_ROT, preparedModel.getYRotation());
                }
                dispatch.register(entry.getValueMap(), variant);

                // Creates and registers the block model into the model output
                preparedModel.asTemplate().create(model, preparedModel.getTextures(), generators.modelOutput);
            });
            generator.with(dispatch);
            generators.blockStateOutput.accept(generator);

            // Generates an item model if the block has an item and the state builder marked a variant for the item model
            Optional.ofNullable(Item.BY_BLOCK.get(block)).ifPresent(item -> {
                Optional.ofNullable(builder.getVariantForItem()).map(PreparedBlockState.Entry::getPreparedModel).ifPresent(definition -> {
                    ResourceLocation itemName = ModelLocationUtils.getModelLocation(item);
                    ResourceLocation model = new ResourceLocation(this.output.getModId(), "block/" + builder.getVariantForItem().getPreparedModel().getName());
                    generators.modelOutput.accept(itemName, new DelegatedModel(model));
                });
            });
        }).run();
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators)
    {
        // Unused
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
