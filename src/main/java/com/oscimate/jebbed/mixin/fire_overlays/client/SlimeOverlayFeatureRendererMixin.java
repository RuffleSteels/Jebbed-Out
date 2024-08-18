package com.oscimate.jebbed.mixin.fire_overlays.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.oscimate.jebbed.CustomRenderLayer;
import com.oscimate.jebbed.Main;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.SlimeOverlayFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SlimeOverlayFeatureRenderer.class)
public class SlimeOverlayFeatureRendererMixin {
    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityTranslucent(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer test(RenderLayer original, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l) {
        if (Main.shadersOff() && livingEntity.hasCustomName() && livingEntity.getCustomName().getString().equals("jeb_")) {
            return CustomRenderLayer.getCustomTintTranslucent(((SlimeOverlayFeatureRenderer<LivingEntity>) (Object) this).getTexture(livingEntity));
        }
        return original;
    }
}
