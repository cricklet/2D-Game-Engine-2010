package gravitygame.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import events.entities.EntityEvent;
import gravity.entities.GravityEntity;
import gravity.events.entities.ECollisionEvent;

public class Missile extends GravityEntity {

	private Spaceship parent;
	
	public Missile(String name, int x, int y, int width, int height,
			double dx, double dy, double angle, double angularVel,
			int mass, Spaceship parent) {
		super(name, x, y, width, height, dx, dy, angle, angularVel, mass);
		this.parent = parent;
	}

	private static final int LIFETIME = 60;
	private int life = 0;
	public void think() {
		super.think();
		life ++;
		if(life > LIFETIME)
			delete();
	}
	
	public boolean ignoreGravity() {
		return true;
	}

	public boolean similarEntityNoClip() {
		return true;
	}

	public void draw(int x, int y, Graphics2D g) {
		g.setColor(Color.yellow);
		g.fillOval(x+1, y+1, 2, 2);
	}

	public Spaceship getParent() {
		return parent;
	}
	
	public void EntityEventReceived(EntityEvent event) {
		if (event instanceof ECollisionEvent) {
			if (event.getSource() instanceof Planet) {
				delete();
			}
		}
	}
}
