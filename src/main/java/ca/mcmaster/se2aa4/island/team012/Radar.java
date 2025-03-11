package ca.mcmaster.se2aa4.island.team012;

class Radar{
    private JSONObject echoCommand = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    Radar(){

    }
    scanForward(direction){
        echoCommand.put("action", "echo");
        echoCommand.put("parameters", new JSONObject().put("direction", direction));
        logger.info(echoCommand.toString());
    }
    scanLeft(direction){
        echoCommand.put("action", "echo");
        echoCommand.put("parameters", new JSONObject().put("direction", direction));//need a way to keep track of directions
        logger.info(echoCommand.toString());
    }
    scanRight(direction){
        echoCommand.put("action", "echo");
        echoCommand.put("parameters", new JSONObject().put("direction", direction));
        logger.info(echoCommand.toString());
    }

}