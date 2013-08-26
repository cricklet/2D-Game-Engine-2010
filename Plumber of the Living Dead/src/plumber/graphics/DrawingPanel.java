package plumber.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import plumber.entities.Mario;


public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Mario mario;
	private int count;

	public DrawingPanel(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setVisible(true);
	}

	public void render(Image img) {
		Graphics g = img.getGraphics();
		g.setColor(Color.gray);
		g.drawString("health: ", 128, 40);
		g.drawRect(178, 28, 103, 13);
		g.fillRect(180, 30, mario.getHealth(), 10);
		g.drawString("killed: " + count, 136, 60);
		getGraphics().drawImage(img, 0, 0, null);
	}

	public void setMario(Mario mario) {
		this.mario = mario;
	}
	
	public void setKillCount(int count) {
		this.count = count;
	}

}
