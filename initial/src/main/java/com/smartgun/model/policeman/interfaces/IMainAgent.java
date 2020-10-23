package policeman.interfaces;

import java.awt.*;
import java.util.List;
import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.Patrol;

public interface IMainAgent {
    List<SmartWatch> smartWatchesWhichNeedsBackup();
    Patrol choosePatrolToIntervence();
    Point coordinatesToSendAmbulance();
}
