package net.riser876.stacknator;

import net.fabricmc.api.ModInitializer;
import net.riser876.stacknator.config.ConfigManager;
import net.riser876.stacknator.core.StackManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class Stacknator implements ModInitializer {

    public static final String MOD_ID = "stacknator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        try {
            ConfigManager.loadConfig();
            Stacknator.LOGGER.info("[Stacknator] Configuration loaded.");
        } catch (Exception e) {
            Stacknator.LOGGER.error("[Stacknator] Failed to load configuration. Check your stacknator.json config file.", e);
            return;
        }

        if (!CONFIG.ENABLED) {
            Stacknator.LOGGER.info("[Stacknator] Mod disabled.");
            return;
        }

        StackManager.process();

        Stacknator.LOGGER.info("[Stacknator] Mod loaded.");
    }
}
