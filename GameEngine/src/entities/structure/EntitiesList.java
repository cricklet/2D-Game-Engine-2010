package entities.structure;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;

import entities.Entity;
import events.game.GameListener;

public class EntitiesList<E extends Entity>
		implements Entities<E> {

	protected ArrayList<E> entities;

	public EntitiesList() {
		entities = new ArrayList<E>();
	}

	/* return all the entities */
	@SuppressWarnings("unchecked")
	public ArrayList<E> getEntities() {
		return (ArrayList<E>) entities.clone();
	}

	/* add an entity */
	public void add(E e) {
		entities.add(e);
	}

	/* remove an entity */
	public void remove(E e) {
		entities.remove(e);
	}

	/* methods for finding entities at certain locations */
	public ArrayList<E> getAt(Point p) {
		ArrayList<E> result = new ArrayList<E>();
		for (E e : entities)
			if (e.getBounds().contains(p))
				result.add(e);
		return result;
	}

	public ArrayList<E> getInside(Shape s) {
		ArrayList<E> result = new ArrayList<E>();
		for (E e : entities)
			if (s.contains(e.getBounds()))
				result.add(e);
		return result;
	}

	public ArrayList<E> getInsideWithOverlap(Shape s) {
		ArrayList<E> result = new ArrayList<E>();
		for (E e : entities)
			if (s.intersects(e.getBounds())
					|| s.contains(e.getBounds())
					|| e.getBounds().contains(s.getBounds()))
				result.add(e);
		return result;
	}

	/*
	 * methods for remove entities. doesn't actually delete entities, just
	 * removes them from this data structure and returns a list of them
	 */
	public ArrayList<E> removeAt(Point p) {
		ArrayList<E> result = new ArrayList<E>();
		for (E e : entities)
			if (e.getBounds().contains(p))
				result.add(e);
		entities.removeAll(result);
		return result;
	}

	public ArrayList<E> removeInside(Shape s) {
		ArrayList<E> result = new ArrayList<E>();
		for (E e : entities)
			if (s.contains(e.getBounds()))
				result.add(e);
		entities.removeAll(result);
		return result;
	}

	public ArrayList<E> removeInsideWithOverlap(Shape s) {
		ArrayList<E> result = new ArrayList<E>();
		for (E e : entities)
			if (s.intersects(e.getBounds())
					|| s.contains(e.getBounds())
					|| e.getBounds().contains(s.getBounds()))
				result.add(e);
		entities.removeAll(result);
		return result;
	}

	public void addGameListener(GameListener l) {
		for (E e : entities)
			e.addGameEventListener(l);
	}

	public void think() {
		for (E e : entities)
			e.think();
	}

}
