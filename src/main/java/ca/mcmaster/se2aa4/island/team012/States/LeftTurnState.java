package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

/**
 * Represents the state where the drone performs a left turn.
 */
public class LeftTurnState implements State {
    private Heading heading; // The current heading of the drone.
    private FlightSystem flightSystem; // Handles drone movement.

    /**
     * Constructor to initialize the LeftTurnState with necessary components.
     * 
     * @param heading The heading direction of the drone.
     * @param controller The controller for managing drone commands.
     * @param dronePosition The current position of the drone.
     */
    public LeftTurnState(Heading heading, Control controller, DronePosition dronePosition) {
        this.heading = heading;
        flightSystem = new FlightSystem(dronePosition, controller);
    }

    /**
     * Handles the behavior of the drone in the left turn state.
     * Executes a left turn.
     * 
     * @param decision The JSON object to store the decision.
     */
    @Override
    public void handle(JSONObject decision) {
        flightSystem.turnLeft(heading, decision); // Perform a left turn.
    }
}
