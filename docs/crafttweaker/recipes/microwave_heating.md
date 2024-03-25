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

### `addRecipe(name, ingredient, result[, time])`

Adds a new heating recipe to the microwave

| Paramater  |                                          Type                                           | Required |                              Description                              |
| :--------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    name    |                                         string                                          |   Yes    |                The name of the recipe, must be unique.                |
| ingredient | [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                       The ingredient to freeze                        |
|   result   |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    | The resulting item from slicing the `ingredient`, can have an amount. |
|    time    |                                           int                                           |    No    |        The duration in ticks to freeze the item. Default `200`        |

---

## Learn More

See **Recipe Managers** on the CraftTweaker [documentation](https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers) for all inbuilt functions.