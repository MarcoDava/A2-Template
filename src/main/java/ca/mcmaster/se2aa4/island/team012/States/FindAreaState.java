package ca.mcmaster.se2aa4.island.team012.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Drone;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.Radar;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.Positioning.Position;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;

public class FindAreaState implements State {

    private MapArea mapArea;
    private Radar radar = new Radar();
    private Heading heading;
    private Position dronePosition;
    private final int ROW=1;
    private final int COL=1;
    private int counter=0;
    private int mapX=0;
    private int mapY=0;

    public FindAreaState(MapArea mapArea) {
        this.mapArea = mapArea;
    }

    @Override
    public void handle(Drone drone, JSONObject decision, JSONObject parameters) {
        if(counter==0){
            mapX=radar.scanForward((Heading)heading.getValue());
        }
        else if(counter==1){
            mapY=radar.scanRight((Heading)heading.getValue());
        }
        else{
            mapArea.setMapArea(mapX,mapY);
            dronePosition=new DronePosition(ROW, COL);
        }
        //this algorithm assumes that we start at 1,1
        //will scan east and west to determine the area in which the drone can fly for the mvp
        
        //this is the starting position of the drone, assumed in the MVP
        //for the final, because we may not start at 1,1. This state should continue to fly until it can find a line that doesnt touch the land
        //this state should find the out of bounds, so the scan should be scanning the out of range and not land. 
    }

}
