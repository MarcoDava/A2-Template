package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.States.FindLengthState;
import ca.mcmaster.se2aa4.island.team012.States.FindWidthState;
import ca.mcmaster.se2aa4.island.team012.States.SpiralSearchState;
import ca.mcmaster.se2aa4.island.team012.States.State;
import ca.mcmaster.se2aa4.island.team012.States.Status;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;

public class SimpleDroneBrain extends DroneBrain {
    private Status currentStatus;
    private State currentState;
    private State findLengthState;
    private State findWidthState;
    private State spiralSearchState;
    private Drone drone;
    private MapArea mapArea;
    private DroneRetrieval droneRetriever;
    private Battery battery;
    private DronePosition dronePosition;
    private Command action;
    private Heading heading;
    private SimpleDroneBrain droneBrain;
    private Control controller;
    private static final Logger logger = LogManager.getLogger();

    public SimpleDroneBrain(Drone drone,Battery battery,DronePosition dronePosition,Heading heading,Control controller,MapArea mapArea) {
        this.drone=drone;
        this.battery=battery;
        this.dronePosition=dronePosition;
        this.controller=controller;
        this.heading=heading;
        this.mapArea=mapArea;
        currentStatus=Status.FIND_LENGTH_STATE;
        findLengthState = new FindLengthState(this.mapArea,this.drone,this.heading,this.controller);
        findWidthState = new FindWidthState(this.mapArea,this.drone,this.heading,this.controller);
        spiralSearchState = new SpiralSearchState(this.mapArea,this.dronePosition,this.controller,this.heading);
        droneRetriever= new DroneRetrieval(this.drone,this.mapArea,this.battery,this.dronePosition);
    }

    @Override

    public String makeDecision(JSONObject decision) {
        if (this.droneRetriever.dangerAssesment() != DangerType.NEUTRAL) {
            this.droneRetriever.handleDanger(decision, droneRetriever.dangerAssesment());
        } else {
            logger.info("Got here 9");
            switch (currentStatus) {
                case FIND_LENGTH_STATE:
                    logger.info("Got here 10");
                    logger.info("STATE STATUS " + Status.FIND_LENGTH_STATE);
                    logger.info("Got here 11");
                    this.currentState = this.findLengthState;
                    logger.info("Got here 12");
                    break;
                case FIND_WIDTH_STATE:
                    logger.info("STATE STATUS " + Status.FIND_WIDTH_STATE);
                    this.currentState = this.findWidthState;
                    break;
                case SPIRAL_SEARCH_STATE:
                    logger.info("STATE STATUS " + Status.SPIRAL_SEARCH_STATE);
                    this.currentState = this.spiralSearchState;
                    break;
                default:
                    break;
            }
            logger.info("Got here 13");
            this.currentState.handle(drone, decision);
            logger.info("Got here 14");
        }
        logger.info(decision.toString());
        return decision.toString();
    }
    
    public Status getStatus(){
        return currentStatus;
    }

    public void setStatus(Status status){
        this.currentStatus=status;
    }

    public Command getCommand(){
        return action;
    }

    public void setCommand(Command action){
        this.action=action;
    }


}
