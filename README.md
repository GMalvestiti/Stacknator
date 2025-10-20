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
- **checks**:<br>
  Defines the checks to be performed.<br>
  Type: `object` Default:<br>
  - **enable_checks**:<br>
    Enables or disables all checks.<br>
    Type: `boolean` Default: `true`<br>
  - **check_damageable**:<br>
    Enables or disables the damageable check.<br>
    Type: `boolean` Default: `true`<br>
  - **check_stackable**:<br>
    Enables or disables the stackable check.<br>
    Type: `boolean` Default: `false`<br>
  - **check_minimum_stack_size**:<br>
    Enables or disables the minimum stack size check.<br>
    Type: `boolean` Default: `true`<br>
  - **check_maximum_stack_size**:<br>
    Enables or disables the maximum stack size check.<br>
    Type: `boolean` Default: `true`<br>
- **remove_defaults**:<br>
  Enables or disables the removal from the configuration stack entries that match the default stack size.<br>
  Type: `boolean` Default: `false`<br>
- **filler**:<br>
  Defines the filler settings.<br>
  Type: `object` Default:<br>
  - **enable_filler**:<br>
    Enables or disables the filler. When enabled, all registered items will be processed with the following settings.<br>
    Type: `boolean` Default: `false`<br>
  - **filter_damageable**:<br>
    Enables or disables filtering of damageable items.<br>
    Type: `boolean` Default: `true`<br>
  - **filter_unstackable**:<br>
    Enables or disables filtering of unstackable items.<br>
    Type: `boolean` Default: `false`<br>
  - **reset_stacks**:<br>
    Enables or disables resetting of stack sizes to the default values, overriding any defined custom stack size.<br>
    Type: `boolean` Default: `false`<br>
  - **disable_after**:<br>
    Disables the filler after one run.<br>
    Type: `boolean` Default: `true`<br>
- **sort_stacks**:<br>
  Enables or disabled sorting the stack entries alphabetically by their keys.<br>
  Type: `boolean` Default: `true`<br>
- **stacks**:<br>
  Defines item identifiers and their custom stack sizes.<br>
  Type: `object` Default: `{}`<br>

## Checks

Stacknator performs the following checks for each stack entry in the config file before modifying the stack size:

- **Entry Key Format**:<br>
  Verifies if the entry key follows the `namespace:item` format.<br>
  **On Failure**: Skips the entry.
- **Item Existence**:<br>
  Confirms the item exists in the game.<br>
  **On Failure**: Skips the entry.
- **Item Damageable**:<br>
  Ensures the item is not damageable (e.g. tools, weapons and armor).<br>
  **On Failure**: Skips the entry.
- **Item Stackable**:<br>
  Confirms the item is stackable (damageable and maximum count above 1).<br>
  **On Failure**: Skips the entry.
- **Entry Value Below 1**:<br>
  Checks if the stack size is less than 1.<br>
  **On Failure**: Skips the entry.
- **Entry Value Above 99**:<br>
  Checks if the stack size exceeds 99.<br>
  **On Failure**: Adjusts to 99.

## Full Configuration Example

```json
{
  "enabled": true,
  "log_success": true,
  "checks": {
    "enable_checks": true,
    "check_damageable": true,
    "check_stackable": false,
    "check_minimum_stack_size": true,
    "check_maximum_stack_size": true
  },
  "remove_defaults": false,
  "filler": {
    "enable_filler": false,
    "filter_damageable": true,
    "filter_unstackable": false,
    "reset_stacks": false,
    "disable_after": true
  },
  "sort_stacks": true,
  "stacks": {
    "minecraft:egg": 64,
    "minecraft:red_bed": 16,
    "minecraft:ender_pearl": 32,
    "minecraft:oak_log": 8,
    "minecraft:tnt": 1
  }
}