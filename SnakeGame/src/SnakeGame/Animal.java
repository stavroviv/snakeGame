package SnakeGame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Animal implements Runnable {
		
	int delay;
	Thread thread;
	boolean die, isPaused;
	int frogDelay = GlobalVars.snakeDelay*10;
	
	int[] x = new int[GlobalVars.Width*GlobalVars.Height];
	int[] y = new int[GlobalVars.Width*GlobalVars.Height];
	
	public int[] lastX = new int[3];
	public int[] lastY = new int[3];
	
	public int i, animSize;
		
	boolean inGame;
	GameAction curGame;
	
	Direction dir;
	animalType aType;
	
	public animalType getaType(){
		return this.aType;
	}
	
	Animal(animalType aType, GameAction curGame) {
		
		this.aType = aType;
		this.curGame = curGame;
		
		if (aType == animalType.Snake) {
			
			animSize = GlobalVars.snakeSize;
			delay = GlobalVars.snakeDelay;

			int j = 0;
			for (i = animSize; i > 0; i--) {
				x[j] = i * GlobalVars.block;
				y[j] = 0;
				j++;
			}

		} else {
			
			animSize = 1;
			delay = frogDelay;
						
			int newX = getNewPos(GlobalVars.playWidth);
			int newY = getNewPos(GlobalVars.playHeight);
			
			while (!checkNewPos(newX, newY)) {

				newX = getNewPos(GlobalVars.playWidth);
				newY = getNewPos(GlobalVars.playHeight);
			}
			
			x[0] = newX;
			y[0] = newY;
			
		}
		
		dir = Direction.Right;
		
		thread = new Thread(this);
		thread.start(); 
    	
	}

	int getNewPos(int arg){
		Random random = new Random();
		return random.nextInt(arg/ GlobalVars.block) * GlobalVars.block;
	}
	
	public void stopAnim(){
		thread.interrupt();
	}
	
	synchronized public void pauseAnim() {
		isPaused = true;
	}
	
	synchronized void unPauseAnim() {
		isPaused = false;
		notify();
	}
	
	public void run() {
		
		try {

			while (!thread.isInterrupted()) {

				move();
				Thread.sleep(delay);

				synchronized (this) {
					while (isPaused) wait();
				}
			}

		} catch (InterruptedException e) {
	
		}
	}

	boolean checkNewPos(int newX, int newY){
		
		boolean result=true;
		
		// snake with froggs
		for (int j = 1; j <= curGame.snake.animSize - 1; j++) {
			if (curGame.snake.x[j] == newX && curGame.snake.y[j] == newY) {
				return false;
			}
		}
		
		// froggs with froggs
//		for (Animal curAnim: GameAction.anim) { --- throws sometimes java.util.ConcurrentModificationException
		for (int j = 1; j <= curGame.anim.size() - 1; j++) {	
			
			Animal curAnim = curGame.anim.get(j);
			
			if (aType == animalType.Snake) continue;
			
			if (!curAnim.equals(this)&&curAnim.x[0] == newX && curAnim.y[0] == newY) {
				return false;
			}
			
		}
		
		return result;
		
	}
	
	public void move() {
				
		if (aType == animalType.Snake) {
		
			lastX[0] = x[0];
			lastY[0] = y[0];
			lastX[1] = x[animSize-1];
			lastY[1] = y[animSize-1];
			lastX[2] = x[animSize-2];
			lastY[2] = y[animSize-2];
			
			for (i = animSize-1; i > 0; i--) {
				
				x[i] = x[i - 1];
				y[i] = y[i - 1];
			}
						
			if (dir == Direction.Right)	x[0] = x[0] + GlobalVars.block;
			if (dir == Direction.Left)	x[0] = x[0] - GlobalVars.block;
			if (dir == Direction.Up)	y[0] = y[0] - GlobalVars.block;
			if (dir == Direction.Down)	y[0] = y[0] + GlobalVars.block;
			
			curGame.checkCollisions();
			
			if (x[0] > GlobalVars.playWidth-GlobalVars.block)	x[0] = 0;
			if (y[0] > GlobalVars.playHeight-GlobalVars.block)	y[0] = 0;
			if (x[0] < 0)	x[0] = GlobalVars.playWidth-GlobalVars.block;
			if (y[0] < 0)	y[0] = GlobalVars.playHeight-GlobalVars.block;
			
		} else {
		
			lastX[0] = x[0];
			lastY[0] = y[0];
			
			Map<String, Integer> froggCoord = new HashMap<String, Integer>();
			froggCoord = getNewFroggCoord(x[0], y[0]);
			
			if (froggCoord.get("newX") > GlobalVars.playWidth -GlobalVars.block) froggCoord.put("newX", 0);
			if (froggCoord.get("newY") > GlobalVars.playHeight-GlobalVars.block) froggCoord.put("newY", 0);
			if (froggCoord.get("newX") < 0)	froggCoord.put("newX", GlobalVars.playWidth -GlobalVars.block);
			if (froggCoord.get("newY") < 0)	froggCoord.put("newY", GlobalVars.playHeight-GlobalVars.block);
			
			if (checkNewPos(froggCoord.get("newX"), froggCoord.get("newY"))) {
				x[0] = froggCoord.get("newX");
				y[0] = froggCoord.get("newY");
			} 
			
		}
	
	}

	private Map<String, Integer> getNewFroggCoord(int curX, int curY){
		
		Map<String, Integer> froggCoord = new HashMap<String, Integer>();
		
		dot[] distance = new dot[4];
		distance[0]= new dot(curX + GlobalVars.block, curY);
		distance[1]= new dot(curX - GlobalVars.block, curY);
		distance[2]= new dot(curX, curY + GlobalVars.block);
		distance[3]= new dot(curX, curY - GlobalVars.block);
			
		Arrays.sort(distance);
		
		double propability = Math.random();
		
		if (propability<GlobalVars.froggProbability){
			froggCoord.put("newX", distance[0].getX());
			froggCoord.put("newY", distance[0].getY());
		} else {
			Random rand = new Random();
			int r = 1 + rand.nextInt(3);
			froggCoord.put("newX", distance[r].getX());
			froggCoord.put("newY", distance[r].getY());
		}
		
		return froggCoord;
	}
		
	public class dot implements Comparable<dot>{
	    public int x;
	    public int y;
	    public double prob;
	    
	    public dot(int x, int y) {
	    	this.x = x;
	        this.y = y;
		}

	    public void setPropability(double prob) {
	    	this.prob = prob;
	    }
	    
	    public int getX() {
	    	return this.x;
	    }
		
	    double getDistance(int frogX, int frogY){
			return Math.sqrt(Math.pow(frogX-curGame.snake.x[0],2)+Math.pow(frogY-curGame.snake.y[0],2));
		}
		
	    public int getY() {
	    	return this.y;
	    }
	    
		public double getDistance() {
	         return Math.sqrt(Math.pow(this.x-curGame.snake.x[0],2)+Math.pow(this.y-curGame.snake.y[0],2));
	    }

		@Override
		public int compareTo(dot o) {
			double d = o.getDistance()-this.getDistance();
			int i = (int) d;
			return i;
		}
	}
	
}

enum Direction {
	Up, Down, Left, Right
};
enum animalType {
	Snake, Frogg, RedFrogg, BlueFrogg
}