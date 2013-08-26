package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Entity;
import entities.structure.Entities;

@SuppressWarnings("unchecked")
public class Renderer<Entities_ extends Entities> {
	protected Entities_ entities;
	
	public Renderer(Entities_ entities) {
		this.entities = entities;
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
		g.setBackground(new Color(0,0,0,0));
		
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		
		/* render each entity */
		ArrayList<Entity> e_render = entities.getInsideWithOverlap(
				new Rectangle(x,y,width,height));
		for(Entity e : e_render) {
			e.draw(e.getBounds().x-view.x, e.getBounds().y-view.y, g);
		}
		
		g.dispose();
		return result;
	}
		
}
