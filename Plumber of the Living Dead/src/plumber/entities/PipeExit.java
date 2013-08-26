package plumber.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import events.entities.EntityEvent;
import events.game.GameListener;

import platformer.entities.StaticEntity;
import plumber.graphics.sprites.StaticSprites;


public class PipeExit extends StaticEntity {

	private static final int WIDTH = 64;
	private static final int HEIGHT = 64;

	private Image img;

	public PipeExit(int x, int y) {
		super("Pipe Entry", x, y, WIDTH, HEIGHT);

		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.drawImage(StaticSprites.pipe_exit.getImage(), 0, 0, WIDTH, HEIGHT, 0, 0,
				WIDTH, HEIGHT, null);
	}

	public void draw(int x, int y, Graphics2D g) {
		g.drawImage(img, x, y, null);
	}

	public boolean ignorePhysics() {
		return true;
	}

	public boolean entityNoClip() {
		return true;
	}

	public void EntityEventReceived(EntityEvent event) {
		
	}
	
	public void think() {
	}
}
