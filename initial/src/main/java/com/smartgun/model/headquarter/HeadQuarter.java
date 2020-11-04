package com.smartgun.model.headquarter;

import com.smartgun.model.headquarter.interfaces.IHeadQuarter;

import java.awt.Point;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.policeman.Navigation;
import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.Policeman;
import com.smartgun.model.headquarter.MainAgent;
import com.smartgun.model.headquarter.MonitoringAgent;

public class HeadQuarter implements IHeadQuarter {

    private double patrolRadius;    //promień patrolowanego obszaru jeśli uniwersalny to przechowywany tutaj, jeśli nie to w "Patrol"
    private Map<Integer, Integer>  patrolsPerDistrict;  //ilość patroli na danej dzielnicy
    private Map<Integer, Integer> ambulancesPerDistrict;   //ilość ambulansów
    private MainAgent mainAgent;
    private Point ambulanceBasePosition;
    private MonitoringAgent monitoringAgent;
    private List<Patrol> patrols;

    // TEMPORARY
    public HeadQuarter(Integer countOfPatrols, MonitoringAgent monitoringAgent) {
        this.ambulanceBasePosition = new Point(1, 1);
        this.monitoringAgent = monitoringAgent;
        this.patrols = generatePatrols(countOfPatrols);
    }

    public HeadQuarter(double patrolRadius, Map<Integer, Integer> patrolsPerDistrict, Map<Integer,
            Integer> ambulancesPerDistrict, MainAgent mainAgent, Point ambulanceBasePosition) {
        this.patrolRadius = patrolRadius;
        this.patrolsPerDistrict = patrolsPerDistrict;
        this.ambulancesPerDistrict = ambulancesPerDistrict;
        this.mainAgent = mainAgent;
        this.ambulanceBasePosition = ambulanceBasePosition;
    }

    // TEMPORARY
    public List<Patrol> getPatrols() {
        return this.patrols;
    }

    @Override
    public void changePatrolRadius(int patrolId) {

    }

    @Override
    public void changePatrolPerDistrict(int districtId, int patrols) {
        this.patrolsPerDistrict.put(districtId,patrols);
    }

    @Override
    public void changeAmbulancePerDistrict(int districtId, int ambulances) {
        this.ambulancesPerDistrict.put(districtId, ambulances);
    }

    @Override
    public void sendPatrolTo(Point point) {
        mainAgent.coordinatesToSendAmbulance();
    }

    @Override
    public List<Patrol> generatePatrols(Integer countOfPatrols) {
        List<Patrol> generatedPatrols = new ArrayList<>();
        if (countOfPatrols != null) {
            for (int i = 0; i < countOfPatrols; i++) {
                Navigation navigation = new Navigation();
                SmartWatch smartWatch = new SmartWatch(
                        new Point(
                                (int)this.ambulanceBasePosition.getX(),
                                (int)this.ambulanceBasePosition.getY()
                        ),
                        navigation
                );

                this.monitoringAgent.addSmartWatch(smartWatch);

                generatedPatrols.add(
                        new Patrol(
                                0,
                                smartWatch,
                                navigation,
                                new Policeman(true),
                                new Policeman(false)
                                // TODO WHEN X WILL BE ADDED: X connector;
                        )
                );
            }
        }

        return generatedPatrols;
    }
}
