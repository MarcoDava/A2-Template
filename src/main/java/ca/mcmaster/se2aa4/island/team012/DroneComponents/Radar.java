package ca.mcmaster.se2aa4.island.team012.DroneComponents;
import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

class Radar{
    private JSONObject decision = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    private Battery battery;
    public Radar(){

    }
    public int scanForward(Heading direction){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", direction));
        logger.info(decision.toString());
        int range=extractScanJson(decision.toString());
        return range;
    }
    public int scanLeft(Heading direction){
        Direction leftHeading=null;
        if(direction.compareHeading(Direction.N)){
            leftHeading=Direction.W;
        }
        else if(direction.compareHeading(Direction.W)){
            leftHeading=Direction.S;
        }
        else if(direction.compareHeading(Direction.S)){
            leftHeading=Direction.E;
        }
        else{
            leftHeading=Direction.N;
        }
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", leftHeading));//need a way to keep track of directions
        int range=extractScanJson(decision.toString());
        logger.info(decision.toString());
        return range;
    }
    public int scanRight(Heading direction){
        Direction rightHeading=null;
        if(direction.compareHeading(Direction.N)){
            rightHeading=Direction.E;
        }
        else if(direction.compareHeading(Direction.E)){
            rightHeading=Direction.S;
        }
        else if(direction.compareHeading(Direction.S)){
            rightHeading=Direction.W;
        }
        else{
            rightHeading=Direction.N;
        }
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", rightHeading));
        int range=extractScanJson(decision.toString());
        logger.info(decision.toString());
        return range;
    }
    private int extractRange(String jsonResponse){
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(jsonResponse)));
        battery.useBattery(response.getInt("cost"));
        JSONObject extras=response.getJSONObject("extras");
        JSONArray ground=extras.getJSONArray("sites");
        String groundString=ground.toString();
        boolean foundGround=false;
        if(groundString.equals("GROUND")){
            foundGround=true;
        }
        JSONArray range=extras.getJSONArray("range");
        int rangeInt= range.getInt(0);
        return rangeInt;
    }

}