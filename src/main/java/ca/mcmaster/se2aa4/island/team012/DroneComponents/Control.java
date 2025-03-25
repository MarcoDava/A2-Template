package ca.mcmaster.se2aa4.island.team012.DroneComponents;



public class Control { // stores the ENUM of Command, because DroneBrain and Results Acknowledger need shared reference to the same action status
    private Command action;
    
    public Control(Command action){
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
