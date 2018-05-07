package SnakeGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class GameAction extends Canvas {
	
	private static final long serialVersionUID = 8066511088862750276L;
	static int Score;
	public int delay = 10;
	public static Animal snake;
	public static ArrayList<Animal> anim = new ArrayList<Animal>();
	static Timer timer;
	static boolean inGame = false;
	boolean isPaused = false;
	
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

		if (paintAll) {
			for (int i = 0; i < GlobalVars.playWidth; i = i + GlobalVars.block) {
				for (int j = 0; j < GlobalVars.playHeight; j = j + GlobalVars.block) {
					g.setColor(Color.black);
					g.fillRect(i, j, GlobalVars.block, GlobalVars.block);
				}
			}
		} else {
			
			int k = 0;
			for (Animal curAnim: GameAction.anim) {
				
				int max = 3;
				if (k != 0) max = 1;

				for (int j = 0; j < max; j++) {
					g.setClip(curAnim.lastX[j], curAnim.lastY[j], GlobalVars.block, GlobalVars.block);
					g.setColor(Color.BLACK);
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
				
		g.setColor(col);
		g.fillOval(xAnim + (GlobalVars.block - GlobalVars.block / size) / 2, yAnim + (GlobalVars.block - GlobalVars.block / size) / 2, GlobalVars.block / size, GlobalVars.block / size);
		g.setColor(Color.white);
		g.drawOval(xAnim + (GlobalVars.block - GlobalVars.block / size) / 2, yAnim + (GlobalVars.block - GlobalVars.block / size) / 2, GlobalVars.block / size, GlobalVars.block / size);
		
	}
		
	public void draw(Graphics g) {

		paintForeground(g,false);
		
		mainForm.labelScore.setText("Score: " + Score);
		
		if (!inGame) return;

		for (int f = 1; f < GameAction.anim.size(); f++) {

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
						Animal frogg = new Animal(aType);
						anim.add(frogg);
					}
				}
				;

			}
		}
		
		for (Animal curAnim: GameAction.anim) {	
			
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
		paintForeground(g, true);
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
			Animal frogg = new Animal(aType);
			anim.add(frogg);
		}
		
	}
	
	public void Start() {
		
		if (!isPaused) {
			
			anim.clear();
			
			Score = 0;
			
			snake = new Animal(animalType.Snake);
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
			
		} 
		else {
			for (Animal curAnim: GameAction.anim) {
				curAnim.unPauseAnim();
			}
		}

		timer.start();
		inGame = true;
		isPaused = false;
		
	}
	
	public void Pause() {

		isPaused = true;
		for (Animal curAnim: GameAction.anim) {
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

	
}


