package ca.mcmaster.se2aa4.island.team012.DroneComponents;


import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.States.Status;


public abstract class DroneBrain {

    public abstract String makeDecision(JSONObject decision);
    
}
