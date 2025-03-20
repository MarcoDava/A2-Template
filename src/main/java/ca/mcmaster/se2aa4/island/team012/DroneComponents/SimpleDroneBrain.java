package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

public class SimpleDroneBrain extends DroneBrain {

    private Drone drone;
    private boolean initializedMap=false;
    private MapArea mapArea;
    private int startRow=1;
    private int startCol=1;
    private int endRow;
    private int endCol;

    public SimpleDroneBrain(Drone drone) {
        this.drone=drone;
    }

    @Override
    public void makeDecision(JSONObject parameters, JSONObject decision) {
        if(dronePosition.getDronePosition()){
            
        }
    }
    private void shrinkSearchArea(){
        startRow++;
        startCol++;
        endRow--;
        endCol--;
    }
}
