package gravitygame.events.game;

import events.game.GEntityCreateEvent;
import gravitygame.entities.Missile;
import gravitygame.entities.Spaceship;

public class GCreateMissleEvent extends GEntityCreateEvent<Missile> {

	private static final long serialVersionUID = 1L;

	public GCreateMissleEvent(Object source, int x, int y, int dx, int dy) {
		super(source);
		entity = new Missile("Missile", x-2, y-2, 4, 4, dx, dy, 0, 0, 1, (Spaceship) getSource());
	}

}
