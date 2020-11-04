package com.smartgun.model.headquarter.interfaces;

import java.awt.Point;
import com.smartgun.model.headquarter.Ambulance;

public interface IAmbulances {
    void sendAmbulanceTo(Point point, int ambulanceId);
    Point whereIsAmbulance(int ambulanceId);
    void addAmbulance(Ambulance ambulance);
    void removeAmbulance(int id);
}
