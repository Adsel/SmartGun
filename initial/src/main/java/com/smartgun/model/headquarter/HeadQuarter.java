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

    @Override
    public void sendPatrolTo(Point point) {
        mainAgent.coordinatesToSendAmbulance();
    }

    // TODO in next roadmap: rand position in this sector
    public Point generatePatrolPosition(Sector sector, Map map) {
        int randedX = 0;
        int randedY = 0;

        int minX = sector.getLeftUpperCorner().x;
        int maxX = sector.getRightBottomCorner().x;
        int minY = sector.getLeftUpperCorner().y;
        int maxY = sector.getRightBottomCorner().y;

        while (map.isWall(randedX,randedY)){
            randedX = minX + (int) (Math.random() * (maxX - minX));
            randedY = minY + (int) (Math.random() * (maxY - minY));
        }

//        new Point(0,0), new Point(19,39)

//        Integer randedX = (int) this.ambulanceBasePosition.getX();
//        Integer randedY = (int) this.ambulanceBasePosition.getY();

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
                        this.generatePatrolPosition(sector, this.map),
                        navigation
                );

                monitoringAgent.addSmartWatch(smartWatch);

                addPatrol(smartWatch, navigation, sector);
            }
        }

        Integer additionalPatrols = patrolsCount - this.patrols.size();
        List<Sector> sectors = receiveSectorsForAdditionalPatrols(additionalPatrols);

        for (Sector sector : sectors) {
                Navigation navigation = new Navigation();
                SmartWatch smartWatch = new SmartWatch(
                        this.generatePatrolPosition(sector, this.map),
                        navigation
                );

                monitoringAgent.addSmartWatch(smartWatch);
                addPatrol(smartWatch, navigation, sector);
        }
    }

    private List<Sector> receiveSectorsForAdditionalPatrols(int numberOfPatrols){
        //TODO: improve refactor randomly draw sectors
        List<Sector> sectors = new ArrayList<>();
        for(int i = 0; i < numberOfPatrols; i++){
            sectors.add(this.sectors.get(0));
        }
        return sectors;
    }

    @Override
    public void addPatrol(SmartWatch sw, Navigation nv, Sector sector) {
        this.patrols.add(
                new Patrol(
                        0,
                        sw,
                        nv,
                        new Policeman(true),
                        new Policeman(false),
                        this.map,
                        sector
                        // TODO WHEN X WILL BE ADDED: X connector;
                )
        );
    }
}
