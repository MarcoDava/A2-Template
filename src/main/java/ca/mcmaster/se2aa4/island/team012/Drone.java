package ca.mcmaster.se2aa4.island.team012;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Drone implements IExplorerRaid{//reduce the amount of times that the dron changes heading, rarely use scan. 
//use echo to guide the drone. 
    private Heading heading=new Heading(Direction.E);//might switch to a string instead of enum
    private Battery battery=new Battery(100);//will need to get the correct values
    private FlightSystem flightSystem=new FlightSystem(battery, heading);
    private Photoscanner photoscanner=new Photoscanner();
    private final Logger logger = LogManager.getLogger();
    public Drone(){

    }
    public boolean gridSearch(){
        for(int i=0;i<10;i++){
            flightSystem.fly();
            photoscanner.scanBelow();
        }
        flightSystem.turnNorth();
        flightSystem.fly();
        photoscanner.scanBelow();
        flightSystem.turnWest();
        for(int i=0;i<10;i++){
            flightSystem.fly();
            photoscanner.scanBelow();
        }
        flightSystem.turnSouth();
        flightSystem.fly();
        photoscanner.scanBelow();
        flightSystem.turnEast();
        for(int i=0;i<10;i++){
            flightSystem.fly();
            photoscanner.scanBelow();
        }
        flightSystem.stop();
        return true;
    }

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        decision.put("action", "stop"); // we stop the exploration immediately
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

    public static void main(String[] args) {
        Drone drone = new Drone();
        if(drone.photoscanner.scanBelow()){
            drone.logger.info("Scanned below");
        }
    }
    
}