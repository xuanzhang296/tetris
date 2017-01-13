package tetris;

public class I extends Tetromino{
	public I(){
		this(0,0);
	}
	public I(int row,int col){
		cells[0] =  new Cell(row,col,Tetris.I);
		cells[1] = new Cell(row,col+1,Tetris.I);
		cells[2] = new Cell(row,col+2,Tetris.I);
		cells[3] = new Cell(row,col+3,Tetris.I);
	}
}
