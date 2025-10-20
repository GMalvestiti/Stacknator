package net.riser876.stacknator.mixin;

import net.riser876.stacknator.config.ConfigManager;
import net.riser876.stacknator.util.StacknatorGlobals;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.event.Level;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

import static net.riser876.stacknator.config.ConfigManager.CONFIG;

public class StacknatorMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
        try {
            ConfigManager.loadConfig();
            StacknatorGlobals.log("Configuration loaded.");
        } catch (Exception ex) {
            StacknatorGlobals.log(Level.ERROR, "Failed to load configuration. Check your stacknator.json config file.", ex);
        }
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("net.riser876.stacknator")) {
            return CONFIG.ENABLED;
        }
        return true;
    }

    @Override
    public String getRefMapperConfig() {return null;}
    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
    @Override
    public List<String> getMixins() {return null;}
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
