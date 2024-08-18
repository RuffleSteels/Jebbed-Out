package com.oscimate.jebbed.mixin.fire_overlays.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Cancellable;
import com.oscimate.jebbed.CustomRenderLayer;
import com.oscimate.jebbed.Main;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @ModifyExpressionValue(method = "getRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getTexture(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/Identifier;"))
    private Identifier test(Identifier original, LivingEntity entity, boolean showBody, boolean translucent, boolean showOutline, @Cancellable CallbackInfoReturnable<RenderLayer> cir) {
        if (Main.shadersOff() && entity.hasCustomName() && entity.getCustomName().getString().equals("jeb_")) {
            cir.setReturnValue(CustomRenderLayer.getCustomTint(original));
        }

        return original;
    }
}
