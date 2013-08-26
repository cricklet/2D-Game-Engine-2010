package gravity;

import engine.Engine;
import entities.structure.EntitiesList;
import graphics.Renderer;
import gravity.entities.GravityEntity;
import gravity.physics.GravityPhysics;

public class GravityEngine extends
		Engine<EntitiesList<GravityEntity>, Renderer<EntitiesList<GravityEntity>>, GravityPhysics> {
	public GravityEngine() {
		super();
		entities = new EntitiesList<GravityEntity>();
		renderer = new Renderer(entities);
		physics = new GravityPhysics(entities);
	}
}
