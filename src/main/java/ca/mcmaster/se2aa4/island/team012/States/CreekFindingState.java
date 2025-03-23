package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

/**
 * When the drone is in this state, it will first save the position at where it
 * first started and start circling the island. It will keep the island on its
 * left side and only fly along the coastline. While it is doing this, it is
 * also finding the dimensions of the island. It will save how many rows it flew
 * and how many columns it flew. These dimensions will become relevant in the
 * next state. This state ends when the drone does one full loop
 */

//if radar scanforward returns 1, then we turn right.
//if radar scan left returns more than 0, then we turn left
//if radar scan left, right and forward return 0, then we do 
//a 180. this is done by turning right 3 times and then left once.
//if we accidentally go into land because of turns, turn right twice.
//we need an efficient way of storing commands.will use boolean variables 
//to complete a command

public class CreekFindingState implements State {
    MapArea mapArea;
    public CreekFindingState(MapArea mapArea) {
        this.mapArea = mapArea;
    }
    @Override
    public String handle(JSONObject decision) {
        return decision.toString();
    }
}
