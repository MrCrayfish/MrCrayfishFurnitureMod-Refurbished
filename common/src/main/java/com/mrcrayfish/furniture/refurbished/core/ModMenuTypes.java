package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.inventory.*;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModMenuTypes
{
    public static final RegistryEntry<MenuType<WorkbenchMenu>> WORKBENCH = RegistryEntry.menuTypeWithData(Utils.resource("workbench"), WorkbenchMenu::new);
    public static final RegistryEntry<MenuType<AbstractContainerMenu>> FREEZER = RegistryEntry.menuType(Utils.resource("freezer"), Services.MENU::createFreezerMenu);
    public static final RegistryEntry<MenuType<AbstractContainerMenu>> MICROWAVE = RegistryEntry.menuType(Utils.resource("microwave"), Services.MENU::createMicrowaveMenu);
    public static final RegistryEntry<MenuType<AbstractContainerMenu>> STOVE = RegistryEntry.menuType(Utils.resource("stove"), Services.MENU::createStoveMenu);
    public static final RegistryEntry<MenuType<PostBoxMenu>> POST_BOX = RegistryEntry.menuType(Utils.resource("post_box"), PostBoxMenu::new);
    public static final RegistryEntry<MenuType<ElectricityGeneratorMenu>> ELECTRICITY_GENERATOR = RegistryEntry.menuType(Utils.resource("electricity_generator"), ElectricityGeneratorMenu::new);
    public static final RegistryEntry<MenuType<RecycleBinMenu>> RECYCLE_BIN = RegistryEntry.menuType(Utils.resource("recycle_bin"), RecycleBinMenu::new);
    public static final RegistryEntry<MenuType<ComputerMenu>> COMPUTER = RegistryEntry.menuTypeWithData(Utils.resource("computer"), ComputerMenu::new);
    public static final RegistryEntry<MenuType<DoorMatMenu>> DOOR_MAT = RegistryEntry.menuType(Utils.resource("door_mat"), DoorMatMenu::new);
}
