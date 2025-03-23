package ca.mcmaster.se2aa4.island.team012.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Command;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Radar;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;

/**
 * This state is when the program just starts. In this state, we need to find an area
 * where we can start the search. We will do this by scanning left, right, and forward
 * to find which direction there is land and where there is no land. Then the drone will
 * go in the direction where there is no land until scans no longer return land. At this
 * point, the drone will have found an area where it can start the search and enter
 * FindLengthState.
 */
public class DimensionAlignState implements State {
    private int eastCtr;
    private int westCtr;
    private int northCtr;
    private int southCtr;
    private int actionCtr=0;
    private Radar radar;
    private Heading heading;
    private FlightSystem flightSystem;

    private final Logger logger = LogManager.getLogger();

    public DimensionAlignState(Heading heading, Control controller) {
        this.eastCtr = 0;
        this.westCtr = 0;
        this.northCtr = 0;
        this.southCtr = 0;
        this.radar = new Radar(controller);
    }

    @Override
    public String handle(JSONObject decision) {
        if (actionCtr % 4 == 0) {                   // scan forward
            radar.scanForward(heading, decision);
        } else if (actionCtr % 4 == 1) {            // scan left
            radar.scanLeft(heading, decision);
        } else if (actionCtr % 4 == 2) {            // scan right
            radar.scanRight(heading, decision);
        } else {                                    // move forward
            flightSystem.fly(heading, decision);
            updateCtr();
        }
        this.actionCtr++;
        return decision.toString();
    }

    

    private void updateCtr() {
        if (heading.compareHeading(Direction.E)) {
            this.eastCtr++;
        } else if (heading.compareHeading(Direction.W)) {
            this.westCtr++;
        } else if (heading.compareHeading(Direction.N)) {
            this.northCtr++;
        } else {
            this.southCtr++;
        }
    }

    // used to find starting position after mapArea is defined
    public int[] getDirectionCtr(){
        int[] ctr = {eastCtr, westCtr, northCtr, southCtr};
        return ctr;
    }

}
