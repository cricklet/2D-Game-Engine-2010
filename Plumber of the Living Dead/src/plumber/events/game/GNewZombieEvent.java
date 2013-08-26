package plumber.events.game;

import events.game.GameEvent;

public class GNewZombieEvent extends GameEvent {

	private static final long serialVersionUID = 1L;

	private int x;
	private int y;

	public GNewZombieEvent(Object source, int id, int x, int y) {
		super(source, id);
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
