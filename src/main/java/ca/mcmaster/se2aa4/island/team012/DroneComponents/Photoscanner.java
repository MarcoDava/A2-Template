package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.CreekPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;

class Photoscanner {

    private JSONObject decision = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    private Battery battery;
    private CreekPosition creekPosition;
    private DronePosition dronePosition;

    public boolean scanBelow() {//might need position as a parameter depending on how the scanBelow function works
        decision.put("action", "scan");
        logger.info(decision.toString());
        extractInformation(decision.toString());
        return true;
    }

    public boolean extractInformation(String jsonResponse) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(jsonResponse)));

        battery.useBattery(response.getInt("cost"));

        JSONObject headings = response.getJSONObject("extras");
        JSONArray sites = headings.getJSONArray("sites");
        JSONArray creeks = headings.getJSONArray("creeks");


        // extract sites and creeks from the JSON arrays

        // if there is a creek ??and it is not in the list of creeks??
        //      add the creek to the list
        // if there is an emergency site ??and it is not in the list of emergency sites??
        //      add the site to the list
        creekPosition.addCreekPosition(dronePosition);
        return true;
    }

}
