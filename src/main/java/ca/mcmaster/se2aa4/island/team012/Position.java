package ca.mcmaster.se2aa4.island.team012;

public class Position{//might not need this class

    int[] position=new int[2];
    public Position(int row, int col){
        position={row,col};
    }
    public Position getPosition(){
        return position;
    }
    public void setPosition(Position position){
        this.position=position;
    }
}