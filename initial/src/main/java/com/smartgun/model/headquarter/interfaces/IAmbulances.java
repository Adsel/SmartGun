package com.smartgun.model.headquarter.interfaces;

import com.smartgun.model.headquarter.Ambulance;

import java.awt.*;

public interface IAmbulances {
    void sendAmbulanceTo(Point point, int ambulanceId);
    Point whereIsAmbulance(int ambulanceId);
    void addAmbulance(Ambulance ambulance);
    void removeAmbulance(int id);
}
