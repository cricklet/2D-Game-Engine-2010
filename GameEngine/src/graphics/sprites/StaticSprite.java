package graphics.sprites;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StaticSprite implements Sprite {
	private Image image;

	public StaticSprite(String name) {
		GifDecoder decoder = new GifDecoder();
		try {
			decoder.read(new BufferedInputStream(new FileInputStream(name)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		image = decoder.getImage();
	}
	
	public Image getImage() {
		return image;
	}

	public Image getImage(int i) {
		return image;
	}

	public int getWidth() {
		return image.getWidth(null);
	}

	public int getHeight() {
		return image.getHeight(null);
	}
}