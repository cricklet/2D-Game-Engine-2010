package events.game;

import java.util.EventObject;

public class GameEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_ID = 0;
	private int id;
	
	public GameEvent(Object source, int id) {
		super(source);
		this.id = id;
	}
	
	public GameEvent(Object source) {
		super(source);
		id = DEFAULT_ID;
	}

}
