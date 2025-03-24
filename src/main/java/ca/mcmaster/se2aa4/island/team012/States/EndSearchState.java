package ca.mcmaster.se2aa4.island.team012.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;

public class EndSearchState implements State {
    private FlightSystem flightSystem;
    private static final Logger logger = LogManager.getLogger();
    public EndSearchState(DronePosition dronePosition,Control controller){ 
        flightSystem=new FlightSystem(dronePosition,controller);
    }
    @Override
    public void handle(JSONObject decision){
        logger.info("Ending search");
        flightSystem.stop(decision);
        logger.info("ended search");
    }
}
