package com.mrcrayfish.furniture.refurbished.compat.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler;
import com.mrcrayfish.furniture.refurbished.Constants;
import org.apache.logging.log4j.Logger;

/**
 * Author: MrCrayfish
 */
@CraftTweakerPlugin(Constants.MOD_ID + ":plugin")
public class Plugin implements ICraftTweakerPlugin
{
    @Override
    public void registerRecipeComponents(IRecipeComponentRegistrationHandler handler)
    {
        handler.registerRecipeComponent(CustomRecipeComponents.Metadata.PROCESSING_CATEGORY);
    }
}
