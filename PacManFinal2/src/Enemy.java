import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

public class Enemy extends Object {
	final int VERTICAL = Settings.DIRECTION_UP;
	final int HORIZONTAL = Settings.DIRECTION_LEFT;
	//AI Assignment 
	public static final int BLUE = 0;
	public static final int PURPLE = 1;
	public static final int GREEN = 2;
	public static final int RED = 3;
	
	//Variables related to the AI
	public static final int STATE_DEATH = -1;
	public static final int STATE_WAIT = 0;
	public static final int GET_OUT = 3;
	public static final int STATE_ATTACK = 1;
	public static final int STATE_RUN_AWAY = 2;
	
	static final int TIMER_CHANGE = 20*2;
	static final int TIMER_DEATH = 300; 
	
	static final int TIMER_CHANGE_MODE = 800;
	static  int timerMode = 0;
	
	static boolean isAttacking = true;
	
	int state = STATE_WAIT;
	int type;
	
	int TIMER_START;
	
	Point initialPosition;
	int lastMove;
	int timer;
	static int nbrGetOut = 0;
	
	public Enemy(double _x, double _y, int typeEnemy) {
		//Constructor of the function Enemy, position = (x,y).
		//There are four type of Enemies : BLUE, PURPLE, GREEN, RED (each represented by an integer)
		//The enemies do not start to move at the same time (that's why we have to introduce a variable TIMER_START)
	
		super(_x, _y, 20, 20, new Image[][] {Settings.EnemiesImg[typeEnemy],Settings.EnemiesImg[4]});
		initialPosition = new Point((int)x,(int)y);
		type = typeEnemy;
		// TODO Auto-generated constructor stub
		switch (typeEnemy) {
		case BLUE :
			speed=0.5;	
			TIMER_START=0;
			break;
		case PURPLE :
			speed=0.5;
			TIMER_START=200;
			break;
		case GREEN :
			speed=0.5;
			TIMER_START=400;
			break;
		case RED : 
			speed=0.5;
			TIMER_START=600;
			break;
		}
	}
	
	public void AIRun(Object character) {
		//This function makes the enemy make a decision according to the situation (the position of the character, and the state of the enemy)
		//Different states :
		//1.) STATE_WAIT : the enemy is visible, but doesn't move
		//2.) STATE_DEATH : the enemy isn't visible and doesn't move on the screen but its position get back to its initial position
		//3.) GET_OUT : the enemy is moving to the case of the screen to get out of its box
		//4.) STATE_ATTACK : the enemy is following the player
		//5.) STATE_RUN_AWAY : the enemy is running away from the player
		
		handleState();
		switch (state) {
		case STATE_WAIT :
			handleWait();
			break;
		case STATE_DEATH :
			handleDeath();
			break;
		case GET_OUT :
			//choose a move in direction of the case [10,6] (recorded in the variable lastMove)
			lastMove = AIRunAttackDirection(10*20, 6*20);
			move(lastMove);
			break;
		case STATE_ATTACK :
			//choose a move in direction of the character (recorded in the variable lastMove)
			lastMove = AIRunAttackDirection(character.x, character.y);
			move(lastMove);
			
			break;
		case STATE_RUN_AWAY :
			//choose a move (recorded in the variable lastMove), running away from the character
			lastMove = AIRunAttackDirection(character.x, character.y);
			move(AIRunRunDirection(character.x, character.y));
			break;
		}
		
		//handle the index of the animation (if the enemy is running away, the color changes to blue)
		if (state == STATE_RUN_AWAY) {
			indexLAnim = 1;
		}
		else {
			indexLAnim = 0;
		}
		
		//Handle the attacks towards the character or from the character
		handleAttack(character);
	}
	
	public void handleAttack(Object character) {
		//We firstly look if the two objects collides by using the function Calculs.collision
		//if the enemy is attacking and the two collides, the character dies
		//Otherwise, it's the enemy which dies
		if (Calculs.collision(character.getRectangle(), this.getRectangle())) {
			if (state == STATE_ATTACK) {
				Game.death();
			}
			else if (state == STATE_RUN_AWAY) {
				changeState(STATE_DEATH);
			}
		}
	}
	
