package engine.editor;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import engine.Engine;
import engine.Game;
import entities.Entity;
import events.game.GameEvent;

/* To create a map editor, extend this class.
 */
public abstract class Editor extends Game<Engine<?, ?, ?>> {

	private static final long serialVersionUID = 1L;

	public enum Mode {
		RUN, PAUSED
	};

	public JMenu editorMenu;
	public Mode mode;
	public EditorDrawingPanel drawingPanel;

	public Editor(int w_window, int h_window, int w, int h) {
		super("Editor");

		JMenuBar menuBar = new JMenuBar();
		editorMenu = new JMenu("Editor");
		menuBar.add(editorMenu);
		setJMenuBar(menuBar);

		drawingPanel = new EditorDrawingPanel(w, h, this);

		JScrollPane scroller = new JScrollPane(drawingPanel);
		drawingPanel.addScroller(scroller);
		getContentPane().add(scroller);
		scroller.setOpaque(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		mode = Mode.PAUSED;

		addKeyListener(new KeyInputHandler());

		pack();
		setSize(w_window, h_window);
		setVisible(true);
	}

	private Class<?> currentEntityClass;
	private ButtonGroup radioButtonGroup;

	public void addEntityType(final Class<?> c) {
		JRadioButton item = new JRadioButton(c.getName());
		item.setSelected(true);

		if (radioButtonGroup == null)
			radioButtonGroup = new ButtonGroup();
		
		radioButtonGroup.add(item);
		editorMenu.add(item);

		ActionListener itemAction = new ActionListener() {
			private Class<?> entityClass = c;

			public void actionPerformed(ActionEvent actionEvent) {
				currentEntityClass = entityClass;
			}
		};
		
		item.addActionListener(itemAction);
		currentEntityClass = c;
	}

	public void addEntity(int x, int y) {
		addEntity(currentEntityClass, x, y);
	}

	public abstract void addEntity(Class<?> c, int x, int y);

	public class KeyInputHandler implements KeyListener {
		public void keyPressed(KeyEvent e) {
			drawingPanel.dispatchEvent(e);
		}

		public void keyReleased(KeyEvent e) {
			drawingPanel.dispatchEvent(e);
		}

		public void keyTyped(KeyEvent e) {
			drawingPanel.dispatchEvent(e);
		}
	}

}
