package com.smartgun.model.policeman.interfaces;

import java.awt.Point;

public interface ISmartWatch {
    public Point sendGeolocalization();
    public void getInterventionData();
    public boolean getFired();
    public void sendData();
}
