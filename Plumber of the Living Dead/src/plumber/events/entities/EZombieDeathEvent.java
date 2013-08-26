package plumber.events.entities;

import events.entities.EntityEvent;

public class EZombieDeathEvent extends EntityEvent{

	private static final long serialVersionUID = -9166279148514512585L;

	private int xbump;
	private int ybump;
	
	public EZombieDeathEvent(Object source, int id, int xbump, int ybump) {
		super(source, id);
		this.xbump = xbump;
		this.ybump = ybump;
	}
	
	public int getXBump() {return xbump;}
	public int getYBump() {return ybump;}

}
