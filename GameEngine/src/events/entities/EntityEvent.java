package events.entities;

import java.util.EventObject;

public class EntityEvent extends EventObject{

	private static final long serialVersionUID = -9166279148514512585L;

	public static final int DEFAULT_ID = 1;
	
	private int id;
	
	public EntityEvent(Object source, int id) {
		super(source);
		this.id = id;
	}
	
	public EntityEvent(Object source) {
		super(source);
		this.id = DEFAULT_ID;
	}
	
	public int getID() {
		return id;
	}

}
