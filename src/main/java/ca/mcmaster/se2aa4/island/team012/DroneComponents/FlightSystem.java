package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class FlightSystem {

    private JSONObject decision = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    private Battery battery;
    private Heading heading;

    public FlightSystem(Battery initialBattery, Heading initialHeading) {
        battery = initialBattery;
        heading = initialHeading;
    }

    public boolean fly() {
        decision.put("action", "fly");
        logger.info(decision.toString());
        battery.useBattery(2);
        return true;
    }

    public boolean stop() {
        decision.put("action", "stop");
        logger.info(decision.toString());
        return true;
    }

    public void turnRight(){
        if(heading.compareHeading(Direction.N)){
            turnEast();
        } else if(heading.compareHeading(Direction.E)){
            turnSouth();
        } else if(heading.compareHeading(Direction.S)){
            turnWest();
        } else if(heading.compareHeading(Direction.W)){
            turnNorth();
        }
    }

    public void turnLeft(){
        if(heading.compareHeading(Direction.N)){
            turnWest();
        } else if(heading.compareHeading(Direction.W)){
            turnSouth();
        } else if(heading.compareHeading(Direction.S)){
            turnEast();
        } else if(heading.compareHeading(Direction.E)){
            turnNorth();
        }
    }

    public boolean turnNorth() {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "N"));
        heading.updateHeading(Direction.N);
        logger.info(decision.toString());
        battery.useBattery(4);
        return true;
    }

    public boolean turnEast() {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "E"));
        heading.updateHeading(Direction.E);
        logger.info(decision.toString());
        battery.useBattery(4);
        return true;
    }

    public boolean turnSouth() {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "S"));
        heading.updateHeading(Direction.S);
        logger.info(decision.toString());
        battery.useBattery(4);
        return true;
    }

    public boolean turnWest() {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "W"));
        heading.updateHeading(Direction.W);
        logger.info(decision.toString());
        battery.useBattery(4);
        return true;
    }
}
