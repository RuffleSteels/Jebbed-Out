package com.oscimate.jebbed.config;

import com.mojang.blaze3d.systems.RenderSystem;
import com.oscimate.jebbed.GameRendererSetting;
import com.oscimate.jebbed.Main;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.awt.*;


public class ConfigScreen extends Screen {
    private Screen parent = null;
    public ConfigScreen() {
        super(Text.translatable("options.videoTitle"));
    }

    public ConfigScreen(Screen screen) {
        super(Text.translatable("options.videoTitle"));
        parent = screen;
    }

    Slider customTimeSliderWidget;

    @Override
    protected void init() {
        customTimeSliderWidget = new Slider(this.width / 2 - 75, height/2 - 23, 150, 20, Text.translatable("jebbed.config.title.speed"), Main.CONFIG_MANAGER.getJebbedSpeed());
        this.addDrawableChild(customTimeSliderWidget);
        this.addDrawableChild(new ButtonWidget.Builder(ScreenTexts.DONE, button -> close()).dimensions(width / 2 - 100, height/2 + 3, 200, 20).build());
        super.init();
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        Identifier id = new Identifier("jebbed:textures/gui/warning.png");

        float h = height/3f;
        int spriteWidth = Math.round(h * (3070f / 2160));

        int x = width/2 - spriteWidth/2;
        float y = height - (height - (height/2f + 23))/2 - h/2;
        int z = 10;

        float u1 = (0.0F + 0.0F) /3070;
        float u2 = (0.0F + 3070) / 3070;
        float v1 = (0.0F + 0.0F) / 2160;
        float v2 = (0.0F + 2160) / 2160;

        float alpha = Math.max(((this.customTimeSliderWidget.getSliderValue() - 50) / 50f), 0f);

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, id);
        RenderSystem.setShader(GameRendererSetting::getPositionColorTexProgram);
        Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(matrix4f, (float)x, (float)y, (float)z).color(1.0f, 1.0f, 1.0f, alpha).texture(u1, v1).next();
        bufferBuilder.vertex(matrix4f, (float)x, (float)y+h, (float)z).color(1.0f, 1.0f, 1.0f, alpha).texture(u1, v2).next();
        bufferBuilder.vertex(matrix4f, (float)x+spriteWidth, (float)y+h, (float)z).color(1.0f, 1.0f, 1.0f, alpha).texture(u2, v2).next();
        bufferBuilder.vertex(matrix4f, (float)x+spriteWidth, (float)y, (float)z).color(1.0f, 1.0f, 1.0f, alpha).texture(u2, v1).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
        context.drawText(this.textRenderer, "Rainbow Speed", this.width / 2 - textRenderer.getWidth("Rainbow Speed") / 2, height/2 - 23 - 4 - textRenderer.fontHeight, 0xFFFFFF, false);
    }
    @Override
    public void close() {
        if (parent == null) {
            super.close();
        } else {
            client.setScreen(parent);
        }
       Main.CONFIG_MANAGER.save();
    }
}