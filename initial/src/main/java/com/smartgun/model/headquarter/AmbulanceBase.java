package com.smartgun.model.headquarter;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import com.smartgun.model.headquarter.interfaces.IAmbulances;
import com.smartgun.model.map.Map;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.simulation.InitialData;

/*Klasa będzie przechowaywała listę wszytkich ambulansów. Domyślnie ustawia lokalizację na 0,0 (centrum ambulasów)
* oraz domyślnie ambulans jest pusty.
* Można dodać do niego patrol oraz usunąć (w razie awari ambulansu patrol musi zmienić pojazd)
* Ponadto jeśli w czasie akcji ambulas zostanie uszkodzony, można go usunąć z listy
*
* Trzeba by się zastanowić jeszcze nad statusem ambulansu (w akcji, wolny, zepsuty ??)
* */

public class AmbulanceBase {
    private List<Ambulance> ambulances;
    private MainAgent mainAgent;
    private Map map;
    private InitialData initialData;


    public AmbulanceBase() {
        initAmbulances();
    }

    void initAmbulances(){
        ambulances = new ArrayList<>();
        int id = 0;
        Point point = map.recieveHospitalList().get(0);
        for (int i=0; i<initialData.getAmbulancesCount(); i++){
            ambulances.add(new Ambulance(id, point, point, map));
            id++;
        }
    }

    public Ambulance chooseAmbulanceToIntervention(){
        return ambulances.stream().
                filter(ambulance -> ambulance.getState() == Ambulance.State.WAITING)
                .findFirst()
                .orElse(null);
    }
    public void sendAmbulanceIntervention(Point point, Ambulance ambulance) {
        ambulance.goToIntervention(point);
    }

    public Point whereIsAmbulance(int ambulanceId) {
        return getAmbulanceById(ambulanceId).getActualPosition();
    }

    public void addAmbulance(Ambulance ambulance) {
        ambulances.add(ambulance);
    }

    public void removeAmbulance(int id) {
        ambulances.remove(getAmbulanceById(id));
    }

    Ambulance getAmbulanceById(int id){
        return ambulances.stream().filter(ambulance -> ambulance.getId() == id).findFirst().orElse(null);
        //optionalDouble
    }
}
