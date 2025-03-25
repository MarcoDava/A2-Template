package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Photoscanner;

/**
 * Represents the state where the drone performs a spiral search starting from the middle of the map.
 */
public class SpiralFromMiddleState implements State {
    private Heading heading; // The current heading of the drone.
    private FlightSystem flightSystem; // Handles drone movement.
    private Photoscanner photoscanner; // Handles scanning functionality.

    private int remainingForwards; // Tracks the remaining forward movements in the current segment.
    private int numForwards; // Tracks the number of forward movements in the current spiral layer.
    private int numTurns; // Tracks the number of turns made in the spiral.
    private boolean scanning; // Indicates whether the drone is in a scanning step.
    
    
    /**
     * Constructor to initialize the SpiralFromMiddleState with necessary components.
     * 
     * @param dronePosition The current position of the drone.
     * @param controller The controller for managing drone commands.
     * @param heading The heading direction of the drone.
     */
    public SpiralFromMiddleState(DronePosition dronePosition, Control controller, Heading heading) { // contructor copied unchanged from spiralsearchstate
        this.heading = heading;
        flightSystem = new FlightSystem(dronePosition,controller);
        photoscanner = new Photoscanner(controller);
    }


    /**
     * Handles the behavior of the drone in the spiral search state.
     * Alternates between scanning and moving in a spiral pattern.
     * 
     * @param decision The JSON object to store the decision.
     */
    @Override
    public void handle(JSONObject decision) {
        if (scanning) { // Scanning step.
            photoscanner.scanBelow(decision); // Perform a scan below the drone.
            scanning = false; // Alternate to movement step.
        } else { // Movement step.
            if (remainingForwards != 0) { // Move forward if there are remaining steps.
                flightSystem.fly(heading, decision);
                remainingForwards--;
            } else { // If no forward steps remain, turn right.
                flightSystem.turnRight(heading, decision);
                numTurns++;
                if (numTurns > 0 && numTurns % 2 == 1) { // Increase forward steps after every second turn.
                    numForwards++;
                }
                remainingForwards = numForwards; // Reset the forward step counter.
            }
            scanning = true; // Alternate to scanning step.
        }
    }

}