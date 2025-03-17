package ca.mcmaster.se2aa4.island.team012.Positioning;

public class IslandPosition extends Position{//this class might not be necessary
    private Position islandPosition;
    public IslandPosition(int row, int col) {
        super(row, col);
    }
    public Position getLastSavedPosition() {
        return islandPosition;
    }
    public void setDronePosition(int row, int col) {
        islandPosition = new Position(row, col);
    }
}
