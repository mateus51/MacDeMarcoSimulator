import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;

public class Pictures {

	static StartingPoint sp;
	static Image platform, ball; // Static variables that define the animated
									// platform and ball
	URL url;

	static AudioClip music, wind, bounce;

	// Static variable to account for levels
	static int level = 1;

	public Pictures() {

	}

	public Pictures(StartingPoint sp) {
		try {
			url = sp.getDocumentBase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		platform = sp.getImage(url, "Images/platform.png");
		music = sp.getAudioClip(url, "Audio/MacDemarco.au");
		bounce = sp.getAudioClip(url, "Audio/bounce.au");
		wind = sp.getAudioClip(url, "Audio/wind.au");

		this.sp = sp;
	}

	/**
	 * Prints the score to the applet's screen
	 * 
	 * @param sp
	 *            a variable for StartingPoint
	 * @param g
	 *            Graphics variable
	 */
	public void printScore(StartingPoint sp, Graphics g) {

		// Sets antialiasing
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		String str = Integer.toString(sp.getScore());
		Font font = new Font("Minecraft", Font.BOLD, 24);
		g.setFont(font);

		// Prints the level the player is
		g2d.setColor(new Color(0, 0, 116));
		g2d.drawString("LEVEL: " + Pictures.level, sp.getWidth() - 218, 52);
		g2d.setColor(new Color(0, 66, 156)); // Shadow
		g2d.drawString("LEVEL: " + Pictures.level, sp.getWidth() - 220, 50); // Shadow

		// Writes the score to the screen
		g2d.setColor(Color.BLACK);
		g2d.drawString("SCORE: " + str, sp.getWidth() - 218, 92);
		g2d.setColor(new Color(50, 181, 111)); // Shadow
		g2d.drawString("SCORE: " + str, sp.getWidth() - 220, 90); // Shadow

		// Writes the lives to the screen
		g2d.setColor(new Color(0, 0, 116));
		g2d.drawString("VICEROYS: " + sp.ball.getLives(), sp.getWidth() - 218, 132);
		g2d.setColor(new Color(0, 66, 156)); // Shadow
		g2d.drawString("VICEROYS: " + sp.ball.getLives(), sp.getWidth() - 220, 130); // Shadow
	}

}
