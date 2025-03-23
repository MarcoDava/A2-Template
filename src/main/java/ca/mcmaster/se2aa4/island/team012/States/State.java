package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

public interface State {

    public void handle(JSONObject decision);
}
