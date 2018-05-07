package SnakeGame;

public class GlobalVars {

	public static int block=30, Height=50, Width=50;
	public static int playHeight=block*Height, playWidth=block*Width;
	public static int snakeSize=4;
	public static int snakeDelay=100;
	public static boolean DoubleBuffered = true;
	
	public static void refreshVars() {
		playHeight = block * Height;
		playWidth  = block * Width;
	}
}
