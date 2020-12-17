package com.smartgun.model.headquarter;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import com.smartgun.model.headquarter.interfaces.IHeadQuarter;
import com.smartgun.model.headquarter.interfaces.IMainAgent;
import com.smartgun.model.map.Map;
import com.smartgun.model.map.Sector;
import com.smartgun.model.map.SectorType;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.policeman.Navigation;
import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.Policeman;
import com.smartgun.model.headquarter.MainAgent;
import com.smartgun.model.headquarter.MonitoringAgent;

public class HeadQuarter implements IHeadQuarter {
    private List<Patrol> patrols;
    private List<Sector> sectors;
    private Integer ambulancesCount;
    private Map map;

    private double patrolRadius;    //promień patrolowanego obszaru jeśli uniwersalny to przechowywany tutaj, jeśli nie to w "Patrol"
    private IMainAgent mainAgent;
    private Point ambulanceBasePosition;
    private MonitoringAgent monitoringAgent;

    public HeadQuarter(
            List<Sector> sectors, Integer ambulancesCount,
            Integer[] patrolsPerDistrict, Map map,
            IMainAgent mainAgent, Point ambulanceBasePosition,
            double patrolRadius, Integer patrolCount
    ) {
        this.monitoringAgent = new MonitoringAgent();
        this.sectors = sectors;
        this.ambulancesCount = ambulancesCount;
        this.map = map;
        this.mainAgent = mainAgent;
        this.ambulanceBasePosition = ambulanceBasePosition;
        this.patrolRadius = patrolRadius;
        this.generatePatrols(patrolsPerDistrict, patrolCount);
    }

    public List<Patrol> getPatrols() {
        return this.patrols;
    }

    // method which sends patrol
    @Override
    public void sendPatrolTo(Point point) {
//        mainAgent.coordinatesToSendAmbulance();
    }

    // TODO in next roadmap: rand position in this sector
    public Point generatePatrolPosition(Sector sector) {
        Integer randedX = (int) this.ambulanceBasePosition.getX();
        Integer randedY = (int) this.ambulanceBasePosition.getY();

        return new Point(randedX, randedY);
    }

    @Override
    public void generatePatrols(Integer[] patrolsPerDistrict, Integer patrolsCount) {
        // init Patrols and starting position
        this.patrols = new ArrayList<>();
        for (Sector sector: this.sectors) {
            Integer patrols = patrolsPerDistrict[SectorType.valueOf(sector.getSectorType().toString()).ordinal()];
            for (int i = 0; i < patrols; i++) {

                Navigation navigation = new Navigation();
                SmartWatch smartWatch = new SmartWatch(
                        this.generatePatrolPosition(sector),
                        navigation
                );

                monitoringAgent.addSmartWatch(smartWatch);

                addPatrol(smartWatch, navigation);
            }
        }

        Integer additionalPatrols = patrolsCount - this.patrols.size();
        for (int j = 0; j < additionalPatrols; j++) {
                Navigation navigation = new Navigation();
                SmartWatch smartWatch = new SmartWatch(
                        new Point(0, 0),
                        navigation
                );

                monitoringAgent.addSmartWatch(smartWatch);
                addPatrol(smartWatch, navigation);
        }
    }

    //wyposezenie nowych patroli
    @Override
    public void addPatrol(SmartWatch sw, Navigation nv) {
        this.patrols.add(
                new Patrol(
                        0,
                        sw,
                        nv,
                        new Policeman(true),
                        new Policeman(false)
                        // TODO WHEN X WILL BE ADDED: X connector;
                )
        );
    }


}
