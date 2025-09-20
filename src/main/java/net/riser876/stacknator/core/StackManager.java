package net.riser876.stacknator.core;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.riser876.stacknator.Stacknator;

import java.util.HashMap;
import java.util.Map;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class StackManager {

    public static void process() {

        HashMap<Item, Integer> items = new HashMap<>();

        for (Map.Entry<String, Integer> entry : CONFIG.STACKS.entrySet()) {
            String key = entry.getKey();

            if (key.split(":").length != 2) {
                Stacknator.LOGGER.info("[Stacknator] Invalid item key: {}. Skipping.", key);
                continue;
            }

            processEntry(items, entry);
        }

        items.forEach(StackManager::setStackSize);
    }

    private static void processEntry(HashMap<Item, Integer> items, Map.Entry<String, Integer> entry) {
        try {
            Item item = Registries.ITEM.get(getIdentifier(entry.getKey()));

            if (item.equals(Items.AIR)) {
                Stacknator.LOGGER.info("[Stacknator] Item {} not found. Skipping.", entry.getKey());
                return;
            }

            addItem(items, item, entry.getValue());
        } catch (Exception e) {
            Stacknator.LOGGER.error("[Stacknator] Failed to process item: {}. Skipping.", entry.getKey());
        }
    }

    private static Identifier getIdentifier(String key) {
        String[] parts = key.split(":");
        return Identifier.of(parts[0], parts[1]);
    }

    private static void addItem(HashMap<Item, Integer> items, Item item, int stackSize) {
        if (item.getDefaultStack().isDamageable()) {
            Stacknator.LOGGER.info("[Stacknator] Item {} is damageable. Skipping.", item.toString());
            return;
        }

        if (stackSize <= 0) {
            Stacknator.LOGGER.info("[Stacknator] Invalid stack size: {} for item {}. Skipping.", stackSize, item.toString());
            return;
        }

        if (stackSize > 99) {
            Stacknator.LOGGER.info("[Stacknator] Stack size exceeds the limit: {} for item {}. Changing it to 99.", stackSize, item.toString());
            stackSize = 99;
        }

        items.put(item, stackSize);
    }

    private static void setStackSize(Item item, int stackSize) {
        if (CONFIG.LOG_SUCCESS) {
            Stacknator.LOGGER.info("[Stacknator] Setting stack size of {} to {}.", item.toString(), stackSize);
        }

        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(item, builder -> {
                builder.add(DataComponentTypes.MAX_STACK_SIZE, stackSize);
            });
        });
    }
}
