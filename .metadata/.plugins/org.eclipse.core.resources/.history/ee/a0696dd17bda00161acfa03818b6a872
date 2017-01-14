package tetris;

import java.util.Random;

public abstract class Tetromino {
	Cell[] cells;
	
	public Tetromino(){
		cells = new Cell[4];
	}
	public static Tetromino randomOne(){
		Random random = new Random();
		int type = random.nextInt(7);
		switch(type){
		case 0:
			return new T();
		case 1:
			return new I();
		case 2:
			return new S();
		case 3:
			return new L();
		case 4:
			return new O();
		case 5:
			return new Z();
		case 6:
			return new J();
			
		}
		return null;
	}
	public void drop(){
		for(int i=0; i<cells.length;i++){
			cells[i].drop();
		}
	}
	public void moveLeft(){
		for(int i=0; i< cells.length;i++){
			cells[i].moveLeft();
		}
	}
	public void moveRight(){
		for(int i=0; i< cells.length;i++){
			cells[i].moveRight();
		}
		
	}
}
