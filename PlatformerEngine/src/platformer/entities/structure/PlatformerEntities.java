package platformer.entities.structure;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;

import platformer.entities.CharacterEntity;
import platformer.entities.StaticEntity;

import entities.structure.Entities;
import entities.structure.EntitiesList;
import entities.Entity;
import events.game.GameListener;

public class PlatformerEntities implements Entities<Entity> {

	EntitiesList<StaticEntity> s_entities;
	EntitiesList<CharacterEntity> m_entities;

	public PlatformerEntities() {
		s_entities = new EntitiesList<StaticEntity>();
		m_entities = new EntitiesList<CharacterEntity>();
	}
	
	public void think() {
		s_entities.think();
		m_entities.think();
	}

	public void add(Entity e) {
		if (e instanceof StaticEntity)
			s_entities.add((StaticEntity) e);
		if (e instanceof CharacterEntity)
			m_entities.add((CharacterEntity) e);
	}

	public void remove(Entity e) {
		if (e instanceof StaticEntity)
			s_entities.remove((StaticEntity) e);
		if (e instanceof CharacterEntity)
			m_entities.remove((CharacterEntity) e);
	}

	public void addGameListener(GameListener l) {
		ArrayList<Entity> entities_list 
				= getEntities();
		for(Entity e : entities_list) {
			e.addGameEventListener(l);
		}
	}

	public ArrayList<Entity> getEntities() {
		ArrayList<Entity> entities_list = new ArrayList<Entity>();
		entities_list.addAll(m_entities.getEntities());
		entities_list.addAll(s_entities.getEntities());
		return entities_list;
	}
	
	public EntitiesList<CharacterEntity> getMoveableEntities() {
		return m_entities;
	}
	
	public EntitiesList<StaticEntity> getStaticEntities() {
		return s_entities;
	}

	public ArrayList<Entity> getAt(Point p) {
		ArrayList<Entity> entities_list = new ArrayList<Entity>();
		entities_list.addAll(m_entities.getAt(p));
		entities_list.addAll(s_entities.getAt(p));
		return entities_list;
	}

	public ArrayList<Entity> getInside(Shape s) {
		ArrayList<Entity> entities_list = new ArrayList<Entity>();
		entities_list.addAll(m_entities.getInside(s));
		entities_list.addAll(s_entities.getInside(s));
		return entities_list;
	}

	public ArrayList<Entity> getInsideWithOverlap(Shape s) {
		ArrayList<Entity> entities_list = new ArrayList<Entity>();
		entities_list.addAll(m_entities.getInsideWithOverlap(s));
		entities_list.addAll(s_entities.getInsideWithOverlap(s));
		return entities_list;
	}

	public ArrayList<Entity> removeAt(Point p) {
		ArrayList<Entity> entities_list = new ArrayList<Entity>();
		entities_list.addAll(m_entities.removeAt(p));
		entities_list.addAll(s_entities.removeAt(p));
		return entities_list;
	}

	public ArrayList<Entity> removeInside(Shape s) {
		ArrayList<Entity> entities_list = new ArrayList<Entity>();
		entities_list.addAll(m_entities.removeInside(s));
		entities_list.addAll(s_entities.removeInside(s));
		return entities_list;
	}

	public ArrayList<Entity> removeInsideWithOverlap(Shape s) {
		ArrayList<Entity> entities_list = new ArrayList<Entity>();
		entities_list.addAll(m_entities.removeInsideWithOverlap(s));
		entities_list.addAll(s_entities.removeInsideWithOverlap(s));
		return entities_list;
	}

}
