package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Photoscanner;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

/**
 * This state is to find the emergency site. It will fly towards the center of
 * the island (calculated from previous state) and then start flying in a spiral
 * pattern going outwards. This will maximize the efficiency of the flight
 * patterns. This state will end when the emergency site is found or if the
 * drone starts going out of bounds.
 */
public class SpiralSearchState implements State {

    private MapArea mapArea;
    private int counter=0;
    private int numberOfTurns=0;
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;
    private FlightSystem flightSystem;
    private Photoscanner photoScanner;
    private DronePosition dronePosition;

    public SpiralSearchState(MapArea mapArea) {
        this.mapArea = mapArea;

        endRow=mapArea.getRows()-1;
        endCol=mapArea.getCols()-1;
    }
    @Override
    public void handle(Drone drone, JSONObject decision, JSONObject parameters) {

        if(counter%2==0){
            photoScanner.scanBelow();
        }
        else{
            if(dronePosition.getDronePosition()[0]==startRow+1||dronePosition.getDronePosition()[0]==endRow-1||dronePosition.getDronePosition()[1]==startCol+1||dronePosition.getDronePosition()[1]==endCol-1){
                flightSystem.turnRight();
                numberOfTurns=(numberOfTurns+1)%4;
                if(numberOfTurns==3){
                    shrinkSearchArea();
                    numberOfTurns=0;
                }
            }
            else{
                flightSystem.fly();
            }
        }
        counter=(counter+1)%2;
    }

    private void shrinkSearchArea(){
        startRow++;
        startCol++;
        endRow--;
        endCol--;
    }
}
