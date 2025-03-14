package ca.mcmaster.se2aa4.island.team012.Positioning;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team012.Observer;
import ca.mcmaster.se2aa4.island.team012.Subject;

public class Heading implements Subject{
    private List<Observer> observers = new ArrayList<>();
    private Direction heading;
    private static final Logger logger = LogManager.getLogger();

    public Heading(Direction heading){
        this.heading=heading;
    }
    public Direction getHeading(){
        return heading;
    }
    public boolean changeHeading(Direction heading){
        if(this.heading==Direction.N && heading==Direction.S){//checks if it is asking to turn backwards, if so return false. I might change this to just turn to west first then south to turn around.
            logger.info("Cannot turn backwards");
            return false;
        }
        else if(this.heading==Direction.S&&heading==Direction.N){
            logger.info("Cannot turn backwards");
            return false;

        }
        else if(this.heading==Direction.E&&heading==Direction.W){
            logger.info("Cannot turn backwards");
            return false;

        }
        else if(this.heading==Direction.W&&heading==Direction.E){
            logger.info("Cannot turn backwards");
            return false;

        }
        this.heading=heading;
        notifyObservers();
        return true;
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