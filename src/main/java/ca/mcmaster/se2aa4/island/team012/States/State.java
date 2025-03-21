package ca.mcmaster.se2aa4.island.team012.States;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import org.json.JSONObject;

public interface State {

    void handle(Drone drone, JSONObject decision);
}
