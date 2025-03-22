
package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import ca.mcmaster.se2aa4.island.team012.States.Status;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.Position;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.Positioning.CreekPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.EmergencyPosition;


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
    private FlightSystem flightSystem=new FlightSystem(heading);
    private Photoscanner photoscanner=new Photoscanner();
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
        this.currentStatus = Status.FIND_LENGTH_STATE;
        logger.info("Got here");
        droneBrain = new SimpleDroneBrain(this.drone, this.batteryLevel, this.dronePosition, this.heading);
        logger.info("Got here 11");
        resultsAcknowledger=new ResultsAcknowledger(this.batteryLevel, this.mapArea, drone, dronePosition, creekPosition, emergencyPosition,this.droneBrain);
        logger.info("Got here 12");
    }

    @Override
    public String takeDecision() {
        logger.info("Got here 1");
        JSONObject decision = new JSONObject();
        // unsure if these need to be created because
        logger.info("Got here 2");
        String catchDecision = droneBrain.makeDecision(decision); // should take state as well
        return catchDecision; // or something like this because I dont think we can 
    }

    @Override
    public void acknowledgeResults(String s){
        logger.info("Got here 20");
        resultsAcknowledger.updateValues(s);
        logger.info("** Response received:\n"+s);

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
        return "no creek found";
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
