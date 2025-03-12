package ca.mcmaster.se2aa4.island.team012;

public class Battery{
    private int batteryLevel;
    public Battery(int initialBattery){
        batteryLevel=initialBattery;
    }
    public int getBattery(){
        return batteryLevel;
    }
    public void useBattery(int usedBattery){
        batteryLevel-=usedBattery;
    }
}