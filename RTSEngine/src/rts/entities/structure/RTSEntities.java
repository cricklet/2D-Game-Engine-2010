package rts.entities.structure;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;

import rts.entities.RTSMoveableEntity;
import rts.entities.RTSStaticEntity;

import entities.Entity;
import entities.structure.Entities;
import entities.structure.EntitiesList;
import events.game.GameListener;

public class RTSEntities implements Entities<Entity> {
	private EntitiesList<RTSStaticEntity> sEntities;
	private EntitiesList<RTSMoveableEntity> mEntities;
	
	public RTSEntities() {
		sEntities = new EntitiesList<RTSStaticEntity>();
		mEntities = new EntitiesList<RTSMoveableEntity>();
	}
	
	public void add(Entity e) {
		if (e instanceof RTSStaticEntity)
			sEntities.add((RTSStaticEntity) e);
		else if (e instanceof RTSMoveableEntity)
			mEntities.add((RTSMoveableEntity) e);
		else
			System.err.println("Entity not an RTSEntity: " + e);
	}
	
	public void addGameListener(GameListener l) {
		sEntities.addGameListener(l);
		mEntities.addGameListener(l);
	}

	public ArrayList<Entity> getAt(Point p) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		result.addAll(sEntities.getAt(p));
		result.addAll(mEntities.getAt(p));
		return result;
	}

	public ArrayList<Entity> getEntities() {
		ArrayList<Entity> result = new ArrayList<Entity>();
		result.addAll(sEntities.getEntities());
		result.addAll(mEntities.getEntities());
		return result;
	}

	public ArrayList<Entity> getInside(Shape s) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		result.addAll(sEntities.getInside(s));
		result.addAll(mEntities.getInside(s));
		return result;	}

	public ArrayList<Entity> getInsideWithOverlap(Shape s) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		result.addAll(sEntities.getInsideWithOverlap(s));
		result.addAll(mEntities.getInsideWithOverlap(s));
		return result;
	}

	public void remove(Entity e) {
		if (e instanceof RTSStaticEntity)
			sEntities.remove((RTSStaticEntity) e);
		else if (e instanceof RTSMoveableEntity)
			mEntities.remove((RTSMoveableEntity) e);
		else
			System.err.println("Entity not an RTSEntity: " + e);
	}

	public ArrayList<Entity> removeAt(Point p) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		result.addAll(sEntities.removeAt(p));
		result.addAll(mEntities.removeAt(p));
		return result;
	}

	public ArrayList<Entity> removeInside(Shape s) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		result.addAll(sEntities.removeInside(s));
		result.addAll(mEntities.removeInside(s));
		return result;
	}

	public ArrayList<Entity> removeInsideWithOverlap(Shape s) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		result.addAll(sEntities.removeInsideWithOverlap(s));
		result.addAll(mEntities.removeInsideWithOverlap(s));
		return result;
	}

	public EntitiesList<RTSMoveableEntity> getMoveableEntities() {
		return mEntities;
	}
	
	public EntitiesList<RTSStaticEntity> getStaticEntities() {
		return sEntities;
	}

	public void think() {
		sEntities.think();
		mEntities.think();
	}
	
}
