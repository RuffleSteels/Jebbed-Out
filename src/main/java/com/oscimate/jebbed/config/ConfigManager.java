package com.oscimate.jebbed.config;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    public float getJebbedSpeed() {
        return jebbedSpeed;
    }

    public void setJebbedSpeed(float jebbedSpeed) {
        this.jebbedSpeed = jebbedSpeed;
    }

    private float jebbedSpeed;

    private static final Gson GSON = new Gson();
    public static Path file = FabricLoader.getInstance().getConfigDir().resolve("jebbed" + ".json");

    public Boolean fileExists() {
        return Files.exists(file);
    }
    public void getStartupConfig() {
        JebbedConfig jsonOutput = null;
        if(fileExists()) {
            try (Reader reader = Files.newBufferedReader(file)) {
                jsonOutput = GSON.fromJson(reader, JebbedConfig.class);
            } catch (IOException e) {

            }
        }
        if (jsonOutput.getJebbedSpeed() < 0 || jsonOutput.getJebbedSpeed() > 100) {
            jsonOutput.setJebbedSpeed(0);
            save();
        } else {
            setJebbedSpeed(jsonOutput.getJebbedSpeed());
        }
    }

    public void save() {
        try {
            Files.writeString(file, GSON.toJson(new JebbedConfig()));
        } catch (IOException e) {

        }
    }
}
