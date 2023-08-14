package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.blockentity.MailboxBlockEntity;
import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.MailboxMenu;
import com.mrcrayfish.furniture.refurbished.inventory.MicrowaveMenu;
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
    public static final RegistryEntry<MenuType<MailboxMenu>> MAIL_BOX = RegistryEntry.menuType(Utils.resource("mail_box"), MailboxMenu::new);
}
