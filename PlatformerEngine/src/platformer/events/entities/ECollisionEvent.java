package platformer.events.entities;

import events.entities.EntityEvent;

public class ECollisionEvent extends EntityEvent{

	private static final long serialVersionUID = -9166279148514512585L;

	public static final int TOP = 1;
	public static final int BOTTOM = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final int INSIDE = 5;
	public static final int OUTSIDE = 6;
	
	public ECollisionEvent(Object source, int id) {
		super(source, id);
	}

}
