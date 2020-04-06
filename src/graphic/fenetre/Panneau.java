package graphic.fenetre;

import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panneau extends JPanel {
	private static Panneau instance;
	
	private Panneau() {

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Renderer.getInstance().paint(g);
	}
	
	
	public static Panneau getInstance() {
		if (instance == null)
			instance = new Panneau();
		return instance;
	}
}