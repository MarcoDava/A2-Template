package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.States.EndSearchState;
import ca.mcmaster.se2aa4.island.team012.States.FindLengthState;
import ca.mcmaster.se2aa4.island.team012.States.FindWidthState;
import ca.mcmaster.se2aa4.island.team012.States.SpiralSearchState;
import ca.mcmaster.se2aa4.island.team012.States.State;
import ca.mcmaster.se2aa4.island.team012.States.Status;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.States.DimensionAlignState;


/**
 * This class is responsible for the decision making of the drone
 */
public class SimpleDroneBrain extends DroneBrain {
    private Status currentStatus;
    private State currentState;
    private State DimensionAlignState;
    private State findLengthState;
    private State findWidthState;
    private State spiralSearchState;
    private State endSearchState;
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

    /*
     * This is the constructor for the SimpleDroneBrain class
     * 
     * @param drone the drone
     * @param battery the battery of the drone
     * @param dronePosition the position of the drone
     * @param heading the heading of the drone
     * @param controller the controller of the drone
     * @param mapArea the mapArea of the drone
     */
    public SimpleDroneBrain(Drone drone,Battery battery,DronePosition dronePosition,Heading heading,Control controller,MapArea mapArea) {
        this.drone=drone;
        this.battery=battery;
        this.dronePosition=dronePosition;
        this.controller=controller;
        this.heading=heading;
        this.mapArea=mapArea;

        currentStatus=Status.FIND_LENGTH_STATE;

        DimensionAlignState = new DimensionAlignState(this.heading,this.controller);
        findLengthState = new FindLengthState(this.heading,this.controller);
        findWidthState = new FindWidthState(this.heading,this.controller);
        spiralSearchState = new SpiralSearchState(this.mapArea,this.dronePosition,this.controller,this.heading);
        endSearchState=new EndSearchState();
        droneRetriever= new DroneRetrieval(this.drone,this.mapArea,this.battery,this.dronePosition);
    }

    /*
     * This function will make a decision for the drone
     * 
     * @param decision the decision to be made
     * @return the decision to be made
     */
    @Override
    public String makeDecision(JSONObject decision) {
        if (this.droneRetriever.dangerAssesment() != DangerType.NEUTRAL) { // checks if run out of battery or out of search area 
            this.droneRetriever.handleDanger(decision, droneRetriever.dangerAssesment()); // prevents drone crashing - Marco to fix
        } 
        else { // process action based on state, as no risk
            logger.info("Got here 9");
            switch (currentStatus) {
                case DIMENSION_ALIGN_STATE:
                    logger.info("STATE STATUS " + Status.FIND_LENGTH_STATE);
                    this.currentState = this.DimensionAlignState;
                    break;

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

                case END_SEARCH_STATE:
                    logger.info("STATE STATUS " + Status.END_SEARCH_STATE);
                    this.currentState = this.endSearchState;
                    break;

                default:
                    break;
            }
            this.currentState.handle(decision);
            
        }
        logger.info(decision.toString());
        return decision.toString();
    }
    
    /*
     * This function will return the current status of the drone
     * 
     * @return the current status of the drone
     */
    public Status getStatus(){
        return currentStatus;
    }
    
    /*
     * This function will set the current status of the drone
     * 
     * @param status the status to be set
     */
    public void setStatus(Status status){
        this.currentStatus=status;
    }

    /*
     * This function will return the current state of the drone
     * 
     * @return the current state of the drone
     */
    public Command getCommand(){
        return action;
    }

    /*
     * This function will set the current state of the drone
     * 
     * @param action the state to be set
     */
    public void setCommand(Command action){
        this.action=action;
    }


}
