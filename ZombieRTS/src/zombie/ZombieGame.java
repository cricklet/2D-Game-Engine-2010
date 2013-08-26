package zombie;

import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import rts.RTSEngine;
import rts.RTSGame;
import rts.entities.RTSAwareEntity;
import rts.graphics.RTSDrawingPanel;
import zombie.graphics.ZombieDrawingPanel;

import engine.Game;
import entities.Entity;
import events.game.GameEvent;

public class ZombieGame extends RTSGame {

	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private static final int MAP_WIDTH = 1986;
	private static final int MAP_HEIGHT = 704;

	public enum Mode {
		RUN, PAUSED
	};

	public Mode mode;
	
	public static void main(String argv[]) {
		ZombieGame game;
		game = new ZombieGame("Zombie Defense 1.0");
		game.initiateEntities();
		game.loop();
	}

	public ZombieGame(String name) {
		super(name);

		engine = new RTSEngine(MAP_WIDTH, MAP_HEIGHT);

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

		drawingPanel = new ZombieDrawingPanel(
				new Rectangle(MAP_WIDTH / 2 - WIDTH / 2,
						MAP_HEIGHT / 2 - HEIGHT / 2,
						WIDTH, HEIGHT), MAP_WIDTH, MAP_HEIGHT);
		drawingPanel.setIgnoreRepaint(true);
		getContentPane().add(drawingPanel);
		
		drawingPanel.addGameEventListener(this);
		drawingPanel.addGameEventListener(engine);

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
			drawingPanel.render(engine.render(drawingPanel.getView()));
		}
	}

	private void initiateEntities() {
		LinkedList<Entity> elist = ZLevelCreator.generate(MAP_WIDTH, MAP_HEIGHT);
		for (Entity e : elist) {
			engine.getEntities().add(e);
			if (e instanceof RTSAwareEntity)
				((RTSAwareEntity) e).setEntities(engine.getEntities());
		}
		engine.getEntities().addGameListener(this);
		engine.getEntities().addGameListener(engine);
	}

	public void GameEventReceived(GameEvent event) {
		super.GameEventReceived(event);
	}

}
