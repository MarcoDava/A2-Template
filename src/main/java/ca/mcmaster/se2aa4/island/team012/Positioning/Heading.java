package ca.mcmaster.se2aa4.island.team012.Positioning;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team012.Observer;
import ca.mcmaster.se2aa4.island.team012.Subject;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.DroneDetails;

public class Heading extends DroneDetails implements Subject{
    private List<Observer> observers = new ArrayList<>();
    private Direction heading;
    private static final Logger logger = LogManager.getLogger();

    public Heading(Direction heading){
        this.heading=heading;
    }
    @Override
    public Object getValue(){
        return heading;
    }
    @Override
    public void updateValue(Object value){
        this.heading=(Direction)value;
        notifyObservers();
    }
    public boolean compareHeading(Direction heading){
        return this.heading==heading;
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
            observer.update("heading",heading);
        }
    }
}