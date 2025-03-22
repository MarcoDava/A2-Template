package ca.mcmaster.se2aa4.island.team012.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Command;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.DroneBrain;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Radar;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.SimpleDroneBrain;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;

public class FindLengthState implements State {

    private MapArea mapArea;
    private Radar radar = new Radar();
    private Heading heading;
    private Drone drone;
    private DronePosition dronePosition;
    private Control controller;
    private static final Logger logger = LogManager.getLogger();


    public FindLengthState(MapArea mapArea, Drone drone,Heading heading, Control controller) {
        this.mapArea = mapArea;
        this.drone=drone;
        this.heading=heading;
        this.controller=controller;
    }

    @Override
    public String handle(Drone drone, JSONObject decision) {
        logger.info("Got here 14");
        controller.setCommand(Command.SCAN);
        logger.info("Got here 14");
        radar.scanForward(heading,decision);
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
        logger.info(decision.toString());
        return decision.toString();
    }

}
