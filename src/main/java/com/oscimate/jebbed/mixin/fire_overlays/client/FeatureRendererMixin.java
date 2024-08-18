package com.oscimate.jebbed.mixin.fire_overlays.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.oscimate.jebbed.CustomRenderLayer;
import com.oscimate.jebbed.Main;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FeatureRenderer.class)
public class FeatureRendererMixin {
    @ModifyExpressionValue(method = "renderModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private static RenderLayer test(RenderLayer original, EntityModel<LivingEntity> model,
                                    Identifier texture,
                                    MatrixStack matrices,
                                    VertexConsumerProvider vertexConsumers,
                                    int light,
                                    LivingEntity entity,
                                    float red,
                                    float green,
                                    float blue) {
        if (Main.shadersOff() && entity.hasCustomName() && entity.getCustomName().getString().equals("jeb_")) {
            return CustomRenderLayer.getCustomTint(texture);
        }
        return original;
    }

}
