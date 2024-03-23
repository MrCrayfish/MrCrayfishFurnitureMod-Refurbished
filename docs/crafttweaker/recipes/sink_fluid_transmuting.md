---
sidebar_position: 9
sidebar_label: Sink (Fluid Transmuting)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Sink (Fluid Transmuting)

TODO

## Recipe Manager
`<recipetype:refurbished_furniture:sink_fluid_transmuting>`

## Functions

### `addRecipe(name, fluid, catalyst, output)`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    name     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|    fluid    | [IFluidStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/fluid/IFluidStack) |   Yes    |                             The ingredient to freeze                              |
|   catalyst    |     [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |
|   output    |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |
