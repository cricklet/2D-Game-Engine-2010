package plumber.entities;

import events.entities.EntityEvent;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import platformer.entities.StaticEntity;
import plumber.events.game.GNewZombieEvent;
import plumber.graphics.sprites.StaticSprites;

public class Ground extends StaticEntity {
	private static final int WIDTH_ = 32;
	private static final int HEIGHT = 32;

	private Image img;

	public Ground(int x, int y, int width) {
		super("Ground", x, y, width, HEIGHT);
		img = new BufferedImage(width, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.drawImage(StaticSprites.ground.getImage(), 0, 0, width, HEIGHT, 0, 0,
				WIDTH_, HEIGHT, null);
	}

	public void think() {
		if (Math.random() <= 0.04) {
			int x = (int) (getX() + 64 + (getWidth() - 128) * Math.random());
			int y = (int) (getY() - 32);
			fireGameEvent(new GNewZombieEvent(this, 0, x, y));
		}
	}

	public void draw(int x, int y, Graphics2D g) {
		g.drawImage(img, x, y, null);
	}

	public boolean entityNoClip() {
		return false;
	}

	public void EntityEventReceived(EntityEvent event) {

	}

}
