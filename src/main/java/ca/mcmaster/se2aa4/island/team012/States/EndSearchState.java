package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;

public class EndSearchState implements State {
    private FlightSystem flightSystem;
    public EndSearchState(DronePosition dronePosition,Control controller){ 
        flightSystem=new FlightSystem(dronePosition,controller);
    }
    @Override
    public void handle(JSONObject decision){
        flightSystem.stop(decision);
    }
}
