package zombie.graphics;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import rts.graphics.RTSDrawingPanel;

public class ZombieDrawingPanel extends RTSDrawingPanel {

	private static final long serialVersionUID = 1L;

	public ZombieDrawingPanel(Rectangle view, int maxx, int maxy) {
		super(view, maxx, maxy);
		setPreferredSize(new Dimension((int) view.getWidth(),
				(int) view.getHeight()));
	}
	
	public void render(Image img) {
		super.render(img);
	}
	
}
