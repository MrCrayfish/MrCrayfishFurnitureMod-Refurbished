---
sidebar_position: 11
sidebar_label: Workbench (Constructing)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Workbench (Constructing)

The Workbench is a new crafting system introduced in MrCrayfish's Furniture Mod: Refurbished. It is the new method of constructing all furniture in the mod. Unlike the crafting table, the workbench doesn't work of a crafting grid, it instead simple requires materials be present in the player inventory. Materials can be any number of items with support for an amount. Recipes can also be the exact same without conflicting. The Workbench requires electrical power in order for it to function.

## Recipe Manager
`<recipetype:refurbished_furniture:workbench_constructing>`

## Custom Functions

### `addRecipe(name, result, materials[][, notification])`

Adds a new constructing recipe to the workbench

|  Paramater   |                                      Type                                       | Required |                              Description                               |
| :----------: | :-----------------------------------------------------------------------------: | :------: | :--------------------------------------------------------------------: |
|     name     |                                     string                                      |   Yes    |                The name of the recipe, must be unique.                 |
|    result    | [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    |  The resulting item from constructing the recipe, can have an amount.  |
|  materials   |                               StackedIngredient[]                               |   Yes    | The ingredients required to construct the `result`, can have a amount. |
| notification |                                     boolean                                     |    No    | Show notifcation when player unlocks the recipe. **CURRENTLY UNUSED.** |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    <recipetype:refurbished_furniture:workbench_constructing>.addRecipe(
        "heating/easy_diamonds",
        <item:minecraft:diamond> * 64, 
        [<item:minecraft:apple> * 3, <item:minecraft:stick> * 4],
        false
    );
    ```
  </TabItem>
  <TabItem value="json" label="Datapack Equivelant">
    ```json title="(ZIP File) 🡢 /data/[namespace]/recipes/constructing/easy_diamonds.json"
    {
        "type": "refurbished_furniture:workbench_constructing",
        "materials": [
            {
                "ingredient": {
                    "item": "minecraft:apple"
                },
                "count": 3
            },
            {
                "ingredient": {
                    "item": "minecraft:stick"
                },
                "count": 4
            }
        ],
        "result": {
            "item": "minecraft:diamond",
            "count": 64
        }
    }
    ```
  </TabItem>
</Tabs>

---

## Learn More

See **Recipe Managers** on the CraftTweaker [documentation](https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers) for all inbuilt functions.