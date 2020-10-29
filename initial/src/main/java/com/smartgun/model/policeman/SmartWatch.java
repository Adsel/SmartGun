package com.smartgun.model.policeman;

import java.awt.Point;

public class SmartWatch {
    private Point coordinates;
    private Navigation navigation;

    public SmartWatch(Point coordinates, Navigation navigation) {
        this.coordinates = coordinates;
        this.navigation = navigation;
    }

    /**** TODO *****/
    // READS GEOLOCALIZATION AND 'SENDS' TO X
    // TODO: periodically sends data to X
    public Point sendGeolocalization() {
        return coordinates;
    }
    //    public void setConnector(X connector) {
    //        this.connector = connector;
    //    }
    /**** /TODO *****/

    // GETS INTERVENTION DATA FROM MC (MC->X->Patrol->SmartWatch)
    public void getInterventionData() {

    }

    // GETS DATA ABOUT FIRES FROM Gun (GN)
    public boolean getFired() {
        return true;
    }

    // 'SENDS' DATA TO Navigation (NV)
    public void sendData() {
        this.navigation.getDataFromSmartWatch();
    }

}