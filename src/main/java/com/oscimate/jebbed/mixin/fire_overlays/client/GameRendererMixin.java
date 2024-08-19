package com.oscimate.jebbed.mixin.fire_overlays.client;

import com.oscimate.jebbed.GameRendererSetting;
import com.oscimate.jebbed.Main;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void onTick(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (GameRendererSetting.getRenderTypeCustomTint() != null) {
            float interpolatedCounter = MathHelper.lerp(tickDelta, Main.previousCounter, Main.counter == 0 ? 200 : Main.counter);
            GameRendererSetting.getRenderTypeCustomTint().getUniform("Time").set((float) interpolatedCounter);
        }
    }
}
