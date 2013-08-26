package plumber.events.game;

import events.game.GameEvent;


public class GZombieKilledEvent extends GameEvent{
	private static final long serialVersionUID = 62417373281270005L;

	public static final int DEFAULT = 0;
	
	public GZombieKilledEvent(Object source, int id) {
		super(source, id);
	}
}
