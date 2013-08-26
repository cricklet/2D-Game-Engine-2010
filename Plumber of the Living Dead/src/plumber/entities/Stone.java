package plumber.entities;

import entities.Entity;
import entities.structure.Entities;
import events.entities.EntityEvent;
import events.game.GameListener;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import platformer.entities.CharacterEntity;
import platformer.entities.StaticEntity;
import platformer.entities.structure.PlatformerEntities;
import platformer.events.entities.ECollisionEvent;
import plumber.events.game.GKillZombiesEvent;
import plumber.graphics.sprites.StaticSprites;

public class Stone extends StaticEntity {
	private static final int WIDTH_ = 32;
	private static final int HEIGHT = 32;

	private Image img;
	private Image img_hit1;
	private Image img_hit2;

	// private PlatformerEntities entities;

	public Stone(int x, int y, int width) {
		super("Stone", x, y, width, HEIGHT);

		img = StaticSprites.stone.getImage();
		img_hit1 = StaticSprites.stone_hit1.getImage();
		img_hit2 = StaticSprites.stone_hit2.getImage();
	}

	private boolean hit;
	private int xhit;
	private int hitTime;

	public void draw(int x, int y, Graphics2D g) {
		if (!hit) {
			g.drawImage(img, x, y, x + (int) getWidth(), y
					+ (int) getHeight(), 0, 0, 1,
					32, null);
		} else {
			hitTime++;
			if (hitTime > 10) {
				hitTime = 0;
				hit = false;
			}

			int startx = xhit - 20;
			if (startx < x + 8)
				startx = x + 8;
			int endx = xhit + 20;
			if (endx > x + getWidth() - 8)
				endx = x + (int) getWidth() - 8;

			g.drawImage(img_hit1, startx + 4, y - 4, endx - 4, y + 32,
					0, 0, 1, 36, null);
			g.drawImage(img_hit2, startx, y - 4, startx + 4, y + 32,
					0, 0, 1, 36, null);
			g.drawImage(img_hit2, endx - 4, y - 4, endx, y + 32,
					0, 0, 1, 36, null);
			g.drawImage(img, x, y, startx, y + 32, 0, 0, 1, 32, null);
			g.drawImage(img, endx, y, x + (int) getWidth(),
					y + 32, 0, 0, 1, 32, null);
		}
	}

	public void hitFromBelow(int x, int y) {
		hit = true;
		xhit = x;
		Rectangle bounds = new Rectangle(x - 16, (int) getY() - 8, 32, 8);
		fireGameEvent(new GKillZombiesEvent(this, 0, bounds));
	}

	public boolean entityNoClip() {
		return false;
	}

	public void EntityEventReceived(EntityEvent event) {
		Entity source = (Entity) event.getSource();
		if (event instanceof ECollisionEvent) {
			if (source instanceof Mario
					&& event.getID() == ECollisionEvent.BOTTOM) {

			}
		}
	}

	public void think() {
	}
}
