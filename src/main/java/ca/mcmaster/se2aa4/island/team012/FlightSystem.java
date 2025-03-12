package ca.mcmaster.se2aa4.island.team012;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


class FlightSystem{
    private JSONObject decision = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    private Battery battery;
    private Heading heading;
    public FlightSystem(Battery initialBattery, Heading initialHeading){
        battery=initialBattery;
        heading=initialHeading;
    }
    public boolean fly(){
        decision.put("action", "fly");
        logger.info(decision.toString());
        battery.useBattery(2);
        return true;
    }
    public boolean stop(){
        decision.put("action", "stop");
        logger.info(decision.toString());
        return true;
    }
    public boolean turnNorth(){
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "N"));
        heading=Heading.N;
        logger.info(decision.toString());
        battery.useBattery(4);
        return true;
    }
    public boolean turnEast(){
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "E"));
        heading.changeHeading(Direction.E);
        logger.info(decision.toString());
        battery.useBattery(4);
        return true;
    }
    public boolean turnSouth(){
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "S"));
        heading=Heading.S;
        logger.info(decision.toString());
        battery.useBattery(4);
        return true;
    }
    public boolean turnWest(){
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", "W"));
        heading=Heading.W;
        logger.info(decision.toString());
        battery.useBattery(4);
        notifyObservers("heading", heading);
        return true;
    }
}