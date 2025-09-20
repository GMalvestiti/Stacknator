package net.riser876.stacknator.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();
    private static final String CONFIG_FILE_NAME = "stacknator.json";
    public static Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME);

    public static Config CONFIG;

    public static void loadConfig() {
        if (Files.notExists(ConfigManager.CONFIG_PATH)) {
            loadDefaultConfig();
            return;
        }

        try {
            String json = Files.readString(ConfigManager.CONFIG_PATH);
            ConfigManager.CONFIG = GSON.fromJson(json, Config.class);
            ConfigManager.saveConfig();
        } catch (IOException | JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDefaultConfig() {
        ConfigManager.CONFIG = new Config();
        ConfigManager.saveConfig();
    }

    private static void saveConfig() {
        try {
            String json = GSON.toJson(ConfigManager.CONFIG);
            Files.write(ConfigManager.CONFIG_PATH, json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
