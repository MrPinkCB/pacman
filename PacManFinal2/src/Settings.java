import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Settings {
	/**
	 * This class contains all the resources of the game (images, initial positions, font, color used,...)
	 */
	
	//Colors
	public static final String COLOR_WINDOW = "0x0c0947";
	public static final String COLOR_BORDER = "0x2c277d";
	public static final String COLOR_DARK = "0xc9c7ff";
	public static final String COLOR_SHADOW = "0x3b3698";
	
	public static final String COLOR_BACKGROUND = "0x03021d";
	public static final String COLOR_BORDER_GAME = "0x1919df";
	public static final String COLOR_BACKGROUND_INNER = "0x0a0838";
	
	//Font 
	public static Font font1 = new Font("Zorque", Font.PLAIN, 15);
	public static  Font font2 = new Font("Zorque", Font.PLAIN, 25);

	//Directions 
	public static final int DIRECTION_DOWN = 0;
	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_LEFT = 2;
	public static final int DIRECTION_RIGHT = 3;
	

	//Case who appears and then disappears
	public static final int casexgetout = 11;
	public static final int caseygetout = 9;
	
	public static Image[][] EnemiesImg = new Image[5][5];
	public static Image[] pacman = new Image[3];
	
	//Level
	public static final int[][] level1 = new int[][] {
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,1,1,1,0,1,1,1,1,0,1,0,1,1,1,1,0,1,1,1,0,1},
		{1,0,1,1,1,0,1,1,1,1,0,1,0,1,1,1,1,0,1,1,1,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,1,1,1,0,1,0,1,1,1,1,1,1,1,0,1,0,1,1,1,0,1},
		{1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1},
		{1,1,1,1,1,0,1,1,1,1,0,1,0,1,1,1,1,0,1,1,1,1,1},
		{0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0},
		{0,0,0,0,1,0,1,0,2,2,2,0,2,2,2,0,1,0,1,0,0,0,0},
		{0,0,0,0,1,0,0,0,2,4,4,0,4,4,2,0,0,0,1,0,0,0,0},
		{0,0,0,0,1,0,1,0,2,4,0,0,0,4,2,0,1,0,1,0,0,0,0},
		{0,0,0,0,1,0,1,0,2,4,4,0,4,4,2,0,1,0,1,0,0,0,0},
		{1,1,1,1,1,0,1,0,2,2,2,2,2,2,2,0,1,0,1,1,1,1,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1},
		{1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1},
		{1,1,0,1,1,0,1,0,1,1,1,1,1,1,1,0,1,0,1,1,0,1,1},
		{1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1},
		{1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
	
		public static final int[][] dotlevel1 = new int[][] {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,1,0},
			{0,1,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0},
			{0,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,0},
			{0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0},
			{0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0},
			{0,0,1,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,1,0,0},
			{0,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,0},
			{0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};

	//Enemies, starting point
	public static Point startBlue = new Point(10*20,11*20);
	public static Point startPurple = new Point(11*20,11*20);
	public static Point startGreen = new Point(12*20,11*20);
	public static Point startRed =new Point(11*20,12*20);
	
	//Pacman
	public static Point startingPoint = new Point(11*20,4*20);
	
	public static void generateFonts()  {

		try {
		    //Create the font to use. Specify the size
		    font1 = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(15f);
		    font2 = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(25f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //Register the font
		    ge.registerFont(font1);
		    ge.registerFont(font2);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadRessources() {
		try {			
			String[] enemieNames = new String[]{"blue","purple","green","red","run"};
			
			for (int j = 0; j<5; j++) {
				for (int i = 0; i<5; i++) {
					 EnemiesImg[j][i] = ImageIO.read(new File("Graphics/"+enemieNames[j]+"anim"+(i+1)+".PNG"));
				}
			}

			for (int i = 0; i<3;i++) {
				pacman[i] = ImageIO.read(new File("Graphics/pacmananim" + (i+1) + ".PNG"));
			}
		} 
		catch (IOException e) {		
			e.printStackTrace();	
		}
	}
}
