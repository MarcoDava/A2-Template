package ca.mcmaster.se2aa4.island.team012;

public class Battery{
    private batteryLevel;
    public Battery(initialBatttery){
        batteryLevel=initialBattery;
    }
    public int getBattery(){
        return batteryLevel;
    }
    public void useBattery(int usedBattery){
        batteryLevel-=usedBattery;
    }
}