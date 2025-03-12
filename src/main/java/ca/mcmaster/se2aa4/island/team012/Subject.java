package ca.mcmaster.se2aa4.island.team012;

import java.util.ArrayList;
import java.util.List;

public interface Subject {

    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}