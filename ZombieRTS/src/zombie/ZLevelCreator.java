package zombie;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

import zombie.entities.House;
import zombie.entities.Person;
import zombie.entities.Tree;
import zombie.graphics.CharacterSprite;
import zombie.graphics.StaticSprites;
import zombie.graphics.CharacterSprite.CharacterAction;
import zombie.graphics.CharacterSprite.CharacterDir;

import entities.Entity;
import graphics.sprites.AnimatedSprite;

public class ZLevelCreator {

	public static LinkedList<Entity> generate(int width, int height) {
		LinkedList<Entity> entities = new LinkedList<Entity>();
		fillWithTrees(entities, new Rectangle(0, 0, width, height));
		deleteWithin(entities,new Ellipse2D.Double(width / 2 - 192,
				height / 2 - 192, 384, 384));
		deleteWithin(entities, new Ellipse2D.Double(
				width / 2 - 192 - 576, height / 2 - 192, 384, 384));
		deleteWithin(entities, new Ellipse2D.Double(
				width / 2 - 192 + 576, height / 2 - 192, 384, 384));
		deleteWithin(entities, new Rectangle(0, height / 2 - 40, width,	80));

		int housew = StaticSprites.house.getWidth();
		int househ = StaticSprites.house.getHeight();
		entities.add(new House("House", width / 2 - housew / 2,
				height / 2 - househ / 2,
				House.getDefaultWidth(), House.getDefaultHeight()));

		entities.add(new Person("Person", width / 2 - 100, height / 2,
				Person.getDefaultWidth(), Person.getDefaultHeight()));
		entities.add(new Person("Person", width / 2 + 100, height / 2,
				Person.getDefaultWidth(), Person.getDefaultHeight()));
		entities.add(new Person("Person", width / 2 + 60,
				height / 2 + 60, Person.getDefaultWidth(), Person
						.getDefaultHeight()));
		/*
		 * entities.add(new Resource(width / 2 - Sprites.bush.getWidth() / 2,
		 * height / 2 - Sprites.bush.getWidth() / 2 + 100, state));
		 * entities.add(new Resource(width / 2 - Sprites.bush.getWidth() / 2,
		 * height / 2 - Sprites.bush.getWidth() / 2 - 100, state));
		 */
		return entities;
	}

	public static void fillWithTrees(LinkedList<Entity> entities, Shape s) {
		int treew = Tree.getDefaultWidth();
		int treeh = Tree.getDefaultHeight();
		double minx = s.getBounds().getMinX();
		double maxx = s.getBounds().getMaxX();
		double miny = s.getBounds().getMinY();
		double maxy = s.getBounds().getMaxY();
		for (int x = (int) minx; x < maxx; x += treew) {
			for (int y = (int) miny; y < maxy; y += treeh) {
				if (s.contains(new Point(x + treew / 2, y
						+ treeh / 2))) {
					entities.add(new Tree("Tree", x, y, treew, treeh));
				}
			}
		}
	}

	public static void deleteWithin(LinkedList<Entity> entities, Shape s) {
		for (int i = 0; i < entities.size(); i++) {
			if (s.contains(entities.get(i).getCenter())) {
				entities.remove(i);
				i--;
			}
		}
	}
}
