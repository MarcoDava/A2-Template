package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.CreekPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;

public class Photoscanner {

    private JSONObject decision = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    private Battery battery;
    private CreekPosition creekPosition;
    private DronePosition dronePosition;

    public void scanBelow(JSONObject decision) {//might need position as a parameter depending on how the scanBelow function works
        decision.put("action", "scan");
        logger.info("Scanning below");
    }

}
