package plumber.entities;

import entities.Entity;
import events.entities.EntityEvent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import platformer.entities.CharacterEntity;
import platformer.entities.StaticEntity;
import platformer.events.entities.ECollisionEvent;
import plumber.events.entities.EZombieDeathEvent;
import plumber.events.game.GZombieKilledEvent;
import plumber.graphics.sprites.ZombieSprite;

public class Zombie extends CharacterEntity {
	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;

	private static final int MAX_HORIZONTAL_V = 1;

	private static final int FRAMES_BETWEEN_ANIM = 3;

	private enum Mode {
		RISE, DEFAULT, DEAD
	};

	private Mode mode;

	private int frame_i = 0;
	private int animation_i = 0;

	private int direction;

	public Zombie(int x, int y) {
		super("Zombie", x, y, WIDTH, HEIGHT, 100);
		mode = Mode.RISE;
		direction = 1;
		if (Math.random() >= 0.5)
			direction = -1;
	}

	private int deletionCounter = 0;

	public void think() {
		if (mode == Mode.DEAD) {
			deletionCounter++;
			if (deletionCounter > 50)
				delete();
			return;
		}
		if (health < 0) {
			mode = Mode.DEAD;
			return;
		}

		if (mode == Mode.DEFAULT)
			setDx(direction * MAX_HORIZONTAL_V);
		super.think();
	}

	private boolean flip;

	public void draw(int x, int y, Graphics2D g) {
		Image result;
		if (mode == Mode.RISE) {
			frame_i++;
			if (frame_i > FRAMES_BETWEEN_ANIM) {
				frame_i = 0;
				animation_i++;
			}
			if (animation_i >= ZombieSprite.zombie_rise.length()) {
				mode = Mode.DEFAULT;
				animation_i = 0;
				result = ZombieSprite.zombie_walk.getImage(animation_i);
			} else
				result = ZombieSprite.zombie_rise.getImage(animation_i);
		} else if (mode == Mode.DEFAULT) {
			frame_i++;
			if (frame_i > FRAMES_BETWEEN_ANIM) {
				frame_i = 0;
				animation_i++;
				if (animation_i >= ZombieSprite.zombie_walk.length())
					animation_i = 0;
			}
			result = ZombieSprite.zombie_walk.getImage(animation_i);
		} else
			result = ZombieSprite.zombie_dying.getImage();

		if (getDx() > 0 || (getDx() == 0 && flip)) {
			g.drawImage(result, x + WIDTH, y, x, y + HEIGHT, 0, 0, WIDTH,
					HEIGHT, null);
			flip = true;
		} else {
			g.drawImage(result, x, y, new Color(0, 0, 0, 0), null);
			flip = false;
		}
	}

	public void inAir() {
		/* do nothing, zombies can't fly */
	}

	public boolean staticEntityNoClip() {
		return mode == Mode.DEAD;
	}

	public boolean similarEntityNoClip() {
		return true;
	}

	public boolean entityNoClip() {
		return mode == Mode.RISE || mode == Mode.DEAD;
	}

	public void EntityEventReceived(EntityEvent event) {
		if (mode == Mode.RISE || mode == Mode.DEAD)
			return;
		Entity source = (Entity) event.getSource();
		if (event instanceof ECollisionEvent) {
			/* if we've run into a wall, turn around */
			if (!source.entityNoClip()
					&& source instanceof StaticEntity) {
				if (((ECollisionEvent) event).getID() == ECollisionEvent.LEFT)
					direction = 1;
				if (((ECollisionEvent) event).getID() == ECollisionEvent.RIGHT)
					direction = -1;
			}
		}
		if (event instanceof EZombieDeathEvent) {
			setVelocity(((EZombieDeathEvent) event).getXBump(),
					((EZombieDeathEvent) event).getYBump());
			mode = Mode.DEAD;
			fireGameEvent(new GZombieKilledEvent(this, 0));
		}
	}
}
