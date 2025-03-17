package ca.mcmaster.se2aa4.island.team012.DroneComponents;
import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.se2aa4.island.team012.Observer;
import ca.mcmaster.se2aa4.island.team012.Subject;

public class Battery extends DroneDetails implements Subject{
    private int batteryLevel;
    private List<Observer> observers = new ArrayList<>();

    public Battery(int initialBattery){
        batteryLevel=initialBattery;
    }

    @Override
    public Object getValue(){
        return batteryLevel;
    }
    public void useBattery(int usedBattery){
        batteryLevel-=usedBattery;
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update("battery",batteryLevel);
        }
    }

}