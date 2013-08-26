package plumber.entities;

import entities.Entity;
import events.entities.EntityEvent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import platformer.entities.CharacterEntity;
import platformer.events.entities.ECollisionEvent;
import plumber.graphics.sprites.MarioSprite;

public class Mario extends CharacterEntity {
	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;

	private static final int JUMP_V = -18;
	private static final double AIR_ACCEL = .6;
	private static final int MAX_HORIZONTAL_V = 10;
	private static final double ACCELERATION = 1;

	private static final int FRAMES_BETWEEN_ANIM = 2;

	private enum Mode {
		DEFAULT, JUMPING, STOP, LOSE
	};

	private Mode mode;

	private boolean right;
	private boolean left;

	private int frame_i = 0;
	private int animation_i = 0;

	public Mario(int x, int y) {
		super("Mario", x, y, WIDTH, HEIGHT, 100);
		mode = Mode.DEFAULT;
		right = false;
		left = false;
	}

	private boolean jump;

	public void jump() {
		jump = true;
	}

	public void forceJump() {
		mode = Mode.DEFAULT;
		setDy(JUMP_V / 2);
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void think() {
		if (mode == Mode.LOSE)
			return;
		if (health < 0) {
			mode = Mode.LOSE;
			setDy(JUMP_V / 2);
			return;
		}

		/* deal with jumping */
		if (mode != Mode.JUMPING && jump == true)
			setDy(JUMP_V);
		jump = false;

		/* adjust acceleration if in the air */
		double a = ACCELERATION;
		if (mode == Mode.JUMPING)
			a = AIR_ACCEL;

		mode = Mode.DEFAULT;

		if (left && !right) {
			if (getDx() > 0)
				mode = Mode.STOP;
			addDDx(-a);
		} else if (right && !left) {
			if (getDx() < 0)
				mode = Mode.STOP;
			addDDx(+a);
		}

		if (a != AIR_ACCEL && (!left && !right) || (left && right)) {
			if (getDx() > 0) {
				mode = Mode.STOP;
				addDDx(-a);
				if (getDx() < 0)
					setDx(0);
			} else if (getDx() < 0) {
				mode = Mode.STOP;
				addDDx(+a);
				if (getDx() > 0)
					setDx(0);
			}
		}

		if (getDx() > MAX_HORIZONTAL_V)
			setDx(MAX_HORIZONTAL_V);
		if (getDx() < -MAX_HORIZONTAL_V)
			setDx(-MAX_HORIZONTAL_V);

		super.think();
	}

	private boolean flip;

	public void draw(int x, int y, Graphics2D g) {
		Image result;
		if (mode == Mode.STOP) {
			result = MarioSprite.mario_stop.getImage();
		} else if (mode == Mode.DEFAULT) {
			if (getDx() == 0)
				result = MarioSprite.mario_idle.getImage();
			else {
				frame_i++;
				if (frame_i > FRAMES_BETWEEN_ANIM) {
					frame_i = 0;
					animation_i++;
					if (animation_i >= MarioSprite.mario_run.length())
						animation_i = 0;
				}
				result = MarioSprite.mario_run.getImage(animation_i);
			}
		} else if (mode == Mode.JUMPING) {
			result = MarioSprite.mario_jump.getImage();
		} else
			result = MarioSprite.mario_lose.getImage();

		if (getDx() > 0 || (getDx() == 0 && flip)) {
			g.drawImage(result, x, y, x + WIDTH, y + HEIGHT, WIDTH, 0, 0,
					HEIGHT, null);
			flip = true;
		} else {
			g.drawImage(result, x, y, new Color(0, 0, 0, 0), null);
			flip = false;
		}
	}

	public void inAir() {
		if (mode != Mode.LOSE)
			mode = Mode.JUMPING;
	}

	public boolean staticEntityNoClip() {
		return mode == Mode.LOSE;
	}

	public boolean entityNoClip() {
		return false;
	}

	public void hurt(int damage) {
		if (-dhealth < damage)
			dhealth -= damage;
	}

	public void EntityEventReceived(EntityEvent event) {
		Entity source = (Entity) event.getSource();
		if (event instanceof ECollisionEvent) {
			if (source instanceof Stone) {
				if (((ECollisionEvent) event).getID() == ECollisionEvent.TOP) {
					((Stone) source).hitFromBelow((int) getX() + 16, (int) getY());
				}
			}
			if (source instanceof Zombie) {
				if (((ECollisionEvent) event).getID() == ECollisionEvent.TOP) {
					hurt(5);
					if (Math.random() >= 0.5)
						setVelocity(10, -3);
					else
						setVelocity(-10, -3);
				}
				if (((ECollisionEvent) event).getID() == ECollisionEvent.LEFT) {
					if (getY() + getHeight() > source.getY() + 10) {
						hurt(10);
						this.setVelocity(-10, -5);
					}
				}
				if (((ECollisionEvent) event).getID() == ECollisionEvent.RIGHT) {
					if (getY() + getHeight() > source.getY() + 10) {
						hurt(10);
						this.setVelocity(10, -5);
					}
				}
			}
		}
	}
}
