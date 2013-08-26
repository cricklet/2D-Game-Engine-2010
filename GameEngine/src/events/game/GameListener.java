package events.game;

import java.util.EventListener;

public interface GameListener extends
		EventListener {
	public void GameEventReceived(GameEvent event);
}
