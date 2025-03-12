package ca.mcmaster.se2aa4.island.team012;

import java.util.ArrayList;
import java.util.List;

public class Heading implements Subject{
    private List<Observer> observers = new ArrayList<>();
    Direction heading;

    public Heading(Direction heading){
        this.heading=heading;
    }
    public Direction getHeading(){
        return heading;
    }
    public void changeHeading(Direction heading){
        this.heading=heading;
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
            observer.update(heading);
        }
    }
}