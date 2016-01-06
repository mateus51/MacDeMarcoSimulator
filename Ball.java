import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Ball {

	// Ball size variables
	private int x = 400;
	private int y = 25;
	private int radius = 20;

	// Variables that dictates the amount of change in the ball's movement
	private double dx = 1;
	private double dy = 0;
	private double gameDy = -75; // shoots the ball towards the top

	// Physics related variables
	private double frictionXAxis = 0.8;
	private double gravity = 15;
	private double energyLoss = 1;
	private double dt = 0.2;
	private int agility = 3;
	private int maxSpeed = 20;

	private boolean gameOver = false;
	public int lives = 3;

	public Ball() {

	}

	public Ball(int i, int j) {
		x = i;
		y = j;
	}

	// Moves the ball to the right
	public void moveRight() {
		if (dx + agility < maxSpeed) // 20 is an arbitrary speed. This is as
										// fast as the ball goes
			dx += agility;
	}

	// Moves the ball to the left
	public void moveLeft() {
		if (dx + agility > -maxSpeed) // maxSpeed is as fast as the ball goes
			dx -= agility;
	}

	public void jump() {
		if (dy > 0) {
			dy += 50;
			dy = -dy;
		}
	}

	public void update(StartingPoint sp) {
		// COLLISIONS ON THE LEFT AND RIGHT SIDE OF THE WINDOW - X COORDINATE
		// Detects ball collision on the right side of the window
		if ((x + dx) > (sp.getWidth() - radius)) {
			x = sp.getWidth() - radius - 1;
			dx = -dx; // Reverses the direction of the ball
		}
		// Detects collision on the left side of the window
		else if ((x + dx) < (0 + radius)) {
			x = 0 + radius;
			dx = -dx;
		} else {
			x = (int) (x + dx);
		}

		// Friction
		// Slows down the ball once it touches the bottom of the page
		if (y == (sp.getHeight() - radius - 1)) {
			dx *= frictionXAxis;

			// Slows the ball down a bit quicker
			if (Math.abs(dx) < 0.8)
				dx = 0;
		}

		// Detects a collision on the bottom of the page
		if (y - 200 > sp.getHeight() - radius - 1) {
			// REMOVE -200 FROM ^ AFTER TESTING GAME OVER - THIS MAKES THE BALL
			// GO THROUGH
			/*
			 * WORKS - REMOVE COMMENTS AFTER TESTING GAME OVER y =
			 * sp.getHeight() - radius - 1; dy *= energyLoss; // Avoids bouncing
			 * forever dy = gameDy; // Reverses the direction of the movement
			 * sp.setScore(sp.getScore() - 100); // Takes 100 points from the
			 * player if the ball touches the ground
			 */

			if (lives > 1) {
				lives--;
				y = sp.getHeight() - radius - 1;
				dy *= energyLoss; // Avoids bouncing forever
				dy = gameDy; // Reverses the direction of the movement
			} else {
				lives = 0;
				gameOver = true;
			}
			// gameOver = true; // Finishes the game if the player touches the
			// ground - works without the if statement above
		}

		// If it's not on the bottom of the page, use sp physics equation
		// to calculate the movement
		else {
			// Velocity formula
			dy += (gravity * dt);
			// Position formula
			y += (dy * dt) + 0.5 * (gravity * dt * dt);
		}
	}

	public void paint(Graphics g, StartingPoint sp, Image icon) {
		g.setColor(Color.GREEN);
		// g.fillOval(x - radius, y - radius, radius*2, radius*2);
		g.drawImage(icon, x - 30, y - 30, sp);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public double getGameDy() {
		return gameDy;
	}

	public void setGameDy(double gameDy) {
		this.gameDy = gameDy;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public boolean getGameOver() {
		return this.gameOver;
	}

}
