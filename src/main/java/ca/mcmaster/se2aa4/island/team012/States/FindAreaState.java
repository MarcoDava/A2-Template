package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Position;

public class FindAreaState implements State{
    Position dronePosition;
    public FindAreaState(){
        dronePosition = new DronePosition();
    }
    @Override
    public void handle(Drone drone,JSONObject decision,JSONObject parameters){
        radar.scanForward()
        radar.scanRight()
        //will scan east and west to determine the area in which the drone can fly
    }

}
