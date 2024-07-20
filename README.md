![refurbished_furniture_banner](https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/assets/4958241/cb5e85cd-ee66-49d5-89aa-8f145e79b7f3)
![requires_framework](https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/assets/4958241/980cc39e-8a23-4aa1-a15d-5fc16a1fc3e4) ![forge](https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/assets/4958241/3068bff9-49f0-4dfc-9348-0d3aa1543444) ![fabric](https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/assets/4958241/27591fbc-b53a-4149-842d-967d25a63ec0) ![neoforge](https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/assets/4958241/a1a576e9-9240-4794-a407-a5d4783a8a7a)

## About
Introducing the next generation of MrCrayfish's Furniture Mod, built from the ground up with brand new blocks, models, original sounds, and gameplay mechanics. Experience the new yet familiar charm of the original mod, and furnish your house with the hundreds of new and original decorational and functional blocks. Jump into new gameplay mechanics with the introduction of the electricity system to power all your appliances and electronics. Take control of the mod, now featuring integration for CraftTweaker and extensive datapack support.

## Features
- Adds over 440+ functional and cosmetic blocks to decorate your house!
- Over 70+ original sound effects (all recorded and engineered by MrCrayfish) to fulfil your immersion.
- Brand new electricity system to deliver power to appliances and other electronics. Includes electricity generators, light switches, lights, and much more!
- A working mail delivery system that allows you to send and receive items into your own mailbox.
- A fully functional computer block that has fun and useful apps.
- Support for CraftTweaker, Just Enough Items and Farmer's Delight (Pot and Skillet can be used on Stove)

## Building
### Prerequisites
You will need to add a GitHub user and personal token to your Gradle environment in order to access the required dependencies for building.
This should be added as properties into the `gradle.properties` file located in the `GRADLE_USER_HOME` directory.

Add the following properties:
```gradle
gpr.user=<USERNAME>
gpr.key=<GITHUB_PERSONAL_TOKEN>
```
### Data Generation
Generated files such as crafting recipes, blockstates, models, etc are not available out of the gate. You will need to generate them yourself before you build the mod. If you do not generate them, the outputted mod will be missing required data.

Run the following command to generate the data for each respective modloaders subproject
```
./gradlew :forge:Data :fabric:runDatagen :neoforge:runData 
```
### Building the JAR
To build the mod, simply run the following command. This will produce an artifact for common and all the modloader subprojects. The outputted artifacts will be located in `<subproject>/build/libs`.
```
./gradlew build
```
If you would like to target a specific modloader, you can simply run:
```
./gradlew :<modloader>:build
```
You can also generate the data in the same command
```
./gradlew :forge:Data :fabric:runDatagen :neoforge:runData  build
```
It is important to note that if you build the mod yourself, you will have an unsigned version of the mod. This just means that others will not be able to confirm if the mod has been modified. The officially hosted versions on mrcrayfish.com, CurseForge, and Modrinth have been signed will a private key.
## License
The source code of MrCrayfish's Furniture Mod: Refurbished is available under MIT. The Assets are unlicensed and MrCrayfish reserves all rights to them. The Assets are files within the following directories: 
- `src/common/main/resources/assets`
- `src/forge/main/resources/assets` 
- `src/fabric/main/resources/assets`
- `src/neoforge/main/resources/assets`

You can build the mod from source and include the Assets, however, it can only be used strictly for personal use, and distribution is not allowed. Versions of the mod without the Assets fall under the terms of MIT. If you have any inquiries on the usage of the Assets, submit a ticket in the [Discord Server](https://discord.gg/mrcrayfish) under `#mod-support`.

Unmodified versions of MrCrayfish's Furniture Mod: Refurbished may be distributed in a CurseForge or Modrinth modpack. You are free to change configuration files and include datapacks for the interest of providing a custom modpack experience without it counting towards a modified version of the mod.

MrCrayfish loves to see community content. You are allowed to create reviews, share gameplay, and develop addons for the mod. Attribution is not required, however linking back to the official project pages is appreciated. Also feel free to share your creations with MrCrayfish, either on X ([@MrCrayfish](https://twitter.com/MrCrayfish)) or the [Discord Server](https://discord.gg/mrcrayfish).

License notices can be found in `LICENSE` and `ASSETS_LICENSE` in the source files above. They are also packaged with the JAR file for every build of the mod.

## Documentation
All documentation can be found on the Wiki.
## Screenshots
![KitchenDemoScreenshot](https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/assets/4958241/0ef363f0-dfea-4a5b-a02b-a461774f067a)
![BedroomDemoScreenshot](https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/assets/4958241/fc63e172-75a0-4025-99ca-8e7b85cde22a)
![BathroomDemoScreenshot](https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/assets/4958241/310c4700-ef91-4bf9-b946-d5eabf8445f5)
![OutdoorDemoScreenshot](https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/assets/4958241/00d42847-b373-46bb-8555-a270bbb7a079)
