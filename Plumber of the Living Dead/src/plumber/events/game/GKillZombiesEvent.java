package plumber.events.game;

import java.awt.Rectangle;

import events.game.GameEvent;

public class GKillZombiesEvent extends GameEvent {

	private Rectangle bounds;
	
	public GKillZombiesEvent(Object source, int id, Rectangle r) {
		super(source, id);
		bounds = r;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
}
