package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

/**
 * This state is to find the emergency site. It will fly towards the center of
 * the island (calculated from previous state) and then start flying in a spiral
 * pattern going outwards. This will maximize the efficiency of the flight
 * patterns. This state will end when the emergency site is found or if the
 * drone starts going out of bounds.
 */
public class SpiralSearchState implements State {

    MapArea mapArea;
    private int startRow=1;
    private int startCol=1;
    private int endRow;
    private int endCol;
    
    public SpiralSearchState(MapArea mapArea) {
        this.mapArea = mapArea;
    }
    @Override
    public void handle(Drone drone, JSONObject decision, JSONObject parameters) {
        
    }

    private void shrinkSearchArea(){
        startRow++;
        startCol++;
        endRow--;
        endCol--;
    }
}
