package ca.mcmaster.se2aa4.island.team012.Positioning;

import java.util.List;
import java.util.ArrayList;

public class CreekPosition extends Position {

    private List<Position> creekPositions = new ArrayList<Position>();

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
