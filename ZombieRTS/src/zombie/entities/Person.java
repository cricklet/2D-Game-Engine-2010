package zombie.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import entities.Entity;
import events.entities.EntityEvent;
import graphics.sprites.AnimatedSprite;
import rts.entities.RTSAwareEntity;
import rts.entities.RTSMoveableEntity;
import rts.entities.structure.RTSEntities;
import rts.events.entities.RTSRightClickEvent;
import zombie.graphics.CharacterSprite;
import zombie.graphics.CharacterSprite.CharacterAction;
import zombie.graphics.CharacterSprite.CharacterDir;

public class Person extends RTSMoveableEntity implements RTSAwareEntity {

	private LinkedList<Goal> goals;

	private CharacterAction action;
	private CharacterDir dir;

	public Person(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		action = CharacterAction.WALK;
		dir = CharacterDir.E;
		goals = new LinkedList<Goal>();
	}

	private int frame;
	private static final int ANIM_FRAMES = 2;

	public void draw(int x, int y, Graphics2D g) {
		AnimatedSprite animsprite = CharacterSprite.getSprite(action,
				dir);

		if (Math.abs(getDx()) > 0 || Math.abs(getDy()) > 0) {
			frame++;
			frame = frame % (animsprite.length() * ANIM_FRAMES);
			g.drawImage(animsprite.getImage((int) frame / ANIM_FRAMES),
					x, y,
					animsprite.getWidth(), animsprite.getHeight(), null);
		} else {
			frame = animsprite.getIdleFrame() * ANIM_FRAMES;
			g.drawImage(animsprite.getImage(), x, y,
					animsprite.getWidth(), animsprite.getHeight(), null);
		}
	}

	private static final double MAX_V = 3;
	
	private static final int MIN_THINK_INTERVAL = 10;
	private static final int MAX_THINK_INTERVAL = 20;
	private int frameThink = 0;
	private int nextThink = 0;

	public void think() {
		frameThink++;
		if (frameThink < nextThink)
			return;
		
		if(goals.size() > 0) {			
			int difX = (int) (goals.get(0).getX() - getX());
			int difY = (int) (goals.get(0).getY() - getY());
			double totalDif = Math.sqrt(difX*difX + difY*difY);
			
			if(totalDif < 10) {
				setDx(0);
				setDy(0);
				goals.remove(0);
				return;
			}
			
			double normDX = difX / totalDif;
			double normDY = difY / totalDif;

			if(Math.abs(normDY) > Math.abs(normDX)) {
				if(normDY > 0)
					dir = CharacterDir.S;
				else
					dir = CharacterDir.N;
			} else {
				if(normDX > 0)
					dir = CharacterDir.E;
				else
					dir = CharacterDir.W;
			}
			
			setDx(normDX * MAX_V);
			setDy(normDY * MAX_V);
			
			frameThink = 0;
			if(totalDif/MAX_V < MAX_THINK_INTERVAL)
				nextThink = (int) (totalDif/MAX_V);
			else
				nextThink = MIN_THINK_INTERVAL + (int) (Math.random() * (MAX_THINK_INTERVAL - MIN_THINK_INTERVAL));
			return;
		}

		frameThink = 0;
		nextThink = MIN_THINK_INTERVAL
				+ (int) (Math.random() * (MAX_THINK_INTERVAL - MIN_THINK_INTERVAL));
	}

	public static int getDefaultWidth() {
		return 17;
	}

	public static int getDefaultHeight() {
		return 23;
	}

	public Color getOutlineColor() {
		return Color.BLUE;
	}

	public void EntityEventReceived(EntityEvent event) {
		if (event instanceof RTSRightClickEvent) {
			if (!((RTSRightClickEvent) event).getCtrl())
				goals.clear();
			
			Point p = ((RTSRightClickEvent) event).getLocation();
			ArrayList<Entity> entitiesAt = getEntitiesAt(p);
			if (entitiesAt.size() == 0)
				goals.add(new Goal(p, GoalType.WALK));
			else if (entitiesAt.get(0) instanceof Tree)
				goals.add(new Goal(p, GoalType.GATHER_WOOD));
		}
	}
	
	public static enum GoalType {
		WALK, GATHER_WOOD
	};
	
	private class Goal {
		private Point p;
		private GoalType t;
		public Goal(Point p, GoalType type) {
			this.p = p;
			this.t = type;
		}
		
		public GoalType getType() {return t;}
		public Point getPoint() {return p;}
		
		public int getX() {return p.x;}
		public int getY() {return p.y;}
	}

	private RTSEntities entities;
	public RTSEntities getEntities() {
		return entities;
	}
	
	public ArrayList<Entity> getEntitiesAt(Point p) {
		return entities.getAt(p);
	}
	
	public void setEntities(RTSEntities entities) {
		this.entities = entities;
	}
	
}
