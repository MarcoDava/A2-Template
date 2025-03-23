package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

public interface State {

    String handle(JSONObject decision);
}
