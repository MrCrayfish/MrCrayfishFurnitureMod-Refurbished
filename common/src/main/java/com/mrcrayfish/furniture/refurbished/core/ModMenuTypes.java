package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.inventory.MenuType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModMenuTypes
{
    public static final RegistryEntry<MenuType<FreezerMenu>> FREEZER = RegistryEntry.menuType(Utils.resource("freezer"), FreezerMenu::new);
}
