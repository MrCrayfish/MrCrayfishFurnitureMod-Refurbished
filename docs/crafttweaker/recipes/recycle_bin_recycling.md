---
sidebar_position: 8
sidebar_label: Recycle Bin (Recycling)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Recycle Bin (Recycling)

TODO

## Recipe Manager
`<recipetype:refurbished_furniture:recycle_bin_recycling>`

## Functions

### `addRecipe(name, recyclable, scraps[])`

Adds a new recycling recipe to the recycle bin

| Paramater  |                                          Type                                           | Required |                                                   Description                                                    |
| :--------: | :-------------------------------------------------------------------------------------: | :------: | :--------------------------------------------------------------------------------------------------------------: |
|    name    |                                         string                                          |   Yes    |                                     The name of the recipe, must be unique.                                      |
| recyclable | [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                                       The item that is gong to be recycled                                       |
|   scraps   |    [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)    |   Yes    | The scraps (items) from breaking down the `recyclable`. Must have at least `1` item, and no more than `9` items. |

---

### `addScrap(id, scrap)`

Adds an additional item to the scraps of the recipe with the given id. If the recipe already has `9` scraps, this call will be ignored.

| Paramater |                                      Type                                       | Required |                              Description                              |
| :-------: | :-----------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                     string                                      |   Yes    |                The name of the recipe, must be unique.                |
|   scrap   | [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

---

### `addScrap(id, scraps[])`

Adds additional items to the scraps of the recipe with the given id. If the recipe already has `9` scraps or the new total is greater than `9`, this call will be ignored.

| Paramater |                                       Type                                        | Required |                              Description                              |
| :-------: | :-------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                      string                                       |   Yes    |                The name of the recipe, must be unique.                |
|  scraps   | [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

---

### `removeOutput(id, scrap)`

Remove the scrap (item) from the recipe with the given id. If the item does not exist in the scraps, this call will be ignored.

| Paramater |                                      Type                                       | Required |                              Description                              |
| :-------: | :-----------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                     string                                      |   Yes    |                The name of the recipe, must be unique.                |
|   scrap   | [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

---

### `removeOutput(id, scraps[])`

Removes the scraps (items) from the recipe with the given id. This will remove any items that match from the given scraps. Items that don't exist in the scraps of the matching recipe, will simply be ignored. 

| Paramater |                                       Type                                        | Required |                              Description                              |
| :-------: | :-------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                      string                                       |   Yes    |                The name of the recipe, must be unique.                |
|  scraps   | [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

---

### `replaceOutput(id, from, to)`

Replaces an item in the scraps of the matching recipe with a different item. If the item to replace does not exist, this call will simply be ignored.

| Paramater |                                       Type                                        | Required |                              Description                              |
| :-------: | :-------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                      string                                       |   Yes    |                The name of the recipe, must be unique.                |
|   from    | [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |
|    to     | [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

---

## Learn More

See **Recipe Managers** on the CraftTweaker [documentation](https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers) for all inbuilt functions.