import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class Item {

	private int x, y;
	private int dx;
	private int radius;
	private boolean createNew = false;
	private StartingPoint sp;
	private Image icon;

	public Item(int x, Image img) {
		this.x = x;
		Random r = new Random();
		y = r.nextInt(450) + radius;
		this.radius = 12;
		this.dx = -2;
		this.icon = img;
	}

	public void update(StartingPoint sp, Ball ball) {
		this.setSp(sp);
		x += dx;
		checkForCollision(ball);

		// Remove the item if it's out of the applet's boundaries
		if (x < 0 - radius) {
			// Random r = new Random();
			// x = sp.getWidth() + 2000 + r.nextInt(300); // Items don't show up
			// as often as platforms
			createNew = true;
		}
	}

	private void checkForCollision(Ball ball) {
		// Variables that represent the ball parameter
		int ballX = ball.getX();
		int ballY = ball.getY();
		int ballRadius = ball.getRadius();

		// Pythagoras formula for checking collisions between the player's ball
		// and the items
		int a = x - ballX;
		int b = y - ballY;
		double c = Math.sqrt((double) (a * a) + (double) (b * b)); // Distance
																	// between
																	// ball and
																	// item's
																	// centers
		int collide = radius + ballRadius;

		if (c < collide) {
			performAction(ball);
			createNew = true;
		}

	}

	public void performAction(Ball ball) {

	}

	public void paint(Graphics g) {
		// g.fillOval(x - radius, y - radius, radius*2, radius*2);
		g.drawImage(icon, x - 20, y - 20, this.sp);
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

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public StartingPoint getSp() {
		return sp;
	}

	public void setSp(StartingPoint sp) {
		this.sp = sp;
	}

}
