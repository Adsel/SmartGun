package com.smartgun.model.headquarter;

import com.smartgun.model.headquarter.interfaces.IAmbulances;
import com.smartgun.model.policeman.Patrol;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/*Klasa będzie przechowaywała listę wszytkich ambulansów. Domyślnie ustawia lokalizację na 0,0 (centrum ambulasów)
* oraz domyślnie ambulans jest pusty.
* Można dodać do niego patrol oraz usunąć (w razie awari ambulansu patrol musi zmienić pojazd)
* Ponadto jeśli w czasie akcji ambulas zostanie uszkodzony, można go usunąć z listy
*
* Trzeba by się zastanowić jeszcze nad statusem ambulansu (w akcji, wolny, zepsuty ??)
* */

public class Ambulances implements IAmbulances {
    private List<Ambulance> ambulances;
    private MainAgent mainAgent;
    private Point point = new Point(0,0);//domyślna lokalizacja ambulasów

    public Ambulances() {
        initAmbulances();
    }

    void initAmbulances(){
        ambulances = new ArrayList<>();
        int id = 0;
        for (int i=0; i<mainAgent.getPolicePatrols().size(); i++){
            ambulances.add(new Ambulance(id,point));
            id++;
        }
    }

    void addPatrolToAmbulance(Patrol patrol, int ambulanceId){
        getAmbulanceById(ambulanceId).setPatrol(patrol);
    }

    @Override
    public void sendAmbulanceTo(Point point, int ambulanceId) {
        getAmbulanceById(ambulanceId).setPoint(point);
    }

    @Override
    public Point whereIsAmbulance(int ambulanceId) {
        return getAmbulanceById(ambulanceId).getPoint();
    }

    @Override
    public void addAmbulance(Ambulance ambulance) {
        ambulances.add(ambulance);
    }

    @Override
    public void removeAmbulance(int id) {
        ambulances.remove(getAmbulanceById(id));
    }

    Ambulance getAmbulanceById(int id){
        return ambulances.stream().filter(ambulance -> ambulance.getId() == id).findFirst().orElse(null);
        //optionalDouble
    }
}
