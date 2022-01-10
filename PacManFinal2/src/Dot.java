import java.awt.Color;
import java.awt.Graphics2D;

public class Dot extends Object {
	static final int TIMER_TAKEN = 800;
	
	boolean taken = false;
	
	int timer = 0;
	
	public Dot(double _x, double _y) {
		//Constructor of the class Dot
		super(_x, _y, 10, 10, null);
		// TODO Auto-generated constructor stub
	}
	
	public void action() {
		//if the dot hasn't been taken yet, nothing happens in this function (which the function action of the class object
		//but redefined)
		//otherwise, a timer is set such that after 800ms, if the dot was taken, it isn't anymore
		if (taken == true) {
			timer++;
			if (timer>=TIMER_TAKEN) {
				timer = 0;
				taken= false;
			}
		}
	}
	
	public void verifyCollision(Object character) {
		//This function is to verify if the big dot collides with the character, if it's true and the dot hasn't been taken
		//the dot is taken.
		if (Calculs.collision(getRectangle(), character.getRectangle()) && taken == false) {
			taken = true;
			Enemy.isAttacking = false;
		}
	}

	@Override
	public void drawOnScreen(Graphics2D g2d) {
		//Draw the dot on the screen if it hasn't been taken. It's draw in the center of a case
		if (taken == false) {
			g2d.setColor(Color.WHITE);
			g2d.fillOval((int)(x+(20-w)/2), (int)(y+(20-h)/2), w, h);
		}
	}
	
}