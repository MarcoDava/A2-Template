package ca.mcmaster.se2aa4.island.team012;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

class Radar{
    private JSONObject echoCommand = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    public Radar(){

    }
    public boolean scanForward(int[] direction){
        echoCommand.put("action", "echo");
        echoCommand.put("parameters", new JSONObject().put("direction", direction));
        logger.info(echoCommand.toString());
        return true;
    }
    public boolean scanLeft(Heading direction){
        echoCommand.put("action", "echo");
        echoCommand.put("parameters", new JSONObject().put("direction", direction));//need a way to keep track of directions
        logger.info(echoCommand.toString());
        return true;
    }
    public  boolean scanRight(Heading direction){
        echoCommand.put("action", "echo");
        echoCommand.put("parameters", new JSONObject().put("direction", direction));
        logger.info(echoCommand.toString());
        return true;
    }

}