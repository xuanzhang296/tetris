package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
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
	private int level;
	private int index;
	private int speed;
	private Timer timer;
	private int state;
	public static final int RUNNING = 0;
	public static final int PAUSE =1;
	public static final int GAME_OVER =2;
	private Cell[][] wall = new Cell [ROWS][COLS];
	private Tetromino tetromino;
	private Tetromino nextOne;
	private static BufferedImage background;
	private static BufferedImage overImage;
	private static BufferedImage gameOver;
	private static BufferedImage pause;
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
			gameOver = ImageIO.read(Tetris.class.getResource("gameOver.png"));
			pause = ImageIO.read(Tetris.class.getResource("pause.png"));
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
		paintScore(g);
		paintState(g);
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
	private void paintScore(Graphics g) {
		int x = 292;
		int y = 162;
		g.setColor(new Color(0xffffff));
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
		g.setFont(font);
		g.drawString("SCORE:" + score, x, y);
		y += 56;
		g.drawString("LINES:" + lines, x, y);
		y += 56;
		g.drawString("LEVEL:" + level, x, y);
		x = 290;
		y = 160;
		g.setColor(new Color(0x667799));
		g.drawString("SCORE:" + score, x, y);
		y += 56;
		g.drawString("LINES:" + lines, x, y);
		y += 56;
		g.drawString("LEVEL:" + level, x, y);
	}
	private void paintState(Graphics g) {
		switch (state) {
		case PAUSE:
			g.drawImage(pause, -15, -15, null);
			break;
		case GAME_OVER:
			g.drawImage(gameOver, -15, -15, null);
			break;
		}
	}

	public void action(){
		wall [18][0] = new Cell(18,0,Tetris.L);
		wall [19][0] = new Cell(19,0,Tetris.L);
		wall [19][1] = new Cell(19,1,Tetris.L);
		wall [19][2] = new Cell(19,2,Tetris.L);
		tetromino = Tetromino.randomOne();
		nextOne = Tetromino.randomOne();
		state = RUNNING;
		repaint();
		//key listener step1:
		/*		KeyListener l = new KeyListener(){
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
						case KeyEvent.VK_SPACE:
							hardDropAction();
							break;
						case KeyEvent.VK_UP:
							rotateRightAction();
							break;
						}
						repaint();
					}
					public void keyReleased(KeyEvent e){
						
					}
					public void keyTyped(KeyEvent e){
						
					}
				};
				*/
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (state) {
				case GAME_OVER:
					processGameoverKey(key);
					break;
				case PAUSE:
					processPauseKey(key);
					break;
				case RUNNING:
					processRunningKey(key);
				}
				repaint();
			}
		});
				//step2
				setFocusable(true);//panel can get focus
				requestFocus();//request focus
				
				timer = new Timer();
				timer.schedule(new TimerTask(){
					public void run() {
						speed = 40 - (lines / 100);
						speed = speed < 1 ? 1 : speed;
						level = 41 - speed;
						if (state == RUNNING && index % speed == 0) {
							softDropAction();
						}
						index++;
						repaint();
					}
				},10,10);
	}
	private void processPauseKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_C:
			this.index = 0;
			state = RUNNING;
			break;
		}
	}

	protected void processRunningKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_RIGHT:
			Tetris.this.moveRightAction();
			break;
		case KeyEvent.VK_LEFT:
			Tetris.this.moveLeftAction();
			break;
		case KeyEvent.VK_DOWN:
			softDropAction();
			break;
		case KeyEvent.VK_SPACE:
			hardDropAction();
			break;
		case KeyEvent.VK_UP:
			rotateRightAction();
			break;
		case KeyEvent.VK_P:
			state = PAUSE;
			break;
		}

	}

	protected void processGameoverKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_S:
			this.lines = 0;
			this.score = 0;
			this.wall = new Cell[ROWS][COLS];
			this.tetromino = Tetromino.randomOne();
			this.nextOne = Tetromino.randomOne();
			this.state = RUNNING;
			this.index = 0;
			break;
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
	private boolean canDrop() {
		Cell[] cells = tetromino.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			if (row == ROWS - 1) {
				return false;
			}
		}
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if (wall[row + 1][col] != null) {
				return false;
			}
		}
		return true;
	}
	private int[] scoreTable = { 0, 1, 10, 100, 500 };
	public void softDropAction() {
		if (canDrop()) {
			tetromino.drop();
		} else {
			landIntoWall();
			int lines = destroyLines();
			this.lines += lines;
			// lines = 0 1 2 3 4
			// {0,1,10,100,500};
			this.score += scoreTable[lines];
			if (isGameOver()) {
				state = GAME_OVER;
			} else {
				tetromino = nextOne;
				nextOne = Tetromino.randomOne();
			}
		}
	}
	private void landIntoWall() {
		Cell[] cells = tetromino.cells;
		for (Cell cell : cells) {
			int row = cell.getRow();
			int col = cell.getCol();
			wall[row][col] = cell;
		}
	}
	private int destroyLines() {
		int lines = 0;
		for (int row = 0; row < ROWS; row++) {
			if (isFullCells(row)) {
				deleteRow(row);
				lines++;
			}
		}
		return lines;
	}
	private boolean isFullCells(int row) {
		Cell[] line = wall[row];
		for (Cell cell : line) {
			if (cell == null) {
				return false;
			}
		}
		return true;
	}
	
	private void deleteRow(int row) {
		for (int i = row; i >= 1; i--) {
			System.arraycopy(wall[i - 1], 0, wall[i], 0, COLS);
		}
		Arrays.fill(wall[0], null);
	}
	public void hardDropAction() {
		while (canDrop()) {
			tetromino.drop();
		}
		landIntoWall();
		int lines = destroyLines();
		this.lines += lines;
		this.score += scoreTable[lines];
		if (isGameOver()) {
			state = GAME_OVER;
		} else {
			tetromino = nextOne;
			nextOne = Tetromino.randomOne();
		}
	}
	private void rotateRightAction() {
		tetromino.rotateRight();
		if (outOfBound() || coincide()) {
			tetromino.rotateLeft();
		}
		
	}
	private boolean isGameOver() {
		Cell[] cells = nextOne.cells;
		for (Cell cell : cells) {
			int row = cell.getRow();
			int col = cell.getCol();
			if (wall[row][col] != null) {
				return true;
			}
		}
		return false;
	}

	
	
	
}
