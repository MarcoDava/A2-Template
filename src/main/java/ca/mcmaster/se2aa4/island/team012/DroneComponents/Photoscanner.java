package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Photoscanner {
    private Control controller;
    private static final Logger logger = LogManager.getLogger();

    public Photoscanner(Control controller) {
        this.controller=controller;
    }   

    public void scanBelow(JSONObject decision) {//might need position as a parameter depending on how the scanBelow function works
        decision.put("action", "scan");
        logger.info("Scanning below");
        controller.setCommand(Command.SCAN);
    }

}
