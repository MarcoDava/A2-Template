package ca.mcmaster.se2aa4.island.team012.Positioning;

public class DronePosition extends Position{
    private Position dronePosition;
    public DronePosition(int row, int col) {
        super(row, col);
    }
    public Position getDronePosition() {
        return dronePosition;
    }
    public void setDronePosition(int row, int col) {
        dronePosition = new Position(row, col);
    }
}
