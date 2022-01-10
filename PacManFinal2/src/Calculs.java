import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Calculs {
	public static boolean collisionGamefield(double px, double py, int w, int h, int[][] gamefield) {
		try {
			//px, py is the coordinate in the screen, we search all the numbers of the cases of the play on which the rectangle (px,py,w,h) are
			//These cases are (c1x,c1y),(c2x,c1y),(c1x,c2y),(c2x,c2y)
			int c1x = (int)(px/20);
			int c2x = (int)((px+w-1)/20);
			int c1y = (int)(py/20);
			int c2y = (int)((py+h-1)/20);

			//All the cases on which the rectangle is located have to have a value of 0
			if (gamefield[c1y][c1x]==0 && gamefield[c1y][c2x]==0 && gamefield[c2y][c1x]==0 && gamefield[c2y][c2x]==0) {
				return false;
			}
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public static boolean collision(Rectangle box1, Rectangle box2) {
		//We look if the 2 rectangle collide, return true if they collide
		if	((box2.x >= box1.x + box1.width) || (box2.x + box2.width <= box1.x)
				|| (box2.y >= box1.y + box1.height) || (box2.y + box2.height <= box1.y))			
			{
				return false; 
			}					
			else
			{
				return true;	
			}	
	}
	
	public static boolean collisionAB(double x,double y,double w,double h, double xx, double yy, double ww, double hh) {	
		if	((xx >= x + w) || (xx + ww <= x)
			|| (yy >= y + h) || (yy + hh <= y))			
		{
			return false; 
		}					
		else
		{
			return true;	
		}			
	}
	
	public static int getRandomInt(int max) {
		//return a random number between 0 and max-1;
		return (int)(Math.floor(Math.random()*max));
	}

	public static Image imageAfterRotation(double angle, Image img) {
		//Return a rotation of the image img with a rotation of angle (centered)
		BufferedImage img2 = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img2.createGraphics();
		
		AffineTransform rotation = new AffineTransform();
		rotation = AffineTransform.getRotateInstance(angle,(int)(img.getWidth(null)/2),(int)(img.getHeight(null)/2));
		g2d.drawImage(img, rotation, null);
		
		return img2;
	}
	
	public static int giveOppositeDirection(int direction) {
		//Give the opposite direction of the given direction
		//The int direction is a constant
		switch (direction) {
		case Settings.DIRECTION_DOWN :
			return Settings.DIRECTION_UP;
		case Settings.DIRECTION_UP :
			return Settings.DIRECTION_DOWN;
		case Settings.DIRECTION_LEFT :			
			return Settings.DIRECTION_RIGHT;
		case Settings.DIRECTION_RIGHT :
			return Settings.DIRECTION_LEFT;
		default :
			return 0;
		}
	}
}