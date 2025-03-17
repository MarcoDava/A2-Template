package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Position;

public class FindAreaState implements State{
    int[] mapArea;
    MapArea 
    Position dronePosition;
    public FindAreaState(){
        dronePosition = new DronePosition();
    }
    @Override
    public void handle(Drone drone,JSONObject decision,JSONObject parameters){
        mapX=radar.scanForward()
        mapY=radar.scanRight()//this algorithm assumes that we start at 1,1
        //will scan east and west to determine the area in which the drone can fly for the mvp
        mapArea=new int[]{mapX,mapY}

        //for the final, because we may not start at 1,1. This state should continue to fly until it can find a line that doesnt touch the land
        //this state should find the out of bounds, so the scan should be scanning the out of range and not land. 
    }

}
