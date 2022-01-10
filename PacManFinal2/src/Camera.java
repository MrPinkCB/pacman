import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;


public class Camera extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		//We draw to the screen the game
		Graphics2D g2d = (Graphics2D)g; //Conversion of Graphics to Graphics2D, so that we can draw rotation of images
		
		if (Menu.mode == Menu.MODE_GAME) {
			//We draw the play field
			drawWindowScreen(g2d);
			//We draw the content of the play field
			drawPlayField(g2d);
			if (Game.defeat != Game.NO) drawInterfaceLose(g2d);
		}
		else if (Menu.mode == Menu.MODE_MENU) {
			//We draw the play field
			drawWindowScreen(g2d);
			drawTitleMenu(g2d);
		}
		else {
			drawScore(g2d);
		}

	}
	
	public void drawletter(Graphics2D g2d, int[][] list, int px, int py, int indcolor) {
		//Draw a letter on the screen (by drawing a blue rectangle if the case i,j is 1, draw nothing otherwise
		for (int i = 0; i<list.length; i++) {
			for (int j = 0; j<list[0].length; j++) {
				int c = list[i][j]-1;
				if (c!=-1) {
					g2d.setColor(Color.decode(Settings.COLOR_BORDER_GAME));
					g2d.fillRect(px+j*10, py+i*10, 10,10);

				}
			}
		}
	}
	
	public void drawTitleMenu(Graphics2D g2d) {
		//Draw the title of the game
		int l = 0;
		for(int i = 0; i<6;i++) {
			drawletter(g2d, Menu.listletters.get(i), 60 + 10*l, 150, i);
			l+=Menu.listletters.get(i)[0].length+1;
		}
		
	}
	
	public void drawScore(Graphics2D g2d) {
		//Background
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.decode(Settings.COLOR_WINDOW));
		g2d.fillRect(0, 0, (23)*20, (22)*20);

		//Panel containing the scores
		int x = 20;
		int y = 20;
		int w = 20*(23-2);
		int h = 20*(22-2);

		g2d.setFont(Settings.font1);
		g2d.setColor(Color.decode(Settings.COLOR_BACKGROUND));
		g2d.fillRect(x, y, w, h);
		
		//Write the scores and the names
		g2d.setColor(Color.decode(Settings.COLOR_DARK));
		int xtxt = x+20;
		int xn = xtxt+40;
		int xs = xn+40+Highscore.maxLength;
		int d = 18;
		
		for(int i = 0; i<Highscore.scores.size();i++) {
			if (i<=10) {
				g2d.drawString(String.valueOf(i+1) +".", xtxt, y+(i+1)*d);
				g2d.drawString(Highscore.names.get(i), xn, y+(i+1)*d);
				g2d.drawString(String.valueOf(Highscore.scores.get(i)), xs, y+(i+1)*d);
			}

		}
		
	}

	public void drawWindowScreen(Graphics2D g2d) {
		//We draw the play field		
		g2d.setColor(Color.decode(Settings.COLOR_BACKGROUND));
		g2d.fillRect(0, 0, (23)*20, (22)*20);
		g2d.setColor(Color.decode(Settings.COLOR_BORDER));
	}
	
	public void drawInterfaceLose(Graphics2D g2d) {
		//Text for game win or lose
		if (Game.defeat == Game.LOSE) {
			Camera.drawCenteredString(g2d, "You lose", new Rectangle(0,0,(23)*20, (22)*20), Settings.font2);
		}
		else {
			Camera.drawCenteredString(g2d, "Congratulations !", new Rectangle(0,0,(23)*20, (22)*20), Settings.font2);
		}
		
	}
	
	public void drawPlayField(Graphics2D g2d) {

		g2d.setStroke(new BasicStroke(2));
		
		for (int i = 0; i<22; i++) {
			for (int j = 0; j<23; j++) {
				//If there is a wall in the specified case (j,i) on the screen Game.gamefield[i][j] = 1 or 2),
				//we draw a fill rectangle (j*20, i*20, 20, 20)
				//the color is different is Game.gamefield[i][j]=2 
				if (Game.gamefield[i][j]==1) {
					g2d.setColor(Color.decode(Settings.COLOR_BORDER_GAME));
					g2d.fillRect(j*20, i*20, 20, 20);
				}
				else if (Game.gamefield[i][j]==2) {
					g2d.setColor(Color.decode(Settings.COLOR_BORDER_GAME));
					g2d.fillRect(j*20+5, i*20+5, 10, 10);
				}
				if (LDots.dot(i, j)==true) { //We draw a dot on the case if there is a dot. Size = 4*4
					g2d.setColor(Color.WHITE);
					g2d.fillOval(j*20+20/2-2,i*20+20/2-2, 4, 4);
				}
			}
		}

		for (Object o : Game.listComponents) {
			//We draw the object on the screen by using the function drawOnScreen of the class Object
			o.drawOnScreen(g2d);
		}
		
	}
	
	//Functions that draw centered String
	public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent() + 3;
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	public static int  maxInFontW(Graphics g, List<String> vTxt, Font font) {
		//Look what string in the list has the maximum width 
		int max = 0;
		FontMetrics metrics = g.getFontMetrics(font);
		for (String txt : vTxt) {
			int c = metrics.stringWidth(txt);
			if (c>max) max =c;
		}
		return max;
	}

}
