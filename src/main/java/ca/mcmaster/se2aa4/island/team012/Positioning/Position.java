package ca.mcmaster.se2aa4.island.team012.Positioning;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.se2aa4.island.team012.Observer;
import ca.mcmaster.se2aa4.island.team012.Subject;
import ca.mcmaster.se2aa4.island.team012.DroneComponents.DroneDetails;

public class Position extends DroneDetails implements Subject{ // Class for storing (row, col) coordinates
    private int row;
    private int col;
    private int[] position;
    private String stringPosition;

    private List<Observer> observers = new ArrayList<>();

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public Object getValue() {
        return row+","+col;
    }

    @Override
    public void updateValue(Object value) {
        this.stringPosition = (String) value;
        String[] values = ((String) value).split(",");
        this.row = Integer.parseInt(values[0]);
        this.col = Integer.parseInt(values[1]);
        notifyObservers();
    }

    public int[] getPosition(){
        return new int[]{row, col};
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
            observer.update("position", stringPosition);
        }
    }
}
