package physics;

import entities.structure.Entities;

public abstract class Physics <Entities_ extends Entities<?>>{
	protected Entities_ entities;
	
	public Physics(Entities_ entities) {
		this.entities = entities;
	}
	
	/* moves the entities according to their desired velocities */
	public abstract void physics();
}
