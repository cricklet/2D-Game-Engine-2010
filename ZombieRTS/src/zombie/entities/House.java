package zombie.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import zombie.graphics.StaticSprites;
import events.entities.EntityEvent;
import rts.entities.RTSStaticEntity;

public class House extends RTSStaticEntity {
	
	public House(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
	}

	public void EntityEventReceived(EntityEvent event) {
	}

	public void draw(int x, int y, Graphics2D g) {
		g.drawImage(StaticSprites.house.getImage(), x, y,
				(int) getWidth(), (int) getHeight(), null);
	}

	public void think() {
	}

	public Color getOutlineColor() {
		return Color.BLUE;
	}

	public static int getDefaultWidth() {return 64;}
	public static int getDefaultHeight() {return 67;}
}
