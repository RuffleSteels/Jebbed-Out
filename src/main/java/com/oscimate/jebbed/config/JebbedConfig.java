package com.oscimate.jebbed.config;

import com.oscimate.jebbed.Main;

public class JebbedConfig {
    public float  getJebbedSpeed() {
        return jebbedSpeed;
    }

    public void setJebbedSpeed(float  jebbedSpeed) {
        this.jebbedSpeed = jebbedSpeed;
    }

    private float  jebbedSpeed = Main.CONFIG_MANAGER.getJebbedSpeed();
}