	public int triesToGo(int deltadir, int dir) {
		//the character tries to go in the direction given by the sign of deltadir (if deltadir<0 and dir = HORIZONTAL, 
		//the best direction is left, if dir = VERTICAL, the best direction is up, ...)
		//If it can't go to this direction, the function return -1, otherwise it returns the best direction
		if (deltadir <0 && lastMove != Calculs.giveOppositeDirection(dir)) { //the best possibility to get close is to go up
			if (canMove(dir)) {
				return dir;
			}
		}
		else if (deltadir>0 && lastMove != dir) {//the best possibility to get close is to go down
			if (canMove(Calculs.giveOppositeDirection(dir))) {
				return Calculs.giveOppositeDirection(dir);
			}
		}
		return -1;
	}
	
	public int AIRunAttackDirection(double px, double py) {
		//AI makes the enemy make decision for the next move he is going to make
		
		//The enemy takes decisions every 20px, otherwise, he continues to move in the direction he was moving
		timer++;
		if (timer>=TIMER_CHANGE) {
			timer=0;
			int move=-1;
			//int move is the variable of the next move the enemy is going to take
			//initially, its value is -1. In the end, it'll be one of the four directions left, right, down or up
			
			//We look if the main character is left or right or up or down to the enemy
			int dirx = (int)(px/20)-(int)(this.x/20);
			int diry = (int)(py/20)-(int)(this.y/20);
			//dirx>0 => the character is right to the enemy, otherwise it's left
			//diry>0 => the character is down to the enemy, otherwise it's up
			
			//we choose randomly if the character tries first to turn horizontally or vertically. The objective is that two enemies don't take the same direction each time
			boolean vfirst = Calculs.getRandomInt(2)==0;
		
			if (vfirst) {
				//the enemy first tries to move vertically in the direction of the character. The enemy tries if possible to never turn around
				//if the enemy can't go in the best direction, it'll try another direction
				move=triesToGo(diry,VERTICAL);
				if (move==-1) { //if the enemy couldn't go in the best vertical direction, it tries to go to the best horizontal direction
					move=triesToGo(dirx,HORIZONTAL);
				}
			}
			else {
				//same code but the enemy tries firstly to go up
				move=triesToGo(dirx,HORIZONTAL);
				if (move==-1) {
					move=triesToGo(diry,VERTICAL);
				}
			}
			
			if (move==-1) {
				//choose if the enemy tries to continue firstly his move or tries to turn, even in the wrong direction
				boolean choosecontinue = Calculs.getRandomInt(2)==0;
				if (choosecontinue) {
					if (canMove(lastMove)) {
						move=lastMove;
					}
					for (int i = 0;i<4;i++) {
						//We test all the direction except the opposite to find the right one
						if (canMove(i) && i!=Calculs.giveOppositeDirection(lastMove)) {
							move = i;
						}
					}
				}
				else {
					for (int i = 0;i<4;i++) {
						//We test all the direction except the opposite and the current direction to find the right one
						if (canMove(i) && i!=Calculs.giveOppositeDirection(lastMove) && i!=lastMove) {
							move = i;
						}
					}
					if (canMove(lastMove)) {
						move=lastMove;
					}
				}
			}
			if (move==-1) {
				move=Calculs.giveOppositeDirection(lastMove);
			}


			//After all this process, move is equal to one of the four directions
			return move;

		}
		else {
			return lastMove;
		}
	}

