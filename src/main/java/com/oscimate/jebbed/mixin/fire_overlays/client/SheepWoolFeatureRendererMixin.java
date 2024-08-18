package com.oscimate.jebbed.mixin.fire_overlays.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.oscimate.jebbed.Main;
import net.minecraft.client.render.entity.feature.SheepWoolFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SheepWoolFeatureRenderer.class)
public class SheepWoolFeatureRendererMixin {
    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/SheepEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;hasCustomName()Z"))
    private boolean test(boolean original) {
        return !Main.shadersOff();
    }
}
