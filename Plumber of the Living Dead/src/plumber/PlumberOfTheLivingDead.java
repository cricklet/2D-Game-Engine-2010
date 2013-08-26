package plumber;

import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import platformer.PlatformerEngine;
import platformer.entities.CharacterEntity;
import platformer.entities.structure.PlatformerEntities;
import platformer.events.entities.*;
import platformer.physics.PlatformerPhysics;
import plumber.entities.*;
import plumber.events.entities.EZombieDeathEvent;
import plumber.events.game.*;
import plumber.graphics.DrawingPanel;
import engine.Game;
import events.game.GameEvent;

public class PlumberOfTheLivingDead extends Game<PlatformerEngine> {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public int killCount;

	public enum Mode {
		RUN, PAUSED
	};

	public Mode mode;
	public DrawingPanel drawingPanel;

	public static void main(String argv[]) {
		PlumberOfTheLivingDead game;
		game = new PlumberOfTheLivingDead("Plumber of the Living Dead");
		game.initiateEntities();
		game.loop();
	}

	public void think() {
		if (mode == Mode.RUN) {
			engine.think();
			drawingPanel.render(engine.render(new Rectangle(0, 0, WIDTH,
						HEIGHT)));
			stateThink();
		}
	}

	// Executed when the applet is first created.
	public PlumberOfTheLivingDead(String name) {
		super(name);

		engine = new PlatformerEngine();

		JMenuBar menuBar = new JMenuBar();
		final JCheckBox pause = new JCheckBox("Pause");
		pause.setSelected(false);
		pause.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (((JCheckBox) e.getSource()).isSelected())
					mode = Mode.PAUSED;
				else
					mode = Mode.RUN;
			}
		});

		JMenu menu = new JMenu("Game");
		menu.add(pause);
		menuBar.add(menu);
		setJMenuBar(menuBar);

		drawingPanel = new DrawingPanel(WIDTH, HEIGHT);
		drawingPanel.setIgnoreRepaint(true);
		getContentPane().add(drawingPanel);

		addKeyListener(new KeyInputHandler());

		// Display the window.
		pack();
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		mode = Mode.RUN;
	}

	private void stateThink() {
		int x = (int) mario.getX();
		int y = (int) mario.getY();
		int w = (int) mario.getWidth();
		int h = (int) mario.getHeight() + 5;
		Rectangle bounds = new Rectangle(x, y, w, h);

		boolean jump = false;
		ArrayList<CharacterEntity> touching = engine.getEntities()
				.getMoveableEntities().getInsideWithOverlap(bounds);
		for (CharacterEntity o : touching) {
			if (o == mario)
				continue;
			if (o.entityNoClip())
				continue;
			if (!o.toString().equals("Zombie"))
				continue;

			boolean[] sides = PlatformerPhysics.overlapSide(
					x, y, w, h, bounds, o.getBounds());
			boolean otop = sides[0];
			boolean obottom = sides[1];
			boolean oleft = sides[2];
			boolean oright = sides[3];

			if (otop && mario.getDy() > 0) {
				mario.addDxDy(0, o.getY() - mario.getHeight() - y);
				o.EntityEventReceived(new EZombieDeathEvent(mario, 0, 0,
						-5));
				jump = true;
			} else if (oleft) {
				mario.EntityEventReceived(new ECollisionEvent(o,
						ECollisionEvent.LEFT));
			} else if (oright) {
				mario.EntityEventReceived(new ECollisionEvent(o,
						ECollisionEvent.RIGHT));
			} else if (obottom) {
				mario.EntityEventReceived(new ECollisionEvent(o,
						ECollisionEvent.BOTTOM));
			}
		}

		if (jump)
			mario.forceJump();
	}

	private Mario mario;

	private void initiateEntities() {
		Ground ground;
		PipeEntry entry1;
		PipeEntry entry2;

		mario = new Mario(100, 100);
		engine.getEntities().add(mario);
		drawingPanel.setMario(mario);

		PlatformerEntities entities = engine.getEntities();

		entities.add(new Stone(-16, HEIGHT / 4, 3 * WIDTH / 8));
		entities
				.add(new Stone(5 * WIDTH / 8, HEIGHT / 4, 3 * WIDTH / 8));

		entities.add(new Stone(WIDTH / 4, HEIGHT / 2, WIDTH / 2));

		entities.add(new Stone(-16, 3 * HEIGHT / 4 - 32, 3 * WIDTH / 8));
		entities.add(new Stone(5 * WIDTH / 8, 3 * HEIGHT / 4 - 32,
				3 * WIDTH / 8));

		entities.add(new Stone(-16, -16, WIDTH + 32));
		entities.add(new Stone(-16, HEIGHT / 4, 3 * WIDTH / 8));

		entities.add(new Zombie(200, HEIGHT - 64));

		PipeExit exit1 = new PipeExit(64, 16);
		PipeExit exit2 = new PipeExit(WIDTH - 128, 16);
		PipeExit exit3 = new PipeExit(64, HEIGHT / 4 + 32);
		PipeExit exit4 = new PipeExit(WIDTH - 128, HEIGHT / 4 + 32);
		PipeExit[] exits = { exit1, exit2, exit3, exit4 };
		entities.add(exit1);
		entities.add(exit2);
		entities.add(exit3);
		entities.add(exit4);

		entities.add(new Wall(-16, 0, HEIGHT - 64));
		entities.add(new Wall(WIDTH - 16, 0, HEIGHT - 64));
		entities.add(new Stone(-16, -16, WIDTH + 32));

		entry1 = new PipeEntry(-38, HEIGHT - 85, 1, exits);
		entities.add(entry1);
		entry2 = new PipeEntry(WIDTH - 24, HEIGHT - 85, -1, exits);
		entities.add(entry2);

		ground = new Ground(0, HEIGHT - 32, WIDTH);
		entities.add(ground);

		entities.addGameListener(this);
		entities.addGameListener(engine);
	}

	public class KeyInputHandler implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				mario.setLeft(true);
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				mario.setRight(true);
			if (e.getKeyCode() == KeyEvent.VK_UP)
				mario.jump();
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				mario.setLeft(false);
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				mario.setRight(false);
		}

		public void keyTyped(KeyEvent e) {

		}
	}

	public void GameEventReceived(GameEvent event) {
		if (event instanceof GNewZombieEvent) {
			int x = ((GNewZombieEvent) event).getX();
			int y = ((GNewZombieEvent) event).getY();
			Zombie zombie = new Zombie(x, y);
			zombie.addGameEventListener(this);
			zombie.addGameEventListener(engine);
			engine.getEntities().add(zombie);
		}
		if (event instanceof GZombieKilledEvent) {
			killCount++;
			drawingPanel.setKillCount(killCount);
		}
		if (event instanceof GKillZombiesEvent) {
			ArrayList<CharacterEntity> touching = engine.getEntities()
					.getMoveableEntities().getInsideWithOverlap(
							((GKillZombiesEvent) event).getBounds());
			for (CharacterEntity e : touching) {
				if (e instanceof Zombie) {
					e.EntityEventReceived(new EZombieDeathEvent(event
							.getSource(), 0, 0, -5));
				}
			}

		}
	}
}
