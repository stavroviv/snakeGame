package SnakeGame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Animals implements Runnable {
		
	int delay;
	Thread thread;
	boolean die, isPaused;
	int snakeDelay = 100;
	int frogDelay = 1000;
	
	int[] x = new int[mainForm.playWidth];
	int[] y = new int[mainForm.playHeight];
	
	public int[] lastX = new int[mainForm.playWidth];
	public int[] lastY = new int[mainForm.playHeight];
	
	public int i, animSize;
		
	boolean inGame;
	
	Direction dir;
	animalType aType;
	
	public animalType getaType(){
		return this.aType;
	}
	
	Animals(animalType aType) {
		
		this.aType = aType;
		
		if (aType == animalType.Snake) {
			animSize = mainForm.snakeSize;
			delay = snakeDelay;
			int j = 0;
			for (i = animSize; i > 0; i--) {
				x[j] = i * mainForm.block;
				y[j] = 0;
				j++;
			}
		} else {
			animSize = 1;
			delay = frogDelay;
			Random random = new Random();
			int newX = random.nextInt(mainForm.playWidth / mainForm.block) * mainForm.block;
			int newY = random.nextInt(mainForm.playHeight / mainForm.block) * mainForm.block;
			while (!checkNewPos(newX,newY)) {
				newX = random.nextInt(mainForm.playWidth / mainForm.block) * mainForm.block;
				newY = random.nextInt(mainForm.playHeight / mainForm.block) * mainForm.block;	
			}
			x[0] = newX;
			y[0] = newY;
		}
		
		dir = Direction.Right;
		
		thread = new Thread(this);
		thread.start(); 
    	
	}

	public void stopAnim(){
		thread.interrupt();
	}
	
	public void pauseAnim() {
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
		for (int j = 1; j <= GameAction.snake.animSize - 1; j++) {
			if (GameAction.snake.x[j] == newX && GameAction.snake.y[j] == newY) {
				return false;
			}
		}
		
		// froggs with froggs
		for (Animals curAnim: GameAction.anim) {

			if (!curAnim.equals(this)&&curAnim.x[0] == newX && curAnim.y[0] == newY) {
				return false;
			}
		}
		return result;
		
	}
	
	public void move() {
				
		if (aType == animalType.Snake) {
			
			int g=1;
			
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
						
			if (dir == Direction.Right)	x[0] = x[0] + mainForm.block;
			if (dir == Direction.Left)	x[0] = x[0] - mainForm.block;
			if (dir == Direction.Up)	y[0] = y[0] - mainForm.block;
			if (dir == Direction.Down)	y[0] = y[0] + mainForm.block;
			
			GameAction.checkCollisions();
			
			if (x[0] > mainForm.playWidth-mainForm.block)	x[0] = 0;
			if (y[0] > mainForm.playHeight-mainForm.block)	y[0] = 0;
			if (x[0] < 0)	x[0] = mainForm.playWidth-mainForm.block;
			if (y[0] < 0)	y[0] = mainForm.playHeight-mainForm.block;
			
		} else {
		
			lastX[0] = x[0];
			lastY[0] = y[0];
			
			Map<String, Integer> froggCoord = new HashMap<String, Integer>();
			froggCoord = getNewFroggCoord(x[0], y[0]);
			
			if (froggCoord.get("newX") > mainForm.playWidth-mainForm.block)	 froggCoord.put("newX", 0);
			if (froggCoord.get("newY") > mainForm.playHeight-mainForm.block) froggCoord.put("newY", 0);
			if (froggCoord.get("newX") < 0)	froggCoord.put("newX", mainForm.playWidth-mainForm.block);
			if (froggCoord.get("newY") < 0)	froggCoord.put("newY", mainForm.playHeight-mainForm.block);
			
			if (checkNewPos(froggCoord.get("newX"),froggCoord.get("newY"))) {
				x[0] = froggCoord.get("newX");
				y[0] = froggCoord.get("newY");
			} 
			
		}
	
	}

	private Map<String, Integer> getNewFroggCoord(int curX, int curY){
		
		Map<String, Integer> froggCoord = new HashMap<String, Integer>();
		
		dot[] distance = new dot[4];
		distance[0]= new dot(curX + mainForm.block, curY);
		distance[1]= new dot(curX - mainForm.block, curY);
		distance[2]= new dot(curX, 					curY + mainForm.block);
		distance[3]= new dot(curX, 					curY - mainForm.block);
			
		Arrays.sort(distance);
		
		double propability = Math.random();
		
		if (propability<mainForm.froggProbability){
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
			return Math.sqrt(Math.pow(frogX-GameAction.snake.x[0],2)+Math.pow(frogY-GameAction.snake.y[0],2));
		}
		
	    public int getY() {
	    	return this.y;
	    }
	    
		public double getDistance() {
	         return Math.sqrt(Math.pow(this.x-GameAction.snake.x[0],2)+Math.pow(this.y-GameAction.snake.y[0],2));
	    }

		@Override
		public int compareTo(dot o) {
			// TODO Auto-generated method stub
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