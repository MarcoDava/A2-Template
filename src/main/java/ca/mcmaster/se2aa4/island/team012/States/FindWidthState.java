package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Radar;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

/**
 * Represents the state where the drone searches for the width of the map.
 * The drone alternates between scanning left and right.
 */
public class FindWidthState implements State {
    private Radar radar; // Handles radar scanning functionality.
    private Heading heading; // The current heading of the drone.
    private int ctr = 0; // Counter to alternate between scanning left and right.

    /**
     * Constructor to initialize the FindWidthState with necessary components.
     * 
     * @param heading The heading direction of the drone.
     * @param controller The controller for managing drone commands.
     * @param dronePosition The current position of the drone.
     */
    public FindWidthState(Heading heading, Control controller, DronePosition dronePosition) {
        this.heading = heading;
        radar = new Radar(controller);
    }

    /**
     * Handles the behavior of the drone in the find width state.
     * Alternates between scanning left and right.
     * 
     * @param decision The JSON object to store the decision.
     */
    @Override
    public void handle(JSONObject decision) {
        if (ctr == 0) { // Scan right on the first action.
            radar.scanRight(heading, decision);
        } else { // Scan left on the next action.
            radar.scanLeft(heading, decision);
        }
        ctr++; // Increment the counter to alternate actions.
    }
}
