package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.CreekPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.StartingPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.EmergencyPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import eu.ace_design.island.bot.IExplorerRaid;

/**
 * Represents the main drone class that implements the IExplorerRaid interface.
 * Handles initialization, decision-making, and result acknowledgment.
 */
public class Drone implements IExplorerRaid{//reduce the amount of times that the dron changes heading, rarely use scan. 
    private DroneBrain droneBrain;
    private DronePosition dronePosition;
    private CreekPosition creekPosition;
    private EmergencyPosition emergencyPosition;
    private Heading heading;
    private Battery batteryLevel;
    private StartingPosition startingPosition;
    private ResultsAcknowledger resultsAcknowledger;
    private MapArea mapArea;
    private Control controller;

    /**
     * Initializes the drone with the given configuration string.
     * 
     * @param s The configuration string in JSON format.
     */
    @Override
    public void initialize(String s) {
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));

        String direction = info.getString("heading");
        if (direction.equals("N")) {
            heading=new Heading(Direction.N);
        } else if (direction.equals("E")) {
            heading=new Heading(Direction.E);
        } else if (direction.equals("S")) {
            heading=new Heading(Direction.S);
        } else if (direction.equals("W")) {
            heading=new Heading(Direction.W);
        }

        batteryLevel = new Battery(info.getInt("budget"));
        this.controller=new Control(Command.NEUTRAL);
        this.mapArea=new MapArea(new int[]{-1,-1});  
        dronePosition=new DronePosition(-1,-1);
        startingPosition=new StartingPosition(-1,-1);
        creekPosition=new CreekPosition(-1,-1);
        emergencyPosition=new EmergencyPosition(-1,-1);
        droneBrain = new SimpleDroneBrain( this.batteryLevel, this.dronePosition, this.startingPosition, this.heading,this.controller, this.mapArea);
        resultsAcknowledger=new ResultsAcknowledger(this.batteryLevel, this.heading, this.mapArea, this.dronePosition, this.startingPosition, this.creekPosition, this.emergencyPosition,this.droneBrain, this.controller);
    }

    /**
     * Makes a decision for the drone's next action.
     * 
     * @return The decision as a string.
     */
    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();

        droneBrain.makeDecision(decision);
        return decision.toString();

    }

    /**
     * Acknowledges the results of the last action taken by the drone.
     * 
     * @param s The result string in JSON format.
     */
    @Override
    public void acknowledgeResults(String s){
        resultsAcknowledger.updateValues(s);

        
    }

    /**
     * Delivers the final report of the drone's exploration.
     * 
     * @return The ID of the closest creek.
     */
    @Override
    public String deliverFinalReport() {
        // return id of creek thats closest to 
        return this.creekPosition.getCreekID();
    }
    
}
