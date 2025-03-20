package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.json.JSONObject;

public class ResultsAcknowledger{
    private Battery battery;
    public ResultsAcknowledger(){

    }

    public void extractBattery(JSONObject decision){
        battery.updateValue((Object)decision.getInt("battery"));
    }

}