package ca.mcmaster.se2aa4.island.team012.States;

import java.util.ResourceBundle.Control;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;

// This state is just where the drone flies towards the island.
public class ApproachIslandState implements State {
    private MapArea mapArea;
    private int mapCenterRow;
    private int mapCenterCol;
    private DronePosition dronePosition;
    private Heading heading;
    private FlightSystem flightSystem;
    private Control controller;

    public ApproachIslandState(MapArea mapArea,DronePosition dronePosition,Heading heading,Control controller){ 
        this.mapArea = mapArea;
        this.dronePosition=dronePosition;
        this.heading=heading;
        mapCenterRow=mapArea.getRows()/2;
        mapCenterCol=mapArea.getCols()/2;
        this.controller=controller;
    }
    @Override
    public String handle(Drone drone, JSONObject decision) {
        if(dronePosition.getRow()!=mapCenterRow-1 && dronePosition.getRow()!=mapCenterRow+1 && dronePosition.getRow()!=mapCenterRow){//if the drone is not 1 above, 1 below or at the center row, it will execute the below code.
            //the reason for these particular conditions is that the drone will always be at the center row even when it turns, because a turn is one forward and one in the direction of the turn.
            if(dronePosition.getRow()-mapCenterRow>0){//this means that the droneposition is above the center row
                if(heading.compareHeading(Direction.N)){
                    flightSystem.turnRight(heading, decision);
                }
                else if(heading.compareHeading(Direction.E)){
                    flightSystem.turnRight(heading, decision);
                }
                else if(heading.compareHeading(Direction.S)){
                    flightSystem.fly(heading, decision);
                }
                else if(heading.compareHeading(Direction.W)){
                    flightSystem.turnLeft(heading, decision);
                }
            }
            else if(dronePosition.getRow()-mapCenterRow<0){//this means that the droneposition is below the center row
                if(heading.compareHeading(Direction.N)){
                    flightSystem.fly(heading, decision);
                }
                else if(heading.compareHeading(Direction.E)){
                    flightSystem.turnLeft(heading, decision);
                }
                else if(heading.compareHeading(Direction.S)){
                    flightSystem.turnLeft(heading, decision);
                }
                else if(heading.compareHeading(Direction.W)){
                    flightSystem.turnRight(heading, decision);
                }
            }
        } 
        else{
            if(dronePosition.getCol()-mapCenterCol>0){//this means that the droneposition is to the right of the center col
                if(heading.compareHeading(Direction.N)){
                    flightSystem.turnRight(heading, decision);
                }
                else if(heading.compareHeading(Direction.E)){
                    flightSystem.turnRight(heading, decision);
                }
                else if(heading.compareHeading(Direction.S)){
                    flightSystem.turnLeft(heading, decision);
                }
                else if(heading.compareHeading(Direction.W)){
                    flightSystem.fly(heading, decision);
                }
            }
            else if(dronePosition.getCol()-mapCenterCol<0){//this means that the droneposition is to the left of the center col
                if(heading.compareHeading(Direction.N)){
                    flightSystem.turnLeft(heading, decision);
                }
                else if(heading.compareHeading(Direction.E)){
                    flightSystem.fly(heading, decision);
                }
                else if(heading.compareHeading(Direction.S)){
                    flightSystem.turnRight(heading, decision);
                }
                else if(heading.compareHeading(Direction.W)){
                    flightSystem.turnLeft(heading, decision);
                }
            }
        }
        
        return decision.toString();
    }
}
// 
