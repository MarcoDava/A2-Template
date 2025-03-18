package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;

/**
 * When the drone is in this state, it will first save the position at where it
 * first started and start circling the island. It will keep the island on its
 * left side and only fly along the coastline. While it is doing this, it is
 * also finding the dimensions of the island. It will save how many rows it flew
 * and how many columns it flew. These dimensions will become relevant in the
 * next state. This state ends when the drone does one full loop
 */
public class CreekFindingState implements State {

    @Override
    public void handle(Drone drone, JSONObject decision, JSONObject parameters) {
    }
}
