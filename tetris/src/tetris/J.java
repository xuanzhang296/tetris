package tetris;

public class J extends Tetromino{
	public J(){
		this(0,0);
	}
	public J(int row,int col){
		cells[0] =  new Cell(row+1,col,Tetris.J);
		cells[1] = new Cell(row+1,col+1,Tetris.J);
		cells[2] = new Cell(row+1,col+2,Tetris.J);
		cells[3] = new Cell(row,col+2,Tetris.J);
	}
}