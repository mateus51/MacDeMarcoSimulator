import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	public Keyboard() {
		// TODO Auto-generated constructor stub
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e, StartingPoint sp) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			sp.ball.moveLeft();
			// ball.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			sp.ball.moveRight();
			break;
		case KeyEvent.VK_SPACE:
			sp.ball.jump();
			sp.setScore(sp.getScore() - 50); // Each jump costs 50 points
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
