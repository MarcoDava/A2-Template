package ca.mcmaster.se2aa4.island.team012;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

class Photoscanner{
    private JSONObject decision = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    private Battery battery;
    public boolean scanBelow(){//might need position as a parameter depending on how the scanBelow function works
        decision.put("action", "scan");
        logger.info(decision.toString());
        return true;
    }
    public void extractInformation(String jsonResponse){
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(jsonResponse)));

        battery.useBattery(response.getInt("cost"));

        JSONObject headings=response.getJSONObject("extras");
        JSONArray sites=headings.getJSONArray("sites");
        JSONArray creeks=headings.getJSONArray("creeks");
    }

}