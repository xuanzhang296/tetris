package tetris;

public class Cell {
	int row;
	int col;
	public Cell(int row,int col){
		this.row = row;
		this.col = col;
	}
	public Cell(){
		this(0, 0);
	}
	public void drop(){
		row++;
	}
	public void drop(int d){
		row+=d;
	}
	public void moveLeft(int d){
		col-=d;
	}
	public void moveLeft(){
		col--;
	}
	public void moveRight(int d){
		col+=d;
	}
	public void moveRight(){
		col++;
	}
	public String toString(){
		String a = "("+this.row+","+this.col+")";
		return a;
	}
	
	

}
