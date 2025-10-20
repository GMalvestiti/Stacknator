package net.riser876.stacknator.core;

import net.riser876.stacknator.config.ConfigManager;
import net.riser876.stacknator.util.StacknatorGlobals;
import org.slf4j.event.Level;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class StackSorter {

    public static void process() {

        if (!CONFIG.SORTER.SORTER_ENABLED) {
            StacknatorGlobals.log("Sorter disabled. Skipping operation.");
            return;
        }

        if (CONFIG.SORTER.SORT_TAGS) {
            StacknatorGlobals.log("Tag sorting enabled. Sorting tags...");
            StackSorter.sortTags();
        } else {
            StacknatorGlobals.log("Tag sorting disabled. Skipping.");
        }

        if (CONFIG.SORTER.SORT_ITEMS) {
            StacknatorGlobals.log("Item sorting enabled. Sorting items...");
            StackSorter.sortItems();
        } else {
            StacknatorGlobals.log("Item sorting disabled. Skipping.");
        }

        ConfigManager.saveConfig();
    }

    private static void sortTags() {
        Map<String, Integer> fallback = new LinkedHashMap<>(CONFIG.TAGS);

        try {
            Map<String, Integer> sorted = CONFIG.TAGS.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            CONFIG.TAGS.clear();
            CONFIG.TAGS.putAll(sorted);

            StacknatorGlobals.log("Tags sorted successfully.");
        } catch (Exception ex) {
            StacknatorGlobals.log(Level.ERROR, "Failed to sort tags. Reverting to previous state.", ex);

            CONFIG.TAGS.clear();
            CONFIG.TAGS.putAll(fallback);
        }
    }

    private static void sortItems() {
        Map<String, Integer> fallback = new LinkedHashMap<>(CONFIG.ITEMS);

        try {
            Map<String, Integer> sorted = CONFIG.ITEMS.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            CONFIG.ITEMS.clear();
            CONFIG.ITEMS.putAll(sorted);

            StacknatorGlobals.log("Items sorted successfully.");
        } catch (Exception ex) {
            StacknatorGlobals.log(Level.ERROR, "Failed to sort items. Reverting to previous state.", ex);

            CONFIG.ITEMS.clear();
            CONFIG.ITEMS.putAll(fallback);
        }
    }
}
