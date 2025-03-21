
package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import ca.mcmaster.se2aa4.island.team012.States.Status;
import ca.mcmaster.se2aa4.island.team012.States.State;
import ca.mcmaster.se2aa4.island.team012.States.ApproachIslandState;
import ca.mcmaster.se2aa4.island.team012.States.CreekFindingState;
import ca.mcmaster.se2aa4.island.team012.States.SpiralSearchState;
import ca.mcmaster.se2aa4.island.team012.States.LocatingIslandState;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.DroneObserver;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.Position;


public class Drone implements IExplorerRaid{//reduce the amount of times that the dron changes heading, rarely use scan. 
//use echo to guide the drone. 
    private Status currentStatus;
    private DroneBrain droneBrain;
    private Drone drone;
    private DronePosition dronePosition;
    private Heading heading;
    private Battery batteryLevel;

    private FlightSystem flightSystem=new FlightSystem(batteryLevel, heading);
    private Photoscanner photoscanner=new Photoscanner();
    private final Logger logger = LogManager.getLogger();


    public Drone(){
        currentStatus = Status.LOCATING_ISLAND_STATE;
        DroneBrain droneBrain = new SimpleDroneBrain(this.drone, this.batteryLevel, this.dronePosition);
    }


    @Override
    public void initialize(String s) {
        DroneObserver observer = new DroneObserver();
        batteryLevel.addObserver(observer);
        heading.addObserver(observer);

        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        info.getString("position");
        dronePosition = new DronePosition(1,1);// need to fill in row and column

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
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject(); 
        JSONObject parameters = new JSONObject();
        // unsure if these need to be created because

        String catchDecision = droneBrain.makeDecision(parameters, decision); // should take state as well
        return catchDecision; // or something like this because I dont think we can 
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));

        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        // pass in cost, convert to int, etc, then return -J
        // ex:
        // barreryStorageDummy = battryStorageDummy - processCost(cost)


        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        // ?pass status into action status checker and do something if it was bad?
        // depends on what status's there are beyond error if its required -J

        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

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
    public Status getStatus(){
        return currentStatus;
    }
    
}
