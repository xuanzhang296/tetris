package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class cubeFrame extends JFrame {
	private int cubex=30,cubey=0;
	// constructor
	public cubeFrame(){
		// size close location panel
		setSize(350,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);// null means in the middle
		cubePanel panel = new cubePanel();
		add(panel);
		/*  key listener:
		 * step 1 :create key listener 
		 * step 2 : register in panel
		 * step 3 : when key press happen, key listener execute
		 * */
		//key listener step1:
		KeyListener l = new KeyListener(){
			public void keyPressed(KeyEvent e){
				//System.out.println("press:" + e.getKeyCode());
				//cubex+=30;
				//repaint();
				int key = e.getKeyCode();
				switch(key){
				case KeyEvent.VK_RIGHT:
					cubex+=20;
					break;
				case KeyEvent.VK_LEFT:
					cubex-=20;
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
		panel.addKeyListener(l);// register listner
		panel.setFocusable(true);//panel can get focus
		panel.requestFocus();//request focus
		Timer timer = new Timer();
		TimerTask dropTask = new TimerTask(){
			public void run(){
				cubey +=10;
				repaint();// tetris.cubeFrame.this.repaint();
			}
		};
		timer.schedule(dropTask, 1000, 500);
		
	}
	// main fuction
	public static void main(String[] args){
		cubeFrame frame=new cubeFrame();
		frame.setVisible(true);
		
	}
	//inner class panel
	private class cubePanel extends JPanel{
		public void paint(Graphics g){
			// background color
			g.setColor(Color.WHITE);
			//                     r g b
			//g.setColor(new Color(0x000000));// use number represent color
			g.fillRect(0, 0, getWidth(), getHeight());
			// draw the shade of the cube
			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect(cubex+5, cubey+5, 30, 30, 8, 8);
			//g.setColor(Color.RED);
			g.drawRoundRect(cubex+5, cubey+5, 30, 30, 8, 8);
			//draw the cube
			g.setColor(Color.BLUE);
			g.fillRoundRect(cubex, cubey, 30, 30, 8, 8);
			g.drawRoundRect(cubex, cubey, 30, 30, 8, 8);
			
			
		}
		
	}
}
