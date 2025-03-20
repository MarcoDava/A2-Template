package ca.mcmaster.se2aa4.island.team012.DroneComponents;


import org.json.JSONObject;


public abstract class DroneBrain {
    public abstract void makeDecision(JSONObject parameters, JSONObject decision);
   
}
