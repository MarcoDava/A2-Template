package ca.mcmaster.se2aa4.island.team012;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

class FlightSystem{
    private JSONObject flyCommand = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    FlightSystem(){
        
    }
    fly(){
        flyCommand.put("action", "fly");
        logger.info(flyCommand.toString());
    }
    stop(){
        flyCommand.put("action", "stop");
        ogger.info(flyCommand.toString());
    }
    turnNorth(){
        flyCommand.put("action", "heading");
        flyCommand.put("parameters", new JSONObject().put("direction", "N"));
        logger.info(flyCommand.toString());
    }
    turnEast(){
        flyCommand.put("action", "heading");
        flyCommand.put("parameters", new JSONObject().put("direction", "E"));
        logger.info(flyCommand.toString());
    }
    turnSouth(){
        flyCommand.put("action", "heading");
        flyCommand.put("parameters", new JSONObject().put("direction", "S"));
        logger.info(flyCommand.toString());
    }
    turnWest(){
        flyCommand.put("action", "heading");
        flyCommand.put("parameters", new JSONObject().put("direction", "W"));
        logger.info(flyCommand.toString());
    }
}