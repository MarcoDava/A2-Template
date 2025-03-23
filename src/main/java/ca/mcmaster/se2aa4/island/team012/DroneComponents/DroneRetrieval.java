package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import java.lang.Math;

public class DroneRetrieval {

    private static final Logger logger = LogManager.getLogger();
    private boolean rangeDanger;
    private boolean batteryDanger;
    private Heading heading;
    private FlightSystem flightSystem;
    private MapArea mapArea;
    private DronePosition dronePosition;
    private Battery battery;
    private final int MIN_ROW = 2;
    private final int MIN_COL = 2;

    

    private final int RANGE_BORDER = 1;

    public DroneRetrieval(MapArea mapArea, Battery battery,DronePosition dronePosition,Control controller,Heading heading) {
        this.mapArea = mapArea;
        this.battery=battery;
        this.dronePosition = dronePosition;
        this.heading = heading;
        flightSystem = new FlightSystem(dronePosition, controller);
    }

    public DangerType dangerAssesment(){
        if(rangeDanger()){
            return DangerType.OUTOFRANGE;
        }
        
        else if(batteryDanger()){
            return DangerType.BATTERYLOW;
        }
        return DangerType.NEUTRAL;
    }

    public void handleDanger(JSONObject decision, DangerType dangerType){
        if(dangerType==DangerType.OUTOFRANGE){
            logger.info("Drone is going out of range, Taking control to avoid going MIA");
            handleRangeDanger(decision);
        }
        else if(dangerType==DangerType.BATTERYLOW){
            logger.info("Battery is low, stopping program now");
            flightSystem.stop(decision);
        }
    }

    private void handleRangeDanger(JSONObject decision){
        if(dronePosition.getRow()==MIN_ROW && heading.compareHeading(Direction.N)){
            if(dronePosition.getCol()>mapArea.getCols()/2){
                flightSystem.turnLeft(heading, decision);
            }
            else{
                flightSystem.turnRight(heading, decision);
            }
        }
        else if(dronePosition.getRow()==mapArea.getRows()-MIN_ROW && heading.compareHeading(Direction.S)){
            if(dronePosition.getCol()>mapArea.getCols()/2){
                flightSystem.turnRight(heading, decision);
            }
            else{
                flightSystem.turnLeft(heading, decision);
            }
        }
        else if(dronePosition.getCol()==MIN_COL && heading.compareHeading(Direction.W)){
            if(dronePosition.getRow()>mapArea.getRows()/2){
                flightSystem.turnRight(heading, decision);
            }
            else{
                flightSystem.turnLeft(heading, decision);
            }
        }
        else if(dronePosition.getCol()==mapArea.getCols()-MIN_COL && heading.compareHeading(Direction.E)){
            if(dronePosition.getRow()>mapArea.getRows()/2){
                flightSystem.turnLeft(heading, decision);
            }
            else{
                flightSystem.turnRight(heading, decision);
            }
        }
    }


    private boolean rangeDanger() { // if going to go out of bounds, turn right or left based on where there is the most open map
        if(dronePosition.getRow()==MIN_ROW && heading.compareHeading(Direction.N)){
            return true;
        }
        else if(dronePosition.getRow()==mapArea.getRows()-MIN_ROW && heading.compareHeading(Direction.S)){
            return true;
        }
        else if(dronePosition.getCol()==MIN_COL && heading.compareHeading(Direction.W)){
            return true;
        }
        else if(dronePosition.getCol()==mapArea.getCols()-MIN_COL && heading.compareHeading(Direction.E)){
            return true;
        }
        return false;
    }

    public boolean batteryDanger() {  // run  out of battery --> stop the program
        logger.info(Math.sqrt(dronePosition.getRow()*dronePosition.getRow()+dronePosition.getCol()*dronePosition.getCol()));
        if (Math.sqrt(dronePosition.getRow()*dronePosition.getRow()+dronePosition.getCol()*dronePosition.getCol())>battery.getBattery()) {
            return true;
        }
        return false;
    }
}
