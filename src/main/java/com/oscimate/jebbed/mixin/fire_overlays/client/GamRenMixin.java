package com.oscimate.jebbed.mixin.fire_overlays.client;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.mojang.datafixers.util.Pair;
import com.oscimate.jebbed.GameRendererSetting;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public abstract class GamRenMixin {
    @ModifyReceiver(method = "loadPrograms", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private List<Pair<ShaderProgram, Consumer<ShaderProgram>>> addShaderPrograms(List<Pair<ShaderProgram, Consumer<ShaderProgram>>> instance, Object e, ResourceFactory factory) throws IOException {
        instance.add(Pair.of(new ShaderProgram(factory, "jebbed/rendertype_custom_tint", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL), program -> {
            GameRendererSetting.renderTypeCustomTint = program;
        }));
        return instance;
    }


}
