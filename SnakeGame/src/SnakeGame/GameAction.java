package SnakeGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class GameAction extends Canvas {
	
	static int Score;
	public int delay = 50;
	public static Animals snake;
	public static Animals[] anim = new Animals[mainForm.playHeight*mainForm.playWidth];
	int i, j, count;
	int xApple, yApple;
	static Timer timer;
	static boolean inGame = false;
	public Direction dir;
	boolean isPaused = false;
	Graphics buf;

	void paintForeground(Graphics g) {

		for (i = 0; i < mainForm.playWidth; i = i + mainForm.block) {
			for (j = 0; j < mainForm.playHeight; j = j + mainForm.block) {
				g.setColor(Color.black);
				g.fillRect(i, j, mainForm.block, mainForm.block);

			}
		}
	}

	void paintPart(Animals anim, Graphics g, int size) {
		
		g.setClip(anim.x[i],anim.y[i], mainForm.block, mainForm.block);

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
		g.fillOval(anim.x[i] + (mainForm.block - mainForm.block / size) / 2, anim.y[i] + (mainForm.block - mainForm.block / size) / 2, mainForm.block / size, mainForm.block / size);
		g.setColor(Color.white);
		g.drawOval(anim.x[i] + (mainForm.block - mainForm.block / size) / 2, anim.y[i] + (mainForm.block - mainForm.block / size) / 2, mainForm.block / size, mainForm.block / size);
		
	}
		
	public void draw(Graphics g) {

		paintForeground(g);
		mainForm.labelScore.setText("Score: " + Score);
		
		if (!inGame) return;
		
					
		for (int k = 0; k <= mainForm.totalAnimals-1; k++) {
			
//			if (anim[k]==null)	break;
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
						
			for (i = 0; i < anim[k].animSize; i++) {
				
				if (i == 0) {
					paintPart(anim[k], g, 2);
				} else if (i == anim[k].animSize - 1) {
					paintPart(anim[k], g, 4);
				} else {
					paintPart(anim[k], g, 3);
				}
			}
		}

		
	
	}
	
	// @Override
	public void update(Graphics g){

		Image offScreenlmage = createImage(mainForm.playWidth, mainForm.playHeight);
		Graphics offscreenGraphics = offScreenlmage.getGraphics();
		draw(offscreenGraphics);
		g.drawImage(offScreenlmage, 0, 0, null);

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
	
	public static void gameOver () {
		mainForm.labelScore.setText("<html>Score: "+Score+"<p>GAME OVER</html>");
		Stop();
	}
	
	public static void Stop() {
		
		if (!inGame) return;
		
		timer.stop();
		inGame = false;

		for (int k = 0; k <= mainForm.totalAnimals - 1; k++) {
			if (anim[k] == null) continue;
			anim[k].stopAnim();
			anim[k]=null;
		}
		
		snakeApp.snakeGame.setButtonsEnabled("Stop");
		
		
		
	}

}


