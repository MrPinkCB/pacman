import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Camera drawing;
	public static ButtonPanel contentButtons;
	
	JPanel container;
	Thread t;
	Game g;
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Window f = new Window();
	}
	
	public Window() {
		//Initialize the window and call the functions for the initialization
		super();
		Settings.loadRessources();
		Game.linkWindow(this);
		Settings.generateFonts();
		Menu.initializeButtons();
		DataBaseHandler.initialize();
		configureCamera();
		getDifferencesDimension();
		setGraphicsWindow();
		Game.reinitializeGame();
		setEvenementiel();
		startThread();
	}
	
	public void setGraphicsWindow() {
		//This function is used for the graphics of the window
		this.setVisible(true);
		this.setSize(23*20+bh,22*20+bv);
		this.setResizable(false);
		this.setTitle("Pac-man");
		Image icon = Toolkit.getDefaultToolkit().getImage("Graphics/icone.png");  
		this.setIconImage(icon);  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	public static int bh;
	public static int bv;
	public void getDifferencesDimension() {
		//To find the distance between the real size of the window and the size of the screen
		bh = getSize().width - drawing.getSize().width;
		bv = getSize().height - drawing.getSize().height;
	}
	
	public void setEvenementiel() {
		//Creation of the environmental (key listener for the game)
		this.addKeyListener(this);
	}
	
	public void configureCamera() {
		//Configure the content of the JFrame
		contentButtons =  new ButtonPanel();
		
		drawing = new Camera();

        Game.linkDraw(drawing);
        
        this.getContentPane().add(drawing);
		this.getContentPane().add(contentButtons, BorderLayout.SOUTH);
    	setVisible(true);
	}
	
	public void startThread() {
		//Start the game by using a thread
		g = new Game();
		t = new Thread(g);
		t.start();
	}
	

	public static JTextField textScoreName;
	public static JLabel labelName;
	
	class ButtonPanel extends JPanel implements ActionListener
	{

		/**
		 * This class is used to display the menu
		 */
		private static final long serialVersionUID = 1L;

		public ButtonPanel() {
			//Add the components to the panel
			textScoreName = new JTextField();
			textScoreName.setPreferredSize(new Dimension(100,26));
			labelName = new JLabel("Score : 0");
			add(createButton("Play", null) );
			add(createButton("Scores", null) );
			add(createButton("Exit", null) );
			add(labelName);
			add(textScoreName);
			add(createButton("Save", null) );
			this.addKeyListener(Window.this);

		}

		private JButton createButton(String text, Color background) {
			JButton button = new JButton( text );
			button.setBackground( background );
			button.addActionListener( this );
			button.addKeyListener(Window.this);
			return button;
		}

		public void actionPerformed(ActionEvent e)
		{
			JButton button = (JButton)e.getSource();
			String value = button.getText();
			Menu.actionButton(value);
	
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == 37) {
			Game.keyLeftPressed = true;
		}
		if (e.getKeyCode() == 38) {
			Game.keyUpPressed = true;
		}
		if (e.getKeyCode() == 39) {
			Game.keyRightPressed = true;
		}
		if (e.getKeyCode() == 40) {
			Game.keyDownPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			Game.keyLeftPressed = false;
		}
		if (e.getKeyCode() == 38) {
			Game.keyUpPressed = false;
		}
		if (e.getKeyCode() == 39) {
			Game.keyRightPressed = false;
		}
		if (e.getKeyCode() == 40) {
			Game.keyDownPressed = false;
		}

	}
}
