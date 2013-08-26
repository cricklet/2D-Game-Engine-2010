package engine.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;

import engine.Engine;

public class EditorDrawingPanel extends JDesktopPane {
	private static final long serialVersionUID = 1L;
	private JScrollPane scroller;

	private Editor editor;

	public EditorDrawingPanel(int w, int h, Editor editor) {
		this.editor = editor;
		setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		setPreferredSize(new Dimension(w, h));

		MouseInputHandler adapter = new MouseInputHandler();
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		addKeyListener(adapter);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics graphics) {
		if (offImg != null) {
			Rectangle view = scroller.getViewport().getViewRect();
			graphics.drawImage(offImg, view.x, view.y, null);
		}
	}

	private Image off_image;

	private void checkOffscreenImage() {
		Dimension d = getSize();
		if (off_image == null || off_image.getWidth(null) != d.width
				|| off_image.getHeight(null) != d.height) {
			off_image = createImage(d.width, d.height);
		}
	}

	private Image offImg;

	public void render(Image img) {
		offImg = img;
		repaint();
	}

	public void addScroller(JScrollPane scroller) {
		this.scroller = scroller;
	}

	public Rectangle getView() {
		return scroller.getViewport().getViewRect();
	}

	public void pan(int dx, int dy) {
		Rectangle view = scroller.getViewport().getViewRect();
		Point newPoint = new Point(view.x + dx, view.y + dy);
		if (newPoint.getX() + view.getWidth() > getWidth())
			newPoint.move(getWidth() - view.width, newPoint.y);
		if (newPoint.getY() + view.getHeight() > getHeight())
			newPoint.move(newPoint.x, getHeight() - view.height);
		scroller.getViewport().setViewPosition(newPoint);
	}

	protected Rectangle dragRect;

	public class MouseInputHandler implements MouseListener,
			MouseMotionListener, KeyListener {
		private Point initial;
		private Point lastAbs = new Point(0, 0);
		private boolean pan;

		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON2) {
				pan = true;
			} else if (ctrl && e.getButton() == MouseEvent.BUTTON1) {
				editor.addEntity(e.getX(), e.getY());
			} else if (e.getButton() == MouseEvent.BUTTON1) {
				initial = new Point(e.getX(), e.getY());
			} else {
			}
			lastAbs.setLocation(e.getLocationOnScreen());
		}

		public void mouseReleased(MouseEvent e) {
			if (initial != null) {
				int width = Math.abs(e.getX() - initial.x);
				int height = Math.abs(e.getY() - initial.y);
				int x = Math.min(initial.x, e.getX());
				int y = Math.min(initial.y, e.getY());
				Rectangle selection = new Rectangle(x, y, width, height);
				initial = null;
				dragRect = null;
			} else if (pan) {
				pan = false;
			} else {
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (initial != null) {
				int width = Math.abs(e.getX() - initial.x);
				int height = Math.abs(e.getY() - initial.y);
				int x = Math.min(initial.x, e.getX());
				int y = Math.min(initial.y, e.getY());
				dragRect = new Rectangle(x, y, width, height);
			} else if (pan) {
				// calculation of move
				int dx = lastAbs.x - e.getXOnScreen();
				int dy = lastAbs.y - e.getYOnScreen();
				pan(dx, dy);
			} else {
			}
			lastAbs.setLocation(e.getLocationOnScreen());
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}

		boolean ctrl = false;

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_CONTROL)
				ctrl = true;
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_CONTROL)
				ctrl = false;
		}

		public void keyTyped(KeyEvent e) {
		}
	}
}
