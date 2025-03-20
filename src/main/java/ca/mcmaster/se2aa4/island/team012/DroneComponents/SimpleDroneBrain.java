package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.States.State;
import ca.mcmaster.se2aa4.island.team012.States.SpiralSearchState;
import ca.mcmaster.se2aa4.island.team012.States.FindAreaState;
import ca.mcmaster.se2aa4.island.team012.States.Status;

import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;

public class SimpleDroneBrain extends DroneBrain {
    private State currentState;
    private State findAreaState;
    private State spiralSearchState;
    private Drone drone;
    private MapArea mapArea;
    DroneRetrieval droneRetriever = new DroneRetrieval(drone,mapArea);
    private static final Logger logger = LogManager.getLogger();

    public SimpleDroneBrain(Drone drone) {
        this.drone=drone;
    }

    @Override

    public void makeDecision(JSONObject parameters, JSONObject decision) {
        if (this.droneRetriever.dangerAssesment() != DangerType.NEUTRAL) {
            this.droneRetriever.handleDanger(decision, parameters,droneRetriever.dangerAssesment());
        } else {
            switch (drone.getStatus()) {
                case FIND_AREA_STATE:
                    logger.info("STATE STATUS " + Status.FIND_AREA_STATE);
                    this.currentState = this.findAreaState;
                    break;
                case SPIRAL_SEARCH_STATE:
                    logger.info("STATE STATUS " + Status.FIND_AREA_STATE);
                    this.currentState = this.findAreaState;
                    break;
                default:
                    break;
            }
            this.currentState.handle(drone, decision, parameters);
        }
    }


}
