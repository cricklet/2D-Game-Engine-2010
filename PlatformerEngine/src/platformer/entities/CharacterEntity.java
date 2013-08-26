package platformer.entities;

import java.awt.Point;

import entities.Entity;
import entities.MoveableEntity;

public abstract class CharacterEntity extends MoveableEntity {
	protected int health;
	protected int dhealth;
	public CharacterEntity(String name, int x, int y, int width, int height,
			int health) {
		super(name, x, y, width, height);
		this.health = health;
		dhealth = 0;
	}

	/* this function determines what actions this entity should do */
	public void think() {
		health += dhealth;
		dhealth = 0;
	}

	public int getHealth() {
		return health;
	}

	/* hurt the entity (not immediate) */
	protected void hurt(int damage) {
		dhealth -= damage;
	}

	public abstract void inAir();
}
