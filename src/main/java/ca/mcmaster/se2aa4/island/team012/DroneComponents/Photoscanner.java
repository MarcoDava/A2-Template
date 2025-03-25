package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.json.JSONObject;

public class Photoscanner {
    private Control controller;

    public Photoscanner(Control controller) {
        this.controller=controller;
    }   

    public void scanBelow(JSONObject decision) {//might need position as a parameter depending on how the scanBelow function works
        decision.put("action", "scan");
        controller.setCommand(Command.SCAN);
    }

}
