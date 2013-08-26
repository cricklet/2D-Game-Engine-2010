package plumber.entities;

import events.entities.EntityEvent;
import events.game.GameListener;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import platformer.entities.StaticEntity;
import plumber.graphics.sprites.StaticSprites;

public class Wall extends StaticEntity{

	private static final int WIDTH = 32;
	private static final int HEIGHT_ = 32;
	
	private Image img;
	
	public Wall(int x, int y, int height) {
		super("Wall", x,y,WIDTH,height);
		img = new BufferedImage(WIDTH,height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.drawImage(StaticSprites.wall.getImage(),
				0,0,WIDTH,height,0,0,WIDTH,HEIGHT_,null);
	}

	public void draw(int x, int y, Graphics2D g) {
		g.drawImage(img, x, y, null);
	}

	public boolean entityNoClip() {
		return false;
	}

	public void EntityEventReceived(EntityEvent event) {
		
	}
	
	public void think() {
	}
}
