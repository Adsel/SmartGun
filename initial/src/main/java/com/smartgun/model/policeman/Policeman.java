package com.smartgun.model.policeman;

import com.smartgun.model.policeman.interfaces.IPoliceman;

public class Policeman implements IPoliceman {
    private boolean hasSmartWatch;

    public Policeman(boolean hasSmartWatch) {
        this.hasSmartWatch = hasSmartWatch;
    }
}
