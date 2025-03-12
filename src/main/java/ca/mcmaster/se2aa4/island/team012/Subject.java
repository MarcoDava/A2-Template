package ca.mcmaster.se2aa4.island.team012;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {//might not need this
        observers.remove(observer);
    }

    protected void notifyObservers(String property, Object value) {
        for (Observer observer : observers) {
            observer.update(property, value);
        }
    }
}