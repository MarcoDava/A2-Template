package ca.mcmaster.se2aa4.island.team012;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

class FlightSystem{
    private JSONObject flyCommand = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    private Battery battery;
    private Heading heading;
    public FlightSystem(Battery initialBattery, Heading initialHeading){
        battery=initialBattery;
        heading=initialHeading;
    }
    public boolean fly(){
        flyCommand.put("action", "fly");
        logger.info(flyCommand.toString());
        battery.useBattery(2);
        return true;
    }
    public boolean stop(){
        flyCommand.put("action", "stop");
        logger.info(flyCommand.toString());
    }
    public boolean turnNorth(){
        flyCommand.put("action", "heading");
        flyCommand.put("parameters", new JSONObject().put("direction", "N"));
        heading=Heading.N;
        logger.info(flyCommand.toString());
        battery.useBattery(4);
    }
    public boolean turnEast(){
        flyCommand.put("action", "heading");
        flyCommand.put("parameters", new JSONObject().put("direction", "E"));
        heading=Heading.E;
        logger.info(flyCommand.toString());
        battery.useBattery(4);
    }
    public boolean turnSouth(){
        flyCommand.put("action", "heading");
        flyCommand.put("parameters", new JSONObject().put("direction", "S"));
        heading=Heading.S;
        logger.info(flyCommand.toString());
        battery.useBattery(4);
    }
    public boolean turnWest(){
        flyCommand.put("action", "heading");
        flyCommand.put("parameters", new JSONObject().put("direction", "W"));
        heading=Heading.W;
        logger.info(flyCommand.toString());
        battery.useBattery(4);
    }
}