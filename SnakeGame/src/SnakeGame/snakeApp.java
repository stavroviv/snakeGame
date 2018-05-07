package SnakeGame;

public class snakeApp {
	
	public static void main(String[] args) {
		
		for (int i = 0; i < args.length; i++){

			int curVar = Integer.parseInt(args[i]);
			switch (i) {
			case 0:
				GlobalVars.Height = curVar;
				break;
			case 1:
				GlobalVars.Width = curVar;
				break;
			case 2:
				GlobalVars.snakeSize = curVar;
				break;	
			case 3:
				GlobalVars.snakeDelay = curVar;
				break;	
			}

		}
		
		GlobalVars.refreshVars();
		
		mainForm snakeGame = new mainForm();
		snakeGame.setVisible(true); 
		
	}

}
