package rts.events.game;

import java.awt.Point;
import java.awt.Rectangle;

import events.game.GameEvent;

public class RTSGSelectEvent extends GameEvent{

	private Rectangle selection;
	private boolean ctrl;
	
	public RTSGSelectEvent(Object source, Rectangle rect, boolean ctrl) {
		super(source);
		this.selection = rect;
		this.ctrl = ctrl;
	}
	
	public Rectangle getSelection() {
		return selection;
	}
	
	public boolean getCtrl() {
		return ctrl;
	}

}
