package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class Radar {
    private static final Logger logger = LogManager.getLogger();
    private Control controller;

    public Radar(Control controller) {
        this.controller=controller;
    }

    public void scanForward(Heading direction,JSONObject decision) {
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

    public void scanLeft(Heading direction,JSONObject decision) {//resembles the heading the drone is currently headed
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

    public void scanRight(Heading direction,JSONObject decision) {
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

    public void scanNorth(JSONObject decision){
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
    

}
