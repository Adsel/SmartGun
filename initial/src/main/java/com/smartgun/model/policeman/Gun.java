package com.smartgun.model.policeman;

import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.interfaces.IGun;

public class Gun implements IGun {
    private SmartWatch smartWatch;

    public Gun(SmartWatch smartWatch) {
        this.smartWatch = smartWatch;
    }

    // FIRES AND SENDS INFO TO SMARTWATCH (SV)
    @Override
    public void fire() {
        this.smartWatch.getFired();
    }
}
