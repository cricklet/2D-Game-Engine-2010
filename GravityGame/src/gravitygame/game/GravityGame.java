package gravitygame.game;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import engine.Game;
import entities.structure.EntitiesList;
import events.game.GameEvent;
import gravity.GravityEngine;
import gravity.entities.GravityEntity;
import gravitygame.entities.Missile;
import gravitygame.entities.Planet;
import gravitygame.entities.Spaceship;
import gravitygame.events.entities.EOffScreen;
import gravitygame.events.game.GCreateMissleEvent;
import gravitygame.graphics.DrawingPanel;

public class GravityGame extends Game<GravityEngine> {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public enum Mode {
		RUN, PAUSED
	};

	public Mode mode;
	public DrawingPanel drawingPanel;

	public static void main(String argv[]) {
		GravityGame game;
		game = new GravityGame("Gravity test");
		game.initiateEntities();
		game.loop();
	}

	public GravityGame(String name) {
		super(name);

		engine = new GravityEngine();

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

	public void think() {
		if (mode == Mode.RUN) {
			engine.think();
			stateThink();
			drawingPanel.render(engine.render(new Rectangle(0, 0, WIDTH,
						HEIGHT)));
		}
	}

	private void stateThink() {
		drawingPanel.setHealths(spaceship1.getHealth(), spaceship2
				.getHealth());
		if(!drawingPanel.getBounds().contains(spaceship1.getBounds())) {
			spaceship1.EntityEventReceived(new EOffScreen(spaceship1));
		}
		if(!drawingPanel.getBounds().contains(spaceship2.getBounds())) {
			spaceship2.EntityEventReceived(new EOffScreen(spaceship2));
		}
	}

	private Planet planet;
	private Spaceship spaceship1;
	private Spaceship spaceship2;

	private void initiateEntities() {
		planet = new Planet("Planet", WIDTH / 2 - 50, HEIGHT / 2 - 50,
				100, 100, 0, 0, 0, 0, 10000, Color.gray);
		spaceship1 = new Spaceship("Spaceship 1", WIDTH / 2 - 200,
				HEIGHT / 2 - 5, 14, 10, 0, 3,
				0, 0.015, 1, Color.blue);
		spaceship2 = new Spaceship("Spaceship 2", WIDTH / 2 + 200,
				HEIGHT / 2 - 5, 14, 10, 0, -3,
				0, 0.015, 1, Color.red);
		EntitiesList<GravityEntity> entities = engine.getEntities();
		entities.add(planet);
		entities.add(spaceship1);
		entities.add(spaceship2);
		entities.addGameListener(this);
		entities.addGameListener(engine);
	}

	public class KeyInputHandler implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == 'a')
				spaceship1.rotateLeft(true);
			if (e.getKeyChar() == 'd')
				spaceship1.rotateRight(true);
			if (e.getKeyChar() == 'w')
				spaceship1.fireBoosters(true);
			if (e.getKeyChar() == 's')
				spaceship1.fireBrakes(true);
			if (e.getKeyChar() == 'f')
				spaceship1.shoot();
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				spaceship2.rotateLeft(true);
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				spaceship2.rotateRight(true);
			if (e.getKeyCode() == KeyEvent.VK_UP)
				spaceship2.fireBoosters(true);
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				spaceship2.fireBrakes(true);
			if (e.getKeyChar() == '/')
				spaceship2.shoot();
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyChar() == 'a')
				spaceship1.rotateLeft(false);
			if (e.getKeyChar() == 'd')
				spaceship1.rotateRight(false);
			if (e.getKeyChar() == 'w')
				spaceship1.fireBoosters(false);
			if (e.getKeyChar() == 's')
				spaceship1.fireBrakes(false);
			if (e.getKeyChar() == 'f')
				;
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				spaceship2.rotateLeft(false);
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				spaceship2.rotateRight(false);
			if (e.getKeyCode() == KeyEvent.VK_UP)
				spaceship2.fireBoosters(false);
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				spaceship2.fireBrakes(false);
			if (e.getKeyChar() == '/')
				;
		}

		public void keyTyped(KeyEvent e) {

		}
	}

	public void GameEventReceived(GameEvent event) {
		if (event instanceof GCreateMissleEvent) {
			Missile missile = ((GCreateMissleEvent) event).getEntity();
			missile.addGameEventListener(this);
			missile.addGameEventListener(engine);
		}
	}
}
