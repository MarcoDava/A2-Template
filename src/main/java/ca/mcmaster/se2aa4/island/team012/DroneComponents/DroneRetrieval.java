package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import java.util.logging.Logger;

public class DroneRetrieval {

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
        if(outOfRange()){

        }
        else if(batteryDanger()){

        }
        return true;
    }

    public void handleDanger() {

    }


    private void rangeDanger(int range) {
        if (range <= RANGE_BORDER && heading.getValue() == mapArea.getPrevEchoDirection()) {
            this.rangeDanger = true;
            logger.info("Approaching OUT OF RANGE area changing direction");
        } else {
            this.rangeDanger = false;
        }
    }

    public boolean batteryDanger() {
        if (dronePosition.getValue()) {
            return false;
        }
        return true;
    }
}
