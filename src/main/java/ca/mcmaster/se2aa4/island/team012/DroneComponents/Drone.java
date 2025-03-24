
package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.CreekPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.EmergencyPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.States.Status;
import eu.ace_design.island.bot.IExplorerRaid;


public class Drone implements IExplorerRaid{//reduce the amount of times that the dron changes heading, rarely use scan. 
//use echo to guide the drone. 
    private Status currentStatus;
    private SimpleDroneBrain droneBrain;
    private Drone drone;
    private DronePosition dronePosition;
    private CreekPosition creekPosition;
    private EmergencyPosition emergencyPosition;
    private Heading heading;
    private Battery batteryLevel;
    private Command action;
    private ResultsAcknowledger resultsAcknowledger;
    private MapArea mapArea;
    private Control controller;
    // private FlightSystem flightSystem=new FlightSystem();
    // private Photoscanner photoscanner=new Photoscanner();
    private final Logger logger = LogManager.getLogger();


    // public Drone(){
    //     currentStatus = Status.LOCATING_ISLAND_STATE;
    //     droneBrain = new SimpleDroneBrain(this.drone, this.batteryLevel, this.dronePosition);
    //     resultsAcknowledger=new ResultsAcknowledger(this.batteryLevel, this.mapArea, drone, dronePosition, creekPosition, emergencyPosition);
    // }


    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

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
        logger.info("The drone is facing {}", direction);

        batteryLevel = new Battery(info.getInt("budget"));
        logger.info("Battery level is {}", batteryLevel.getBattery());

        // logger.info("** Initializing the Exploration Command Center");
        // JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        // logger.info("** Initialization info:\n {}", info.toString(2));
        // String direction = info.getString("heading");
        // Integer batteryLevel = info.getInt("budget");
        // logger.info("The drone is facing {}", direction);
        // logger.info("Battery level is {}", batteryLevel);
        logger.info("The current status is {}", currentStatus);
        this.controller=new Control(Command.NEUTRAL);
        this.mapArea=new MapArea(new int[]{-1,-1});  
        dronePosition=new DronePosition(-1,-1);
        creekPosition=new CreekPosition(-1,-1);
        emergencyPosition=new EmergencyPosition(-1,-1);
        droneBrain = new SimpleDroneBrain(this.drone, this.batteryLevel, this.dronePosition, this.heading,this.controller, this.mapArea);
        logger.info("here");
        resultsAcknowledger=new ResultsAcknowledger(this.batteryLevel, this.heading, this.mapArea, this.drone, this.dronePosition, this.creekPosition, this.emergencyPosition,this.droneBrain, this.controller);
    }

    @Override
    public String takeDecision() {
        logger.info("here");
        JSONObject decision = new JSONObject(); // unsure if these need to be created because of below logic

        // **** assume actionQueue already set up as a queue of Strings in this (Drone) class with the below code -J****

        /*
         * import java.util.LinkedList;
         * import java.util.Queue;
         * 
         * Queue<String> actionQueue = new LinkedList<>();
         * // .add(String s) to enqueue
         * // .remove() to dequeue
         * // .peek() to peek at first in queue
        */
        /*
            // this is the implementation to go in this function -J
         * if (actionQueue.empty() = true) {
         *      actionQueue = dronebrain.makedecision(actionQueue);
         * }
         * // do next action
         *  String nextAction = actionQueue.remove();
         * 
         * return nextAction;
         */

        // below may be replaced by above
        droneBrain.makeDecision(decision);
        logger.info(decision.toString());
        return decision.toString();

    }

    @Override
    public void acknowledgeResults(String s){
        logger.info("** Response received:\n"+s);
        resultsAcknowledger.updateValues(s);

        // Integer cost = response.getInt("cost");
        // logger.info("The cost of the action was {}", cost);
        // pass in cost, convert to int, etc, then return -J
        // ex:
        // barreryStorageDummy = battryStorageDummy - processCost(cost)

        // String status = response.getString("status");
        // logger.info("The status of the drone is {}", status);
        // ?pass status into action status checker and do something if it was bad?
        // depends on what status's there are beyond error if its required -J

        // JSONObject extraInfo = response.getJSONObject("extras");
        // logger.info("Additional information received: {}", extraInfo);

        // storage may be formatted many ways, but something will exist to pass into and process that string'd JSON response -J
        // ex:
        // infoDummyStorage = updateInfo(infoDummyStorage, extraInfo)


        
    }

    @Override
    public String deliverFinalReport() {
        // return id of creek thats closest to 
        logger.info("** Delivering final report");
        logger.info("The closest creek is: {}", creekPosition.getCreekID());
        return creekPosition.getCreekID();
    }


    // non-required for interface, implemented by Marco:
    // public Status getStatus(){
    //     logger.info("Got here 10");
    //     return currentStatus;
    // }

    // public void setStatus(Status status){
    //     this.currentStatus=status;
    // }

    // public Command getCommand(){
    //     return action;
    // }

    // public void setCommand(Command action){
    //     this.action=action;
    // }
    
}
