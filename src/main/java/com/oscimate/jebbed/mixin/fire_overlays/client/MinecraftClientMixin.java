package com.oscimate.jebbed.mixin.fire_overlays.client;

import com.oscimate.jebbed.GameRendererSetting;
import com.oscimate.jebbed.Main;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Main.previousCounter = Main.counter;

        if (GameRendererSetting.getRenderTypeCustomTint() != null) {
            Main.counter += 1 + (float)(Math.pow(Main.CONFIG_MANAGER.getJebbedSpeed()/100, 2) * 49);;
            if (Main.counter > 200) {
                Main.counter = 0;
            }
        }
    }
}
