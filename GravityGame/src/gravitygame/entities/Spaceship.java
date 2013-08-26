package gravitygame.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import events.entities.EntityEvent;
import gravity.entities.GravityEntity;
import gravity.events.entities.ECollisionEvent;
import gravitygame.events.entities.EOffScreen;
import gravitygame.events.game.GCreateMissleEvent;

public class Spaceship extends GravityEntity {

	public Polygon shape;
	public Queue<Point> trail;

	private Color color;

	private int health;

	private double startx, starty;
	private double startdx, startdy;

	public Spaceship(String name, int x, int y, int width, int height,
			double dx, double dy, double angle, double angularVel,
			int mass, Color color) {
		super(name, x, y, width, height, dx, dy, angle, angularVel, mass);
		this.startx = x;
		this.starty = y;
		this.startdx = dx;
		this.startdy = dy;
		shape = new Polygon();
		shape.addPoint(-width / 2, height / 2);
		shape.addPoint(-width / 2, -height / 2);
		shape.addPoint(width / 2, 0);
		trail = new LinkedList<Point>();
		this.color = color;
		health = 10;
	}

	public int getHealth() {
		return health;
	}

	public boolean similarEntityNoClip() {
		return true;
	}

	public void draw(int x, int y, Graphics2D g) {
		g.setColor(Color.white);
		for (Point p : trail)
			g.fillOval(p.x, p.y, 2, 2);
		g.translate(getCenterX(), getCenterY());
		g.rotate(-angle);
		g.setColor(color);
		g.fillPolygon(shape);
		g.rotate(angle);
		g.translate(-getCenterX(), -getCenterY());
	}

	private boolean left, right, boost, stop;

	private static final double turnAccel = 0.01;
	private static final double accel = 0.1;

	public void think() {
		if (health < 1) {
			delete();
			return;
		}

		if (trail.size() > 4) {
			if (Math.random() < 0.9)
				trail.remove();
		} else if (Math.random() < 0.4 && trail.size() > 0)
			trail.remove();

		if (left)
			this.addDW(turnAccel);
		if (right)
			this.addDW(-turnAccel);
		if (boost) {
			if (Math.random() < 0.7) {
				trail.add(new Point((int) (Math.random() * getWidth() + getX()),
						(int) (Math.random() * getHeight() + getY())));
			}

			double ddx = Math.abs(Math.cos(angle) * accel);
			double ddy = Math.abs(Math.sin(angle) * accel);

			if (angle > Math.PI / 2 && angle < 1.5 * Math.PI)
				ddx *= -1;
			if (angle < Math.PI)
				ddy *= -1;

			addDDx(ddx);
			addDDy(ddy);
		}
		if (stop) {
			setDx(getDx() * 0.9);
			setDy(getDy() * 0.9);
		}
		super.think();
	}

	public void rotateLeft(boolean left) {
		this.left = left;
	}

	public void rotateRight(boolean right) {
		this.right = right;
	}

	public void fireBoosters(boolean boost) {
		this.boost = boost;
	}

	public void fireBrakes(boolean stop) {
		this.stop = stop;
	}

	public static final int missileSpeed = 10;

	public void shoot() {
		double dx_ = Math.abs(Math.cos(angle) * missileSpeed);
		double dy_ = Math.abs(Math.sin(angle) * missileSpeed);

		if (angle > Math.PI / 2 && angle < 1.5 * Math.PI)
			dx_ *= -1;
		if (angle < Math.PI)
			dy_ *= -1;

		this.fireGameEvent(new GCreateMissleEvent(this, (int) getX(), (int) getY(),
				(int) dx_, (int) dy_));
	}

	public void EntityEventReceived(EntityEvent event) {
		if (event instanceof ECollisionEvent) {
			if (event.getSource() instanceof Missile
					&& ((Missile) event.getSource()).getParent() != this) {
				health--;
				((Missile) event.getSource()).delete();
			}
			if (event.getSource() instanceof Planet) {
				health -= 3;
				setLocation(startx, starty);
				setDx(startdx);
				setDy(startdy);
			}
		}
		if (event instanceof EOffScreen) {
			health -= 3;
			setLocation(startx, starty);
			setDx(startdx);
			setDy(startdy);
		}
	}

}
