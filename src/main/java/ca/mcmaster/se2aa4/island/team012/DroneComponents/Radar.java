package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;

public class Radar {
    private static final Logger logger = LogManager.getLogger();
    private Control controller;

    public Radar(Control controller) {
        this.controller=controller;
    }

    public void scanForward(Heading direction,JSONObject decision) {
        if (direction.compareHeading(Direction.N)) {
            scanNorth(decision);
        } else if (direction.compareHeading(Direction.W)) {
           scanWest(decision);
        } else if (direction.compareHeading(Direction.S)) {
            scanSouth(decision);
        } else {
            scanEast(decision);
        }
        controller.setCommand(Command.SCAN);
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
        controller.setCommand(Command.SCAN);
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
        controller.setCommand(Command.SCAN);
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

}
