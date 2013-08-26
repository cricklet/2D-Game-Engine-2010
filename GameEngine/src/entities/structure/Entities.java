package entities.structure;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;

import events.game.GameListener;
import entities.Entity;

public interface Entities<E extends Entity> {
	public void think();

	public void addGameListener(GameListener l);

	/* return all the entities */
	public ArrayList<E> getEntities();
	
	/* add an entity */
	public void add(E e);
	
	/* remove an entity */
	public void remove(E e);
	
	/* methods for finding entities at certain locations */
	public ArrayList<E> getAt(Point p);
	public ArrayList<E> getInside(Shape s);
	public ArrayList<E> getInsideWithOverlap(Shape s);
	
	/* methods for remove entities. doesn't actually delete entities,
	 * just removes them from this data structure and returns a list
	 * of them
	 */
	public ArrayList<E> removeAt(Point p);
	public ArrayList<E> removeInside(Shape s);
	public ArrayList<E> removeInsideWithOverlap(Shape s);
}
