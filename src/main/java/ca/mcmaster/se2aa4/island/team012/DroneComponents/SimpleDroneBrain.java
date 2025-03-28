package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.StartingPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.States.ApproachIslandState;
import ca.mcmaster.se2aa4.island.team012.States.EndSearchState;
import ca.mcmaster.se2aa4.island.team012.States.FindLengthState;
import ca.mcmaster.se2aa4.island.team012.States.FindWidthState;
import ca.mcmaster.se2aa4.island.team012.States.LeftTurnState;
import ca.mcmaster.se2aa4.island.team012.States.LengthAlignState;
import ca.mcmaster.se2aa4.island.team012.States.RightTurnState;
import ca.mcmaster.se2aa4.island.team012.States.SpiralFromMiddleState;
import ca.mcmaster.se2aa4.island.team012.States.State;
import ca.mcmaster.se2aa4.island.team012.States.Status;
import ca.mcmaster.se2aa4.island.team012.States.WidthAlignState;


/**
 * This class is responsible for the decision-making logic of the drone.
 * It manages the drone's states and actions based on its current status.
 */
public class SimpleDroneBrain extends DroneBrain {
    // Fields to store the current status, state, and various components of the drone.
    private Status currentStatus;
    private State currentState;
    private State lengthAlignState;
    private State findLengthState;
    private State rightTurnState;
    private State leftTurnState;
    private State widthAlignState;
    private State findWidthState;
    private State approachIslandState;
    private State spiralFromMiddleState;
    private State spiralFromSiteState;
    private State endSearchState;
    private MapArea mapArea;
    private DroneRetrieval droneRetriever;
    private Battery battery;
    private DronePosition dronePosition;
    private StartingPosition startingPosition;
    private Heading heading;
    private Control controller;

    /**
     * Constructor to initialize the SimpleDroneBrain with necessary components.
     * 
     * @param drone The drone instance.
     * @param battery The battery of the drone.
     * @param dronePosition The current position of the drone.
     * @param startingPosition The starting position of the drone.
     * @param heading The heading direction of the drone.
     * @param controller The controller for managing drone commands.
     * @param mapArea The map area being explored.
     */
    public SimpleDroneBrain(Battery battery, DronePosition dronePosition, StartingPosition startingPosition, Heading heading, Control controller, MapArea mapArea) {
        // Initialize fields with provided parameters.
        this.battery = battery;
        this.dronePosition = dronePosition;
        this.startingPosition = startingPosition;
        this.controller = controller;
        this.heading = heading;
        this.mapArea = mapArea;

        // Set the initial status of the drone.
        currentStatus = Status.LENGTH_ALIGN_STATE;

        // Initialize states with the required components.
        lengthAlignState = new LengthAlignState(this.heading, this.controller, this.dronePosition);
        findLengthState = new FindLengthState(this.heading, this.controller);
        leftTurnState = new LeftTurnState(this.heading, this.controller, this.dronePosition);
        rightTurnState = new RightTurnState(this.heading, this.controller, this.dronePosition);
        widthAlignState = new WidthAlignState(this.heading, this.controller, this.dronePosition);
        findWidthState = new FindWidthState(this.heading, this.controller, this.dronePosition);
        approachIslandState = new ApproachIslandState(this.mapArea, this.dronePosition, this.heading, this.controller);
        spiralFromMiddleState = new SpiralFromMiddleState(this.dronePosition, this.controller, this.heading);
        spiralFromSiteState = new SpiralFromMiddleState(this.dronePosition, this.controller, this.heading);
        endSearchState = new EndSearchState(this.dronePosition, this.controller);

        // Initialize the drone retriever for handling danger scenarios.
        droneRetriever = new DroneRetrieval(this.mapArea, this.battery, this.dronePosition, this.startingPosition, this.controller, this.heading);
    }

    /**
     * Makes a decision for the drone based on its current state and status.
     * 
     * @param decision The JSON object to store the decision.
     * @return The decision as a string.
     */
    @Override
    public String makeDecision(JSONObject decision) {
        // Check for any danger and handle it if necessary.
        if (this.droneRetriever.dangerAssesment() != DangerType.NEUTRAL) { 
            this.droneRetriever.handleDanger(decision, droneRetriever.dangerAssesment());
        } else { 
            // Process action based on the current state if no danger is detected.
            switch (currentStatus) {
                case LENGTH_ALIGN_STATE:
                    this.currentState = this.lengthAlignState;
                    break;
                case FIND_LENGTH_STATE:
                    this.currentState = this.findLengthState;
                    break;
                case LEFT_TURN_STATE:
                    this.currentState = this.leftTurnState;
                    break;
                case RIGHT_TURN_STATE:
                    this.currentState = this.rightTurnState;
                    break;
                case WIDTH_ALIGN_STATE:
                    this.currentState = this.widthAlignState;
                    break;
                case FIND_WIDTH_STATE:
                    this.currentState = this.findWidthState;
                    break;
                case APPROACH_ISLAND_STATE:
                    this.currentState = this.approachIslandState;
                    break;
                case SPIRAL_FROM_MIDDLE_STATE:
                    this.currentState = this.spiralFromMiddleState;
                    break;
                case SPIRAL_FROM_SITE_STATE:
                    this.currentState = this.spiralFromSiteState;
                    break;
                case END_SEARCH_STATE:
                    this.currentState = this.endSearchState;
                    break;
                default:
                    break;
            }
            // Handle the current state and update the decision.
            this.currentState.handle(decision);
        }
        return decision.toString();
    }
    
    /**
     * Gets the current status of the drone.
     * 
     * @return The current status.
     */
    public Status getStatus() {
        return currentStatus;
    }
    
    /**
     * Sets the current status of the drone.
     * 
     * @param status The new status to set.
     */
    public void setStatus(Status status) {
        this.currentStatus = status;
    }
}
