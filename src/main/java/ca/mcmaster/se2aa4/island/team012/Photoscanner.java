package ca.mcmaster.se2aa4.island.team012;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

class Photoscanner{
    private JSONObject scanCommand = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    public boolean scanBelow(){//might need position as a parameter depending on how the scanBelow function works
        scanCommand.put("action", "scan");
        logger.info(scanCommand.toString());
        return true;
    }

}