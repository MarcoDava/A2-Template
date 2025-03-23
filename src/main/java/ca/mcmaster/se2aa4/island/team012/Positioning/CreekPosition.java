package ca.mcmaster.se2aa4.island.team012.Positioning;

import java.util.List;
import java.util.ArrayList;

public class CreekPosition extends Position {// need to remake to only save the closest creek position, no longer need a list

    private Position creekPosition;//use a hashmap instead. position : creek id
    private String creekID;

    public CreekPosition(int row, int col) {
        super(row, col);
    }

    public Position getCreekPositions() {
        return creekPosition;
    }

    public void setCreekPosition(int row, int col) {
        creekPosition = new Position(row, col);
    }
}
