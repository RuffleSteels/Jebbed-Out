package com.oscimate.jebbed.mixin.fire_overlays.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.oscimate.jebbed.CustomRenderLayer;
import com.oscimate.jebbed.Main;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin {

    @Unique
    LivingEntity renderingEntity;

    @Unique
    EquipmentSlot armorSlot;

    @Inject(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/ArmorFeatureRenderer;renderArmorParts(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/model/BipedEntityModel;ILnet/minecraft/util/Identifier;)V"))
    private void test(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model, CallbackInfo ci) {
        this.renderingEntity = entity;
        this.armorSlot = armorSlot;
    }
    @ModifyExpressionValue(method = "renderArmorParts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer test(RenderLayer original, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BipedEntityModel<LivingEntity> model, int i, Identifier identifier) {
        ItemStack stack = renderingEntity.getEquippedStack(armorSlot);
        if (Main.shadersOff() && !stack.isEmpty() && stack.get(DataComponentTypes.CUSTOM_NAME) != null && stack.get(DataComponentTypes.CUSTOM_NAME).getString().equals("jeb_")) {
            return CustomRenderLayer.getCustomTint(identifier);
        }
        return original;
    }

    @ModifyExpressionValue(method = "renderTrim", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getArmorTrims(Z)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer test(RenderLayer original) {
        ItemStack stack = renderingEntity.getEquippedStack(armorSlot);
        if (Main.shadersOff() && !stack.isEmpty() && stack.get(DataComponentTypes.CUSTOM_NAME) != null && stack.get(DataComponentTypes.CUSTOM_NAME).getString().equals("jeb_")) {
            return CustomRenderLayer.getCustomTint(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
        }
        return original;
    }
}
