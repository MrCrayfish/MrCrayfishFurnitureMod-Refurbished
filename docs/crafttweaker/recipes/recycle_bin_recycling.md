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

### `addRecipe(name, input, output[])`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    name     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|    input    | [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                             The ingredient to freeze                              |
|   output    |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |

---

### `addOutput(id, output)`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    id     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|   output    |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |

---

### `addOutput(id, output[])`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    id     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|   output    |     [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |

---

### `removeOutput(id, output)`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    id     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|   output    |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |

---

### `removeOutput(id, output[])`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    id     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|   output    |     [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |

---

### `replaceOutput(id, from, to)`

|  Paramater  |                                          Type                                           | Required |                                    Description                                    |
| :---------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|    id     |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|   from    |     [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |
|   to    |     [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |       The resulting item from cooking the `ingredient`, can have an amount.       |
