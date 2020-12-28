package com.smartgun.model.policeman;

import java.awt.Point;
import com.smartgun.model.policeman.interfaces.ISmartWatch;

public class SmartWatch implements ISmartWatch {
    private Point coordinates;
    private Navigation navigation;

    public SmartWatch(Point coordinates, Navigation navigation) {
        this.coordinates = coordinates;
        this.navigation = navigation;
    }

    /**** TODO *****/
    // READS GEOLOCALIZATION AND 'SENDS' TO X
    // TODO: periodically sends data to X
    @Override
    public Point sendGeolocalization() {
        return coordinates;
    }
    //    public void setConnector(X connector) {
    //        this.connector = connector;
    //    }
    /**** /TODO *****/

    // GETS INTERVENTION DATA FROM MC (MC->X->Patrol->SmartWatch)
    @Override
    public void getInterventionData() {

    }

    // GETS DATA ABOUT FIRES FROM Gun (GN)
    @Override
    public boolean getFired() {
        return true;
    }

    // 'SENDS' DATA TO Navigation (NV)
    @Override
    public void sendData() {
        this.navigation.getDataFromSmartWatch();
    }

    public Point getCoordinates(){return this.coordinates;}

}
