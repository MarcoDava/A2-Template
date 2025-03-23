package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class FlightSystem {

    private static final Logger logger = LogManager.getLogger();
    private DronePosition dronePosition;
    private Control controller;

    public FlightSystem(DronePosition dronePosition,Control controller) {
        this.dronePosition = dronePosition;
        this.controller = controller;
    }

    public void fly(Heading heading, JSONObject decision) {
        decision.put("action", "fly");
        logger.info("Going Forward");
        if(heading.compareHeading(Direction.N)){
            dronePosition.updateDronePosition(-1,0);
        } else if(heading.compareHeading(Direction.E)){
            dronePosition.updateDronePosition(0,1);
        } else if(heading.compareHeading(Direction.S)){
            dronePosition.updateDronePosition(1,0);
        } else if(heading.compareHeading(Direction.W)){
            dronePosition.updateDronePosition(0,-1);
        }
        controller.setCommand(Command.MOVE);
        logger.info(dronePosition.getRow() + " " + dronePosition.getCol());
    }

    public void stop(JSONObject decision) {
        decision.put("action", "stop");
        logger.info("Ending Search");
    }

    public void turnRight(Heading heading, JSONObject decision){
        if(heading.compareHeading(Direction.N)){
            turnEast(heading, decision);
            dronePosition.updateDronePosition(-1, 1);
        } else if(heading.compareHeading(Direction.E)){
            turnSouth(heading, decision);
            dronePosition.updateDronePosition(1, 1);
        } else if(heading.compareHeading(Direction.S)){
            turnWest(heading, decision);
            dronePosition.updateDronePosition(1, -1);
        } else if(heading.compareHeading(Direction.W)){
            turnNorth(heading, decision);
            dronePosition.updateDronePosition(-1, -1);
        }
        logger.info(dronePosition.getRow() + " " + dronePosition.getCol());
        controller.setCommand(Command.TURN);
    }

    public void turnLeft(Heading heading, JSONObject decision){
        if(heading.compareHeading(Direction.N)){
            turnWest(heading, decision);
            dronePosition.updateDronePosition(-1, -1);
        } else if(heading.compareHeading(Direction.W)){
            turnSouth(heading, decision);
            dronePosition.updateDronePosition(1, -1);
        } else if(heading.compareHeading(Direction.S)){
            turnEast(heading, decision);
            dronePosition.updateDronePosition(1, 1);
        } else if(heading.compareHeading(Direction.E)){
            turnNorth(heading, decision);
            dronePosition.updateDronePosition(-1, 1);
        }
        logger.info(dronePosition.getRow() + " " + dronePosition.getCol());
        controller.setCommand(Command.TURN);
    }

    private void turnNorth(Heading heading, JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "N"));
        heading.updateHeading(Direction.N);
        logger.info("Turning North");
    }

    public void turnEast(Heading heading, JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "E"));
        heading.updateHeading(Direction.E);
        logger.info("Turning East");
    }

    public void turnSouth(Heading heading, JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "S"));
        heading.updateHeading(Direction.S);
        logger.info("Turning South");
    }

    public void turnWest(Heading heading, JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "W"));
        heading.updateHeading(Direction.W);
        logger.info("Turning West");
    }
}
