package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Photoscanner;

public class SpiralFromMiddleState implements State {
    private Heading heading;
    private FlightSystem flightSystem;
    private Photoscanner photoscanner;

    private int remainingForwards;
    private int numForwards;
    private int numTurns;
    private boolean scanning;
    
    
    public SpiralFromMiddleState(DronePosition dronePosition, Control controller, Heading heading) { // contructor copied unchanged from spiralsearchstate
        this.heading = heading;
        flightSystem = new FlightSystem(dronePosition,controller);
        photoscanner = new Photoscanner(controller);
    }


    @Override
    public void handle(JSONObject decision) {
        if (scanning == true) { // scanning step
            photoscanner.scanBelow(decision);
            scanning = false; // to alternate between moving and scanning
        }
        else { // movement step
            if (remainingForwards != 0) { // go forward correct number of times
                flightSystem.fly(heading, decision);
                remainingForwards--;
            }
            else { // num forwards = 0
                flightSystem.turnRight(heading, decision); // turn right
                numTurns++;
                if (numTurns > 0 && numTurns % 2 == 1) { // if the 2nd turn in a grouping
                    numForwards++; // increase the number of forwards to be taken
                }
                remainingForwards = numForwards; // reset the counter for forwards
            }
            scanning = true; // to alternate between moving and scanning
        }
    }

}