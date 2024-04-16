---
sidebar_position: 9
sidebar_label: Sink (Fluid Transmuting)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Sink (Fluid Transmuting)

Fluid Transmuting is a new system introduced in MrCrayfish's Furniture Mod: Refurbished. It allows the player to mix a catalyst item into a fluid to produce a new item. Fluid transmuting is performed using a Kitchen Sink. It is currently used in the mod to turn milk, by mixing sea salt, to turn it into cheese.

## Recipe Manager
`<recipetype:refurbished_furniture:sink_fluid_transmuting>`

## Custom Functions

### `addRecipe(name, fluid, catalyst, result)`

Adds a new fluid transmuting recipe to the sink

| Paramater |                                          Type                                           | Required |                                    Description                                    |
| :-------: | :-------------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------------------: |
|   name    |                                         string                                          |   Yes    |                      The name of the recipe, must be unique.                      |
|   fluid   |   [IFluidStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/fluid/IFluidStack)    |   Yes    |                 A fluid. The recipe will consume one bucket full.                 |
| catalyst  | [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |          The item that triggers the reaction. Can only be a single item.          |
|  result   |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    | The resulting item from mixing the `catalyst` with the fluid, can have an amount. |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    // Freezes water with ice to create more ice
    <recipetype:refurbished_furniture:sink_fluid_transmuting>.addRecipe(
        "fluid_transmuting/more_ice",
        <fluid:minecraft:water>, 
        <item:minecraft:ice>,
        <item:minecraft:ice> * 2
    );
    ```
  </TabItem>
  <TabItem value="json" label="Datapack Equivelant">
    ```json title="(ZIP File) 🡢 /data/[namespace]/recipes/recycling/jukebox_scrapping.json"
    {
        "type": "refurbished_furniture:sink_fluid_transmuting",
        "fluid": "minecraft:water",
        "catalyst": {
            "item": "minecraft:ice"
        },
        "result": {
            "item": "minecraft:ice",
            "count": 2
        }
    } 
    ```
  </TabItem>
</Tabs>


---

## Learn More

See **Recipe Managers** on the CraftTweaker [documentation](https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers) for all inbuilt functions.