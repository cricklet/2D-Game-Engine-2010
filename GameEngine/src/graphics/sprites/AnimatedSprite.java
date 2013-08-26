package graphics.sprites;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AnimatedSprite implements Sprite {

	private Image[] frames;
	
	private int idle;

	public AnimatedSprite(String name) {
		idle = 0;
		GifDecoder decoder = new GifDecoder();
		try {
			decoder.read(new BufferedInputStream(new FileInputStream(name)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		frames = decoder.getFrames();
	}
	
	public AnimatedSprite(String name, int idle) {
		this.idle = idle;
		GifDecoder decoder = new GifDecoder();
		try {
			decoder.read(new BufferedInputStream(new FileInputStream(name)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		frames = decoder.getFrames();
	}
	
	public int getIdleFrame() {
		return idle;
	}
	
	public Image getImage() {
		return frames[idle];
	}
	
	public Image getImage(int i) {
		if(i == -1)
			i = idle;
		return frames[i];
	}

	public int getWidth() {
		return frames[idle].getWidth(null);
	}
	
	public int getHeight() {
		return frames[idle].getHeight(null);
	}
	
	public int length() {
		return frames.length;
	}
	
}