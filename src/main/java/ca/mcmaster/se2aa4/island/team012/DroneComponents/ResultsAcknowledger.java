package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

public class ResultsAcknowledger{
    private Battery battery;
    private MapArea mapArea;

    public ResultsAcknowledger(Battery battery, MapArea mapArea){
        this.battery = battery;
        this.mapArea = mapArea;
    }

    public void extractBattery(String s){
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        battery.useBattery(response.getInt("cost"));
    }

    public boolean extractGround(String s){
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras");
        JSONObject Ground = extraInfo.getJSONObject("found");
        if(Ground.toString().equals("GROUND")){
            return true;
        }
        return false;
    }

    public int extractRange(String s){
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras");
        int range = extraInfo.getInt("range");
        return range;
    }

    public boolean extractSites(String s){
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras");
        JSONObject creek = extraInfo.getJSONObject("creeks");
        if(creek.toString().isEmpty()){
            return false;
        }
        return true;
        
    }

    public boolean extractCreeks(String s){
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras");
        JSONObject emergencySite = extraInfo.getJSONObject("sites");
        if(emergencySite.toString().isEmpty()){
            return false;
        }
        return true;
    }



    //need a function that gets the mapX and mapY to determine the size of the map

}