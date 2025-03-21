package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class FlightSystem {

    private static final Logger logger = LogManager.getLogger();
    private Heading heading;

    public FlightSystem(Heading initialHeading) {
        heading = initialHeading;
    }

    public void fly(JSONObject decision) {
        decision.put("action", "fly");
        logger.info("Going Forward");
    }

    public void stop(JSONObject decision) {
        decision.put("action", "stop");
        logger.info("Ending Search");
    }

    public void turnRight(JSONObject decision){
        if(heading.compareHeading(Direction.N)){
            turnEast(decision);
        } else if(heading.compareHeading(Direction.E)){
            turnSouth(decision);
        } else if(heading.compareHeading(Direction.S)){
            turnWest(decision);
        } else if(heading.compareHeading(Direction.W)){
            turnNorth(decision);
        }
    }

    public void turnLeft(JSONObject decision){
        if(heading.compareHeading(Direction.N)){
            turnWest(decision);
        } else if(heading.compareHeading(Direction.W)){
            turnSouth(decision);
        } else if(heading.compareHeading(Direction.S)){
            turnEast(decision);
        } else if(heading.compareHeading(Direction.E)){
            turnNorth(decision);
        }
    }

    private void turnNorth(JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "N"));
        heading.updateHeading(Direction.N);
        logger.info("Turning North");
    }

    public void turnEast(JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "E"));
        heading.updateHeading(Direction.E);
        logger.info("Turning East");
    }

    public void turnSouth(JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "S"));
        heading.updateHeading(Direction.S);
        logger.info("Turning South");
    }

    public void turnWest(JSONObject decision) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "W"));
        heading.updateHeading(Direction.W);
        logger.info("Turning West");
    }
}
