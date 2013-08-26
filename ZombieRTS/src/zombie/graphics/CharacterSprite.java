package zombie.graphics;

import graphics.sprites.AnimatedSprite;

import java.awt.Graphics;

public class CharacterSprite {

	public static enum CharacterDir {
		E, W, N, S
	};

	public static enum CharacterAction {
		WALK, ACT, WALK_HOLD
	};
	
	public static int FRAMES_BETWEEN_ANIM = 3;

	public static final AnimatedSprite walk_E = new AnimatedSprite(
			"sprites/person_walk_east.gif",3);
	public static final AnimatedSprite walk_W = new AnimatedSprite(
			"sprites/person_walk_west.gif",3);
	public static final AnimatedSprite walk_N = new AnimatedSprite(
			"sprites/person_walk_north.gif",3);
	public static final AnimatedSprite walk_S = new AnimatedSprite(
			"sprites/person_walk_south.gif",3);

	public static final AnimatedSprite act_E = new AnimatedSprite(
			"sprites/person_act_east.gif",0);
	public static final AnimatedSprite act_W = new AnimatedSprite(
			"sprites/person_act_west.gif",0);
	public static final AnimatedSprite act_N = new AnimatedSprite(
			"sprites/person_act_north.gif",0);
	public static final AnimatedSprite act_S = new AnimatedSprite(
			"sprites/person_act_south.gif",0);

	public static final AnimatedSprite walk_hold_E = new AnimatedSprite(
			"sprites/person_walkitem_east.gif",2);
	public static final AnimatedSprite walk_hold_W = new AnimatedSprite(
			"sprites/person_walkitem_west.gif",2);
	public static final AnimatedSprite walk_hold_N = new AnimatedSprite(
			"sprites/person_walkitem_north.gif",2);
	public static final AnimatedSprite walk_hold_S = new AnimatedSprite(
			"sprites/person_walkitem_south.gif",2);

	public static AnimatedSprite getSprite(CharacterAction action, CharacterDir dir) {
		AnimatedSprite temp = null;
		if (action == CharacterAction.WALK) {
			if (dir == CharacterDir.E)
				temp = walk_E;
			if (dir == CharacterDir.W)
				temp = walk_W;
			if (dir == CharacterDir.N)
				temp = walk_N;
			if (dir == CharacterDir.S)
				temp = walk_S;
		}
		if (action == CharacterAction.ACT) {
			if (dir == CharacterDir.E)
				temp = act_E;
			if (dir == CharacterDir.W)
				temp = act_W;
			if (dir == CharacterDir.N)
				temp = act_N;
			if (dir == CharacterDir.S)
				temp = act_S;
		}
		if (action == CharacterAction.WALK_HOLD) {
			if (dir == CharacterDir.E)
				temp = walk_hold_E;
			if (dir == CharacterDir.W)
				temp = walk_hold_W;
			if (dir == CharacterDir.N)
				temp = walk_hold_N;
			if (dir == CharacterDir.S)
				temp = walk_hold_S;
		}
		return temp;
	}

}
