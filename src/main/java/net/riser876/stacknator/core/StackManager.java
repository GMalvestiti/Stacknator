package net.riser876.stacknator.core;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.riser876.stacknator.Stacknator;

import java.util.Map;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class StackManager {

    public static void process() {
        for (Map.Entry<String, Integer> entry : CONFIG.STACKS.entrySet()) {
            String key = entry.getKey();

            if (key.split(":").length != 2) {
                Stacknator.LOGGER.info("[Stacknator] Invalid item key: {}. Skipping.", key);
                continue;
            }

            StackManager.processEntry(entry);
        }
    }

    private static void processEntry(Map.Entry<String, Integer> entry) {
        try {
            String[] parts = entry.getKey().split(":");

            Identifier identifier = Identifier.of(parts[0], parts[1]);

            Item item = Registries.ITEM.get(identifier);

            StackManager.validateItem(item, entry.getValue(), entry.getKey());
        } catch (Exception e) {
            Stacknator.LOGGER.error("[Stacknator] Failed to process item: {}. Skipping.", entry.getKey());
        }
    }

    private static void validateItem(Item item, int stackSize, String key) {
        if (item.equals(Items.AIR)) {
            Stacknator.LOGGER.info("[Stacknator] Item {} not found. Skipping.", key);
            return;
        }

        if (item.getDefaultStack().isDamageable()) {
            Stacknator.LOGGER.info("[Stacknator] Item {} is damageable. Skipping.", key);
            return;
        }

        if (stackSize <= 0) {
            Stacknator.LOGGER.info("[Stacknator] Invalid stack size: {} for item {}. Skipping.", stackSize, key);
            return;
        }

        if (stackSize > 99) {
            Stacknator.LOGGER.info("[Stacknator] Stack size exceeds the limit: {} for item {}. Changing it to 99.", stackSize, key);
            stackSize = 99;
        }

        StackManager.setStackSize(item, stackSize);
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
