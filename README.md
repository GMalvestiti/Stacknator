# Stacknator

Stacknator is a lightweight Minecraft mod for Fabric that lets you customize the maximum stack size of any vanilla or modded item via a simple configuration file. By modifying item data components during game initialization, Stacknator delivers a clean, efficient solution without performance overhead or hacky workarounds.

## Compatibility

- **Required:** [Fabric API](https://modrinth.com/mod/fabric-api)
- **Vanilla Items:** Fully compatible.
- **Modded Items:** Generally compatible with most mods.

> **Tags:** Supported through the Filler feature.

> **Tip:** To find an item's identifier, enable Advanced Tooltips in-game by pressing F3 + H, then hover over the item.

## Installation

1. Drop the Stacknator `.jar` into your `mods` folder.
2. Start the server or load into a world.
3. Edit the stacknator.json config file to set your desired stack sizes.
4. Enjoy!

> Required only on the server side for a `survival` gameplay, however it's recommended to have it on the client side as well to be fully compatible some screens (e.g. creative screen).

## Documentation

- **enabled**:<br>
  Enables or disables the mod.<br>
  Type: `boolean` Default: `true`
- **log_modified_items**:<br>
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
  Removes all item entries that match the default stack size.<br>
  Type: `boolean` Default: `false`<br>
- **filler**:<br>
  Defines the filler settings.<br>
  Type: `object` Default:<br>
    - **enable_filler**:<br>
      Enables or disables the filler. When enabled, the mod will iterate all registered items and apply the filler rules to each item while enforcing the configured checks.<br>
      Type: `boolean` Default: `false`<br>
    - **reset_stacks**:<br>
      Enables or disables resetting of stack sizes to the default values, overriding any defined custom stack size.<br>
      Type: `boolean` Default: `false`<br>
    - **run_once**:<br>
      Disables the filler after one run.<br>
      Type: `boolean` Default: `true`<br>
    - **tag_priority**:<br>
      Enables or disables prioritizing tags over item entries when applying the filler.<br>
      Type: `boolean` Default: `true`<br>
    - **tags**:<br>
      Defines tag identifiers and their custom stack sizes.<br>
      Type: `object` Default: `{}`<br>
- **sort_items**:<br>
  Enables or disabled sorting the item entries alphabetically by their keys.<br>
  Type: `boolean` Default: `true`<br>
- **items**:<br>
  Defines item identifiers and their custom stack sizes.<br>
  Type: `object` Default: `{}`<br>

## Checks

Stacknator performs the following checks for each item/tag entry in the config file:

- **Entry Key Format**:<br>
  Verifies if the entry key follows the `namespace:item` format.<br>
  **On Failure**: Skips the entry.
- **Item Damageable**:<br>
  Ensures the item is not damageable (e.g. tools, weapons and armor).<br>
  **On Failure**: Skips the entry.
- **Item Stackable**:<br>
  Confirms the item is stackable (default maximum stack above 1).<br>
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
  "log_modified_items": true,
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
    "reset_stacks": false,
    "run_once": true,
    "tag_priority": true,
    "tags": {
        "#minecraft:logs": 16,
        "#minecraft:planks": 32
    }
  },
  "sort_items": true,
  "items": {
    "minecraft:egg": 64,
    "minecraft:red_bed": 16,
    "minecraft:ender_pearl": 32,
    "minecraft:tnt": 1
  }

}
