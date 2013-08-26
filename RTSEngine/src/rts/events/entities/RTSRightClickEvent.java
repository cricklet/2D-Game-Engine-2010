package rts.events.entities;

import java.awt.Point;

import events.entities.EntityEvent;

public class RTSRightClickEvent extends EntityEvent {
	
	private Point p;
	private boolean ctrl;
	
	public RTSRightClickEvent(Object source, Point p, boolean ctrl) {
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
