package gravity.entities;

import java.awt.Point;

import entities.MoveableEntity;

public abstract class GravityEntity extends MoveableEntity {

	private int mass;

	protected double angle;
	private double angularVel;

	public GravityEntity(String name, int x, int y, int width,
			int height, double dx, double dy, double angle,
			double angularVel, int mass) {
		super(name, x, y, width, height, dx, dy);
		this.angle = angle;
		this.angularVel = angularVel;
		this.mass = mass;
	}

	public boolean entityNoClip() {
		return false;
	}

	private static final double dampenAngularVel = 0.95;

	public void think() {
		angle += angularVel;
		if (angle < 0)
			angle += Math.PI * 2;
		angle %= Math.PI * 2;
		angularVel *= dampenAngularVel;
	}

	public int getMass() {
		return mass;
	}

	public void applyGravity(double Fx, double Fy) {
		double ddx = Fx / mass;
		double ddy = Fy / mass;
		addDDx(ddx);
		addDDy(ddy);
	}

	public void addDW(double dw) {
		this.angularVel += dw;
	}

	public void setAngularVel(double w) {
		this.angularVel = w;
	}

	public abstract boolean similarEntityNoClip();

	public boolean ignoreGravity() {
		return false;
	}

}
