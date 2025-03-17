package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.States.ApproachIslandState;
import ca.mcmaster.se2aa4.island.team012.States.CreekFindingState;
import ca.mcmaster.se2aa4.island.team012.States.LocatingIslandState;
import ca.mcmaster.se2aa4.island.team012.States.SpiralSearchState;
import ca.mcmaster.se2aa4.island.team012.States.State;
import ca.mcmaster.se2aa4.island.team012.States.Status;
import eu.ace_design.island.bot.IExplorerRaid;

public class DroneBrain  {
    private Status currentStatus;
    private State currentState;//we might need to make a seperate class called decision maker to split up methods. 
    private State approachIsland;
    private State creekFinding;
    private State spiralSearch;
    private State locatingIsland;
    private final Logger logger = LogManager.getLogger();
    public DroneBrain(){
        currentStatus = Status.LOCATING_ISLAND_STATE;
        approachIsland=new ApproachIslandState();
        creekFinding=new CreekFindingState();
        spiralSearch=new SpiralSearchState();
        locatingIsland=new LocatingIslandState();
        currentState = locatingIsland;
    }

    public void makeDecision(JSONObject parameters, JSONObject decision) {
        //will have swtich case here to use the different states
    }
}
