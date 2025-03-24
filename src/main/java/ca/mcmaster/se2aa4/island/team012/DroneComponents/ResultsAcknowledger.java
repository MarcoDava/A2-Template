package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.CreekPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.EmergencyPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.States.Status;


public class ResultsAcknowledger{
    private Heading heading;
    private Battery battery;
    private MapArea mapArea;
    private Control controller;
    private Drone drone;
    private SimpleDroneBrain droneBrain;
    private CreekPosition creekPosition;
    private EmergencyPosition emergencyPosition;
    private DronePosition dronePosition;
    private boolean groundFound;
    private boolean creekFound;
    private boolean siteFound;
    private boolean wasGroundFound;
    private boolean XFound;
    private int range;
    private int southDistance;
    private int eastDistance;
    private int northDistance;
    private int westDistance;
    private int actionCtr;
    private int dimensionsAligned;
    private final Logger logger = LogManager.getLogger();
    

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
    public ResultsAcknowledger(Battery battery, Heading heading, MapArea mapArea, Drone drone,DronePosition dronePosition,CreekPosition creekPosition,EmergencyPosition emergencyPosition, SimpleDroneBrain droneBrain, Control controller){
        this.battery = battery;
        this.heading = heading;
        this.mapArea = mapArea;
        this.drone = drone;
        this.dronePosition = dronePosition;
        this.creekPosition = creekPosition;
        this.emergencyPosition = emergencyPosition;
        this.droneBrain=droneBrain;
        this.controller=controller;

        southDistance=0;
        eastDistance=0;
        northDistance=0;
        westDistance=0;

        groundFound=false;
        creekFound=false;
        siteFound=false;
        XFound=false;

        wasGroundFound=false;
        actionCtr=0;
        dimensionsAligned=0;
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
        logger.info("Map Area is: "+mapArea.getRows()+" "+mapArea.getCols());
        extractBattery(response); // extract and update battery
        logger.info(creekPosition.getCreekPosition()[0]+" "+creekPosition.getCreekPosition()[1]+" "+creekPosition.getCreekID());
        if(controller.compareAction(Command.ECHO)){ // if we just used radar
            range=extractRange(extraInfo); // how far away did we scan
            logger.info("SCAN was "+range+" units long");
            groundFound=extractGround(extraInfo); // check if radar found ground or went out of bounds
            logger.info("Ground Found: "+groundFound);
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
                case LENGTH_ALIGN_STATE:
                    lengthStateHandler();
                    break;

                case FIND_LENGTH_STATE:
                    findLengthStateHandler();
                    break;

                case LEFT_TURN_STATE:
                    leftTurnStateHandler();
                    break;

                case RIGHT_TURN_STATE:
                    rightTurnStateHandler();

                case WIDTH_ALIGN_STATE:
                    widthStateHandler();
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
     * This function will handle the dimension align state
     * 
     * when actionCtr == 3, we have scanned forward and both sides.
     * 
     * if we have found no ground on 2 sides, then we have found the spot
     * to start searching for dimensions
     * 
     * if ground on either side, keep scanning until we find a spot with no ground
     */
    private void lengthStateHandler() {
        if(actionCtr % 3 == 2 && !wasGroundFound) { // if no ground found after scanning, we can move onto the next deimension
            dimensionsAligned++;
            if (dimensionsAligned == 2) { // we have aligned ourselves to find the dimensions of the map
                droneBrain.setStatus(Status.FIND_LENGTH_STATE);
            } else {
                // some kind of turn logic here-------------------------------------------------------
                droneBrain.setStatus(Status.LENGTH_ALIGN_STATE);
            }
        } else {
            if (actionCtr % 3 < 2 && groundFound) { // to check if there is ground while scanning
                wasGroundFound = true;
            }
            else if (actionCtr % 3 == 2) { // reset was ground found at the move state
                wasGroundFound = false;
            }
            droneBrain.setStatus(Status.LENGTH_ALIGN_STATE);
        }
        actionCtr++;
        logger.info("actionCtr: " + actionCtr);
    }


    /*
     * This function will handle the find length state
     */
    private void findLengthStateHandler(){
        if(heading.getLastScanDirection()==Direction.N){
            northDistance=range;
        }
        else if(heading.getLastScanDirection()==Direction.E){
            eastDistance=range;
        }
        else if(heading.getLastScanDirection()==Direction.S){
            southDistance=range;
        }
        else if(heading.getLastScanDirection()==Direction.W){
            westDistance=range;
        }
        if(northDistance!=0 &&  southDistance!=0){
            if(northDistance>southDistance && heading.getHeading() == Direction.E){
                droneBrain.setStatus(Status.LEFT_TURN_STATE);
            }
            else if (northDistance<southDistance && heading.getHeading() == Direction.E){
                droneBrain.setStatus(Status.RIGHT_TURN_STATE);
            }
            else if (northDistance>southDistance && heading.getHeading() == Direction.W){
                droneBrain.setStatus(Status.RIGHT_TURN_STATE);
            }
            else if (northDistance<southDistance && heading.getHeading() == Direction.W){
                 droneBrain.setStatus(Status.LEFT_TURN_STATE);
            }
            logger.info(northDistance+" "+southDistance);
            mapArea.setMapY(northDistance+southDistance+1);
            dronePosition.setRow(northDistance+1);
        }
        else if(eastDistance!=0 && westDistance!=0){
            if(eastDistance>westDistance && heading.getHeading() == Direction.N){
                droneBrain.setStatus(Status.RIGHT_TURN_STATE);
            }
            else if (eastDistance<westDistance && heading.getHeading() == Direction.N){
                droneBrain.setStatus(Status.LEFT_TURN_STATE);
            }
            else if (eastDistance>westDistance && heading.getHeading() == Direction.S){
                droneBrain.setStatus(Status.LEFT_TURN_STATE);
            }
            else if (eastDistance<westDistance && heading.getHeading() == Direction.S){
                 droneBrain.setStatus(Status.RIGHT_TURN_STATE);
            }
            logger.info(eastDistance+" "+westDistance);
            mapArea.setMapX(eastDistance+westDistance+1);
            dronePosition.setCol(eastDistance+1);
            XFound=true;
        }
        else{
            droneBrain.setStatus(Status.FIND_LENGTH_STATE); // move to next state
        }
        logger.info("got to here");
    }

    private void leftTurnStateHandler(){
        actionCtr=0;
        droneBrain.setStatus(Status.WIDTH_ALIGN_STATE);
    }

    private void rightTurnStateHandler(){
        actionCtr=0;
        droneBrain.setStatus(Status.WIDTH_ALIGN_STATE);
    }

    private void widthStateHandler() {
        if(actionCtr % 3 == 2 && !wasGroundFound) { // if no ground found after scanning, we can move onto the next deimension
            dimensionsAligned++;
            if (dimensionsAligned == 2) { // we have aligned ourselves to find the dimensions of the map
                droneBrain.setStatus(Status.FIND_WIDTH_STATE);
            } else {
                // some kind of turn logic here-------------------------------------------------------
                droneBrain.setStatus(Status.WIDTH_ALIGN_STATE);
            }
        } else {
            if (actionCtr % 3 < 2 && groundFound) { // to check if there is ground while scanning
                wasGroundFound = true;
            }
            else if (actionCtr % 3 == 2) { // reset was ground found at the move state
                wasGroundFound = false;
            }
            droneBrain.setStatus(Status.WIDTH_ALIGN_STATE);
        }
        actionCtr++;
        logger.info("actionCtr: " + actionCtr);
    }


    /*
     * This function will handle the find width state
     */
    private void findWidthStateHandler(){
        if(heading.getLastScanDirection()==Direction.N){
            logger.info("got to here 1");
            northDistance=range;
        }
        else if(heading.getLastScanDirection()==Direction.E){
            logger.info("got to here 2");
            eastDistance=range;
        }
        else if(heading.getLastScanDirection()==Direction.S){
            logger.info("got to here 3");
            southDistance=range;
        }
        else if(heading.getLastScanDirection()==Direction.W){
            logger.info("got to here 4");
            westDistance=range;
        }
        if(eastDistance!=0 && westDistance!=0 && northDistance!=0 &&  southDistance!=0){
            if(XFound){
                droneBrain.setStatus(Status.APPROACH_ISLAND_STATE);
                mapArea.setMapY(northDistance+southDistance+1);
                dronePosition.setRow(northDistance+1);
            }
            else{
                mapArea.setMapX(eastDistance+westDistance+1);
                dronePosition.setCol(eastDistance+1);
            }
            droneBrain.setStatus(Status.APPROACH_ISLAND_STATE);
        }
        else{
            droneBrain.setStatus(Status.FIND_WIDTH_STATE); // move to next state
        }
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