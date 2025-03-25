package ca.mcmaster.se2aa4.island.team012.States;


import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Control;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.FlightSystem;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Photoscanner;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

// This state is just where the drone flies towards the island.
public class ApproachIslandState implements State {
    private MapArea mapArea;
    private DronePosition dronePosition;
    private Heading heading;
    private FlightSystem flightSystem;
    private Photoscanner photoScanner;



    public ApproachIslandState(MapArea mapArea,DronePosition dronePosition,Heading heading,Control controller){ 
        this.mapArea = mapArea;
        this.dronePosition=dronePosition;
        this.heading=heading;
        flightSystem=new FlightSystem(this.dronePosition,controller);
        photoScanner=new Photoscanner(controller);
    }
    @Override
    public void handle(JSONObject decision) {
        int mapCenterRow=mapArea.getRows()/2;
        int mapCenterCol=mapArea.getCols()/2;
    
        if(dronePosition.getRow()!=mapCenterRow&&dronePosition.getRow()!=mapCenterRow-1&&dronePosition.getRow()!=mapCenterRow+1){//if the drone is not 1 above, 1 below or at the center row, it will execute the below code.
            //the reason for these particular conditions is that the drone will always be at the center row even when it turns, because a turn is one forward and one in the direction of the turn.
            if(dronePosition.getRow()-mapCenterRow<0){//this means that the droneposition is above the center row
                if(heading.compareHeading(Direction.N)){
                    if(dronePosition.getCol()-mapCenterCol<0){
                        flightSystem.turnRight(heading, decision);
                    }
                    else{
                        flightSystem.turnLeft(heading, decision);
                    }
                    
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
            else if(dronePosition.getRow()-mapCenterRow>0){//this means that the droneposition is below the center row
                if(heading.compareHeading(Direction.N)){
                    flightSystem.fly(heading, decision);
                }
                else if(heading.compareHeading(Direction.E)){
                    flightSystem.turnLeft(heading, decision);
                }
                else if(heading.compareHeading(Direction.S)){
                    if(dronePosition.getCol()-mapCenterCol<0){
                        flightSystem.turnLeft(heading, decision);
                    }
                    else{
                        flightSystem.turnRight(heading, decision);
                    }
                }
                else if(heading.compareHeading(Direction.W)){
                    flightSystem.turnRight(heading, decision);
                }
            }
        } 
        else{
            if(dronePosition.getCol()-mapCenterCol<0){//this means that the droneposition is to the left of the center col
                if(heading.compareHeading(Direction.N)){
                    flightSystem.turnRight(heading, decision);
                }
                else if(heading.compareHeading(Direction.E)){
                    flightSystem.fly(heading, decision);
                }
                else if(heading.compareHeading(Direction.S)){
                    flightSystem.turnLeft(heading, decision);
                }
                else if(heading.compareHeading(Direction.W)){
                    flightSystem.turnLeft(heading, decision);
                }
            }
            else if(dronePosition.getCol()-mapCenterCol>0){//this means that the droneposition is to the right of the center col
                if(heading.compareHeading(Direction.N)){
                    flightSystem.turnLeft(heading, decision);
                }
                else if(heading.compareHeading(Direction.E)){
                    flightSystem.turnLeft(heading, decision);
                }
                else if(heading.compareHeading(Direction.S)){
                    flightSystem.turnRight(heading, decision);
                }
                else if(heading.compareHeading(Direction.W)){
                    flightSystem.fly(heading, decision);
                }
            }
        }
    
    }
        
}
// 
