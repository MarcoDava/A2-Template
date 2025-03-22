package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
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
    private boolean creekFound;
    private boolean siteFound;
    private int range;
    private final Logger logger = LogManager.getLogger();
    private Control controller;

    public ResultsAcknowledger(Battery battery, MapArea mapArea, Drone drone,DronePosition dronePosition,CreekPosition creekPosition,EmergencyPosition emergencyPosition, SimpleDroneBrain droneBrain, Control controller){
        this.battery = battery;
        this.mapArea = mapArea;
        this.drone = drone;
        this.dronePosition = dronePosition;
        this.creekPosition = creekPosition;
        this.emergencyPosition = emergencyPosition;
        this.droneBrain=droneBrain;
        this.controller=controller;
        groundFound=false;
        creekFound=false;
        siteFound=false;
    }

    public int returnRange(){
        return range;
    }

    public void extractBattery(JSONObject response){
        battery.useBattery(response.getInt("cost"));
    }

    public boolean extractGround(JSONObject extraInfo){
        logger.info("21");
        String Ground = extraInfo.getString("found");
        logger.info("22");
        if(Ground.equals("GROUND")){
            logger.info("23");
            return true;
        }
        return false;
    }

    public int extractRange(JSONObject extraInfo){
        int range = extraInfo.getInt("range");
        return range;
    }

    private boolean extractSites(JSONObject extraInfo){
        logger.info("hello");
        JSONArray emergencySite = extraInfo.getJSONArray("sites");
        logger.info(emergencySite);
        if(emergencySite.length()==0){
            logger.info("returning false");
            return false;
        }
        return false;
    }

    private boolean extractCreeks(JSONObject extraInfo){
        JSONArray creek = extraInfo.getJSONArray("creeks");
        logger.info(creek.length());
        if(creek.length()==0){
            logger.info("returning false");
            return false;
        }
        return true;
    }

    public void updateValues(String s){
        logger.info("Got here 21");
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info(extraInfo);
        
        extractBattery(response);
        logger.info("Got here 22");
        logger.info(controller.getCommand()==Command.ECHO||droneBrain.getCommand()==Command.ECHO_AROUND);
        if(controller.compareAction(Command.ECHO)){
            range=extractRange(extraInfo);
            logger.info("Got here 23");
            logger.info(range);
            groundFound=extractGround(extraInfo);
            logger.info("Got here 24");
        }
        else if (controller.compareAction(Command.SCAN)){
            logger.info("checking for creeks");
            if(extractCreeks(extraInfo)){
                creekFound=true;
                creekPosition.setCreekPosition(dronePosition.getDronePosition());
            }
            logger.info("checking for sites");
            if(extractSites(extraInfo)){
                siteFound=true;
                emergencyPosition.setEmergencyPosition(dronePosition.getDronePosition());
            }
        }
        else{
            logger.info("Got here 24");

        }
        
        switch (droneBrain.getStatus()) {
                case FIND_LENGTH_STATE:
                    logger.info("Got here 25");
                    findLengthStateHandler();
                case FIND_WIDTH_STATE:
                    findWidthStateHandler();
                case SPIRAL_SEARCH_STATE:
                    spiralSearchStateHandler();
                    break;
                default:
                    break;
            }
    }

    private void findLengthStateHandler(){
        logger.info("Got here 26");
        mapArea.setMapX(range);
        logger.info("Got here 27");
        droneBrain.setStatus(Status.FIND_WIDTH_STATE);
    }

    private void findWidthStateHandler(){
        mapArea.setMapY(range);
        dronePosition=new DronePosition(1,1);
        droneBrain.setStatus(Status.SPIRAL_SEARCH_STATE);
    }

    private void spiralSearchStateHandler(){
        if(creekFound&&siteFound){
            droneBrain.setStatus(Status.END_SEARCH_STATE);
        }
        else{
            droneBrain.setStatus(Status.SPIRAL_SEARCH_STATE);
        }
    }




    //need a function that gets the mapX and mapY to determine the size of the map

}