import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Object {
	final static int TIMER_MAX_ANIM = 15;
	
	double x;
	double y;
	int w;
	int h;
	double vx;
	double vy;
	
	boolean sidex=true;

	double angle = 0; 
	
	double speed = 1;
	
	Image img[][];
	int indexLAnim;
	int indexAnim;
	int timer_anim;
	
	public Object(double _x, double _y, int _w, int _h, Image[][] _img) {
		//Function constructor of the class object, (x,y) is the position of the object in the screen, (w,h), the size
		//and Image[][] an array of dimension 2 of images. The handler of the animations is explained
		//in the code comments of the function handleAnimation()
		x=_x;
		y=_y;
		w=_w;
		h=_h;
		img=_img;
	}
	
	public void action() {
		//This function is used to move the object 
		
		//We want to displace the object with a speed vx (according to coordinate x)
		//If there aren't any collision with the game field (this is checked with a function in the class Calculs)
		//when we add the parameter vx to the position x, then we can move the object to the position x+vx
		//if it's not possible, then vx is set to x. (it's the same for the coordinates y)
		if (!Calculs.collisionGamefield(x+vx, y, w, h, Game.gamefield)) x+=vx;
		else vx=0;
		
		if (!Calculs.collisionGamefield(x, y+vy, w, h, Game.gamefield)) y+=vy;
		else vy=0;
		
		//We change the side of the object. If vx>0, the object is moving to the right so sidex=true
		//Otherwise, the object is moving to the left
		if (vx>0) {
			sidex=true;
		}
		else if (vx<0){
			sidex=false;
		}
		
		//Change the angle of the picture for when it will be displayed, for example, if the player turns up, the picture is rotated with an angle of 90 degrees
		if (vy>0) {
			angle = Math.PI/2;
		}
		else if (vy<0) {
			angle = -Math.PI/2;
		}
		else {
			angle = 0;
		}
		
		//We call the function which handle the animations
		handleAnimation();
	}

	public void handleAnimation() {
		//This function handle the animations
		//Handler of the animations :
		//In the constructor, we load a Image[][] array who contains different animation
		//For example, a Image[][] with dimensions 4*5 will contain 4 animations of length 5
		//The integer indexLAnim refers to the number of the animation which is used
		//The integer indexAnim refers to the number of the picture in the animation which is used
		
		//The picture of the current animation is uploaded every TIMER_MAX_ANIM ms, where TIMER_MAX_ANIM = 15
		timer_anim++;
		if (timer_anim >= TIMER_MAX_ANIM) {
			indexAnim++;
			timer_anim = 0;
		}

		//If the indexAnim is bigger than the number of images of the animation, then indexAnim = 0
		if (indexAnim>=img[indexLAnim].length) {
			indexAnim=0;
		}
	}
	
	public Rectangle getRectangle() {
		//Get a rectangle construct with the position and the size of the object, used for the calculs of collision
		return new Rectangle((int)x,(int)y,w,h);
	}
	
	public void move(int direction) {
		//Function which set the speed of the object to some values according to the direction
		//This value will be used in the function action()
		//The variables theorical_speed are used as reference values (they are never modified in the code, it's just to tell
		//to the object which speed they will have if they do a move)
		switch (direction) {
		case Settings.DIRECTION_DOWN :
			vy=speed;
			break;
		case Settings.DIRECTION_UP :
			vy=-speed;
			break;
		case Settings.DIRECTION_LEFT :
			vx=-speed;
			break;
		case Settings.DIRECTION_RIGHT :
			vx=speed;
			break;
		}
	}
	
	public boolean canMove(int direction) {
		//Function that return true if the object is able to do a move into a specified direction (by looking with
		//the collisions with the game field)
		switch (direction) {
		case Settings.DIRECTION_DOWN :
			return !Calculs.collisionGamefield(x, y+speed, w, h, Game.gamefield);
		case Settings.DIRECTION_UP :
			return !Calculs.collisionGamefield(x, y-speed, w, h, Game.gamefield);
		case Settings.DIRECTION_LEFT :
			return !Calculs.collisionGamefield(x-speed, y, w, h, Game.gamefield);
		case Settings.DIRECTION_RIGHT :
			return !Calculs.collisionGamefield(x+speed, y, w, h, Game.gamefield);
		}
		return false;
	}
	
	public void drawOnScreen(Graphics2D g2d) {
		//Function that draws the object on the screen
		if (angle==0) { //if the angle is 0 (then the character is moving to the right or to the left
			if (sidex) {
				g2d.drawImage(img[indexLAnim][indexAnim], (int)(x), (int)(y), w, h, null); 
				//the player moves to the right, we draw "normally" the picture
			}
			else {
				g2d.drawImage(img[indexLAnim][indexAnim], (int)(x+w), (int)(y), -w, h, null);
				//the player moves to the left, we draw the picture reflected (so the size is negative)
			}
		}
		else {
			g2d.drawImage(Calculs.imageAfterRotation(angle, img[indexLAnim][indexAnim]), (int)(x), (int)(y), w, h, null);
			//the player moves neither to the left nor the right, so we have to make do a rotation to the image of the specified angle by using the function Calculs.imageAfterRotation
			
			
		}
	}
	
}