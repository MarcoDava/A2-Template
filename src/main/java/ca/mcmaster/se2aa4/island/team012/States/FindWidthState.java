package ca.mcmaster.se2aa4.island.team012.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Radar;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class FindWidthState implements State {
    private Radar radar;
    private Heading heading;
    private int ctr=0;

    private static final Logger logger = LogManager.getLogger();

    public FindWidthState(Heading heading, Control controller,DronePosition dronePosition) {
        this.heading=heading;
        radar = new Radar(controller);
    }

    @Override
    public void handle(JSONObject decision) {
        if(ctr==0){
            radar.scanRight(heading,decision);
        }
        else{
            radar.scanLeft(heading,decision);
        }
        ctr++;
        logger.info(decision.toString());
    }
}
