package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class FlightSystem {

    private DronePosition dronePosition;
    private Control controller;

    public FlightSystem(DronePosition dronePosition,Control controller) {
        this.dronePosition = dronePosition;
        this.controller = controller;
    }

    public void fly(Heading heading, JSONObject decision) {
        decision.put("action", "fly");
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
    }

    public void stop(JSONObject decision) {
        decision.put("action", "stop");
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
        controller.setCommand(Command.TURN);
    }

    private void turnNorth(Heading heading, JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "N"));
        heading.updateHeading(Direction.N);
    }

    public void turnEast(Heading heading, JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "E"));
        heading.updateHeading(Direction.E);
    }

    public void turnSouth(Heading heading, JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "S"));
        heading.updateHeading(Direction.S);
    }

    public void turnWest(Heading heading, JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "W"));
        heading.updateHeading(Direction.W);
    }
}
