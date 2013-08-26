package events.entities;

import java.util.EventListener;

public interface EntityListener extends EventListener {
	public void EntityEventReceived(EntityEvent event);
}
