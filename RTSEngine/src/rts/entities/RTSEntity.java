package rts.entities;

import java.awt.Graphics2D;

public interface RTSEntity {
	
	public static enum Type {
		HEALTH, WOOD, MINERAL, FOOD
	};
	
	public void drawOutline(int x, int y, Graphics2D g);
	
	public boolean isSelected();
	
	public void select(boolean selected);
	
	public Type getType();
	
	public double getHealth();
	
	public double takeHealth(double dh);

	public void giveHealth(double dh);
	
	
}
