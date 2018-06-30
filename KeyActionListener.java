package project3;

import java.awt.event.*;
import javax.swing.*;

public class KeyActionListener extends KeyAdapter {
	private static JPanel p;
		
	public KeyActionListener(JPanel panel) {
		p = panel;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		for (ShapeIcon shape : DrawPanelTwo.getShapes()) {
			if (shape.isSelected()) {
				switch(event.getKeyCode()) {
					case KeyEvent.VK_DELETE:
					case KeyEvent.VK_BACK_SPACE:
						shape.remove(p);
						HistoryPanel.logAction("Removed a shape");
						break;
					case KeyEvent.VK_RIGHT:
						shape.rotate(90); 
						HistoryPanel.logAction("Rotated a shape 90 degrees");
						break;
					case KeyEvent.VK_LEFT:
						shape.rotate(-90);
						HistoryPanel.logAction("Rotated a shape -90 degrees");
						break;
					case KeyEvent.VK_J:
						break;
				}
			}
			p.revalidate();
			p.repaint();
		}
	}
	
	public static void updatePanel(JPanel panel) {
		p = panel;
	}
}