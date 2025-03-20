package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

// This state is just where the drone flies towards the island.
public class ApproachIslandState implements State {
    MapArea mapArea;
    public ApproachIslandState(MapArea mapArea) {
        this.mapArea = mapArea;
    }
    @Override
    public void handle(Drone drone, JSONObject decision, JSONObject parameters) {

    }
}
// 
