package SnakeGame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class GlobalVars {

	public static int block=30, Height=20, Width=30;
	public static int playHeight=block*Height, playWidth=block*Width;
	public static int snakeSize=4;
	public static int snakeDelay=100;
	public static boolean DoubleBuffered = true;
	public static int greenFroggs=10, redFroggs=4, blueFroggs=1;
	public static double froggProbability=0.8;
	public static boolean printImages=false;

	public static Map<String, BufferedImage> animalImages = new HashMap<String, BufferedImage>();
	
	public static void refreshVars() {
		playHeight = block * Height;
		playWidth  = block * Width;
	}
	
	public static void initImages() {

		for (animalType aType : animalType.values()) {
			
			BufferedImage bi = null;
			
			try {
				bi = ImageIO.read(GlobalVars.class.getResource("/resourses/" + aType.toString() + ".gif"));
				animalImages.put(aType.toString(), bi);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
