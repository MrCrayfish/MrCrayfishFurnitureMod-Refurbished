---
sidebar_position: 6
sidebar_label: Microwave (Heating)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Microwave (Heating)

TODO

## Recipe Manager
`<recipetype:refurbished_furniture:microwave_heating>`

## Functions

### `addRecipe(name, input, output[, experience[, cookingTime]])`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    name     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|    input    | [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                             The ingredient to freeze                              |
|   output    |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |
| experience  |                                          float                                          |    No    | The amount of experience for successfully frying with this recipe. Default `0`. |
| cookingTime |                                           int                                           |    No    |              The duration in ticks to cook the item. Default `200`              |
