package ca.mcmaster.se2aa4.island.team012;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Drone{//reduce the amount of times that the dron changes heading, rarely use scan. 
//use echo to guide the drone. 
    private Heading heading=Heading.E;//might switch to a string instead of enum
    private Battery battery=new Battery(100);
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
    
}