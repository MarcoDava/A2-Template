package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

public class SpiralFromMiddleState {
    // name is a WIP

    /*

    x = 0
    numForwards = 0
    numTurns = -2

    loop:
        if (x != 0): // go forward correct number of times
            fly()
            x--
        else: // num forwards = 0
            turnRight()
            numTurns++
            if (numTurns > 0 && numTurns % 2 = 1): // if the 2nd turn in a grouping
                numForwards++ // increase the number of forwards to be taken
            x = numForwards // reset the counter for forwards
     */

     public SpiralFromMiddleState(MapArea mapArea, DronePosition dronePosition, Control controller, Heading heading) { // contructor copied unchanged from spiralsearchstate
        this.mapArea = mapArea;
        this.controller = controller;
        this.dronePosition = dronePosition;
        this.heading = heading;
        flightSystem = new FlightSystem(dronePosition);
    }


    @Override
    public String handle(Drone drone, JSONObject decision) {


    }
}