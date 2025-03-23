package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

public class SpiralFromMiddleState implements State {
    private MapArea mapArea;
    private Control controller;
    private DronePosition dronePosition;
    private Heading heading;
    private FlightSystem flightSystem;


    private int remainingForwards;
    private int numForwards;
    private int numTurns;
    private boolean scanning;
    
    
    public SpiralFromMiddleState(MapArea mapArea, DronePosition dronePosition, Control controller, Heading heading) { // contructor copied unchanged from spiralsearchstate
        this.mapArea = mapArea;
        this.controller = controller;
        this.dronePosition = dronePosition;
        this.heading = heading;
        flightSystem = new FlightSystem(dronePosition,controller);
    }


    @Override
    public void handle(JSONObject decision) {
        if (scanning == true) { // scanning step
            scan()
            scanning = false; // to alternate between moving and scanning
        }
        else { // movement step
            if (remainingForwards != 0) { // go forward correct number of times
                fly()
                remainingForwards--;
            }
            else { // num forwards = 0
                turnRight()
                numTurns++;
                if (numTurns > 0 && numTurns % 2 == 1) { // if the 2nd turn in a grouping
                    numForwards++; // increase the number of forwards to be taken
                }
                remainingForwards = numForwards; // reset the counter for forwards
            }
            scanning = true; // to alternate between moving and scanning
        }
        return;
    }

}