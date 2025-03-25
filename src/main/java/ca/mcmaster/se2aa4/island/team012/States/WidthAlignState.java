package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Photoscanner;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Radar;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;


/**
 * Represents the state where the drone aligns itself to start searching for the width of the map.
 * The drone scans left, right, and moves forward to find a suitable starting position.
 */
public class WidthAlignState implements State {

    private int actionCtr; // Counter to track the current action (scan left, scan right, or move forward).
    private Radar radar; // Handles radar scanning functionality.
    private Heading heading; // The current heading of the drone.
    private FlightSystem flightSystem; // Handles drone movement.

    /**
     * Constructor to initialize the WidthAlignState with necessary components.
     * 
     * @param heading The heading direction of the drone.
     * @param controller The controller for managing drone commands.
     * @param position The current position of the drone.
     */
    public WidthAlignState(Heading heading, Control controller, DronePosition position) {
        this.actionCtr = 0;
        this.heading = heading;
        this.flightSystem = new FlightSystem(position, controller);
        this.radar = new Radar(controller);
    }

    /**
     * Handles the behavior of the drone in the width alignment state.
     * Alternates between scanning left, scanning right, and moving forward.
     * 
     * @param decision The JSON object to store the decision.
     */
    @Override
    public void handle(JSONObject decision) {
        if (actionCtr % 3 == 0) { // Scan left.
            radar.scanLeft(heading, decision);
        } else if (actionCtr % 3 == 1) { // Scan right.
            radar.scanRight(heading, decision);
        } else { // Move forward.
            flightSystem.fly(heading, decision);
        }
        this.actionCtr++; // Increment the action counter.
    }
}
