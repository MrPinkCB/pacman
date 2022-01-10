import java.util.LinkedList;
import java.util.List;

public class Menu {
	public static final int MODE_MENU = 0;
	public static final int MODE_GAME = 1;
	public static final int MODE_SCORE = 2;
	
	public static int mode = MODE_MENU;

	public static List<int[][]> listletters = new LinkedList<>();
	//The Main Menu Pacman logo screen
	static int[][] P = 
		{{1,1,1,1},
		  {1,0,0,1},
		  {1,1,1,1},
		  {1,0,0,0},
		  {1,0,0,0}};
	static int[][] A = 
		{{1,1,1,1},
		  {1,0,0,1},
		  {1,1,1,1},
		  {1,0,0,1},
		  {1,0,0,1}};
	static int[][] M = 
		 {{1,0,0,0,1},
		  {1,1,0,1,1},
		  {1,0,1,0,1},
		  {1,0,0,0,1},
		  {1,0,0,0,1}};
	static int[][] N =  
		 {{1,0,0,0,1},
		  {1,1,0,0,1},
		  {1,0,1,0,1},
		  {1,0,0,1,1},
		  {1,0,0,0,1}};
	static int[][] S = 
		{{0,1,1,1,1},
		  {1,0,0,0,0},
		  {0,1,1,1,0},
		  {0,0,0,0,1},
		  {1,1,1,1,0}};
	static int[][] C = 
		{{0,1,1,1,1},
		  {1,0,0,0,0},
		  {1,0,0,0,0},
		  {1,0,0,0,0},
		  {0,1,1,1,1}};
	static int[][] O = 
		{{0,1,1,1,0},
		  {1,0,0,0,1},
		  {1,0,0,0,1},
		  {1,0,0,0,1},
		  {0,1,1,1,0}};
	static int[][] E = 
		{{1,1,1,1,1},
		  {1,0,0,0,0},
		  {1,1,1,1,1},
		  {1,0,0,0,0},
		  {1,1,1,1,1}};
	static int[][] R = 
		{{1,1,1,1,0},
		  {1,0,0,0,1},
		  {1,1,1,1,0},
		  {1,0,0,1,0},
		  {1,0,0,0,1}};
	
	//Initialized letters
	public static void initializeTitle() {
		listletters.add(P);
		listletters.add(A);
		listletters.add(C);
		listletters.add(M);
		listletters.add(A);
		listletters.add(N);

	}
	//Initialized menu
	public static void initializeButtons() {	
		initializeTitle();
		changeMode(MODE_MENU);
	}
	
	public static void changeMode(int nmode) {
		mode = nmode;
	}
	
	public static void actionButton(String arg) {
		switch (arg) {
		case "Play" :
			changeMode(MODE_GAME);
			Game.reinitializeGame();
			
			break;
		case "Scores" :
			if (mode == MODE_MENU || mode == MODE_GAME) {
				Highscore.scoreSet();
				changeMode(MODE_SCORE);
			}
			break;
		case "Exit" :
			Game.wd.dispose();
			break;
		case "Save" :
			if (mode == MODE_GAME) {
				DataBaseHandler.insertDatabase(Window.textScoreName.getText(),Game.score);
			}
			break;
		}
	}

}