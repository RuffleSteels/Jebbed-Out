package com.oscimate.jebbed.config;

import com.oscimate.jebbed.Main;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;


public class ConfigScreen extends Screen {
    public ConfigScreen() {
        super(Text.translatable("options.videoTitle"));
    }

    public ConfigScreen(Screen screen) {
        super(Text.translatable("options.videoTitle"));
    }

    @Override
    protected void init() {
        Slider customTimeSliderWidget = new Slider(this.width / 2 - 75, height/2 + 5, 150, 20, Text.translatable("jebbed.config.title.speed"), Main.CONFIG_MANAGER.getJebbedSpeed());
        this.addDrawableChild(customTimeSliderWidget);
        this.addDrawableChild(new ButtonWidget.Builder(ScreenTexts.DONE, button -> close()).dimensions(width / 2 - 100, height/2 + 30, 200, 20).build());
        super.init();
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, "Rainbow Speed", this.width / 2 - textRenderer.getWidth("Rainbow Speed") / 2, height/2 - 5 - textRenderer.fontHeight, 0xFFFFFF, false);
    }
    @Override
    public void close() {
       super.close();
       Main.CONFIG_MANAGER.save();
    }
}