package rts.physics;

import java.awt.Rectangle;
import java.util.ArrayList;

import physics.Physics;
import rts.entities.RTSMoveableEntity;
import rts.entities.RTSStaticEntity;
import rts.entities.structure.RTSEntities;

public class RTSPhysics extends Physics<RTSEntities> {
	
	private Rectangle bounds;
	
	public RTSPhysics(RTSEntities entities, int width, int height) {
		super(entities);
		bounds = new Rectangle(0,0,width,height);
	}

	public void physics() {
		ArrayList<RTSMoveableEntity> mEntities = entities.getMoveableEntities().getEntities();
		for (RTSMoveableEntity e : mEntities) {
			e.move();
			if(!bounds.contains(e.getCenter()) || !bounds.intersects(e.getBounds()))
				e.delete();
		}
	}

}
