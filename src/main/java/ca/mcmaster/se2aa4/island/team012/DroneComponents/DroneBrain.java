package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.States.FindAreaState;
import ca.mcmaster.se2aa4.island.team012.States.ApproachIslandState;
import ca.mcmaster.se2aa4.island.team012.States.CreekFindingState;
import ca.mcmaster.se2aa4.island.team012.States.LocatingIslandState;
import ca.mcmaster.se2aa4.island.team012.States.SpiralSearchState;
import ca.mcmaster.se2aa4.island.team012.States.State;
import ca.mcmaster.se2aa4.island.team012.States.Status;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

public class DroneBrain {

    private Status currentStatus;
    private State currentState;//we might need to make a seperate class called decision maker to split up methods. 
    private State approachIsland;
    private State creekFinding;
    private State spiralSearch;
    private State locatingIsland;
    private State findAreaState;
    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private MapArea mapArea;
    private DroneRetrieval droneRetriever = new DroneRetrieval(drone,mapArea);

    public DroneBrain(Drone drone) {
        this.drone = drone;
        findAreaState = new FindAreaState(mapArea);
        approachIsland = new ApproachIslandState();
        creekFinding = new CreekFindingState();
        spiralSearch = new SpiralSearchState();
        locatingIsland = new LocatingIslandState();
        currentState = locatingIsland;
    }

    public void makeDecision(JSONObject parameters, JSONObject decision) {
        if (this.droneRetriever.dangerAssesment()) {
            this.droneRetriever.handleDanger(decision, parameters);
        } else {
            switch (drone.getStatus()) {
                case FIND_AREA_STATE:
                    logger.info("STATE STATUS " + Status.FIND_AREA_STATE);
                    this.currentState = this.findAreaState;
                    break;
            }
            this.currentState.handle(drone, decision, parameters);
        }
    }
}
