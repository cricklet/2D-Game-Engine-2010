package entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import events.entities.EntityListener;
import events.game.GEntityDeleteEvent;
import events.game.GameEvent;
import events.game.GameListener;

public abstract class Entity implements EntityListener {
	private Rectangle2D.Double bounds;
	private String name;

	public Entity(String name, double x, double y, double width, double height) {
		this.name = name;
		bounds = new Rectangle2D.Double(x, y, width, height);
	}

	/* the rectangle which represents this entities location/size */
	public Rectangle getBounds() {
		return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
	}
	
	/* the rectangle which represents this entities location/size */
	public Shape getBoundsExact() {
		return (Shape) bounds.clone();
	}
	
	public String toString() {
		return name;
	}

	public Point2D.Double getCenter() {
		return new Point2D.Double(getCenterX(), getCenterY());
	}

	public double getCenterX() {
		return bounds.getCenterX();
	}

	public double getCenterY() {
		return bounds.getCenterY();
	}

	public double getX() {
		return bounds.getX();
	}

	public double getY() {
		return bounds.getY();
	}

	public double getWidth() {
		return bounds.getWidth();
	}

	public double getHeight() {
		return bounds.getHeight();
	}

	public double getDistance(Entity other) {
		return getCenter().distance(other.getCenter());
	}

	public void setBounds(double x, double y, double w, double h) {
		bounds.setRect(x,y,w,h);
	}
	
	public void setLocation(double x, double y) {
		bounds.setRect(x,y,getWidth(),getHeight());
	}
	
	public void setLocation(Point p) {
		bounds.setRect(p.x,p.y,getWidth(),getHeight());
	}

	public abstract void think();

	public abstract void draw(int x, int y, Graphics2D g);
	
	public void draw(double x, double y, Graphics2D g) {
		draw((int) x, (int) y, g);
	}

	public boolean entityNoClip() {
		return false;
	}

	public void delete() {
		fireGameEvent(new GEntityDeleteEvent(this));
		listeners.clear();
	}

	protected ArrayList<GameListener> listeners = new ArrayList<GameListener>();

	public void addGameEventListener(GameListener listener) {
		listeners.add(listener);
	}

	public void removeGameEventListener(GameListener listener) {
		listeners.remove(listener);
	}

	public void fireGameEvent(GameEvent e) {
		for (GameListener l : listeners) {
			l.GameEventReceived(e);
		}
	}

}
