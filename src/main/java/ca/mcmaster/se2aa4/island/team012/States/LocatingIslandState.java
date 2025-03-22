package ca.mcmaster.se2aa4.island.team012.States;


import java.util.ResourceBundle.Control;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.Positioning.Position;

public class LocatingIslandState implements State {

    Position dronePosition;
    MapArea mapArea;

    public LocatingIslandState(MapArea mapArea) {
        this.mapArea=mapArea;
    }

    /*
     * Basics of state:
     * The state should scan and find the drones initial coordinates. It will then find where the island is. If it is found in the initial scan, move on to the next state.
     * If it is not found, it will turn and scan again until land is found. If it is not found, it will turn around and scan again past the original position to check both sides. 
     * 
     * //From the doc://
     * This state is to search the first occurrence of land. If the initial scan 
     * doesn't find land, the drone turns right and scans the left side until it 
     * finds land.
     * 
     */
    @Override
    public String handle(Drone drone, JSONObject decision) {
        this.locateIsland(drone, decision);
        return decision.toString();
    }

    public void locateIsland(Drone drone, JSONObject decision) {

        //scan for island
        //if found, move to next state
        //if not found, turn and scan again
        //if still not found, turn around and scan again
    }
}
