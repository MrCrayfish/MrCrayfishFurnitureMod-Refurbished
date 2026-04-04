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
    public static final RegistryEntry<MenuType<WorkbenchMenu>> WORKBENCH = RegistryEntry.menuTypeWithData(Utils.id("workbench"), WorkbenchMenu.CustomData.STREAM_CODEC, WorkbenchMenu::new);
    public static final RegistryEntry<MenuType<AbstractContainerMenu>> FREEZER = RegistryEntry.menuType(Utils.id("freezer"), Services.MENU::createFreezerMenu);
    public static final RegistryEntry<MenuType<AbstractContainerMenu>> MICROWAVE = RegistryEntry.menuType(Utils.id("microwave"), Services.MENU::createMicrowaveMenu);
    public static final RegistryEntry<MenuType<AbstractContainerMenu>> STOVE = RegistryEntry.menuType(Utils.id("stove"), Services.MENU::createStoveMenu);
    public static final RegistryEntry<MenuType<PostBoxMenu>> POST_BOX = RegistryEntry.menuTypeWithData(Utils.id("post_box"), PostBoxMenu.CustomData.STREAM_CODEC, PostBoxMenu::new);
    public static final RegistryEntry<MenuType<ElectricityGeneratorMenu>> ELECTRICITY_GENERATOR = RegistryEntry.menuType(Utils.id("electricity_generator"), ElectricityGeneratorMenu::new);
    public static final RegistryEntry<MenuType<RecycleBinMenu>> RECYCLE_BIN = RegistryEntry.menuType(Utils.id("recycle_bin"), RecycleBinMenu::new);
    public static final RegistryEntry<MenuType<ComputerMenu>> COMPUTER = RegistryEntry.menuTypeWithData(Utils.id("computer"), ComputerMenu.CustomData.STREAM_CODEC, ComputerMenu::new);
    public static final RegistryEntry<MenuType<DoorMatMenu>> DOOR_MAT = RegistryEntry.menuType(Utils.id("door_mat"), DoorMatMenu::new);
}
