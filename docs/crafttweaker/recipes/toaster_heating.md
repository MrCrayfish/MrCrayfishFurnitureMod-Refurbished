---
sidebar_position: 10
sidebar_label: Toaster (Heating)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Toaster (Heating)

TODO

## Recipe Manager
`<recipetype:refurbished_furniture:toaster_heating>`

## Custom Functions

### `addRecipe(name, ingredient, result[, time])`

Adds a new heating recipe to the toaster

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
    <recipetype:refurbished_furniture:toaster_heating>.addRecipe(
        "heating/easy_diamonds",
        <item:minecraft:apple>, 
        <item:minecraft:diamond> * 64,
        350
    );
    ```
  </TabItem>
  <TabItem value="json" label="Datapack Equivelant">
    ```json title="(ZIP File) 🡢 /data/[namespace]/recipes/heating/easy_diamonds.json"
    {
        "type": "refurbished_furniture:toaster_heating",
        "ingredient": {
            "item": "minecraft:apple"
        },
        "result": {
            "item": "minecraft:diamond",
            "count": 64
        },
        "time": 350
    } 
    ```
  </TabItem>
</Tabs>

---

## Learn More

See **Recipe Managers** on the CraftTweaker [documentation](https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers) for all inbuilt functions.