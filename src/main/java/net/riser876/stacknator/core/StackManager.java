package net.riser876.stacknator.core;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.riser876.stacknator.mixin.ItemAccessor;
import net.riser876.stacknator.util.StacknatorGlobals;
import org.slf4j.event.Level;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class StackManager {

    private static final Map<Item, Integer> QUEUE = new LinkedHashMap<>();

    public static void process() {

        if (CONFIG.TAGS.isEmpty()) {
            StacknatorGlobals.log("Empty tags configuration. Skipping operation.");
        } else {
            StacknatorGlobals.log("Processing tags...");
        }

        for (Map.Entry<String, Integer> entry : CONFIG.TAGS.entrySet()) {
            String key = entry.getKey();

            if (key.split(":").length != 2 || !key.startsWith("#")) {
                StacknatorGlobals.log("Invalid tag key: {}. Skipping.", key);
                continue;
            }

            StackManager.processTag(entry);
        }

        if (CONFIG.ITEMS.isEmpty()) {
            StacknatorGlobals.log("Empty items configuration. Skipping operation.");
        } else {
            StacknatorGlobals.log("Processing items...");
        }

        for (Map.Entry<String, Integer> entry : CONFIG.ITEMS.entrySet()) {
            String key = entry.getKey();

            if (key.split(":").length != 2 || key.startsWith("#")) {
                StacknatorGlobals.log("Invalid item key: {}. Skipping.", key);
                continue;
            }

            StackManager.processItem(entry);
        }

        for (Map.Entry<Item, Integer> entry : StackManager.QUEUE.entrySet()) {
            StackManager.modifyStackSize(entry.getKey(), entry.getValue());
        }
    }

    private static void processTag(Map.Entry<String, Integer> entry) {
        try {
            Identifier identifier = getIdentifier(entry.getKey());

            TagKey<Item> tagKey = TagKey.of(Registries.ITEM.getKey(), identifier);

            Optional<RegistryEntryList.Named<Item>> items = Registries.ITEM.getEntryList(tagKey);

            if (items.isEmpty()) {
                StacknatorGlobals.log("Tag {} empty. Skipping.", entry.getKey());
                return;
            }

            StacknatorGlobals.log("Processing tag {}.", entry.getKey());

            for (RegistryEntry<Item> registryEntry : items.get()) {
                StackManager.validateItem(registryEntry.value(), entry.getValue(), entry.getKey());
            }
        } catch (Exception ex) {
            StacknatorGlobals.log(Level.ERROR, "Failed to process tag: {}. Skipping.", entry.getKey());
        }
    }

    private static void processItem(Map.Entry<String, Integer> entry) {
        try {
            Identifier identifier = getIdentifier(entry.getKey());

            Item item = Registries.ITEM.get(identifier);

            StackManager.validateItem(item, entry.getValue(), entry.getKey());
        } catch (Exception ex) {
            StacknatorGlobals.log(Level.ERROR, "Failed to process item: {}. Skipping.", entry.getKey());
        }
    }

    private static Identifier getIdentifier(String key) {
        return Identifier.of(key.replace("#", ""));
    }

    private static void validateItem(Item item, int stackSize, String key) {
        if (item.equals(Items.AIR) && !item.toString().equals(key)) {
            StacknatorGlobals.log("Item {} not found. Skipping.", key);
            return;
        }

        if (CONFIG.CHECKS.CHECKS_ENABLED) {
            if (CONFIG.CHECKS.CHECK_DAMAGEABLE && item.getDefaultStack().isDamageable()) {
                StacknatorGlobals.log("Item {} is damageable. Skipping.", key);
                return;
            }

            if (CONFIG.CHECKS.CHECK_STACKABLE && !item.getDefaultStack().isStackable()) {
                StacknatorGlobals.log("Item {} is unstackable. Skipping.", key);
                return;
            }

            if (CONFIG.CHECKS.CHECK_MAXIMUM_COUNT_ONE && item.getDefaultStack().getMaxCount() == 1) {
                StacknatorGlobals.log("Item {} has default stack size of 1. Skipping.", key);
                return;
            }

            if (CONFIG.CHECKS.CHECK_MINIMUM_STACK_SIZE && stackSize <= 0) {
                StacknatorGlobals.log("Invalid stack size: {} for item {}. Skipping.", stackSize, key);
                return;
            }

            if (CONFIG.CHECKS.CHECK_MAXIMUM_STACK_SIZE && stackSize > 99) {
                StacknatorGlobals.log("Stack size exceeds the limit: {} for item {}. Changing it to 99.", stackSize, key);
                stackSize = 99;
            }
        }

        StackManager.QUEUE.put(item, stackSize);
    }

    private static void modifyStackSize(Item item, int stackSize) {
        try {
            if (item.getDefaultStack().getMaxCount() == stackSize) {
                StacknatorGlobals.log(CONFIG.LOG_MODIFIED_ITEMS, "Item {} already has stack size of {}. Skipping.", item.toString(), stackSize);
                return;
            }

            StacknatorGlobals.log(CONFIG.LOG_MODIFIED_ITEMS, "Setting stack size of {} to {}.", item.toString(), stackSize);

            ComponentMap.Builder builder = ComponentMap.builder();
            builder.addAll(item.getComponents());
            builder.add(DataComponentTypes.MAX_STACK_SIZE, stackSize);

            ((ItemAccessor) item).setComponents(builder.build());
        } catch (Exception ex) {
            StacknatorGlobals.log(Level.ERROR, "Failed to modify stack size for item: {}. Skipping.", item.toString());
        }
    }

    public static void clear() {
        CONFIG.TAGS.clear();
        CONFIG.ITEMS.clear();
        StackManager.QUEUE.clear();
    }
}
