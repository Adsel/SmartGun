package com.smartgun.model.policeman;

import com.smartgun.model.policeman.SmartWatch;

public class Gun {
    private SmartWatch smartWatch;

    public Gun(SmartWatch smartWatch) {
        this.smartWatch = smartWatch;
    }

    // FIRES AND SENDS INFO TO SMARTWATCH (SV)
    public void fire() {
        this.smartWatch.getFired();
    }
}