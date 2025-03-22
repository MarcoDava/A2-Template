package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class Radar {
    private static final Logger logger = LogManager.getLogger();

    public Radar() {

    }

    public void scanForward(Heading direction,JSONObject decision) {
        logger.info("Got here 15");
        if (direction.compareHeading(Direction.N)) {
            logger.info("Got here 17");
            scanNorth(decision);
        } else if (direction.compareHeading(Direction.W)) {
            logger.info("Got here 16");
           scanWest(decision);
        } else if (direction.compareHeading(Direction.S)) {
            scanSouth(decision);
        } else {
            logger.info("Got here 14");
            scanEast(decision);
        }
        logger.info("Got here 16");
    }

    public void scanLeft(Heading direction,JSONObject decision) {//resembles the heading the drone is currently headed
        if (direction.compareHeading(Direction.N)) {
            scanWest(decision);
        } else if (direction.compareHeading(Direction.W)) {
           scanSouth(decision);
        } else if (direction.compareHeading(Direction.S)) {
            scanEast(decision);
        } else {
            scanNorth(decision);
        }
    }

    public void scanRight(Heading direction,JSONObject decision) {
        if (direction.compareHeading(Direction.N)) {
            scanEast(decision);
        } else if (direction.compareHeading(Direction.E)) {
            scanSouth(decision);
        } else if (direction.compareHeading(Direction.S)) {
            scanWest(decision);
        } else {
            scanNorth(decision);
        }
    }

    public void scanNorth(JSONObject decision){
        logger.info("Got here 16");
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.N));
        logger.info("Scanning North");
    }
    public void scanSouth(JSONObject decision){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.S));
        logger.info("Scanning South");
    }
    public void scanEast(JSONObject decision){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.E));
        logger.info("Scanning East");
    }
    public void scanWest(JSONObject decision){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.W));
        logger.info("Scanning West");
    }

    // private int extractRange(String jsonResponse) {
    //     JSONObject response = new JSONObject(new JSONTokener(new StringReader(jsonResponse)));
    //     battery.useBattery(response.getInt("cost"));
    //     JSONObject extras = response.getJSONObject("extras");
    //     JSONArray range = extras.getJSONArray("range");
    //     int rangeInt = range.getInt(0);
    //     return rangeInt;
    // }

    // private boolean extractGround(String jsonResponse) {
    //     JSONObject response = new JSONObject(new JSONTokener(new StringReader(jsonResponse)));
    //     JSONObject extras = response.getJSONObject("extras");
    //     JSONArray ground = extras.getJSONArray("sites");
    //     String groundString = ground.toString();
    //     boolean foundGround = false;
    //     if (groundString.equals("GROUND")) {
    //         foundGround = true;
    //     }
    //     return foundGround;
    // }

}
