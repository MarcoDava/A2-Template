package ca.mcmaster.se2aa4.island.team012.DroneComponents;


import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.States.Status;


public abstract class DroneBrain {

    public abstract String makeDecision(JSONObject decision);

    
    /*
     * This function will return the current status of the drone
     * 
     * @return the current status of the drone
     */
    public abstract Status getStatus();
    
    /*
     * This function will set the current status of the drone
     * 
     * @param status the status to be set
     */
    public abstract void setStatus(Status status);
    
}
