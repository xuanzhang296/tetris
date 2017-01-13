package tetris;

import java.awt.image.BufferedImage;

public class Cell {
	int row;
	int col;
	private BufferedImage image;
	public Cell(int row,int col,BufferedImage image){
		this.row = row;
		this.col = col;
		this.image = image;
	}
	public Cell(){
		this(0, 0,null);
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
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
	@Override
	public String toString() {
		return "Cell [row=" + row + ", col=" + col + ", image=" + image + "]";
	}
	
	
	
	

}
