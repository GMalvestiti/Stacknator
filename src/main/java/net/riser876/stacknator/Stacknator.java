package net.riser876.stacknator;

import net.fabricmc.api.ModInitializer;
import net.riser876.stacknator.config.ConfigManager;
import net.riser876.stacknator.core.StackManager;
import net.riser876.stacknator.util.StacknatorGlobals;
import org.slf4j.event.Level;

import java.util.Objects;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class Stacknator implements ModInitializer {

    @Override
    public void onInitialize() {
        try {
            ConfigManager.loadConfig();
            StacknatorGlobals.log("Configuration loaded.");
        } catch (Exception ex) {
            StacknatorGlobals.log(Level.ERROR, "Failed to load configuration. Check your stacknator.json config file.", ex);
            return;
        }

        if (Objects.nonNull(CONFIG) && CONFIG.ENABLED) {
            StacknatorGlobals.log("Mod loaded.");
            StackManager.init();
        } else {
            StacknatorGlobals.log("Mod disabled.");
        }
    }
}