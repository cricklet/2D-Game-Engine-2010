package rts.events.game;

import java.awt.Point;
import java.awt.Rectangle;

import events.game.GameEvent;

public class RTSGSelectPointEvent extends GameEvent{

	private Point p;
	private boolean ctrl;
	
	public RTSGSelectPointEvent(Object source, Point p, boolean ctrl) {
		super(source);
		this.p = p;
		this.ctrl = ctrl;
	}
	
	public Point getLocation() {
		return p;
	}
	
	public boolean getCtrl() {
		return ctrl;
	}

}
