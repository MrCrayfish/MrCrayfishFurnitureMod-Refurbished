package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.ElectricityGeneratorMenu;
import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.MicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.inventory.PostBoxMenu;
import com.mrcrayfish.furniture.refurbished.inventory.RecycleBinMenu;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.inventory.MenuType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModMenuTypes
{
    public static final RegistryEntry<MenuType<FreezerMenu>> FREEZER = RegistryEntry.menuType(Utils.resource("freezer"), FreezerMenu::new);
    public static final RegistryEntry<MenuType<MicrowaveMenu>> MICROWAVE = RegistryEntry.menuType(Utils.resource("microwave"), MicrowaveMenu::new);
    public static final RegistryEntry<MenuType<StoveMenu>> STOVE = RegistryEntry.menuType(Utils.resource("stove"), StoveMenu::new);
    public static final RegistryEntry<MenuType<PostBoxMenu>> POST_BOX = RegistryEntry.menuType(Utils.resource("post_box"), PostBoxMenu::new);
    public static final RegistryEntry<MenuType<ElectricityGeneratorMenu>> ELECTRICITY_GENERATOR = RegistryEntry.menuType(Utils.resource("electricity_generator"), ElectricityGeneratorMenu::new);
    public static final RegistryEntry<MenuType<RecycleBinMenu>> RECYCLE_BIN = RegistryEntry.menuType(Utils.resource("recycle_bin"), RecycleBinMenu::new);
    public static final RegistryEntry<MenuType<ComputerMenu>> COMPUTER = RegistryEntry.menuType(Utils.resource("computer"), ComputerMenu::new);
}
