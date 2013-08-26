package gravitygame.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int health1;
	private int health2;
	
	public DrawingPanel(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setVisible(true);
	}

	public void render(Image img) {
		Graphics g = img.getGraphics();
		g.setColor(Color.blue);
		g.drawString("Health: " + health1, 20, 20);
		g.setColor(Color.red);
		g.drawString("Health: " + health2, 20, 40);
		getGraphics().drawImage(img, 0, 0, null);
	}
	
	public void setHealths(int health1, int health2) {
		this.health1 = health1;
		this.health2 = health2;
	}

}
