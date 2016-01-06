import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.Random;

public class StartingPoint extends Applet implements Runnable, KeyListener, MouseMotionListener, MouseListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private Graphics doubleG;

	private static int score;
	private int levelCheck = 0; // Adds 1 each time the game loops through a
								// thread
	private boolean gameOver = false;
	private boolean mouseIn = false; // Checks if the mouse is in the square

	private boolean threadCreated = true;

	double backgroundX = 0;
	double backgroundDx = 1.5;
	URL url;
	Image background, viceroyIcon, capIcon, cigaretteIcon, macIcon;
	Pictures pictures = new Pictures();

	Ball ball;
	Item items[] = new Item[3];
	Platform[] platforms = new Platform[7];
	private Pictures pic;

	/**
	 * Initializes the applet
	 */
	@Override

	public void init() {
		setSize(800, 600);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		try {
			url = getDocumentBase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Images
		background = getImage(url, "Images/background3.png");
		viceroyIcon = getImage(url, "Images/viceroy.png");
		cigaretteIcon = getImage(url, "Images/cigarette.png");
		capIcon = getImage(url, "Images/cap.png");
		macIcon = getImage(url, "Images/macDeMarco.png");
		setPic(new Pictures(this));

		// Plays background music indefinitely
		Pictures.music.loop();

	}

	/**
	 * Sets up the game
	 */
	@Override
	public void start() {

		ball = new Ball();
		score = 0;

		// Generates the first platforms as a line
		for (int i = 0; i < platforms.length; i++) {
			platforms[i] = new Platform(i * 121, 300);
		}

		// Sets up the items
		for (int i = 0; i < items.length; i++) {
			int widthItem = getWidth() + 200 * i;
			Random r = new Random();
			/*
			 * switch(r.nextInt(4)) { case 0: items[i] = new
			 * GravityIncrease(widthItem); break; case 1: items[i] = new
			 * GravityDecrease(widthItem); break; case 2: items[i] = new
			 * AgilityDecrease(widthItem); break; case 3: items[i] = new
			 * ScorePlus(this, widthItem); break; }
			 */
			switch (r.nextInt(2)) {
			case 0:
				items[i] = new Cigarette(this, widthItem, cigaretteIcon);
				break;
			case 1:
				items[i] = new Viceroy(this, widthItem, viceroyIcon);
				break;
			case 2:
				items[i] = new Cap(this, widthItem, capIcon);
				break;
			}
		}
		if (threadCreated) {
			Thread thread = new Thread(this);
			thread.start();
			threadCreated = false;
		}
	}

	@Override
	public void run() {

		// Thread information
		while (true) {

			// Adjust the platform's appearance place
			for (int i = 0; i < platforms.length; i++) {
				int testX = platforms[i].getX();

				if (testX < (0 - platforms[i].getWidth())) {
					Random r = new Random();
					int testI = i;
					if (i == 0)
						testI = platforms.length;

					platforms[i].setX(
							platforms[testI - 1].getX() + platforms[i].getWidth() + Pictures.level * r.nextInt(30));
				}
			}

			// Test for game Over
			gameOver = ball.getGameOver();

			// Changes the level the player is
			if (levelCheck > 400) {
				Pictures.level++;
				levelCheck = 0;
			}

			levelCheck++;

			// Stops counting the score if it's game over
			if (!gameOver)
				score++;

			// Changes the background image - WORKS
			/*
			 * if(getScore() > 100) background = getImage(url,
			 * "Images/background2.png");
			 */

			// Repeats the background image
			if (backgroundX > getWidth() * -1) {
				backgroundX -= backgroundDx;
			}
			// In case the image has fully disappeared
			else
				backgroundX = 0;

			backgroundX -= backgroundDx;

			Random r = new Random();

			// Checks if the item has repositioned for under the applet
			// In case it did, redefines it
			for (int i = 0; i < items.length; i++) {
				if (items[i].isCreateNew()) {
					items[i] = null;
					// Randomizes which item will appear next
					switch (r.nextInt(3)) {
					case 0:
						items[i] = new Cigarette(this, getWidth() + r.nextInt(260), cigaretteIcon);
						break;
					case 1:
						items[i] = new Viceroy(this, getWidth() + r.nextInt(1200), viceroyIcon);
						break;
					case 2:
						items[i] = new Cap(this, getWidth() + r.nextInt(1000), capIcon);
						break;
					}

					items[i].setCreateNew(false);
				}
			}

			ball.update(this);
			for (int i = 0; i < platforms.length; i++)
				platforms[i].update(this, ball);

			for (int i = 0; i < items.length; i++)
				items[i].update(this, ball);

			repaint();

			// Sets the framerate for the game
			try {
				Thread.sleep(15); // 60FPS: 1000ms / 60fps = 16.67 ~= 17
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stops the game
	 */
	@Override
	public void stop() {

	}

	@Override
	public void destroy() {

	}

	@Override
	/**
	 * For double buffering. Gets rid of the flickering problem on animations.
	 *
	 * @param g
	 */
	public void update(Graphics g) {
		// Creates an Image in case it's null
		if (image == null) {
			image = createImage(this.getSize().width, this.getSize().height);
			doubleG = image.getGraphics();
		}

		// Sets the double graphic background color to the same background color
		// from the applet
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		doubleG.setColor(getForeground());

		// Paints to doubleG all the changes made above
		paint(doubleG);
		g.drawImage(image, 0, 0, this);
	}

	@Override
	/**
	 * '"" Paints graphics to the window
	 */
	public void paint(Graphics g) {
		// Allows antialiasing on the text
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(new Color(15, 77, 147));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(background, (int) backgroundX, 0, this); // Draws background
		g.drawImage(background, (int) backgroundX + getWidth(), 0, this); // Draws "twin" background

		// Prints the title of the game
		g.setColor(new Color(50, 147, 111));
		g.setFont(new Font("M", Font.PLAIN, 25));
		g.drawString("MAC DEMARCO SIMULATOR", 10, 30);

		// Prints the ball
		ball.paint(g, this, macIcon);

		// Prints the platforms
		for (int i = 0; i < platforms.length; i++)
			platforms[i].paint(g);
		// Prints the items
		for (int i = 0; i < items.length; i++)
			items[i].paint(g);

		pictures.printScore(this, g);

		// Prints game over options
		if (gameOver) {
			g2d.setColor(new Color(190, 3, 30));
			g2d.drawString("GAME OVER", 300, 300);

			// Outline the GAME OVER text - user for mouse check
			// g.drawRect(280, 310, 180, 40);
			if (mouseIn) {
				g2d.setColor(new Color(143, 203, 120));
				g2d.drawString("Play again?", 310, 340);
			} else {
				g2d.setColor(new Color(190, 3, 30));
				g2d.drawString("Play again?", 310, 340);
			}
		}

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		StartingPoint.score = score;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			ball.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			ball.moveRight();
			break;
		case KeyEvent.VK_SPACE:
			ball.jump();
			this.setScore(getScore() - 50); // Each jump costs 50 points
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		// g.drawRect(280, 300, 180, 40);
		// In between the x position of the Play again rectangle
		if (e.getX() > 280 && e.getX() < 460) {
			if (e.getY() > 310 && e.getY() < 360) {
				mouseIn = true;
			} else
				mouseIn = false;
		} else
			mouseIn = false;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (mouseIn && gameOver) {
			ball.lives = 3;
			start();
			Pictures.music.loop();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public Pictures getPic() {
		return pic;
	}

	public void setPic(Pictures pic) {
		this.pic = pic;
	}

}
