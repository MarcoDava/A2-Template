package ca.mcmaster.se2aa4.island.team012.DroneComponents;


public class Control {
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
}
