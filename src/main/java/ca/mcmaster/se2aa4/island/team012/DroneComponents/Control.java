package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Control {
    private Command action;
    private final Logger logger = LogManager.getLogger();
    
    public Control(Command action){
        logger.info("command recieved");
        this.action=action;
    }

    public Command getCommand(){
        return action;
    }
    public void setCommand(Command action){
        this.action=action;
    }
    public boolean compareAction(Command action){
        return this.action==action;
    }
}
