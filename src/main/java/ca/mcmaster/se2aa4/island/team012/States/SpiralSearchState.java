package ca.mcmaster.se2aa4.island.team012.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Photoscanner;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Command;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;

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
    private Photoscanner photoScanner=new Photoscanner();
    private DronePosition dronePosition;
    private Control controller;
    private Heading heading;
    private static final Logger logger = LogManager.getLogger();

    public SpiralSearchState(MapArea mapArea,DronePosition dronePosition, Control controller, Heading heading) {
        this.mapArea = mapArea;
        this.controller = controller;
        this.dronePosition=dronePosition;
        this.heading=heading;
    }
    @Override
    public String handle(Drone drone, JSONObject decision) {
        logger.info("scanning below");

        if(counter%2==0){
            logger.info("executing if statement");
            photoScanner.scanBelow(decision);
            controller.setCommand(Command.SCAN);
        }
        else{
            if(dronePosition.getRow()==startRow+1||dronePosition.getRow()==endRow-1||dronePosition.getCol()==startCol+1||dronePosition.getCol()==endCol-1){
                flightSystem.turnRight(decision);
                numberOfTurns=(numberOfTurns+1)%4;
                if(numberOfTurns==3){
                    shrinkSearchArea();
                    numberOfTurns=0;
                }
            }
            else{
                flightSystem.fly(decision);
            }
        }
        counter=(counter+1)%2;
        return decision.toString();
    }

    private void shrinkSearchArea(){
        startRow++;
        startCol++;
        endRow--;
        endCol--;
    }
}
