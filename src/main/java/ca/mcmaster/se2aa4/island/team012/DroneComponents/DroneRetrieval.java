package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

public class DroneRetrieval {

    private static final Logger logger = LogManager.getLogger();
    private boolean rangeDanger;
    private boolean batteryDanger;
    private Heading heading;

    private Drone drone;
    private MapArea mapArea;
    private DronePosition dronePosition;

   

    private final int RANGE_BORDER = 1;

    public DroneRetrieval(Drone drone, MapArea mapArea) {
        this.drone = drone;
        this.mapArea = mapArea;
    }

    public boolean dangerAssesment(){
        if(rangeDanger()){

        }
        else if(batteryDanger()){

        }
        return true;
    }

    public void handleDanger(JSONObject parameters, JSONObject decision) {

    }


    private boolean rangeDanger() {
        if (dronePosition.getDronePosition()[0]==2 || dronePosition.getDronePosition()[1]==2
        ||dronePosition.getDronePosition()[0]==mapArea.getRows()-2||dronePosition.getDronePosition()[1]==mapArea.getCols()-2) {
            logger.info("Approaching OUT OF RANGE area changing direction");
            return true;
        }
        return false;
    }

    public boolean batteryDanger() {
        if ((int)dronePosition.getValue()<20) {
            return false;
        }
        return true;
    }
}
