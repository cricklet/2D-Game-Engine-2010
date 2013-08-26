package gravity.physics;

import java.util.ArrayList;

import entities.structure.Entities;
import gravity.entities.GravityEntity;
import gravity.events.entities.ECollisionEvent;
import physics.Physics;

public class GravityPhysics extends Physics<Entities<GravityEntity>> {

	public GravityPhysics(Entities<GravityEntity> entities) {
		super(entities);
	}

	public void physics() {
		ArrayList<GravityEntity> elist = entities.getEntities();
		for (int i = 0; i < elist.size(); i++) {
			GravityEntity e = elist.get(i);
			for (int j = i + 1; j < elist.size(); j++) {
				GravityEntity o = elist.get(j);

				if (e.getBounds().intersects(o.getBounds())) {
					if (e.getBoundsExact().intersects(o.getBounds()) &&
							o.getBoundsExact().intersects(e.getBounds())) {
						e.EntityEventReceived(new ECollisionEvent(o));
						o.EntityEventReceived(new ECollisionEvent(e));
					}
				}

				if (e.ignoreGravity() || o.ignoreGravity())
					continue;
				double[] F = getGravityForce(e, o);
				e.applyGravity(F[0], F[1]);
				o.applyGravity(-F[0], -F[1]);
			}
		}
		for (GravityEntity e : elist) {
			e.move();
		}
	}

	public double[] getGravityForce(GravityEntity e, GravityEntity o) {
		double r = e.getDistance(o);
		double Mm = 0.001 * e.getMass() * o.getMass();
		double dx = e.getCenterX() - o.getCenterX();
		double dy = e.getCenterY() - o.getCenterY();

		double F;
		if (r == 0)
			F = 0;
		else
			F = Mm / r;

		double Fx, Fy;
		double theta = Math.atan(dy / dx);
		Fx = Math.abs(Math.cos(theta) * F);
		Fy = Math.abs(Math.sin(theta) * F);
		if (dx > 0)
			Fx *= -1;
		if (dy > 0)
			Fy *= -1;

		return new double[] { Fx, Fy };
	}
}
