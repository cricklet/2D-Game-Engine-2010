package engine;

import javax.swing.JFrame;

import events.game.GameListener;

/* To make a game, extend this class with the corresponding Engine.
 * The engine object will do most of the work except actually drawing to the screen.
 */
public abstract class Game<Engine_ extends Engine<?, ?, ?>> extends JFrame implements GameListener {

	private static final long serialVersionUID = 1L;
	public Engine_ engine;

	protected int FPS = 30;
	
	public Game(String name) {
		super(name);
	}
	
	protected void loop() {
		long lasttime = System.currentTimeMillis();
		while (true) {
			lasttime = System.currentTimeMillis();

			think();

			try {
				long sleeptime = lasttime + 1000 / FPS
						- System.currentTimeMillis();
				Thread.sleep(sleeptime);
			} catch (IllegalArgumentException e) {
			} catch (InterruptedException e) {
			}
		}
	}

	public abstract void think();
	
}
