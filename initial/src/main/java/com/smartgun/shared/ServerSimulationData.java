package com.smartgun.shared;

import com.smartgun.model.headquarter.HeadQuarter;
import com.smartgun.model.headquarter.MainAgent;
import com.smartgun.model.headquarter.interfaces.IMainAgent;
import com.smartgun.model.map.Map;
import com.smartgun.model.map.Sector;
import com.smartgun.model.policeman.Patrol;

import java.awt.*;
import java.util.List;

public class ServerSimulationData {
    private HeadQuarter headQuarter;

    public ServerSimulationData() {}

    public ServerSimulationData(
            List<Sector> sectors, Integer[] patrolsPerDistrict,
            Map map,
            double patrolRadius, Integer ambulancesCount, Integer patrolCount
    ) {
        IMainAgent mainAgent = new MainAgent();
        Point ambulanceBasePosition = new Point(0, 0);
        headQuarter = new HeadQuarter(
                sectors, ambulancesCount,
                patrolsPerDistrict, map,
                mainAgent,
                ambulanceBasePosition,
                patrolRadius, patrolCount
        );
        headQuarter.generatePatrols(patrolsPerDistrict, patrolCount);
    }

    public List<Patrol> getPatrols() { return headQuarter.getPatrols(); }
}
