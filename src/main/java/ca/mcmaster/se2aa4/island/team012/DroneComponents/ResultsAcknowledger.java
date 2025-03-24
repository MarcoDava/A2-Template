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
        logger.info("Battery level is: "+battery.getBattery());
    }

    /*
     * This function will extract the ground from the response from the server
     * 
     * @param extraInfo the JSONObject that contains the ground
     * @return true if ground is found, false otherwise
     */
    public boolean extractGround(JSONObject extraInfo){
        String Ground = extraInfo.getString("found");
        if(Ground.equals("GROUND")){
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
        JSONArray emergencySite = extraInfo.getJSONArray("sites");
        if(emergencySite.length()==0){
            logger.info("returning false, no site found");
            return false;
        }
        logger.info("site found, returning true");
        emergencyPosition.setEmergencyPosition(dronePosition.getRow(),dronePosition.getCol(),emergencySite.getString(0));
        return true;
    }
    /*
     * This function will extract the creeks from the response from the server
     * 
     * @param extraInfo the JSONObject that contains the creeks
     * @return true if creeks are found, false otherwise
     */
    private boolean extractCreeks(JSONObject extraInfo){
        JSONArray creek = extraInfo.getJSONArray("creeks");
        if(creek.length()==0){
            logger.info("returning false, no creek found");
            return false;
        }
        logger.info("creek found, returning true");
        creekPosition.setCreekPosition(dronePosition.getRow(), dronePosition.getCol(), creek.getString(0));
        return true;
    }
    /*
     * This function will update the values of the drone based on the response from the server
     * 
     * @param s the response from the server
     */

    public void updateValues(String s) { // called in every loop by Drone.acknowledgeResults() for processing
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s))); // converts the response from engine to JSON
        JSONObject extraInfo = response.getJSONObject("extras"); // extras contains actual information (always with battery)
        logger.info(dronePosition.getRow()+" "+dronePosition.getCol());
        extractBattery(response); // extract and update battery
        logger.info(creekFound);
        logger.info(creekPosition.getCreekPosition()[0]+" "+creekPosition.getCreekPosition()[1]+" "+creekPosition.getCreekID());

        if(controller.compareAction(Command.ECHO)){ // if we just used radar
            range=extractRange(extraInfo); // how far away did we scan
            logger.info("SCAN was "+range+" units long");
            groundFound=extractGround(extraInfo); // check if radar found ground or went out of bounds
        }
        else if (controller.compareAction(Command.SCAN)){ // if we just used photoscanner
            logger.info("checking for creeks");
            if(extractCreeks(extraInfo)){ // check if we found any creeks
                creekFound=true;
                // save the position of the creek
                // also save the UID of the creek because we need to return it at the end (we stop exectution when creek found)
            }
            logger.info("checking for sites");
            if(extractSites(extraInfo)) { // check if we found any creeks
                siteFound=true;
            }
            logger.info("Emergency Site is at: "+emergencyPosition.getRow()+" "+emergencyPosition.getCol()+" "+emergencyPosition.getSiteID());
            logger.info("Site Found "+siteFound);
        }
        else{
            logger.info("Didnt scan or echo");
        }
        
        switch (droneBrain.getStatus()) {
                case FIND_LENGTH_STATE:
                    findLengthStateHandler();
                    break;

                case FIND_WIDTH_STATE:
                    findWidthStateHandler();
                    break;
                case APPROACH_ISLAND_STATE:
                    approachIslandStateHandler();
                    break;

                case SPIRAL_FROM_MIDDLE_STATE:
                    spiralFromMiddleStateHandler();
                    break;

                case SPIRAL_FROM_SITE_STATE:
                    spiralFromSiteHandler();
                    break;
                
                default:
                    break;
        }
    }

    /*
     * This function will handle the find length state
     */
    private void findLengthStateHandler(){
        mapArea.setMapX(range); // assumes drone is at the edge, so length is the returned rang
        droneBrain.setStatus(Status.FIND_WIDTH_STATE); // move to next state
    }


    /*
     * This function will handle the find width state
     */
    private void findWidthStateHandler(){
        mapArea.setMapY(range);
        dronePosition.setRow(1);
        dronePosition.setCol(1);//assumption for mvp is it starts at 1,1
        droneBrain.setStatus(Status.APPROACH_ISLAND_STATE);
    }

    private void approachIslandStateHandler(){
        logger.info("Middle is at:");
        logger.info(dronePosition.getRow()+" "+dronePosition.getCol());
        if(mapArea.getRows()/2==dronePosition.getRow() && mapArea.getCols()/2==dronePosition.getCol()){
            droneBrain.setStatus(Status.SPIRAL_FROM_MIDDLE_STATE);
        }
        else{
            droneBrain.setStatus(Status.APPROACH_ISLAND_STATE);
        }
        
    }

    /*
     * This function will handle the spiral search state
     */
    private void spiralFromMiddleStateHandler(){
        if(siteFound){ // if we have found the site
            creekFound = false;
            droneBrain.setStatus(Status.SPIRAL_FROM_SITE_STATE); // go to creek spiral
        }
        else{
            droneBrain.setStatus(Status.SPIRAL_FROM_MIDDLE_STATE); // otherwise stay in this state
        }
    }

    private void spiralFromSiteHandler(){
        if(creekFound){ // if we have found a creek and a site so far
            droneBrain.setStatus(Status.END_SEARCH_STATE); // then we end
        }
        else{
            droneBrain.setStatus(Status.SPIRAL_FROM_SITE_STATE); // otherwise stay in this state
        }
    }

    //need a function that gets the mapX and mapY to determine the size of the map

}