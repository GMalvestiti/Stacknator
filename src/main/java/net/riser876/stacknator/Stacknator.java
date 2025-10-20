package net.riser876.stacknator;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.riser876.stacknator.core.StackFiller;
import net.riser876.stacknator.core.StackManager;
import net.riser876.stacknator.core.StackRemover;
import net.riser876.stacknator.core.StackSorter;
import net.riser876.stacknator.util.StacknatorGlobals;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class Stacknator implements ModInitializer {

    @Override
    public void onInitialize() {
        if (!CONFIG.ENABLED) {
            StacknatorGlobals.log("Mod disabled.");
            return;
        }

        StacknatorGlobals.log("Mod loaded.");

        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            StackRemover.process();
            StackFiller.process();
            StackSorter.process();
            StackManager.process();
            StackManager.clear();
        });
    }
}
