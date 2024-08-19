package com.oscimate.jebbed.config;

import com.oscimate.jebbed.Main;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class Slider extends SliderWidget {
    public Slider(int x, int y, int width, int height, Text text, float value) {
        super(x, y, width, height, text, (double) value /100);

        this.updateMessage();
        this.applyValue();
    }

    public float getSliderValue() {
        return Math.round(this.value*100);
    }

    @Override
    protected void updateMessage() {
        Main.CONFIG_MANAGER.setJebbedSpeed(getSliderValue());
    }

    @Override
    protected void applyValue() {
        float sliderValue = getSliderValue();
        Text text = sliderValue == 0  ? Text.translatable(Main.MODID+".config.jebSpeed.min") :
                sliderValue == 50 ? Text.translatable(Main.MODID+".config.jebSpeed.middle") :
                        sliderValue == 100 ? Text.translatable(Main.MODID+".config.jebSpeed.max") :
                                Text.translatable("jebbed.config.title.speed").append(": " + Math.round(sliderValue));
        this.setMessage(text);
    }
}
