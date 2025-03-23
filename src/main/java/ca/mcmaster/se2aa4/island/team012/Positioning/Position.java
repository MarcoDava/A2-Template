package ca.mcmaster.se2aa4.island.team012.Positioning;

public class Position { // Class for storing (row, col) coordinates
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int[] getPosition() {
        return new int[] {row, col};
    }

    public void setRow(int row){
        this.row=row;
    }

    public void setCol(int col){
        this.col=col;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}
