---
sidebar_position: 8
sidebar_label: Recycle Bin (Recycling)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Recycle Bin (Recycling)

The Recycling Bin is a pseudo uncrafter/item deleter. Uncrafting in the game using an items actual crafting recipes is overpowered. The Recycling Bin aims to provide a handcrafted experience that doesn't break the gameplay experience, while still providing useful items from breaking down an item. Due to it's handcrafted nature, a custom recipe has to be provided for the recycle bin in order to return items from breaking it down, it is not automatic. The base mod currently supports most vanilla blocks/items (that make sense) and blocks/items from the mod itself. By default, the recycling bin will consume all items regardless if their is a recycling recipe of not; this acts like a sort of item deleter for lack of better words.

## Recipe Manager
`<recipetype:refurbished_furniture:recycle_bin_recycling>`

## Custom Functions

### `addRecipe(name, recyclable, scraps[])`

Adds a new recycling recipe to the recycle bin

| Paramater  |                                          Type                                           | Required |                                                   Description                                                    |
| :--------: | :-------------------------------------------------------------------------------------: | :------: | :--------------------------------------------------------------------------------------------------------------: |
|    name    |                                         string                                          |   Yes    |                                     The name of the recipe, must be unique.                                      |
| recyclable | [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                                       The item that is gong to be recycled                                       |
|   scraps   |    [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)    |   Yes    | The scraps (items) from breaking down the `recyclable`. Must have at least `1` item, and no more than `9` items. |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    <recipetype:refurbished_furniture:recycle_bin_recycling>.addRecipe(
        "recycling/jukebox_scrapping",
        <item:minecraft:jukebox>, 
        [<item:minecraft:diamond>, <item:minecraft:oak_plank> * 2]
    );
    ```
  </TabItem>
  <TabItem value="json" label="Datapack Equivelant">
    ```json title="(ZIP File) 🡢 /data/[namespace]/recipes/recycling/jukebox_scrapping.json"
    {
        "type": "refurbished_furniture:recycle_bin_recycling",
        "recyclable": {
            "item": "minecraft:jukebox"
        },
        "scraps": [
            {
                "item": "minecraft:diamond"
            },
            {
                "item": "minecraft:oak_plank",
                "count": 2
            }
        ]
    } 
    ```
  </TabItem>
</Tabs>

---

### `addScrap(id, scrap)`

Adds an additional item to the scraps of the recipe with the given id. If the recipe already has `9` scraps, this call will be ignored.

| Paramater |                                      Type                                       | Required |                              Description                              |
| :-------: | :-----------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                     string                                      |   Yes    |                     The id of an existing recipe.                     |
|   scrap   | [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    // Add a stick to the scraps when breaking down a jukebox
    // This recipe comes from the above addRecipe() example.
    <recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(
        "craftteaker:recycling/jukebox_scrapping",
        <item:minecraft:stick>
    );
    ```
  </TabItem>
</Tabs>

---

### `addScrap(id, scraps[])`

Adds additional items to the scraps of the recipe with the given id. If the recipe already has `9` scraps or the new total is greater than `9`, this call will be ignored.

| Paramater |                                       Type                                        | Required |                              Description                              |
| :-------: | :-------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                      string                                       |   Yes    |                The name of the recipe, must be unique.                |
|  scraps   | [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    // Add a stick and 2 apples to the scraps when breaking down a jukebox
    // This recipe comes from the above addRecipe() example.
    <recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(
        "craftteaker:recycling/jukebox_scrapping",
        [<item:minecraft:stick>, <item:minecraft:apple> * 2]
    );
    ```
  </TabItem>
</Tabs>

---

### `removeScrap(id, scrap)`

Remove the scrap (item) from the recipe with the given id. If the item does not exist in the scraps, this call will be ignored.

| Paramater |                                      Type                                       | Required |                              Description                              |
| :-------: | :-----------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                     string                                      |   Yes    |                The name of the recipe, must be unique.                |
|   scrap   | [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    // Remove diamonds from the scraps when breaking down a jukebox
    // This recipe comes from the above addRecipe() example.
    <recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(
        "craftteaker:recycling/jukebox_scrapping",
        <item:minecraft:diamond>
    );
    ```
  </TabItem>
</Tabs>

---

### `removeScrap(id, scraps[])`

Removes the scraps (items) from the recipe with the given id. This will remove any items that match from the given scraps. Items that don't exist in the scraps of the matching recipe, will simply be ignored. 

| Paramater |                                       Type                                        | Required |                              Description                              |
| :-------: | :-------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                      string                                       |   Yes    |                The name of the recipe, must be unique.                |
|  scraps   | [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    // Remove diamond and oak planks from the scraps when breaking down a jukebox
    // This recipe comes from the above addRecipe() example.
    <recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(
        "craftteaker:recycling/jukebox_scrapping",
        [<item:minecraft:diamond>, <item:minecraft:oak_plank>]
    );
    ```
  </TabItem>
</Tabs>

---

### `replaceScrap(id, from, to)`

Replaces an item in the scraps of the matching recipe with a different item. If the item to replace does not exist, this call will simply be ignored.

| Paramater |                                       Type                                        | Required |                              Description                              |
| :-------: | :-------------------------------------------------------------------------------: | :------: | :-------------------------------------------------------------------: |
|    id     |                                      string                                       |   Yes    |                The name of the recipe, must be unique.                |
|   from    | [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |
|    to     | [IItemStack[]](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack) |   Yes    | The resulting item from cooking the `ingredient`, can have an amount. |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    // Replaces diamond with an apple from the scraps when breaking down a jukebox
    // This recipe comes from the above addRecipe() example.
    <recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(
        "craftteaker:recycling/jukebox_scrapping",
        <item:minecraft:diamond>,
        <item:minecraft:apple>
    );
    ```
  </TabItem>
</Tabs>

---

## Learn More

See **Recipe Managers** on the CraftTweaker [documentation](https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers) for all inbuilt functions.