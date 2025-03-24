package ca.mcmaster.se2aa4.island.team012.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Radar;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;

public class FindLengthState implements State {
    private Radar radar;
    private Heading heading;

    private int ctr=0;

    private static final Logger logger = LogManager.getLogger();


    public FindLengthState(Heading heading, Control controller) {
        this.heading=heading;
        radar=new Radar(controller);
    }

    @Override
    public void handle(JSONObject decision) {
        if(ctr==0){
            radar.scanLeft(heading,decision);
        }
        else if(ctr==1){
            radar.scanRight(heading,decision);
        }
        ctr++;
        logger.info(decision.toString());
    }

}
