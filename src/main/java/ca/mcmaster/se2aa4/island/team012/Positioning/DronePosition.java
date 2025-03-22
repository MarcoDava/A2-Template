package ca.mcmaster.se2aa4.island.team012.Positioning;

public class DronePosition extends Position {

    private Position dronePosition;

    public DronePosition() { // Call the parent class constructor with the required argument
    }

    public Position getDronePosition() {
        return dronePosition;
    }

    public void updateDronePosition(int rowsMoved, int colsMoved) {
        int finalRow= getPosition()[0]+rowsMoved;
        int finalCol= getPosition()[1]+colsMoved;
        dronePosition.setRow(finalRow);
        dronePosition.setCol(finalCol);
    }
}
