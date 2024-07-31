package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import com.mrcrayfish.furniture.refurbished.mail.PackageInfo;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.component.DataComponentType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModDataComponents
{
    public static final RegistryEntry<DataComponentType<PaletteImage>> PALETTE_IMAGE = RegistryEntry.dataComponentType(Utils.resource("palette_image"), builder -> {
        return builder.persistent(PaletteImage.CODEC);
    });

    public static final RegistryEntry<DataComponentType<PackageInfo>> PACKAGE_INFO = RegistryEntry.dataComponentType(Utils.resource("package_info"), builder -> {
        return builder.persistent(PackageInfo.CODEC).networkSynchronized(PackageInfo.STREAM_CODEC);
    });
}
