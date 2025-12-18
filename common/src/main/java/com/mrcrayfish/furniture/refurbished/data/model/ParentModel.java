package com.mrcrayfish.furniture.refurbished.data.model;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public abstract class ParentModel<T extends ParentModel<T>>
{
    protected final String name;
    protected final Identifier model;
    protected final TextureSlot[] slots;
    protected TextureMapping textures = new TextureMapping();
    protected boolean isChild = false;

    public ParentModel(String name, Identifier model, TextureSlot[] slots)
    {
        this.name = name;
        this.model = model;
        this.slots = slots;
    }

    public abstract T self();

    public String getName()
    {
        return this.name;
    }

    public Identifier getModel()
    {
        return this.model;
    }

    public TextureSlot[] getSlots()
    {
        return this.slots;
    }

    public T setTexture(TextureSlot slot, Identifier texture)
    {
        this.textures.put(slot, texture);
        return this.self();
    }

    public T setTextures(TextureMapping mapping)
    {
        this.textures = mapping;
        return this.self();
    }

    public TextureMapping getTextures()
    {
        return this.textures;
    }

    public T markAsChild()
    {
        this.isChild = true;
        return this.self();
    }

    public boolean isChild()
    {
        return this.isChild;
    }

    public ModelTemplate asTemplate()
    {
        return new ModelTemplate(Optional.of(this.model), Optional.empty(), this.slots);
    }
}
