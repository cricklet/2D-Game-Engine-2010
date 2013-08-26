package engine;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import physics.Physics;
import entities.Entity;
import entities.structure.Entities;
import events.game.GEntityCreateEvent;
import events.game.GEntityDeleteEvent;
import events.game.GameEvent;
import events.game.GameListener;
import graphics.Renderer;

public abstract class Engine<Entities_ extends Entities, Renderer_ extends Renderer, Physics_ extends Physics<?>>
		implements GameListener {

	/* these data structures hold all the entities */
	protected Entities_ entities;

	/* this object renders the entities as an Image */
	protected Renderer_ renderer;

	/* this object moves the entities */
	protected Physics_ physics;

	private ArrayList<Entity> todelete = new ArrayList<Entity>();
	private ArrayList<Entity> toadd = new ArrayList<Entity>();

	public void think() {
		entities.think();
		physics.physics();

		for (Entity e : todelete)
			entities.remove(e);
		for (Entity e : toadd)
			entities.add(e);
		toadd.clear();
		todelete.clear();
	}

	public Entities_ getEntities() {
		return entities;
	}

	public Image render(Rectangle view) {
		return renderer.render(view);
	}

	public void GameEventReceived(GameEvent event) {
		if (event instanceof GEntityDeleteEvent) {
			System.out.println("Removing entity");
			todelete.add((Entity) event.getSource());
		} else if (event instanceof GEntityCreateEvent) {
			System.out.println("Adding entity");
			toadd.add(((GEntityCreateEvent) event).getEntity());
		}
	}
}
