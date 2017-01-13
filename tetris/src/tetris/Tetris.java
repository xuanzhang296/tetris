package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Tetris extends JPanel {
	public static final int ROWS = 20;
	public static final int COLS = 10;
	private int score;
	private int lines;
	private Cell[][] wall = new Cell [ROWS][COLS];
	private Tetromino tetromino;
	private Tetromino nextOne;
	private static BufferedImage background;
	private static BufferedImage overImage;
	public static BufferedImage T;
	public static BufferedImage S;
	public static BufferedImage Z;
	public static BufferedImage O;
	public static BufferedImage J;
	public static BufferedImage L;
	public static BufferedImage I;
	static{
		try{
			background = ImageIO.read(Tetris.class.getResource("tetris.png"));
			T = ImageIO.read(T.class.getResource("T.png"));
			J = ImageIO.read(T.class.getResource("J.png"));
			S = ImageIO.read(T.class.getResource("S.png"));
			Z = ImageIO.read(T.class.getResource("Z.png"));
			O = ImageIO.read(T.class.getResource("O.png"));
			L = ImageIO.read(T.class.getResource("L.png"));
			I = ImageIO.read(T.class.getResource("I.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, null);
		g.translate(15,15);
		paintWall(g);
		paintTetromino(g);
		paintNextOne(g);
	}
	/*public static void main(String[] args){
		JFrame frame = new JFrame();
		Tetris tetris = new Tetris();		
		frame.add(tetris);
		frame.setSize(700, 1050);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		tetris.setBackground(new Color(0x0000ff));
		
	}*/
	public static final int CELL_SIZE = 26;
	private void paintWall(Graphics g){
		for(int row = 0; row < wall.length;row++){
			Cell[] line = wall[row];
			for(int col = 0; col < line.length; col++){
				Cell cell = line[col];
				int x = col*CELL_SIZE;
				int y = row*CELL_SIZE;
				if(cell == null){
					g.drawRect(x,y,CELL_SIZE,CELL_SIZE);
				}else{
					g.drawImage(cell.getImage(),x-1,y-1,null);
				}
				
			}
		}
	}
	public void action(){
		wall [18][0] = new Cell(18,0,Tetris.L);
		wall [19][0] = new Cell(19,0,Tetris.L);
		wall [19][1] = new Cell(19,1,Tetris.L);
		wall [19][2] = new Cell(19,2,Tetris.L);
		tetromino = Tetromino.randomOne();
		nextOne = Tetromino.randomOne();
		//key listener step1:
				KeyListener l = new KeyListener(){
					public void keyPressed(KeyEvent e){
						//System.out.println("press:" + e.getKeyCode());
						//cubex+=30;
						//repaint();
						int key = e.getKeyCode();
						switch(key){
						case KeyEvent.VK_DOWN:
							tetromino.drop();
							break;
						case KeyEvent.VK_RIGHT:
							moveRightAction();;
							break;
						case KeyEvent.VK_LEFT:
						    moveLeftAction();;
							break;
						}
						repaint();
					}
					public void keyReleased(KeyEvent e){
						
					}
					public void keyTyped(KeyEvent e){
						
					}
				};
				//step2
				addKeyListener(l);// register listener
				setFocusable(true);//panel can get focus
				requestFocus();//request focus
	}
	public void paintTetromino(Graphics g){
		if(tetromino == null){
			return;
		}
		Cell[] cells = tetromino.cells;
		for(int i = 0; i < cells.length; i++){
			Cell cell = cells[i];
			int x = cell.getCol()*CELL_SIZE;
			int y = cell.getRow()*CELL_SIZE;
			g.drawImage(cell.getImage(), x-1, y-1, null);
		}
	}
	public boolean outOfBound(){
		Cell[] cells = tetromino.cells;
		for(int i = 0; i < cells.length;i++){
			Cell cell = cells[i];
			int col = cell.getCol();
			if(col < 0 || col >= COLS){
				return true;
			}
		}
		return false;
	}
	public void paintNextOne(Graphics g){
		if(nextOne == null){return;}
		Cell[] cells = nextOne.cells;
		for(int i = 0; i < cells.length; i++){
			 Cell cell = cells[i];
			 int x= (cell.getCol()+13)*CELL_SIZE;
			 int y= (cell.getRow()+1)*CELL_SIZE;
			 g.drawImage(cell.getImage(), x-1, y-1, null);
			 
		}
	}
	private boolean coincide() {
		Cell[] cells = tetromino.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if (wall[row][col] != null) {
				return true;
			}
		}
		return false;
	}
	public void moveRightAction() {
		tetromino.moveRight();
		if (outOfBound() || coincide()) {
			tetromino.moveLeft();
		}
	}
	public void moveLeftAction() {
		tetromino.moveLeft();
		if (outOfBound() || coincide()) {
			tetromino.moveRight();
		}
	}
	
	
	
}
