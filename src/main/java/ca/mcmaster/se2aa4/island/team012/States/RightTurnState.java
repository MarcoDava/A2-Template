package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class RightTurnState implements State {
    private Heading heading;
    private FlightSystem flightSystem;


    public RightTurnState(Heading heading,Control controller,DronePosition dronePosition) {
        this.heading=heading;
        flightSystem = new FlightSystem(dronePosition,controller);
    }

    @Override
    public void handle(JSONObject decision) {
        flightSystem.turnRight(heading,decision);
    }
}
