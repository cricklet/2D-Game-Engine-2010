package gravitygame.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import events.entities.EntityEvent;
import gravity.entities.GravityEntity;

public class Planet extends GravityEntity {

	private Color color;

	public Planet(String name, int x, int y, int width, int height,
			double dx, double dy, double angle, double angularVel,
			int mass, Color color) {
		super(name, x, y, width, height, dx, dy, angle, angularVel, mass);
		this.color = color;
	}

	public void think() {
		super.think();
	}

	public boolean similarEntityNoClip() {
		return true;
	}

	public void draw(int x, int y, Graphics2D g) {
		g.setColor(color);
		g.fillOval(x, y, (int) getWidth(), (int) getHeight());
	}

	public Shape getBoundsExact() {
		return new Ellipse2D.Float((int) getX(), (int) getY(),
				(int) getWidth(),
				(int) getHeight());
	}

	public void EntityEventReceived(EntityEvent event) {

	}

}
