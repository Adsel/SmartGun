package com.smartgun.model.incident;

import java.awt.Point;
import java.util.Random;

public class Incident {
    private Point incidentPoint;
    private boolean willBeFire;

    public Incident() {
        // TODO: random position
        this.incidentPoint = new Point(1, 1);
        Random rand = new Random();
        int n = rand.nextInt(2);
        // p(A) = 0,5; A - probability of fired incident
        // p(b) = 0,5; B - probability of normal incident
        if (n == 1) {
            this.willBeFire = false;
        } else {
            this.willBeFire = true;
        }
    }

    public boolean isFiredIncident() {
        return this.willBeFire;
    }
}