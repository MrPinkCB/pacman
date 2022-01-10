import java.awt.Image;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Game implements Runnable {
	//Game outcomes
	public static final int NO = -1;
	public static final int WIN = 0;
	public static final int LOSE = 1;
	//Key variables
	public static boolean keyLeftPressed = false;
	public static boolean keyRightPressed = false;
	public static boolean keyUpPressed = false;
	public static boolean keyDownPressed = false;
	
	public static Window wd;
	static Camera drawingLink;
	
	public static int defeat = NO;
	//Initialization of game variables (calling on classes object, enemy and dots)
	static int[][] gamefield = new int[22][23] ;
	static List<Object> listComponents = new LinkedList<>();
	static List<Enemy> listEnemy = new LinkedList<>();
	static List<Dot> listDots = new LinkedList<>();
	
	static Object character;

	static int timegame = 0;
	
	static int score = 0;
	public static int timer=0;
	static long timer_init=0;
	
	boolean terrainDone = false;
	int timerTerrain = 0;
	static int MAX_TIMER_TERRAIN = 60;
	
	public void run() {
		//Thread used every 5 millisecond
		while (true) {
			try {
				Thread.sleep(5);
				
				if (Menu.mode==Menu.MODE_GAME) {
					//If the game is running, we use the function gameloop()
					gameloop();
				}

				drawingLink.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void reinitializeGame() {
		//Re-Initialization of a game
		
		//Firstly, we remove all the existing object (enemies and dots) of the lists such that we have cleared lists
		//to reinitialize the game
		
		listEnemy.clear();
		listComponents.clear();
		listDots.clear();
		
		//We initialize play field, using the resources which are in the class Settings which contains a int[][] level1
		//which contains the "physics" of level1 (0 if a case is empty, 1 if it's a wall, 2 if it's a wall of the box in
		//which the enemies appear
		gamefield = Arrays.copyOf(Settings.level1, Settings.level1.length);
		
		//Initialization of the little dots that the character has to eat, we use an int[][] dotlevel1 which is in the class Settings
		LDots.initializeArray(Settings.dotlevel1);
		
		//Initialization of the enemies and of the big dots, we add all the enemy in a list of enemies
		//We also add all the dots in a list of dots. In this way, further in the program, we'll be able to 
		//handle all the events related to all the enemies or dots just by using a loop
		Enemy blue = new Enemy(Settings.startBlue.x,Settings.startBlue.y, Enemy.BLUE);
		Enemy purple = new Enemy(Settings.startPurple.x,Settings.startPurple.y, Enemy.PURPLE);	
		Enemy green = new Enemy(Settings.startGreen.x,Settings.startGreen.y, Enemy.GREEN);
		Enemy red = new Enemy(Settings.startRed.x,Settings.startRed.y, Enemy.RED);
		listEnemy.add(blue);
		listEnemy.add(purple);
		listEnemy.add(green);
		listEnemy.add(red);
		
		Dot d1 = new Dot(1*20,1*20);
		Dot d2 = new Dot((23-2)*20,1*20);
		Dot d3 = new Dot(1*20,(22-2)*20);
		Dot d4 = new Dot((23-2)*20,(22-2)*20);
		listDots.add(d1);
		listDots.add(d2);
		listDots.add(d3);
		listDots.add(d4);

		//We initialize the character for it to have its initial properties
		character = new Object(Settings.startingPoint.x, Settings.startingPoint.y, 20, 20, new Image[][] {Settings.pacman});
		//We add all the enemies, the dot and the character into a list listComponents made of Object type (it's not
		//the generic type but a class from which all the classes Enemy, Dot and Pacman inherit)
		for (Enemy e : listEnemy) listComponents.add(e);
		for (Dot e : listDots) listComponents.add(e);
		listComponents.add(character);
		
		//We set the number of enemies which are not in the box containing the enemies in the beginning of the game to 0
		//since the enemies are here at the beginning of the game
		Enemy.nbrGetOut=0;
		//We set the score to 0
		score=0;
		//The player hasn't lose yet so defeat=0
		defeat=NO;
	}
	
	public static void linkDraw(Camera dl) {
		//We link a camera (which is inheriting from the class JPanel) to the class Game, so that we can draw 
		//on the screen
		drawingLink = dl;		
	}
	
	public void gameloop() {
		//Game loop of the game, run every 5ms if the game if running
		if (defeat==NO) {
			//run if the the player hasn't lost yet
			
			//Management of the environment with the keys, the value of the boolean are set in the class Window
			//in which we use a key listener
			//if the key left is pressed, the boolean keyLeftPressed will be true (otherwise, it'll be no)
			//in this case, the function move in the class object from which the class Pacman inherit will be 
			//activated, and the parameter Settings.DIRECTION_LEFT (which is just an integer defined in the class
			//Setting will be used). It's the same for other keys right, up, down
			
			if (keyLeftPressed) {
				character.move(Settings.DIRECTION_LEFT);
			}
			else if (keyRightPressed){
				character.move(Settings.DIRECTION_RIGHT);
			}
			else if (keyUpPressed) {
				character.move(Settings.DIRECTION_UP);
			}
			else if (keyDownPressed) {
				character.move(Settings.DIRECTION_DOWN);
			}

			//For all enemies in listEnemy (so for all enemies which appear on the screen), we activate the function
			//related to their artificial intelligence.
			for (Enemy e : listEnemy) {
				e.AIRun(character);
			}
			
			//For all dots in listDots, we activate the function verifyCollision which look if the character collide
			//with one of the big dots
			for (Dot e : listDots) {
				e.verifyCollision(character);
			}
			
			//For all object in listComponents (so for the pacman character, for all the enemies and for all the big dots), 
			//we activate the function action(), which handle their physical properties such as their speed, their position
			//and the animations. The speed has been defined precedently (in AIRun for the enemies, in character.move for the
			//character, but this function will check if the object can move with this speed or not (by checking the collision)
			//If it's possible, it will add the value vx to the coordinate x and the value vy to the coordinate y of the object
			//If not, vx and/or vy will be set to 0
			for (Object o : listComponents) {
				o.action();
			}
			
			//Management of the little dot, this function will verify if the character is in collision with a dot that hasn't been
			//taken yet
			LDots.manageLDots(character);
			//This function is used to handle the box in which the enemies appear. If there aren't any enemies in the box,
			//the box is closed and in the other case, the box is open
			terrainHandle();
			//This function verify the global state of the class Enemy, it means that it verifies if the enemies are running
			//away from the character or are chasing after it
			Enemy.handleStateGlobal();
		}
		else {
			//activated if the player lose
		}
		
		
	}

	public void terrainHandle() {
		/*
		 * this function check how many enemies are in the box which contains the enemies at the beginning of a game
		 * if there still are enemies in the box, this one is open
		 * if not, we close the box
		 */
		
		//if there are 4 enemies out of the box (so no more enemies in) and the box isn't closed yet, we wait 60ms
		//to be sure that every enemy is not in the box anymore (in case they would change direction at the last time)
		//and then we close the box
		if (Enemy.nbrGetOut==4 && !terrainDone) { 
			//if terrainDone = false, it means that we still haven't closed the box
			timerTerrain++; 
			if (MAX_TIMER_TERRAIN<=timerTerrain) { 
				//if 60ms has passed, we close the box
				gamefield[Settings.caseygetout][Settings.casexgetout] = 2; 
				//the linked case of the game field has now a value of 2 so the objects can collide with it
				terrainDone=true;
				//the box is closed and we reinitialize the variable timerTerrain to 0
				timerTerrain=0;
			}

		}
		else if (Enemy.nbrGetOut!= 4) { 
			//There are still enemies in the box, so we set the value of the linked of the game field to 0 so that the enemies can pass by this case
			gamefield[Settings.caseygetout][Settings.casexgetout] = 0;
			terrainDone=false;
			//the variable terrainDone is now equals to false because the box isn't closed yet
		}
	}
	
	public static void death() {
		//This function is used when the character dies
		defeat = LOSE; 
	}
	
	public static void win() {
		//This function is used when the player wins
		defeat = WIN;
	}
	
	public static void linkWindow(Window fe) {
		//Link a Window (which is a class inheriting from JFrame) to the class Game
		wd=fe;
	}
}
