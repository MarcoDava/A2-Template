package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

public class DroneRetrieval {

    private static final Logger logger = LogManager.getLogger();
    private boolean rangeDanger;
    private boolean batteryDanger;
    private Heading heading;
    private FlightSystem flightSystem;
    private Drone drone;
    private MapArea mapArea;
    private DronePosition dronePosition;
    private Battery battery;

   

    private final int RANGE_BORDER = 1;

    public DroneRetrieval(Drone drone, MapArea mapArea, Battery battery,DronePosition dronePosition) {
        this.drone = drone;
        this.mapArea = mapArea;
        this.battery=battery;
        this.dronePosition = dronePosition;
    }

    public DangerType dangerAssesment(){
        if(rangeDanger()!=Direction.NEUTRAL){
            return DangerType.OUTOFRANGE;
        }
        else if(batteryDanger()){
            return DangerType.BATTERYLOW;
        }
        return DangerType.NEUTRAL;
    }

    public void handleDanger(JSONObject decision, DangerType dangerType) {
    }


    private Direction rangeDanger() {
        if (dronePosition.getRow()==2
        ||dronePosition.getRow()==mapArea.getRows()-2) {
            if(dronePosition.getRow()>mapArea.getRows()/2){
                return Direction.S;
            }
            else{
                return Direction.N;
            } 
        }else if(dronePosition.getCol()==2||dronePosition.getCol()==mapArea.getCols()-2){
            if(dronePosition.getCol()>mapArea.getCols()/2){
                return Direction.W;
            }
            else{
                return Direction.E;
            }
        }
        return Direction.NEUTRAL;
    }

    public boolean batteryDanger() {
        //this is currently hard coded, need a better way to determine the danger
        if ((int)battery.getValue()<20) {
            return false;
        }
        return true;
    }
}
