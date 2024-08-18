package com.oscimate.jebbed.config;

import com.oscimate.jebbed.Main;

public class JebbedConfig {
    public long getJebbedSpeed() {
        return jebbedSpeed;
    }

    public void setJebbedSpeed(long jebbedSpeed) {
        this.jebbedSpeed = jebbedSpeed;
    }

    private long jebbedSpeed = Main.CONFIG_MANAGER.getJebbedSpeed();
}
