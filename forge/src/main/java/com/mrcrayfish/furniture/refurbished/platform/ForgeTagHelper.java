package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.compat.CompatibilityTags;
import com.mrcrayfish.furniture.refurbished.platform.services.ITagHelper;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Author: MrCrayfish
 */
public class ForgeTagHelper implements ITagHelper
{
    @Override
    public TagKey<Item> getToolKnivesTag()
    {
        return CompatibilityTags.Items.FORGE_TOOLS_KNIVES;
    }
}
