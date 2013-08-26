package rts.graphics;

import java.awt.Color;
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

import javax.swing.JPanel;

import entities.Entity;
import events.entities.EntityEvent;
import events.entities.EntityListener;
import events.game.GEntityDeleteEvent;
import events.game.GameEvent;
import events.game.GameListener;

import rts.RTSGame;
import rts.events.game.*;

public class RTSDrawingPanel extends JPanel implements EntityListener {

	private static final long serialVersionUID = 1L;
	private RTSDrawingPanelEntity view;
	private int maxx;
	private int maxy;

	public RTSDrawingPanel(Rectangle view, int maxx, int maxy) {
		this.view = new RTSDrawingPanelEntity(
				"Dummy entity. Allows DrawingPanel to communicate with game",
				view.getX(), view.getY(),
				view.getWidth(), view.getHeight());
		this.maxx = maxx;
		this.maxy = maxy;
		RTSInputHandler adapter = new RTSInputHandler();
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		addKeyListener(adapter);
	}

	public Rectangle getView() {
		return view.getBounds();
	}

	public void render(Image img) {
		Graphics g = img.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		if (drawRect != null) {
			g.drawRect(
					(int) drawRect.getX(),
					(int) drawRect.getY(),
					(int) drawRect.getWidth(),
					(int) drawRect.getHeight());
		}
		getGraphics().drawImage(img, 0, 0, null);
	}

	public void moveViewport(int dx, int dy) {
		view.setLocation(view.getX() + dx,
				view.getY() + dy);
	}

	public void pan(double dx, double dy) {
		if (view.getX() + view.getWidth() + dx > maxx)
			dx = maxx - (view.getX() + view.getWidth());
		if (view.getX() + dx < 0)
			dx = -(view.getX());
		if (view.getY() + view.getHeight() + dy > maxy)
			dy = maxy - (view.getY() + view.getHeight());
		if (view.getY() + dy < 0)
			dy = -(view.getY());
		moveViewport((int) dx, (int) dy);
	}

	public void addGameEventListener(GameListener l) {
		view.addGameEventListener(l);
	}

	public class RTSDrawingPanelEntity extends Entity {

		public RTSDrawingPanelEntity(String name, double x, double y,
				double width, double height) {
			super(name, x, y, width, height);
		}

		public void draw(int x, int y, Graphics2D g) {
		}

		public void think() {
		}

		public void EntityEventReceived(EntityEvent event) {
		}
	}

	private Rectangle drawRect;

	public class RTSInputHandler implements MouseListener,
			MouseMotionListener, KeyListener {
		private Point initial;
		private Point lastAbs = new Point(0, 0);
		private boolean pan;
		
		private boolean ctrl;

		public void mousePressed(MouseEvent e) {
			initial = null;
			if (e.getButton() == MouseEvent.BUTTON2) {
				pan = true;
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
				view.fireGameEvent(
						new RTSGSelectEvent(view,
								new Rectangle(
									(int) view.getX() + x, (int) view.getY() + y,
									width, height),
								ctrl));
				initial = null;
				drawRect = null;
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
				drawRect = new Rectangle(x, y, width, height);
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
			if (e.getButton() == MouseEvent.BUTTON1)
				view.fireGameEvent(new RTSGSelectPointEvent(view,
						new Point(e.getX() + (int) view.getX(), e.getY() + (int) view.getY()), ctrl));
			if (e.getButton() == MouseEvent.BUTTON3)
				view.fireGameEvent(new RTSGRightClickEvent(view,
						new Point(e.getX() + (int) view.getX(), e.getY() + (int) view.getY()), ctrl));
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			ctrl = e.isControlDown();
		}

		public void keyReleased(KeyEvent e) {
			ctrl = e.isControlDown();
		}

		public void keyTyped(KeyEvent e) {
		}
	}

	public void EntityEventReceived(EntityEvent event) {

	}
}
