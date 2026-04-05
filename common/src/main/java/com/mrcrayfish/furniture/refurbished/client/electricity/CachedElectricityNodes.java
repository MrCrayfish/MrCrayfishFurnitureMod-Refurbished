package com.mrcrayfish.furniture.refurbished.client.electricity;

import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;

import java.util.Set;

public interface CachedElectricityNodes
{
    Set<IElectricityNode> refurbished_furniture$ElectricityNodes();

    void refurbished_furniture$RemoveInvalidElectricityNodes();
}
