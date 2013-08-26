package rts;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import rts.entities.RTSMoveableEntity;
import rts.entities.RTSEntity;
import rts.entities.RTSStaticEntity;
import rts.events.entities.RTSRightClickEvent;
import rts.events.game.*;
import rts.graphics.RTSDrawingPanel;

import engine.Game;
import entities.Entity;
import events.game.GEntityDeleteEvent;
import events.game.GameEvent;

public abstract class RTSGame extends Game<RTSEngine> {

	private static final long serialVersionUID = 1L;

	private ArrayList<Entity> selected;

	public RTSDrawingPanel drawingPanel;

	public RTSGame(String name) {
		super(name);
		selected = new ArrayList<Entity>();
	}

	public void select(Rectangle selection, boolean ctrl) {
		if (!ctrl) {
			for (Entity e : selected)
				((RTSEntity) e).select(false);
			selected.clear();
		}

		ArrayList<RTSMoveableEntity> mEntities = engine.getEntities()
				.getMoveableEntities().getInside(selection);
		if (mEntities.size() > 0) {
			for (RTSMoveableEntity e : mEntities)
				if (!selected.contains(e)) {
					e.select(true);
					selected.add(e);
				}
			return;
		}

		ArrayList<RTSStaticEntity> sEntities = engine.getEntities()
				.getStaticEntities().getInside(selection);
		if (sEntities.size() > 0) {
			for (RTSStaticEntity e : sEntities)
				if (!selected.contains(e)) {
					e.select(true);
					selected.add(e);
				}
			return;
		}
	}

	public void select(Point p, boolean ctrl) {
		if (!ctrl) {
			for (Entity e : selected)
				((RTSEntity) e).select(false);
			selected.clear();
		}

		ArrayList<RTSMoveableEntity> mEntities = engine.getEntities()
				.getMoveableEntities().getAt(p);
		while (mEntities.size() > 0
				&& !selected.contains(mEntities.get(0))) {
			selected.add(mEntities.get(0));
			mEntities.get(0).select(true);
			return;
		}

		ArrayList<RTSStaticEntity> sEntities = engine.getEntities()
				.getStaticEntities().getAt(p);
		while (sEntities.size() > 0
				&& !selected.contains(sEntities.get(0))) {
			selected.add(sEntities.get(0));
			sEntities.get(0).select(true);
			return;
		}
	}

	public void rightClick(Point p, boolean ctrl) {
		for (Entity e : selected)
			e.EntityEventReceived(new RTSRightClickEvent(this, p, ctrl));
	}

	public void GameEventReceived(GameEvent event) {
		if (event instanceof GEntityDeleteEvent) {
			System.out.println("Removing entity");
			selected.remove((Entity) event.getSource());
		}
		if (event instanceof RTSGSelectEvent) {
			RTSGSelectEvent selectEvent = (RTSGSelectEvent) event;
			select(selectEvent.getSelection(), selectEvent.getCtrl());
		}
		if (event instanceof RTSGSelectPointEvent) {
			RTSGSelectPointEvent selectEvent = (RTSGSelectPointEvent) event;
			select(selectEvent.getLocation(), selectEvent.getCtrl());
		}
		if (event instanceof RTSGRightClickEvent) {
			rightClick(((RTSGRightClickEvent) event).getLocation(),
					((RTSGRightClickEvent) event).getCtrl());
		}
	}

	public class KeyInputHandler implements KeyListener {
		public void keyPressed(KeyEvent e) {
			drawingPanel.dispatchEvent(e);
		}

		public void keyReleased(KeyEvent e) {
			drawingPanel.dispatchEvent(e);
		}

		public void keyTyped(KeyEvent e) {
			drawingPanel.dispatchEvent(e);
		}
	}

}
