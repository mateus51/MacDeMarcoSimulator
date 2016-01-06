import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.Random;

public class Platform {

	private int dx;
	private int x, y, width, height;
	protected Image platformAnimation; // Animated platform
	protected URL url;

	public Platform() {
		dx = -1;
		x = 300;
		y = 300;
		width = 120;
		height = 40;
	}

	public Platform(int x, int y) {
		this.dx = -4;
		this.x = x;
		this.y = y;
		width = 120;
		height = 40;
		// Picture of the platform
		// platformAnimation = Pictures.platfrom;

	}

	public void update(StartingPoint sp, Ball ball) {

		// Increases the speed of the platforms as the player passes levels
		x += -(Pictures.level);

		// Static velocity of the platforms - WORKS
		// x += dx;

		checkForCollision(ball);

		// Remove the platform if it's out of the applet's boundaries
		if (x < 0 - width) {
			Random r = new Random();
			x = sp.getWidth() + r.nextInt(150); // The platform appears on the
												// right side
			y = sp.getHeight() - r.nextInt(400);
		}

	}

	private void checkForCollision(Ball ball) {
		// Variables that represent the ball parameter
		int ballX = ball.getX();
		int ballY = ball.getY();
		int radius = ball.getRadius();

		// Checks if the ball's bottom is touching the top of the platform
		if ((ballY + radius) > y && (ballY + radius) < y + height) {
			// Checks if the ball is touching the top or bottom of the platform
			if (ballX > x && ballX < x + width) {

				double newDy = ball.getGameDy(); // Opposite direction of the
													// ball's current dy
				ball.setY(y - radius); // Positions the ball right above the
										// platform, for collision purposes
				ball.setDy(newDy); // Makes the ball go up once it touches the
									// platform

				// Play bounce audio clip
				Pictures.bounce.play();
			}
		}
	}

	public void paint(Graphics g) {
		// g.setColor(Color.BLUE);
		g.setColor(new Color(77, 114, 152));
		g.fillRect(x, y, width, height);

		// Paint animated platform
		// g.drawImage(platformAnimation, x, y, Pictures.sp);

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
