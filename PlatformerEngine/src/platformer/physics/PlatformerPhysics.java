package platformer.physics;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;



import physics.Physics;
import platformer.entities.CharacterEntity;
import platformer.entities.StaticEntity;
import platformer.entities.structure.PlatformerEntities;
import platformer.events.entities.ECollisionEvent;

public class PlatformerPhysics extends Physics<PlatformerEntities> {

	public static final int GRAVITY = 1;
	public static final int TERMINAL_V = 20;

	public PlatformerPhysics(PlatformerEntities entities) {
		super(entities);
	}

	/* x, y, w, h = old bounds of object
	 * bounds = old bounds + velocity
	 * other overlapping object 
	 */
	public static boolean[] overlapSide(int x, int y, int w, int h,
			Rectangle bounds, Rectangle o) {
		Line2D.Double top = new Line2D.Double(o.getX(), o.getY(), o.getX()
				+ o.getWidth(), o.getY());
		Line2D.Double bottom = new Line2D.Double(o.getX(), o.getY()
				+ o.getHeight(), o.getX() + o.getWidth(), o.getY()
				+ o.getHeight());
		Line2D.Double left = new Line2D.Double(o.getX(), o.getY(), o.getX(), o
				.getY()
				+ o.getHeight());
		Line2D.Double right = new Line2D.Double(o.getX() + o.getWidth(), o
				.getY(), o.getX() + o.getWidth(), o.getY() + o.getHeight());
		boolean otop = bounds.intersectsLine(top);
		boolean obottom = bounds.intersectsLine(bottom);
		boolean oleft = bounds.intersectsLine(left);
		boolean oright = bounds.intersectsLine(right);

		/* this is the ground */
		if (otop && !obottom && !oleft && !oright) {
		}
		/* this is the ceiling */
		else if (!otop && obottom && !oleft && !oright) {
		}
		/* this is a wall */
		else if (!otop && !obottom && oleft && !oright) {
		} else if (!otop && !obottom && !oleft && oright) {
		}
		/* could be the left wall or the ground/ceiling */
		else if (oleft && otop && obottom) {
			otop = false;
			obottom = false;
		} else if (oleft && otop) {
			int cornerx = (int) o.getX();
			int cornery = (int) o.getY();
			int distx = Math.abs(x - cornerx);
			int disty = Math.abs(y - cornery);
			if (distx < disty)
				oleft = false;
			else
				otop = false;
		} else if (oleft && obottom) {
			int cornerx = (int) o.getX();
			int cornery = (int) o.getY() + (int) o.getHeight();
			int distx = Math.abs(x - cornerx);
			int disty = Math.abs(y + h - cornery);
			if (distx < disty)
				oleft = false;
			else
				obottom = false;
		}
		/* could be the left wall or the ground/ceiling */
		else if (oright && otop && obottom) {
			otop = false;
			obottom = false;
		} else if (oright && otop) {
			int cornerx = (int) o.getX() + (int) o.getWidth();
			int cornery = (int) o.getY();
			int distx = Math.abs(x + w - cornerx);
			int disty = Math.abs(y - cornery);
			if (distx < disty)
				oright = false;
			else
				otop = false;
		} else if (oright && obottom) {
			int cornerx = (int) o.getX() + (int) o.getWidth();
			int cornery = (int) o.getY() + (int) o.getHeight();
			int distx = Math.abs(x + w - cornerx);
			int disty = Math.abs(y + h - cornery);
			if (distx < disty)
				oright = false;
			else
				obottom = false;
		}

		boolean[] result = { otop, obottom, oleft, oright };
		return result;
	}

	public void physics() {
		for (CharacterEntity e : entities.getMoveableEntities().getEntities()) {
			Rectangle bounds = e.getBounds();
			int x = bounds.x;
			int y = bounds.y;
			int w = bounds.width;
			int h = bounds.height;
			double dx = e.getDx();
			double dy = e.getDy();

			dy += GRAVITY;
			if (dy > TERMINAL_V)
				dy = TERMINAL_V;
			if (dy < -TERMINAL_V)
				dy = -TERMINAL_V;

			bounds.setBounds(Math.min(x, x + (int) dx), Math.min(y, y
							+ (int) dy), w + Math.abs((int) dx), h
							+ Math.abs((int) dy));

			boolean touching_ground = false;
			ArrayList<StaticEntity> touching = entities.getStaticEntities()
					.getInsideWithOverlap(bounds);
			for (StaticEntity o : touching) {
				if (o.getBounds().contains(bounds)) {
					o.EntityEventReceived(new ECollisionEvent(e,
							ECollisionEvent.INSIDE));
					e.EntityEventReceived(new ECollisionEvent(o,
							ECollisionEvent.OUTSIDE));
				}

				boolean[] sides = overlapSide(x, y, w, h, bounds, o.getBounds());

				boolean otop = sides[0];
				boolean obottom = sides[1];
				boolean oleft = sides[2];
				boolean oright = sides[3];

				/* SEND COLLISION EVENTS */
				/* ground */
				if (otop) {
					o.EntityEventReceived(new ECollisionEvent(e,
							ECollisionEvent.TOP));
					e.EntityEventReceived(new ECollisionEvent(o,
							ECollisionEvent.BOTTOM));
				}
				/* ceiling */
				else if (obottom) {
					o.EntityEventReceived(new ECollisionEvent(e,
							ECollisionEvent.BOTTOM));
					e.EntityEventReceived(new ECollisionEvent(o,
							ECollisionEvent.TOP));
				}
				/* left */
				else if (oleft) {
					o.EntityEventReceived(new ECollisionEvent(e,
							ECollisionEvent.LEFT));
					e.EntityEventReceived(new ECollisionEvent(o,
							ECollisionEvent.RIGHT));
				}
				/* oright */
				else if (oright) {
					o.EntityEventReceived(new ECollisionEvent(e,
							ECollisionEvent.RIGHT));
					e.EntityEventReceived(new ECollisionEvent(o,
							ECollisionEvent.LEFT));
				}

				/* MOVE ENTITIES */
				if (e.staticEntityNoClip() || o.entityNoClip())
					continue;

				/* ground */
				if (otop) {
					e.addDxDy(0, o.getY() - e.getHeight() - y);
					if (dy > 0)
						dy = 0;
					touching_ground = true;
				}
				/* ceiling */
				else if (obottom) {
					e.addDxDy(0, o.getY() + o.getHeight() - y);
					if (dy < 0)
						dy = 0;
				}
				/* left */
				else if (oleft) {
					e.addDxDy(o.getX() - e.getWidth() - x, 0);
					if (dx > 0)
						dx = 0;
				}
				/* oright */
				else if (oright) {
					e.addDxDy(o.getX() + o.getWidth() - x, 0);
					if (dx < 0)
						dx = 0;
				}
			}

			if (!touching_ground) {
				e.inAir();
			}
			
			e.setDx(dx);
			e.setDy(dy);
		}
		
		for (CharacterEntity e : entities.getMoveableEntities().getEntities()) {
			e.move();
		}
	}
}
