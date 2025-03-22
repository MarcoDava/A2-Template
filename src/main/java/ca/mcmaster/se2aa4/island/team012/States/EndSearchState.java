package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class EndSearchState implements State {
    private FlightSystem flightSystem;
    private Heading heading;
    public EndSearchState() {
    }
    @Override
    public String handle(Drone drone, JSONObject decision){
        flightSystem.stop(decision);
        return decision.toString();
    }
}
