package net.riser876.stacknator.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.riser876.stacknator.config.ConfigManager;
import net.riser876.stacknator.util.StacknatorGlobals;
import org.slf4j.event.Level;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class StackFiller {

    public static void process() {

        if (!CONFIG.FILLER.FILLER_ENABLED) {
            StacknatorGlobals.log("Filler disabled. Skipping operation.");
            return;
        }

        StacknatorGlobals.log("Filler enabled. Filling items...");
        Registries.ITEM.stream().forEach(StackFiller::fillItem);
        StacknatorGlobals.log("Filler operation complete.");

        if (CONFIG.FILLER.RUN_ONCE) {
            StacknatorGlobals.log("Disabling filler...");
            CONFIG.FILLER.FILLER_ENABLED = false;
            StacknatorGlobals.log("Filler disabled.");
        }

        ConfigManager.saveConfig();
    }

    private static void fillItem(Item item) {
        try {
            ItemStack defaultStack = item.getDefaultStack();

            if (CONFIG.FILLER.FILTER_DAMAGEABLE && defaultStack.isDamageable()) {
                CONFIG.ITEMS.remove(item.toString());
                return;
            }

            if (CONFIG.FILLER.FILTER_UNSTACKABLE && !defaultStack.isStackable()) {
                CONFIG.ITEMS.remove(item.toString());
                return;
            }

            if (CONFIG.FILLER.FILTER_MAXIMUM_COUNT_ONE && defaultStack.getMaxCount() == 1) {
                CONFIG.ITEMS.remove(item.toString());
                return;
            }

            if (CONFIG.ITEMS.containsKey(item.toString())) {
                if (CONFIG.FILLER.RESET_STACKS) {
                    CONFIG.ITEMS.put(item.toString(), defaultStack.getMaxCount());
                }
            } else {
                CONFIG.ITEMS.put(item.toString(), defaultStack.getMaxCount());
            }
        } catch (Exception ex) {
            StacknatorGlobals.log(Level.ERROR, "Failed to fill item: {}. Skipping.", item.toString());
        }
    }
}
