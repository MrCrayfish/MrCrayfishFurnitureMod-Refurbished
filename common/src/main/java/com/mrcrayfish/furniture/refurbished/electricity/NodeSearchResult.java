package com.mrcrayfish.furniture.refurbished.electricity;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public record NodeSearchResult(boolean overloaded, List<IElectricityNode> nodes)
{
}
