package rts.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Entity;
import entities.structure.Entities;
import graphics.Renderer;

import rts.entities.RTSMoveableEntity;
import rts.entities.RTSStaticEntity;
import rts.entities.structure.RTSEntities;

public class RTSRenderer extends Renderer<RTSEntities> {

	private final Color BACKGROUND = new Color(115, 156, 58);
	
	public RTSRenderer(RTSEntities entities) {
		super(entities);
	}

	/* render the scene */
	public Image render(Rectangle view) {
		int x = view.x;
		int y = view.y;
		int width = view.width;
		int height = view.height;
		Image result = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) result.getGraphics();

		/* erase the image */
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, width, height);
		
		/* render each entity */
		ArrayList<RTSStaticEntity> eStatic = entities.getStaticEntities().getInsideWithOverlap(
				new Rectangle(x,y,width,height));
		ArrayList<RTSMoveableEntity> eMoveable = entities.getMoveableEntities().getInsideWithOverlap(
				new Rectangle(x,y,width,height));
		
		for (RTSStaticEntity e : eStatic)
			e.draw(e.getBounds().x-view.x, e.getBounds().y-view.y, g);

		for (RTSMoveableEntity e : eMoveable)
			e.draw(e.getBounds().x-view.x, e.getBounds().y-view.y, g);
		
		for (RTSMoveableEntity e : eMoveable)
			if (e.isSelected())
				e.drawOutline(e.getBounds().x-view.x, e.getBounds().y-view.y, g);
		
		for (RTSStaticEntity e : eStatic)
			if (e.isSelected())
				e.drawOutline(e.getBounds().x-view.x, e.getBounds().y-view.y, g);
		
		g.dispose();
		return result;
	}
}
