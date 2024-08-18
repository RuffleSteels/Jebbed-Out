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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {

    @Shadow protected abstract Identifier getArmorTexture(ArmorItem item, boolean secondLayer, @Nullable String overlay);

    @Unique
    LivingEntity renderingEntity;

    @Unique
    EquipmentSlot armorSlot;

    @Inject(method = "renderArmor", at = @At(value = "HEAD"))
    private void test(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model, CallbackInfo ci) {
        this.renderingEntity = entity;
        this.armorSlot = armorSlot;
    }
    @ModifyExpressionValue(method = "renderArmorParts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer test(RenderLayer original, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, BipedEntityModel<LivingEntity> model, boolean secondTextureLayer, float red, float green, float blue, @Nullable String overlay) {

        ItemStack stack = renderingEntity.getEquippedStack(armorSlot);
        if (Main.shadersOff() && !stack.isEmpty()) {
            NbtCompound nbtCompound = stack.getSubNbt("display");
            if (nbtCompound != null && nbtCompound.contains("Name", NbtElement.STRING_TYPE)) {
                Text text = Text.Serializer.fromJson(nbtCompound.getString("Name"));
                if (text != null && text.getString().equals("jeb_")) {
                    return CustomRenderLayer.getCustomTint(getArmorTexture(item, secondTextureLayer, overlay));
                }
            }
        }

        return original;
    }

    @ModifyExpressionValue(method = "renderTrim", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getArmorTrims()Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer test(RenderLayer original) {
        ItemStack stack = renderingEntity.getEquippedStack(armorSlot);
        if (Main.shadersOff() && !stack.isEmpty()) {
            NbtCompound nbtCompound = stack.getSubNbt("display");
            if (nbtCompound != null && nbtCompound.contains("Name", NbtElement.STRING_TYPE)) {
                Text text = Text.Serializer.fromJson(nbtCompound.getString("Name"));
                if (text != null && text.getString().equals("jeb_")) {
                    return CustomRenderLayer.getCustomTint(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
                }
            }
        }
        return original;
    }
}
