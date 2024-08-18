package com.oscimate.jebbed;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;


@Environment(value= EnvType.CLIENT)
public class CustomRenderLayer extends RenderLayer {

    protected static final ShaderProgram CUSTOM_TINT_SHADER = new ShaderProgram(GameRendererSetting::getRenderTypeCustomTint);
//    private static final RenderLayer CUSTOM_TINT = RenderLayer.of("custom_tint", POSITION_COLOR_TEXTURE_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 262144, true, false, MultiPhaseParameters.builder().lightmap(ENABLE_LIGHTMAP).program(CUSTOM_TINT_SHADER).transparency(TRANSLUCENT_TRANSPARENCY).depthTest(RenderPhase.LEQUAL_DEPTH_TEST).texture(BLOCK_ATLAS_TEXTURE).build(true));

    private static final Function<Identifier, RenderLayer> CUSTOM_TINT = Util.memoize(
            (Function<Identifier, RenderLayer>)(texture -> {
                RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                        .program(CUSTOM_TINT_SHADER)
                        .texture(new RenderPhase.Texture(texture, false, false))
                        .transparency(NO_TRANSPARENCY)
                        .cull(DISABLE_CULLING)
                        .lightmap(ENABLE_LIGHTMAP)
                        .overlay(ENABLE_OVERLAY_COLOR)
                        .build(true);
                return of("custom_tint", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 1536, true, false, multiPhaseParameters);
            })
    );

    private static final Function<Identifier, RenderLayer> CUSTOM_TINT_TRANSLUCENT = Util.memoize(
            (Function<Identifier, RenderLayer>)(texture -> {
                RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                        .program(CUSTOM_TINT_SHADER)
                        .texture(new RenderPhase.Texture(texture, false, false))
                        .transparency(TRANSLUCENT_TRANSPARENCY)
                        .cull(DISABLE_CULLING)
                        .lightmap(ENABLE_LIGHTMAP)
                        .overlay(ENABLE_OVERLAY_COLOR)
                        .build(true);
                return of("custom_tint", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
            })
    );


    public CustomRenderLayer(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }
    public static RenderLayer getCustomTint(Identifier identifier) {
        return (RenderLayer)CUSTOM_TINT.apply(identifier);
    }
    public static RenderLayer getCustomTintTranslucent(Identifier identifier) {
        return (RenderLayer)CUSTOM_TINT_TRANSLUCENT.apply(identifier);
    }
}
