package tetris;

public class T extends Tetromino{
	public T(){
		this(0,0);
	}
	public T(int row,int col){
		cells[0] =  new Cell(row,col,Tetris.T);
		cells[1] = new Cell(row,col+1,Tetris.T);
		cells[2] = new Cell(row,col+2,Tetris.T);
		cells[3] = new Cell(row+1,col+1,Tetris.T);
	}
	public void print(){
		String str = "";
		for(int i = 0; i < cells.length; i++){
			str+= cells[i].toString()+",";
		}
		System.out.println(str);
	}
}
