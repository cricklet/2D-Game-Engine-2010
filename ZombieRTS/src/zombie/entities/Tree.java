package zombie.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import events.entities.EntityEvent;
import rts.entities.RTSStaticEntity;
import zombie.graphics.StaticSprites;

public class Tree extends RTSStaticEntity {

	public Tree(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
	}

	public void EntityEventReceived(EntityEvent event) {
	}

	public void draw(int x, int y, Graphics2D g) {
		g.drawImage(StaticSprites.tree.getImage(), x, y,
				(int) getWidth(), (int) getHeight(), null);
	}

	public void think() {
	}

	public Color getOutlineColor() {
		return Color.GREEN;
	}
	
	public static int getDefaultWidth() {return 64;}
	public static int getDefaultHeight() {return 80;}
}
