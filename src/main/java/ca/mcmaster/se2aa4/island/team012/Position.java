package ca.mcmaster.se2aa4.island.team012;

import java.util.ArrayList;
import java.util.List;

public class Position implements Subject{ // Class for storing (row, col) coordinates
    private int row;
    private int col;

    private List<Observer> observers = new ArrayList<>();

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int[] getPosition() {
        return new int[]{row, col};
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
        //notifyObservers("position",new int[]{row,col});
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
            observer.update("position",new int[]{row,col});
        }
    }
}
