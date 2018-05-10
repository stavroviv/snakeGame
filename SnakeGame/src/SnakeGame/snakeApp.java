package SnakeGame;

public class snakeApp {
	
	public static void main(String[] args) {
		
		for (int i = 0; i < args.length; i++){

			int curArg=-1;
			
			try {
				curArg = Integer.parseInt(args[i]);
			} catch (NumberFormatException e) {
			
				System.out.println("incorrect arg \"" + argName(i)+"\": " + args[i]);
				continue;
			}
			
			procArg(i, curArg);

		}
		
		GlobalVars.refreshVars();
		GlobalVars.initImages();
		
		mainForm snakeGame = new mainForm();
		snakeGame.setVisible(true); 
		
	}

	static void procArg(int i, int curArg){
		
		switch (i) {
		case 0:
			GlobalVars.Height = curArg;
			break;
		case 1:
			GlobalVars.Width = curArg;
			break;
		case 2:
			GlobalVars.snakeSize = curArg;
			break;
		case 3:
			GlobalVars.snakeDelay = curArg;
			break;
		}
	
	}

	static String argName(int i){
		
		String argName="";
		
		switch (i) {
		case 0:
			argName = "height";
			break;
		case 1:
			argName = "width";
			break;
		case 2:
			argName = "snake size";
			break;
		case 3:
			argName = "snake delay";
			break;
		}
		
		return argName;
	}

}
