package ca.mcmaster.se2aa4.island.team012.Positioning;

public class Position { // Class for storing (row, col) coordinates
    private int[] position;

    public Position(int row, int col) {
        position = new int[]{row, col};
    }

    public int[] getPosition() {
        return position;
    }

    public void setRow(int row){
        position[0]=row;
    }

    public void setCol(int col){
        position[1]=col;
    }

    public int getRow(){
        return position[0];
    }

    public int getCol(){
        return position[1];
    }
}
