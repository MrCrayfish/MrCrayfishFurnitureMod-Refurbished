---
sidebar_position: 11
sidebar_label: Workbench (Constructing)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Workbench (Constructing)

TODO

## Recipe Manager
`<recipetype:refurbished_furniture:workbench_constructing>`

## Functions

### `addRecipe(name, result, materials[], notification]`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    name     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|   result    |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |
|    materials    | [IIngredient[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                             The ingredient to freeze                              |
| notification  |                                          boolean                                          |    No    | The amount of experience for successfully frying with this recipe. Default `0`. |
