package net.riser876.stacknator;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stacknator implements ModInitializer {

    public static final String MOD_ID = "stacknator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        Stacknator.LOGGER.info("[Stacknator] Mod loaded.");
    }
}
