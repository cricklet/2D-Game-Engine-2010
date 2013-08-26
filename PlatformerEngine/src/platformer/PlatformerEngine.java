package platformer;

import platformer.entities.structure.PlatformerEntities;
import platformer.physics.PlatformerPhysics;
import engine.Engine;
import graphics.Renderer;

public class PlatformerEngine
		extends Engine <PlatformerEntities,
						Renderer<PlatformerEntities>,
						PlatformerPhysics> {

	public PlatformerEngine() {
		super();
		entities = new PlatformerEntities();
		renderer = new Renderer(entities);
		physics = new PlatformerPhysics(entities);
	}

}