	public int AIRunRunDirection(double px, double py) {
		//Function exactly the same way than AIRunAttackDirection, except that the enemy run away from the character
		timer++;
		if (timer>=TIMER_CHANGE) {
			timer=0;
			int move=-1;
			//1.) We look if the main character is left or right or up or down to the enemy
			int dirx = -(int)(px/20)+(int)(this.x/20);
			int diry = -(int)(py/20)+(int)(this.y/20);
			//The best direction is the direction opposite from the direction in which the character is from the enemy
			//so dirx is the opposite of what number dirx was in the function AIRunAttackDirection
			
			
			//if the distance between the character and the enemy is too small (square <=50), we try to run away
			//The code is exactly the same than in AIRunAttack
			if (dirx*dirx + diry*diry <=50) {
				//we choose randomly if the character tries first to turn horizontally or vertically
				boolean vfirst = Calculs.getRandomInt(2)==0;
				
				if (vfirst) {
					move=triesToGo(diry,VERTICAL);
					if (move==-1) {
						move=triesToGo(diry,HORIZONTAL);
					}
				}
				else {
					move=triesToGo(diry,HORIZONTAL);
					if (move==-1) {
						move=triesToGo(diry,VERTICAL);
					}
				}
				
				if (move==-1) {
					//choose if the enemy tries to continue firstly his move or tries to turn, even in the wrong direction
					boolean choosecontinue = Calculs.getRandomInt(2)==0;
					if (choosecontinue) {
						if (canMove(lastMove)) {
							move=lastMove;
						}
						for (int i = 0;i<4;i++) {
							//We test all the direction except the opposite to find the right one
							if (canMove(i) && i!=Calculs.giveOppositeDirection(lastMove)) {
								move = i;
							}
						}
					}
					else {
						for (int i = 0;i<4;i++) {
							//We test all the direction except the opposite to find the right one
							if (canMove(i) && i!=Calculs.giveOppositeDirection(lastMove) && i!=lastMove) {
								move = i;
							}
						}
						if (canMove(lastMove)) {
							move=lastMove;
						}
					}
				}
				if (move==-1) {
					move=Calculs.giveOppositeDirection(lastMove);
				}

				//After all this process, move is equal to one of the four directions
				return move;
			}
			else {
				//Otherwise, the enemy tries to continue its move if possible. Otherwise, it turns randomly
				if (canMove(lastMove)) {
					return lastMove;
				}
				else {
					lastMove+=1;
					if (lastMove==4) {
						lastMove=0;
					}
					return lastMove;
				}
			}


		}
		else {
			return lastMove;
		}

	}

	public void handleDeath() {
		//Handle the death of the enemy.
		//The death has a duration of 300ms. During this time, the enemy isn't visible into the screen
		//After that, the enemy is moved to his initial position (which is defined in the constructor of the class)
		//Then the enemy change his state to STATE_WAIT (it'll be waiting some time before moving)
		timer++;
		if (timer>=TIMER_DEATH) {
			timer=0;
			moveToInitialPosition();
			changeState(STATE_WAIT);
		}
	}

	public void handleWait() {
		//The enemy just waits before moving out from the box in which he is
		timer++;
		if (timer>=TIMER_START) {
			timer=0;
			changeState(GET_OUT);
		}
	}

	public void handleState() {
		//Handle the different states of the enemy
		if (state != STATE_DEATH && state != STATE_WAIT && state != GET_OUT) {
			//Actualize the state of the enemy if the enemy is out of the box, in comparison with the global state of the class
			//enemy (so if the enemies are attacking, the state will be STATE_ATTACK, otherwise it'll be STATE_RUN_AWAY
			state = isAttacking? STATE_ATTACK: STATE_RUN_AWAY;
		}
		
		if (state==GET_OUT) {
			if ((int)(x/20)==11 && (int)(y/20)==8) {
				//If the case on which the enemy is standing is the "get out case", the enemy start to attack the character
				//and change his state to STATE_ATTACK
				changeState(STATE_ATTACK);
			}
		}
	}
	
	public void changeState(int arg) {
		//Change the state of the enemy
		//If the state is getOut, the enemy is going to be out of the box so we can increase the variable nbrGetOut
		//If the state is STATE_WAIT, the enemy has just appeared in the box (and it hasn't been counted yet) so we can decrease the variable nbrGetOut
		if (state==GET_OUT) {
			nbrGetOut++;
		}
		if (arg==STATE_WAIT) {
			nbrGetOut--;
		}
		state=arg;
	}

	public void moveToInitialPosition() {
		//Move the enemy to its initial position
		this.x = this.initialPosition.x;
		this.y = this.initialPosition.y;
	}

	public static void handleStateGlobal() {
		//If the enemy are running away from the character, it's only for less than 10s so after this time, the enemies have to attack again
		if (Enemy.isAttacking == false) {
			Enemy.timerMode++;
			if (Enemy.timerMode >= Enemy.TIMER_CHANGE_MODE) {
				timerMode=0;
				Enemy.isAttacking = true;
			}
		}
		
	}

	@Override
	public void drawOnScreen(Graphics2D g2d) {
		//We draw the enemy if and only if he isn't dead
		if (state!=STATE_DEATH) {
			angle=0;
			super.drawOnScreen(g2d);
		}

	}
}
