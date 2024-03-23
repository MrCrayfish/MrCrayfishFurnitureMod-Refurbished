---
sidebar_position: 7
sidebar_label: Oven (Baking)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Oven (Baking)

TODO

## Recipe Manager
`<recipetype:refurbished_furniture:oven_baking>`

## Functions

### `addRecipe(name, input, output[, processTime])`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    name     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|    input    | [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                             The ingredient to freeze                              |
|   output    |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |
| cookingTime |                                           int                                           |    No    |              The duration in ticks to cook the item. Default `200`              |
