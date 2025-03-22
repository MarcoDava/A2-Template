package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.CreekPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.EmergencyPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.States.Status;

public class ResultsAcknowledger{
    private Battery battery;
    private MapArea mapArea;
    private Drone drone;
    private SimpleDroneBrain droneBrain;
    private CreekPosition creekPosition;
    private EmergencyPosition emergencyPosition;
    private DronePosition dronePosition;
    private boolean groundFound;
    private int range;
    private final Logger logger = LogManager.getLogger();

    public ResultsAcknowledger(Battery battery, MapArea mapArea, Drone drone,DronePosition dronePosition,CreekPosition creekPosition,EmergencyPosition emergencyPosition, SimpleDroneBrain droneBrain){
        this.battery = battery;
        this.mapArea = mapArea;
        this.drone = drone;
        this.dronePosition = dronePosition;
        this.creekPosition = creekPosition;
        this.emergencyPosition = emergencyPosition;
        this.droneBrain=droneBrain;
    }

    public int returnRange(){
        return range;
    }

    public void extractBattery(JSONObject response){
        battery.useBattery(response.getInt("cost"));
    }

    public boolean extractGround(JSONObject extraInfo){
        JSONObject Ground = extraInfo.getJSONObject("found");
        if(Ground.toString().equals("GROUND")){
            return true;
        }
        return false;
    }

    public int extractRange(JSONObject extraInfo){
        int range = extraInfo.getInt("range");
        return range;
    }

    private boolean extractSites(JSONObject extraInfo){
        JSONObject creek = extraInfo.getJSONObject("creeks");
        if(creek.toString().isEmpty()){
            return false;
        }
        return true;
        
    }

    private boolean extractCreeks(JSONObject extraInfo){
        JSONObject emergencySite = extraInfo.getJSONObject("sites");
        if(emergencySite.toString().isEmpty()){
            return false;
        }
        return true;
    }

    public void updateValues(String s){
        logger.info("Got here 21");
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras");

        extractBattery(response);
        logger.info("Got here 22");
        if(droneBrain.getCommand()==Command.ECHO){
            range=extractRange(extraInfo);
            logger.info("Got here 23");
            groundFound=extractGround(extraInfo);
            logger.info("Got here 24");
        }
        else if (droneBrain.getCommand()==Command.SCAN||droneBrain.getCommand()==Command.SCAN_AROUND){
            if(extractCreeks(extraInfo)){
                creekPosition.addCreekPosition(dronePosition.getDronePosition());
            }
            if(extractSites(extraInfo)){
                emergencyPosition.setEmergencyPosition(dronePosition.getDronePosition());
            }
        }
        else{
            logger.info("Got here 24");

        }
        switch (droneBrain.getStatus()) {
                case FIND_LENGTH_STATE:
                    findLengthStateHandler();
                case FIND_WIDTH_STATE:
                    findWidthStateHandler();
                default:
                    break;
            }
    }

    private void findLengthStateHandler(){
        mapArea.setMapX(range);
    }

    private void findWidthStateHandler(){
        mapArea.setMapX(range);
    }




    //need a function that gets the mapX and mapY to determine the size of the map

}