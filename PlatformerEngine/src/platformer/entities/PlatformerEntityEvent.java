package platformer.entities;

import events.entities.EntityEvent;

public class PlatformerEntityEvent extends EntityEvent {

	private static final long serialVersionUID = 4284330222641050656L;

	public static final int DELETE = 1;
	public static final int HIT_ABOVE = 1;
	public static final int HIT_LEFT = 1;
	public static final int HIT_RIGHT = 1;
	public static final int HIT_BELOW = 1;

	public PlatformerEntityEvent(Object source, int id) {
		super(source, id);
	}

}
