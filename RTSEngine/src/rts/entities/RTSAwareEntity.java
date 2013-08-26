package rts.entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import entities.Entity;

import rts.entities.structure.RTSEntities;

public interface RTSAwareEntity {
	
	public void setEntities(RTSEntities entites);
	
	public RTSEntities getEntities();
	
	public ArrayList<Entity> getEntitiesAt(Point p);
	
}
