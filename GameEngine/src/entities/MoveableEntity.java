package entities;

import java.awt.Point;

public abstract class MoveableEntity extends Entity {
	
	private double dx;
	private double dy;
	
	public MoveableEntity(String name, int x, int y, int width,
			int height) {
		super(name, x, y, width, height);
		dx = 0;
		dy = 0;
	}

	public MoveableEntity(String name, int x, int y, int width,
			int height, double dx, double dy) {
		super(name, x, y, width, height);
		this.dx = dx;
		this.dy = dy;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}
	
	public void addDDx(double ddx) {
		this.dx += ddx;
	}
	
	public void addDDy(double ddy) {
		this.dy += ddy;
	}
	
	public void addDxDy(double dx, double dy) {
		this.setLocation(getX() + dx, getY() + dy);
	}
	
	/* push the entity (not immediate) */
	protected void setVelocity(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/*
	 * this function moves the entity. this is only called by game engine
	 */
	public void move() {
		this.setLocation(getX() + dx, getY() + dy);
	}

	public boolean staticEntityNoClip() {
		return false;
	}

	public boolean similarEntityNoClip() {
		return false;
	}

}
