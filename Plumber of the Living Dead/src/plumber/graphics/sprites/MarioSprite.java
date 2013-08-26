package plumber.graphics.sprites;

import graphics.sprites.AnimatedSprite;
import graphics.sprites.StaticSprite;

import java.awt.Graphics;

public class MarioSprite {

	public static final AnimatedSprite mario_run = new AnimatedSprite(
			"sprites/mario_run.gif");
	public static final StaticSprite mario_jump = new StaticSprite(
			"sprites/mario_jump.gif");
	public static final StaticSprite mario_idle = new StaticSprite(
			"sprites/mario_idle.gif");
	public static final StaticSprite mario_stop= new StaticSprite(
			"sprites/mario_stop.gif");
	public static final StaticSprite mario_lose= new StaticSprite(
			"sprites/mario_lose.gif");

}