package com.mrcrayfish.furniture.refurbished.client.electricity;

import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;

import java.util.Set;

public interface CachedElectricityNodes
{
    Set<IElectricityNode> refurbishedFurniture$ElectricityNodes();

    void refurbishedFurniture$RemoveInvalidElectricityNodes();
}
