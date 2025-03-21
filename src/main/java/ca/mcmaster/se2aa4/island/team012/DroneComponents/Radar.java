package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class Radar {

    private JSONObject decision = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    private Battery battery;
    private Direction previousScanHeading;

    public Radar() {

    }

    public void scanForward(Heading direction) {
        if (direction.compareHeading(Direction.N)) {
            scanNorth();
        } else if (direction.compareHeading(Direction.W)) {
           scanWest();
        } else if (direction.compareHeading(Direction.S)) {
            scanSouth();
        } else {
            scanEast();
        }
    }

    public void scanLeft(Heading direction) {//resembles the heading the drone is currently headed
        if (direction.compareHeading(Direction.N)) {
            scanWest();
        } else if (direction.compareHeading(Direction.W)) {
           scanSouth();
        } else if (direction.compareHeading(Direction.S)) {
            scanEast();
        } else {
            scanNorth();
        }
    }

    public void scanRight(Heading direction) {
        if (direction.compareHeading(Direction.N)) {
            scanEast();
        } else if (direction.compareHeading(Direction.E)) {
            scanSouth();
        } else if (direction.compareHeading(Direction.S)) {
            scanWest();
        } else {
            scanNorth();
        }
    }

    private void scanNorth(){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.N));
    }
    private void scanSouth(){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.S));
    }
    private void scanEast(){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.E));
    }
    private void scanWest(){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.W));
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
