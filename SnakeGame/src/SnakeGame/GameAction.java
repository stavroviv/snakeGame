package SnakeGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
//import java.util.EventListener;
import java.util.ArrayList;

import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameAction extends Canvas implements MouseListener{
	
	static int Score;
	public int delay = 50;
	public static Animals snake;
	public static ArrayList<Animals> anim = new ArrayList<Animals>();
	int count;
	int xApple, yApple;
	static Timer timer;
	static boolean inGame = false;
	public Direction dir;
	boolean isPaused = false;
	
	public GameAction() {
		addMouseListener(this);
	}
	
	public static void checkCollisions() {
		
		// snake with snake
		for (int j=1; j<=snake.animSize-1; j++){
			if (snake.x[j] == snake.x[0] && snake.y[j] == snake.y[0]) {
				gameOver();
				break;
			}
		}
	
	}

	void paintForeground(Graphics g, boolean paintAll) {

		if (!mainForm.notDoubleBuffered||paintAll) {
			for (int i = 0; i < mainForm.playWidth; i = i + mainForm.block) {
				for (int j = 0; j < mainForm.playHeight; j = j + mainForm.block) {
					g.setColor(Color.black);
					g.fillRect(i, j, mainForm.block, mainForm.block);
				}
			}
		} else {

			for (int k = 0; k <= mainForm.totalAnimals - 1; k++) {
				
				int max = 3;
				if (k != 0) max = 1;

				for (int j = 0; j < max; j++) {
					g.setClip(anim.get(k).lastX[j], anim.get(k).lastY[j], mainForm.block, mainForm.block);
					g.setColor(Color.BLACK);
					g.fillRect(anim.get(k).lastX[j], anim.get(k).lastY[j], mainForm.block, mainForm.block);
				}
			}
		}
		
	}

	void paintPart(Animals anim, int xAnim, int yAnim, Graphics g, int size) {
		
		g.setClip(xAnim, yAnim, mainForm.block, mainForm.block);
		Color col;
		
		switch (anim.getaType()) {
		case Snake:
			col = Color.YELLOW;
			break;
		case Frogg:
			col = Color.green;
			break;
		case RedFrogg:
			col = Color.RED;
			break;
		case BlueFrogg:
			col = Color.BLUE;
			break;
		default:
			col = Color.YELLOW;
			break;
		}
		
		if (anim.getaType()!=animalType.Snake){
			size = 3;
		}
				
		g.setColor(col);
		g.fillOval(xAnim + (mainForm.block - mainForm.block / size) / 2, yAnim + (mainForm.block - mainForm.block / size) / 2, mainForm.block / size, mainForm.block / size);
		g.setColor(Color.white);
		g.drawOval(xAnim + (mainForm.block - mainForm.block / size) / 2, yAnim + (mainForm.block - mainForm.block / size) / 2, mainForm.block / size, mainForm.block / size);
		
	}
		
	public void draw(Graphics g) {

		paintForeground(g,false);
		
		mainForm.labelScore.setText("Score: " + Score);
		
		if (!inGame) return;

		for (int f = 1; f < GameAction.anim.size() - 1; f++) {

			// snake with frogg
			if (snake.x[0] == anim.get(f).x[0] && snake.y[0] == anim.get(f).y[0]) {

				if (anim.get(f).getaType() == animalType.Frogg) {
					snake.animSize++;
					Score++;
				} else if (anim.get(f).getaType() == animalType.RedFrogg && snake.animSize > 3) {
					snake.animSize--;
					Score += 2;
				} else if (anim.get(f).getaType() == animalType.BlueFrogg) {
					gameOver();
//					return;
				}
				
				if (inGame)	anim.remove(f);

			}
		}
		
		for (Animals curAnim: GameAction.anim) {	
			
			if (curAnim.getaType()==animalType.Snake) { 
				for (int i=0; i<curAnim.animSize; i++) {
					if (i == 0) {
						paintPart(curAnim, curAnim.x[i], curAnim.y[i], g, 2);
					} else if (i == curAnim.animSize - 1) {
						paintPart(curAnim, curAnim.x[i], curAnim.y[i], g, 4);
					} else {
						paintPart(curAnim, curAnim.x[i], curAnim.y[i], g, 3);
					}
				}
			} 
			else paintPart(curAnim, curAnim.x[0], curAnim.y[0], g, 3);
			
		}
	
	}
	
	public void paint (Graphics g){
		paintForeground(g, true);
	}
	
	public void update(Graphics g){

		if (mainForm.notDoubleBuffered){
			draw(g);
		} else {
			Image offScreenlmage = createImage(mainForm.playWidth, mainForm.playHeight);
			Graphics offscreenGraphics = offScreenlmage.getGraphics();
			draw(offscreenGraphics);
			g.drawImage(offScreenlmage, 0, 0, null);	
		}

	}

	void addAnimals(int qAnimals, animalType aType) {
		
		for (int i = 0; i < qAnimals; i++) {
			Animals frogg = new Animals(aType);
			anim.add(frogg);
		}
		
	}
	
	public void Start() {
		
		if (!isPaused) {
			
			repaint();
			
			anim.clear();
			
			Score = 0;
			
			count = 0;
			snake = new Animals(animalType.Snake);
			anim.add(snake);
			
			addAnimals(mainForm.greenFroggs, animalType.Frogg);
			addAnimals(mainForm.redFroggs,	 animalType.RedFrogg);
			addAnimals(mainForm.blueFroggs,	 animalType.BlueFrogg);

			timer = new Timer(delay, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					repaint();
				}
			});
		} else {
			for (Animals curAnim: GameAction.anim) {
				curAnim.unPauseAnim();
			}
		}

		timer.start();
		inGame = true;
		isPaused = false;
	}
	
	public void Pause() {

		isPaused = true;
		for (Animals curAnim: GameAction.anim) {
			curAnim.pauseAnim();
		}
		
	}
		
	public static void Stop() {
		
		if (!inGame) return;
		
		timer.stop();
		inGame = false;

		anim.clear();
		
		mainForm.setButtonsEnabled("Stop");
			
	}

	public static void gameOver () {
		mainForm.labelScore.setText("<html>Score: " + Score + "<p>GAME OVER</html>");
		Stop();
	}
	
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		
		if (arg0.getButton()==1) {
			
			switch(snake.dir){
				case Right: snake.dir=Direction.Up; 	break;
				case Left : snake.dir=Direction.Down;	break;
				case Up   : snake.dir=Direction.Left;	break;
				case Down : snake.dir=Direction.Right;	break;
			}
			

		} else if (arg0.getButton()==3){
			
			switch(snake.dir){
				case Right: snake.dir=Direction.Down; 	break;
				case Left : snake.dir=Direction.Up;		break;
				case Up	  : snake.dir=Direction.Right;	break;
				case Down : snake.dir=Direction.Left;	break;
			}

		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}


