package com.mrcrayfish.furniture.refurbished.data.model;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class PreparedStateModel
{
    private final String name;
    private final ResourceLocation model;
    private final TextureSlot[] slots;
    private TextureMapping textures = new TextureMapping();

    private PreparedStateModel(String name, ResourceLocation model, TextureSlot[] slots)
    {
        this.name = name;
        this.model = model;
        this.slots = slots;
    }

    public String getName()
    {
        return this.name;
    }

    public ResourceLocation getModel()
    {
        return this.model;
    }

    public TextureMapping getTextures()
    {
        return this.textures;
    }

    public TextureSlot[] getSlots()
    {
        return this.slots;
    }

    public PreparedStateModel setTexture(TextureSlot slot, ResourceLocation texture)
    {
        this.textures.put(slot, texture);
        return this;
    }

    public PreparedStateModel setTextures(TextureMapping mapping)
    {
        this.textures = mapping;
        return this;
    }

    public ModelTemplate asTemplate()
    {
        return new ModelTemplate(Optional.of(this.model), Optional.empty(), this.slots);
    }

    public static PreparedStateModel create(String name, ResourceLocation model, TextureSlot[] slots)
    {
        return new PreparedStateModel(name, model, slots);
    }
}
