---
sidebar_position: 3
sidebar_label: Freezer (Solidifying)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Freezer (Solidifying)

Solidifying/Freezing is a feature of the Fridge in MrCrayfish's Furniture Mod: Refurbished. It can be used by the player to freeze an item, like converting water into ice.

## Recipe Manager
`<recipetype:refurbished_furniture:freezer_solidifying>`

## Custom Functions

### `addRecipe(name, ingredient, result[, time])`

Adds a new solidifying recipe to the freezer

| Paramater  |                                          Type                                           | Required |                              Description                              |
| :--------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    name    |                                         string                                          |   Yes    |                The name of the recipe, must be unique.                |
| ingredient | [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                       The ingredient to freeze                        |
|   result   |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    | The resulting item from slicing the `ingredient`, can have an amount. |
|    time    |                                           int                                           |    No    |        The duration in ticks to freeze the item. Default `200`        |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    <recipetype:refurbished_furniture:freezer_solidifying>.addRecipe(
        "freezing/easy_diamonds",
        <item:minecraft:apple> | <item:minecraft:potato>, 
        <item:minecraft:diamond> * 64,
        500
    );
    ```
  </TabItem>
  <TabItem value="json" label="Datapack Equivelant">
    ```json title="(ZIP File) 🡢 /data/[namespace]/recipes/freezing/easy_diamonds.json"
    {
        "type": "refurbished_furniture:freezer_solidifying",
        "ingredient": {
            "item": [
                "minecraft:apple",
                "minecraft:potato"
            ]
        },
        "result": {
            "item": "minecraft:diamond",
            "count": 64
        },
        "time": 500
    } 
    ```
  </TabItem>
</Tabs>

---

## Learn More

See **Recipe Managers** on the CraftTweaker [documentation](https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers) for all inbuilt functions.