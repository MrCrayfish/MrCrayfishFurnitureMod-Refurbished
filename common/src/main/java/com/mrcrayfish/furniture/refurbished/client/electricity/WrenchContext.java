package com.mrcrayfish.furniture.refurbished.client.electricity;

import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import org.jetbrains.annotations.Nullable;

public record WrenchContext(@Nullable IElectricityNode selectedNode, @Nullable IElectricityNode targetingNode, @Nullable Connection hoveredConnection)
{
}
