package rts.events.game;

import java.awt.Point;

import events.game.GameEvent;

public class RTSGRightClickEvent extends GameEvent {

	private Point p;
	private boolean ctrl;
	
	public RTSGRightClickEvent(Object source, Point p, boolean ctrl) {
		super(source);
		this.p = p;
		this.ctrl = ctrl;
	}
	
	public boolean getCtrl() {
		return ctrl;
	}
	
	public Point getLocation() {
		return p;
	}
	
}
