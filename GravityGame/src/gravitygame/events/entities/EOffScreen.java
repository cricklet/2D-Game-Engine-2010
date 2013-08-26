package gravitygame.events.entities;

import java.util.EventObject;

import events.entities.EntityEvent;

public class EOffScreen extends EntityEvent {

	private static final long serialVersionUID = 1L;

	public EOffScreen(Object source) {
		super(source);
	}
	
}
