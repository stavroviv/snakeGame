package SnakeGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameAction extends Canvas {
	
	static int Score;
	public int delay = 50;
	public static Animals snake;
	public static Animals[] anim = new Animals[mainForm.playHeight*mainForm.playWidth];
	int count;
	int xApple, yApple;
	static Timer timer;
	static boolean inGame = false;
	public Direction dir;
	boolean isPaused = false;


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
					g.setClip(anim[k].lastX[j], anim[k].lastY[j], mainForm.block, mainForm.block);
					g.setColor(Color.BLACK);
					g.fillRect(anim[k].lastX[j], anim[k].lastY[j], mainForm.block, mainForm.block);
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
		
					
		for (int k = 0; k <= mainForm.totalAnimals-1; k++) {

			if (anim[k].die) continue;
			
			if (k == 0) {
				for (int f = 1; f < anim.length-1; f++) {
					if (anim[f]==null)	break;
					if (anim[f].die) continue;
					if (snake.x[0]==anim[f].x[0]&&snake.y[0]==anim[f].y[0]&&!anim[f].die) {
						if (anim[f].getaType()==animalType.Frogg){
							snake.animSize++;
							Score++;
						} else if (anim[f].getaType()==animalType.RedFrogg&&snake.animSize>3){
							snake.animSize--;
							Score+=2;
						}else if (anim[f].getaType()==animalType.BlueFrogg){
							gameOver();	
							return;
						}
						if (anim[f] != null) {
							anim[f].stopAnim();
							anim[f].die = true;
						}
					}
				}
			}
						
			for (int i = 0; i < anim[k].animSize; i++) {
				
				if (i == 0) {
					paintPart(anim[k], anim[k].x[i], anim[k].y[i], g, 2);
				} else if (i == anim[k].animSize - 1) {
					paintPart(anim[k], anim[k].x[i], anim[k].y[i], g, 4);
				} else {
					paintPart(anim[k], anim[k].x[i], anim[k].y[i], g, 3);
				}
			}
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
			count++;
			Animals frogg = new Animals(aType);
			anim[count] = frogg;
		}
		
	}
	
	public void Start() {
		
		if (!isPaused) {
			
			repaint();
			
			Score = 0;
			
			count = 0;
			snake = new Animals(animalType.Snake);
			anim[count] = snake;
			
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
			for (int k = 0; k <= anim.length - 1; k++) {
				if (anim[k] == null) break;
				anim[k].unPauseAnim();
			}
		}

		timer.start();
		inGame = true;
		isPaused = false;
	}
	
	public void Pause() {

		isPaused = true;
		for (int k = 0; k <= anim.length - 1; k++) {
			if (anim[k] == null) break;
			anim[k].pauseAnim();

		}
		
	}
	
	
	public static void Stop() {
		
		if (!inGame) {
			return;
			}
		
		timer.stop();
		inGame = false;

		for (int k = 0; k <= mainForm.totalAnimals - 1; k++) {
			if (anim[k] == null) continue;
			anim[k].stopAnim();
			anim[k]=null;
		}
		
		snakeApp.snakeGame.setButtonsEnabled("Stop");
			
	}

	public static void gameOver () {
		mainForm.labelScore.setText("<html>Score: "+Score+"<p>GAME OVER</html>");
		Stop();
	}
	
}


