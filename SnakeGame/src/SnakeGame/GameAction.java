package SnakeGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

public class GameAction extends Canvas {
	
	public Animal snake;
	public ArrayList<Animal> anim = new ArrayList<Animal>();
	
	private static final long serialVersionUID = 8066511088862750276L;
	int Score;
	int delay = 50;
	Timer timer;
	boolean inGame = false;
	boolean isPaused = false;
	boolean gameResumed = false;
	
	public GameAction() {
		
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				if (!inGame) return;
				
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
		});
	
	}
	
	public void checkCollisions() {
		
		// snake with snake
		for (int j=1; j<=snake.animSize-1; j++){
			if (snake.x[j] == snake.x[0] && snake.y[j] == snake.y[0]) {
				gameOver();
				break;
			}
		}
	
	}

	void paintBackground(Graphics g, boolean paintAll) {

		if (paintAll) {
			for (int i = 0; i < GlobalVars.playWidth; i = i + GlobalVars.block) {
				for (int j = 0; j < GlobalVars.playHeight; j = j + GlobalVars.block) {
					g.setColor(mainForm.bgColor);
					g.fillRect(i, j, GlobalVars.block, GlobalVars.block);
				}
			}
		} else {
			
			int k = 0;
//			for (Animal curAnim: anim) { --- throws sometimes java.util.ConcurrentModificationException
			for (int i = 0; k <= anim.size() - 1; i++) {
			
				Animal curAnim = anim.get(i);
				
				int max = 3;
				if (k != 0) max = 1;

				for (int j = 0; j < max; j++) {

					g.setClip(curAnim.lastX[j], curAnim.lastY[j], GlobalVars.block, GlobalVars.block);
					g.setColor(mainForm.bgColor);
					g.fillRect(curAnim.lastX[j], curAnim.lastY[j], GlobalVars.block, GlobalVars.block);
				}
				
				k++;
			}
		}
		
	}

	void paintPart(Animal anim, int xAnim, int yAnim, Graphics g, int size) {
		
		g.setClip(xAnim, yAnim, GlobalVars.block, GlobalVars.block);
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
		
		if (GlobalVars.printImages) {
			
			g.drawImage(GlobalVars.animalImages.get(anim.getaType().toString()), xAnim, yAnim, null);
			
		} 
		else {
			g.setColor(col);
			g.fillOval(xAnim + (GlobalVars.block - GlobalVars.block / size) / 2, yAnim + (GlobalVars.block - GlobalVars.block / size) / 2, GlobalVars.block / size, GlobalVars.block / size);
			g.setColor(Color.white);
			g.drawOval(xAnim + (GlobalVars.block - GlobalVars.block / size) / 2, yAnim + (GlobalVars.block - GlobalVars.block / size) / 2, GlobalVars.block / size, GlobalVars.block / size);
		}
		
	}
		
	public void draw(Graphics g) {
				
		if (!isPaused) {
			
			if (!GlobalVars.DoubleBuffered && gameResumed) {
				
				paintBackground(g, true);
				gameResumed = false;
				
			} else
				
				paintBackground(g, false);
			
		}
		
		mainForm.labelScore.setText("Score: " + Score);
		
		if (!inGame) return;

		for (int f = 1; f < anim.size(); f++) {

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
				
				if (inGame) {
					animalType aType = anim.get(f).getaType();
					anim.remove(f);
					if (aType == animalType.Frogg || aType == animalType.RedFrogg) {
						Animal frogg = new Animal(aType, this);
						anim.add(frogg);
					}
				}
				;

			}
		}
		
		for (Animal curAnim: anim) {	
			
			if (curAnim.getaType()==animalType.Snake) { 
				for (int i=0; i<curAnim.animSize; i++) {
					
					int size = 3;
					if (i == 0) size = 2; 
					if (i == curAnim.animSize - 1) size = 4;
					
					paintPart(curAnim, curAnim.x[i], curAnim.y[i], g, size);
				}
			} 
			
			else paintPart(curAnim, curAnim.x[0], curAnim.y[0], g, 3);
			
		}
		
		
		
	}
	
	public void paint (Graphics g){
			
		paintBackground(g, true);
		
	}
	
	public void update(Graphics g){
			
		if (GlobalVars.DoubleBuffered){
			
			Image offScreenlmage = createImage(GlobalVars.playWidth, GlobalVars.playHeight);
			Graphics offscreenGraphics = offScreenlmage.getGraphics();
			draw(offscreenGraphics);
			g.drawImage(offScreenlmage, 0, 0, null);
			
		} 
		
		else draw(g);
	
	}

	void addAnimals(int qAnimals, animalType aType) {
		
		for (int i = 0; i < qAnimals; i++) {
			Animal frogg = new Animal(aType, this);
			anim.add(frogg);
		}
		
	}
	
	public void Start() {
		
		if (!isPaused) {
			
			anim.clear();
			
			Score = 0;
			
			snake = new Animal(animalType.Snake, this);
			anim.add(snake);
			
			addAnimals(GlobalVars.greenFroggs,  animalType.Frogg);
			addAnimals(GlobalVars.redFroggs,	animalType.RedFrogg);
			addAnimals(GlobalVars.blueFroggs,	animalType.BlueFrogg);
			gameResumed = true;
			
			timer = new Timer(delay, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					repaint();
				}
				
			});
			
		} 
		
		else {
			for (Animal curAnim: anim) {
				curAnim.unPauseAnim();
			}
		}

		timer.start();
		inGame = true;
		isPaused = false;
		
	}
	
	public void Pause() {

		isPaused = true;
		for (Animal curAnim: anim) {
			curAnim.pauseAnim();
		}
		
	}
		
	public void Stop() {
		
		if (!inGame) return;
		
		timer.stop();
		inGame = false;
		isPaused = false;
		
		anim.clear();
		
		mainForm.setButtonsEnabled("Stop");
			
	}

	public void gameOver () {
		
		mainForm.labelScore.setText("<html>Score: " + Score + "<p>GAME OVER</html>");
		Stop();
		
	}
	
}


