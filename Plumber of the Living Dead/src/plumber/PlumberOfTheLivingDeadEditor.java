package plumber;

import javax.swing.JOptionPane;

import platformer.PlatformerEngine;
import plumber.entities.Mario;
import plumber.entities.Zombie;

import engine.editor.Editor;
import events.game.GameEvent;

public class PlumberOfTheLivingDeadEditor extends Editor {

	public static void main(String[] args) {
		int w_window = Integer.parseInt(JOptionPane
				.showInputDialog("Input window width in pixels", "640"));
		int h_window = Integer
				.parseInt(JOptionPane
						.showInputDialog(
								"Input window height in pixels", "480"));
		int w = Integer.parseInt(JOptionPane
				.showInputDialog("Input environment width in pixels",
						"1280"));
		int h = Integer.parseInt(JOptionPane
				.showInputDialog("Input environment height in pixels",
						"960"));
		PlumberOfTheLivingDeadEditor e = new PlumberOfTheLivingDeadEditor(
				w_window, h_window, w, h);
		e.loop();
	}

	public PlumberOfTheLivingDeadEditor(int w_window, int h_window,
			int w,
			int h) {
		super(w_window, h_window, w, h);
		engine = new PlatformerEngine();
		addEntityType(Mario.class);
		addEntityType(Zombie.class);
	}

	public void addEntity(Class<?> c, int x, int y) {
		if(c.getName().equals("entities.Mario")) {
			engine.getEntities().add(new Mario(x,y));
		} else if(c.getName().equals("entities.Zombie")) {
			engine.getEntities().add(new Zombie(x,y));
		}
	}

	private static final long serialVersionUID = 1L;

	public void think() {
		drawingPanel.render(engine.render(
				drawingPanel.getView()));
	}

	public void GameEventReceived(GameEvent event) {

	}

}
