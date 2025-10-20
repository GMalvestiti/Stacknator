package net.riser876.stacknator.core;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.riser876.stacknator.config.ConfigManager;
import net.riser876.stacknator.util.StacknatorGlobals;
import org.slf4j.event.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class StackRemover {

    public static void process() {

        if (!CONFIG.REMOVE_DEFAULTS) {
            StacknatorGlobals.log("Remove defaults disabled. Skipping operation.");
            return;
        }

        StacknatorGlobals.log("Remove defaults enabled. Removing defaults...");

        try {
            List<String> toRemove = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : CONFIG.ITEMS.entrySet()) {
                String key = entry.getKey();

                if (key.split(":").length != 2) {
                    continue;
                }

                if (StackRemover.shouldRemoveEntry(entry)) {
                    toRemove.add(key);
                }
            }

            for (String key : toRemove) {
                CONFIG.ITEMS.remove(key);
            }

            StacknatorGlobals.log("Remove defaults operation complete. Removed {} entries.", toRemove.size());
        } catch (Exception ex) {
            StacknatorGlobals.log(Level.ERROR, "Failed to remove defaults.", ex);
        }

        ConfigManager.saveConfig();
    }

    private static boolean shouldRemoveEntry(Map.Entry<String, Integer> entry) {
        try {
            String[] parts = entry.getKey().split(":");

            Identifier identifier = Identifier.of(parts[0], parts[1]);

            Item item = Registries.ITEM.get(identifier);

            if (item.equals(Items.AIR) && !item.toString().equals(entry.getKey())) {
                return false;
            }

            return item.getDefaultStack().getMaxCount() == entry.getValue();
        } catch (Exception ex) {
            return false;
        }
    }
}
