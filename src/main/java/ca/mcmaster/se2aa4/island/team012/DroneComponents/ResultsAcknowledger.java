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

/*
 * This class is responsible for acknowledging the results from the server
 */
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

    /*
     * This is the constructor for the ResultsAcknowledger class
     * 
     * @param battery the battery of the drone
     * @param mapArea the mapArea of the drone
     * @param drone the drone
     * @param dronePosition the position of the drone
     * @param creekPosition the position of the creek
     * @param emergencyPosition the position of the emergency site
     * @param droneBrain the brain of the drone
     */
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

    /*
     * This function will return the range of the drone
     * 
     * @return the range of the drone
     */
    public int returnRange(){
        return range;
    }

    /*
     * This function will extract the battery from the response from the server
     * 
     * @param response the response from the server
     * @return true if battery is found, false otherwise
     */
    public void extractBattery(JSONObject response){
        battery.useBattery(response.getInt("cost"));
    }

    /*
     * This function will extract the ground from the response from the server
     * 
     * @param extraInfo the JSONObject that contains the ground
     * @return true if ground is found, false otherwise
     */
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

    /*
     * This function will extract the sites from the response from the server
     * 
     * @param extraInfo the JSONObject that contains the sites
     * @return true if sites are found, false otherwise
     */
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
    /*
     * This function will extract the creeks from the response from the server
     * 
     * @param extraInfo the JSONObject that contains the creeks
     * @return true if creeks are found, false otherwise
     */
    private boolean extractCreeks(JSONObject extraInfo){
        JSONArray creek = extraInfo.getJSONArray("creeks");
        logger.info(creek.length());
        if(creek.length()==0){
            logger.info("returning false");
            return false;
        }
        return true;
    }
    /*
     * This function will update the values of the drone based on the response from the server
     * 
     * @param s the response from the server
     */
    public void updateValues(String s){
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info(extraInfo);
        extractBattery(response);
        if(controller.compareAction(Command.ECHO)){
            range=extractRange(extraInfo);
            groundFound=extractGround(extraInfo);
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
            logger.info("Didnt scan or echo");
        }
        
        switch (droneBrain.getStatus()) {
                case FIND_LENGTH_STATE:
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

    /*
     * This function will handle the find length state
     */
    private void findLengthStateHandler(){
        logger.info("Got here 26");
        mapArea.setMapX(range);
        droneBrain.setStatus(Status.FIND_WIDTH_STATE);
    }

    /*
     * This function will handle the find width state
     */
    private void findWidthStateHandler(){
        mapArea.setMapY(range);
        dronePosition=new DronePosition(1,1);
        droneBrain.setStatus(Status.SPIRAL_SEARCH_STATE);
    }

    /*
     * This function will handle the spiral search state
     */
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