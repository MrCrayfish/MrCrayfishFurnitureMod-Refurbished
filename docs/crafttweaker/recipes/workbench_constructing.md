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

## Custom Functions

### `addRecipe(name, result, materials[], notification]`

Adds a new constructing recipe to the workbench

|  Paramater   |                                           Type                                            | Required |                              Description                              |
| :----------: | :---------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|     name     |                                          string                                           |   Yes    |                The name of the recipe, must be unique.                |
|    result    |      [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)      |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |
|  materials   | [IIngredient[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                       The ingredient to freeze                        |
| notification |                                          boolean                                          |    No    |  Show notifcation when player unlocks the recipe. Currently unused.   |

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