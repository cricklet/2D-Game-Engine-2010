package platformer.entities;

import entities.Entity;

public abstract class StaticEntity extends Entity {
	public StaticEntity(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
	}
}
