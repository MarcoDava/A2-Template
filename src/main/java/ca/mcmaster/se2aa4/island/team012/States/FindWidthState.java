package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Command;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Radar;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FindWidthState implements State {
    private Radar radar;
    private Heading heading;

    private static final Logger logger = LogManager.getLogger();

    public FindWidthState(Heading heading, Control controller) {

        this.heading=heading;
        radar = new Radar(controller);
    }

    @Override
    public String handle(JSONObject decision) {
        radar.scanRight(heading,decision);

        // else{//need some way to pass the results to maparea
        //     mapArea.setMapArea(mapX,mapY);
        //     drone.setStatus(Status.LOCATING_ISLAND_STATE);
        //     dronePosition.setRow(ROW);
        //     dronePosition.setCol(COL);
        // }
        //this algorithm assumes that we start at 1,1
        //will scan east and west to determine the area in which the drone can fly for the mvp
        
        //this is the starting position of the drone, assumed in the MVP
        //for the final, because we may not start at 1,1. This state should continue to fly until it can find a line that doesnt touch the land
        //this state should find the out of bounds, so the scan should be scanning the out of range and not land. 
        return decision.toString();
    }
}
