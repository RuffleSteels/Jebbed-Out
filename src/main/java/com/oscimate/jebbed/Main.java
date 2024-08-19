package com.oscimate.jebbed;

import com.oscimate.jebbed.config.ConfigManager;
import com.oscimate.jebbed.config.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.irisshaders.iris.api.v0.IrisApi;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;


@Environment(EnvType.CLIENT)
public class Main implements ClientModInitializer {
    public static final String MODID = "jebbed";

    public static final ConfigManager CONFIG_MANAGER = new ConfigManager();

    public static float counter = 0;
    public static float previousCounter = 0;
    public static boolean irisInstalled = false;
    public static final KeyBinding configKeybind = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.jebbed.openConfig", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_J, "jebbed.title")
    );

    public static boolean shadersOff() {
        return !(irisInstalled && IrisApi.getInstance().isShaderPackInUse());
    }



    @Override
    public void onInitializeClient() {

        if(!CONFIG_MANAGER.fileExists()) {
            CONFIG_MANAGER.save();
        }
        CONFIG_MANAGER.getStartupConfig();

        irisInstalled = FabricLoader.getInstance().isModLoaded("iris");
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && client.player != null) {
                if (client.currentScreen == null) {
                    while (configKeybind.isPressed()) {
                        client.setScreen(new ConfigScreen());
                    }
                }
            }
        });

    }
}