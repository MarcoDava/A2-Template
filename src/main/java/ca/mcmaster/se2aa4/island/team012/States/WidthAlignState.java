package ca.mcmaster.se2aa4.island.team012.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Photoscanner;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Radar;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;


/**
 * This state is when the program just starts. In this state, we need to find an area
 * where we can start the search. We will do this by scanning left, right, and forward
 * to find which direction there is land and where there is no land. Then the drone will
 * go in the direction where there is no land until scans no longer return land. At this
 * point, the drone will have found an area where it can start the search and enter
 * FindLengthState.
 */
public class WidthAlignState implements State {
    private int eastCtr;
    private int westCtr;
    private int northCtr;
    private int southCtr;
    private int actionCtr;
    private Radar radar;
    private Heading heading;
    private FlightSystem flightSystem;
    private Photoscanner photoscanner;
    int ctr=0;


    private final Logger logger = LogManager.getLogger();

    public WidthAlignState(Heading heading, Control controller, DronePosition position) {
        this.eastCtr = 0;
        this.westCtr = 0;
        this.northCtr = 0;
        this.southCtr = 0;
        this.actionCtr = 0;
        this.heading = heading;
        this.flightSystem = new FlightSystem(position, controller);
        this.radar = new Radar(controller);
        this.photoscanner = new Photoscanner(controller);
    }

    /*
     * This method is responsible for handling a situation where the drone is started
     * in a random postition on the map.
     * 
     * @param decision the decision that the drone will make
     * @return the decision that the drone will make
     */
    @Override
    public void handle(JSONObject decision) {
        if(ctr%2==0){
            photoscanner.scanBelow(decision);
        }
        else{
            if (actionCtr % 3 == 0) {                   // scan left
                radar.scanLeft(heading, decision);
            } else if (actionCtr % 3 == 1) {            // scan right
                radar.scanRight(heading, decision);
            } else {                                    // move forward
                updateCtr();
                flightSystem.fly(heading, decision);
            }
            this.actionCtr++;
        }
    
        ctr++;
        
    }

    
    /*
     * This method is responsible for updating the counters for the directions
     * that the drone has flown in.
     * 
     * @return void
     */
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

}
