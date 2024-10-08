package com.oscimate.jebbed.mixin.fire_overlays.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.oscimate.jebbed.CustomRenderLayer;
import com.oscimate.jebbed.Main;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraFeatureRendererMixin {
    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer test(Identifier texture, Operation<RenderLayer> original, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack stack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (Main.shadersOff() && !stack.isEmpty() && stack.get(DataComponentTypes.CUSTOM_NAME) != null && stack.get(DataComponentTypes.CUSTOM_NAME).getString().equals("jeb_")) {
            return CustomRenderLayer.getCustomTint(texture);
        }
        return original.call(texture);
    }
}
