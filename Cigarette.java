import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

/**
 * This class implements a Cigarette item on the "Mac DeMarco Simulator" game
 * 
 * @author Mateus Lopes Teixeira
 */
public class Cigarette extends Item {

	private StartingPoint appletInfo;

	/**
	 * Constructor for the Cigarette class
	 * 
	 * @param appletInfo
	 *            a StartingPoint object
	 * @param x
	 *            the x position on screen where the item will be spawned
	 */
	public Cigarette(StartingPoint appletInfo, int x, Image img) {
		super(x, img);
		this.appletInfo = appletInfo;
	}

	@Override
	/**
	 * Performs an action based on the item. Cigarette gives the player extra
	 * 100 points
	 */
	public void performAction(Ball ball) {
		appletInfo.setScore(appletInfo.getScore() + 100);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		super.paint(g);
	}
}
