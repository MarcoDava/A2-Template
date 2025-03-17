package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Position;

public class LocatingIslandState implements State{
    Position dronePosition;
    public LocatingIslandState(){
        dronePosition = new DronePosition();
    }
    /*
     * Basics of state:
     * The state should scan and find the drones initial coordinates. It will then find where the island is. If it is found in the initial scan, move on to the next state.
     * If it is not found, it will turn and scan again until land is found. If it is not found, it will turn around and scan again past the original position to check both sides. 
     * 
     * 
     * 
     */
    @Override
    public void handle(Drone drone,JSONObject decision,JSONObject parameters){
        this.locateIsland(drone, decision, parameters);
    }

    public void locateIsland(Drone drone,JSONObject decision,JSONObject parameters){
        
        //scan for island
        //if found, move to next state
        //if not found, turn and scan again
        //if still not found, turn around and scan again
    }
}
