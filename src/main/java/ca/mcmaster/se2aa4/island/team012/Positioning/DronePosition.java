package ca.mcmaster.se2aa4.island.team012.Positioning;

public class DronePosition extends Position {

    private Position dronePosition;

    public DronePosition(int row, int col) {
        super(row, col);
    }

    public int[] getDronePosition() {
        String dronePositionString = (String)dronePosition.getValue();
        String[] dronePositionArrayString = dronePositionString.split(",");
        int[] dronePositionArray = new int[dronePositionArrayString.length];
        for (int i = 0; i < dronePositionArrayString.length; i++) {
            dronePositionArray[i] = Integer.parseInt(dronePositionArrayString[i]);
        }
        return dronePositionArray;
    }

    public void setDronePosition(int row, int col) {
        dronePosition = new Position(row, col);
    }
}
