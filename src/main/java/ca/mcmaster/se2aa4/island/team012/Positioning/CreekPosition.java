package ca.mcmaster.se2aa4.island.team012.Positioning;

import java.util.List;
import java.util.ArrayList;

public class CreekPosition extends Position {// need to remake to only save the closest creek position, no longer need a list

    private List<Position> creekPositions = new ArrayList<Position>();//use a hashmap instead. position : creek id

    public CreekPosition(Position position) {
        super(position.getPosition());
    }

    public List<Position> getCreekPositions() {
        return creekPositions;
    }

    public void addCreekPosition(Position position) {
        creekPositions.add(position);
    }
}
