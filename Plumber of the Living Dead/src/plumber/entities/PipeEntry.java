package plumber.entities;

import entities.Entity;
import events.entities.EntityEvent;
import events.game.GameListener;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import platformer.entities.CharacterEntity;
import platformer.entities.StaticEntity;
import platformer.events.entities.ECollisionEvent;
import plumber.graphics.sprites.StaticSprites;

public class PipeEntry extends StaticEntity {

	private static final int WIDTH = 64;
	private static final int HEIGHT = 64;

	private Image img;

	private PipeExit[] exits;

	public PipeEntry(int x, int y, int dir, PipeExit[] exits) {
		super("Pipe Entry", x, y, WIDTH, HEIGHT);

		this.exits = exits;

		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		if (dir == -1)
			g.drawImage(StaticSprites.pipe.getImage(), WIDTH, 0, 0, HEIGHT, 0,
					0, WIDTH, HEIGHT, null);
		if (dir == 1)
			g.drawImage(StaticSprites.pipe.getImage(), 0, 0, WIDTH, HEIGHT, 0,
					0, WIDTH, HEIGHT, null);
	}

	int index = 0;

	public Point getExit() {
		index++;
		if (index >= exits.length)
			index = 0;
		return new Point((int) exits[index].getX() + 16, (int) exits[index].getY());
	}

	public void draw(int x, int y, Graphics2D g) {
		g.drawImage(img, x, y, null);
	}

	public boolean entityNoClip() {
		return true;
	}

	public void EntityEventReceived(EntityEvent event) {
		Entity source = (Entity) event.getSource();
		if (event instanceof ECollisionEvent) {
			if (event.getID() == ECollisionEvent.INSIDE
					&& source instanceof CharacterEntity
					&& !((CharacterEntity) source).staticEntityNoClip()) {
				/* teleport it */
				((CharacterEntity) source).setLocation(getExit());
				((CharacterEntity) source).setDx(0);
			}
		}
	}

	public void think() {
	}
}
