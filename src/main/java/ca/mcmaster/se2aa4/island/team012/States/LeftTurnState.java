package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;

// This state is just where the drone flies towards the island.
public class LeftTurnState implements State {
    private DronePosition dronePosition;
    private Heading heading;
    private FlightSystem flightSystem;

    public LeftTurnState(Control controller,Heading heading){ 
        this.heading=heading;
        flightSystem=new FlightSystem(dronePosition, controller);
    }
    @Override
    public String handle(JSONObject decision) {
        flightSystem.turnLeft(heading, decision);
        return decision.toString();
    }
}
// 
