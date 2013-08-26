package rts;

import rts.entities.structure.RTSEntities;
import rts.graphics.RTSRenderer;
import rts.physics.RTSPhysics;
import engine.Engine;

public class RTSEngine
		extends
		Engine<RTSEntities, RTSRenderer, RTSPhysics> {
	
	public RTSEngine(int width, int height) {
		super();
		entities = new RTSEntities();
		renderer = new RTSRenderer(entities);
		physics = new RTSPhysics(entities, width, height);
	}
}
