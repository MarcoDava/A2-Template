package ca.mcmaster.se2aa4.island.team012.DroneComponents;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.EmergencyPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.MapArea;
import java.util.logging.Logger;


public class DroneRetrieval {
    private boolean rangeDanger;
    private boolean batteryDanger;

    private Drone drone;
    private MapArea mapArea;
    private DronePosition dronePosition;
    
    private final int RANGE_BORDER = 1;
    
    public DroneRetrieval(Drone drone, MapArea mapArea) {
        this.drone = drone;
        this.mapArea = mapArea;
    }

    public void setRangeDanger(int range) {
        if (range <= RANGE_BORDER && mapArea.getHeading() == mapArea.getPrevEchoDirection()) {
            this.rangeDanger = true;
            logger.info("Approaching OUT OF RANGE area changing direction");
        } else {
            this.rangeDanger = false;
        }
    }

    public void handleDanger(){

    }

    public boolean setBatteryDanger(){
        if(dronePosition.getValue()){
            return false;
        }
        return true;
    }
}
