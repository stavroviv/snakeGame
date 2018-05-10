package SnakeGame;

public class GlobalVars {

	public static int block=30, Height=20, Width=30;
	public static int playHeight=block*Height, playWidth=block*Width;
	public static int snakeSize=4;
	public static int snakeDelay=100;
	public static boolean DoubleBuffered = true;
	public static int greenFroggs=10, redFroggs=4, blueFroggs=1;
	public static double froggProbability=0.8;
	
	public static void refreshVars() {
		playHeight = block * Height;
		playWidth  = block * Width;
	}
}
