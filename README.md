# Stacknator

Stacknator is a lightweight Minecraft mod for Fabric that lets you customize the maximum stack size of any vanilla or modded item via a simple configuration file. By modifying item data components during game initialization, Stacknator delivers a clean, efficient solution without performance overhead or hacky workarounds.

## Compatibility

- **Required:** [Fabric API](https://modrinth.com/mod/fabric-api)
- **Vanilla Items:** Fully compatible.
- **Modded Items:** Should be compatible with any modded items, as long as the items are correctly registered.

> **Tags:** Currently not supported, as Stacknator prioritizes clean, non-intrusive solutions.

> **Tip:** To find an item's identifier, enable Advanced Tooltips in-game by pressing F3 + H, then hover over the item.

## Installation

1. Drop the Stacknator `.jar` into your `mods` folder.
2. Start the server or load into a world.
3. Edit the stacknator.json config file to set your desired stack sizes.
4. Enjoy!

## Documentation

- **enabled**:<br>
  Enables or disables the mod.<br>
  Type: `boolean` Default: `true`
- **log_success**:<br>
  Toggles logging of successful stack size changes (other logs always show).<br>
  Type: `boolean` Default: `true`
- **stacks**:<br>
  Defines item identifiers and their custom stack sizes.<br>
  Type: `object` Default: `{}`<br>

## Checks

Stacknator performs the following checks for each stack entry in the config file:

- **Entry Key Format**:<br>
  Verifies if the entry key follows the `namespace:item` format.<br>
  **On Failure**: Skips the entry.
- **Item Existence**:<br>
  Confirms the item exists in the game.<br>
  **On Failure**: Skips the entry.
- **Item Damageable**:<br>
  Ensures the item is not damageable (e.g. tools, weapons and armor).<br>
  **On Failure**: Skips the entry.
- **Entry Value Below 1**:<br>
  Checks if the stack size is less than 1.<br>
  **On Failure**: Skips the entry.
- **Entry Value Above 99**:<br>
  Checks if the stack size exceeds 99.<br>
  **On Failure**: Adjusts to 99.

## Full Configuration Example:

```json
{
    "enabled": true,
    "log_success": true,
    "stacks": {
        "minecraft:egg": 64,
        "minecraft:red_bed": 16,
        "minecraft:ender_pearl": 32,
        "minecraft:oak_log": 8,
        "minecraft:tnt": 1
    }
}