package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

/**
 * Handles radar scanning functionality for the drone.
 * Allows the drone to scan in different directions.
 */
public class Radar {
    private Control controller;

    public Radar(Control controller) {
        this.controller=controller;
    }

    /**
     * Scans in the forward direction based on the drone's current heading.
     * 
     * @param direction The current heading of the drone.
     * @param decision The JSON object to store the scan decision.
     */
    public void scanForward(Heading direction, JSONObject decision) {
        if (direction.compareHeading(Direction.N)) {
            scanNorth(decision);
            direction.setScanDirection(Direction.N);
        } else if (direction.compareHeading(Direction.W)) {
           scanWest(decision);
            direction.setScanDirection(Direction.W);
        } else if (direction.compareHeading(Direction.S)) {
            scanSouth(decision);
            direction.setScanDirection(Direction.S);
        } else {
            scanEast(decision);
            direction.setScanDirection(Direction.E);
        }
        controller.setCommand(Command.ECHO);
    }

    /**
     * Scans to the left of the drone's current heading.
     * 
     * @param direction The current heading of the drone.
     * @param decision The JSON object to store the scan decision.
     */
    public void scanLeft(Heading direction, JSONObject decision) {//resembles the heading the drone is currently headed
        if (direction.compareHeading(Direction.N)) {
            scanWest(decision);
            direction.setScanDirection(Direction.W);
        } else if (direction.compareHeading(Direction.W)) {
           scanSouth(decision);
            direction.setScanDirection(Direction.S);
        } else if (direction.compareHeading(Direction.S)) {
            scanEast(decision);
            direction.setScanDirection(Direction.E);
        } else {
            scanNorth(decision);
            direction.setScanDirection(Direction.N);
        }
        controller.setCommand(Command.ECHO);
    }

    /**
     * Scans to the right of the drone's current heading.
     * 
     * @param direction The current heading of the drone.
     * @param decision The JSON object to store the scan decision.
     */
    public void scanRight(Heading direction, JSONObject decision) {
        if (direction.compareHeading(Direction.N)) {
            scanEast(decision);
            direction.setScanDirection(Direction.E);
        } else if (direction.compareHeading(Direction.E)) {
            scanSouth(decision);
            direction.setScanDirection(Direction.S);
        } else if (direction.compareHeading(Direction.S)) {
            scanWest(decision);
            direction.setScanDirection(Direction.W);
        } else {
            scanNorth(decision);
            direction.setScanDirection(Direction.N);
        }
        controller.setCommand(Command.ECHO);
    }

    private void scanNorth(JSONObject decision){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.N));
    }
    private void scanSouth(JSONObject decision){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.S));
    }
    private void scanEast(JSONObject decision){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.E));
    }
    private void scanWest(JSONObject decision){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", Direction.W));
    }
    

}
