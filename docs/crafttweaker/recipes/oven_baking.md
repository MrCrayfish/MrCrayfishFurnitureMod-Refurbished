---
sidebar_position: 7
sidebar_label: Oven (Baking)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Oven (Baking)

The Oven (a part of the Stove block) allows you to bake items. It has the ability to bake up to three different items simultaneously. They are also baked independantly, so it doesn't matter it another item is already half way baked when you add it into the Oven.

## Recipe Manager
`<recipetype:refurbished_furniture:oven_baking>`

## Custom Functions

### `addRecipe(name, ingredient, result[, time])`

Adds a new baking recipe to the oven

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
    <recipetype:refurbished_furniture:oven_baking>.addRecipe(
        "heating/easy_diamonds",
        <item:minecraft:apple>, 
        <item:minecraft:diamond> * 2,
        100
    );
    ```
  </TabItem>
  <TabItem value="json" label="Datapack Equivelant">
    ```json title="(ZIP File) 🡢 /data/[namespace]/recipes/baking/easy_diamonds.json"
    {
        "type": "refurbished_furniture:oven_baking",
        "ingredient": {
            "item": "minecraft:apple"
        },
        "result": {
            "item": "minecraft:diamond",
            "count": 2
        },
        "time": 100
    } 
    ```
  </TabItem>
</Tabs>

---

## Learn More

See **Recipe Managers** on the CraftTweaker [documentation](https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers) for all inbuilt functions.