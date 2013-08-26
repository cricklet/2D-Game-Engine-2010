package events.game;

import entities.Entity;


public abstract class GEntityCreateEvent<E extends Entity> extends GameEvent{
	private static final long serialVersionUID = 62417373281270005L;
	
	protected E entity;
	
	public GEntityCreateEvent(Object source) {
		super(source);
	}
	
	public E getEntity() {return entity;}
	
}
