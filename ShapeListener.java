package project3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ShapeListener extends MouseAdapter {
	private static JPanel p;
	private JPanel linePanel;
	private Point initial, screen;
	private Point beginning, end;
	private static ArrayList<ShapeIcon> selectedShapes;
	
	public ShapeListener(JPanel panel) {
		p = panel;
		selectedShapes = new ArrayList<ShapeIcon>();
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		if (event.getSource() instanceof JLabel) {
			ShapeIcon shape = ShapeIcon.findShape((JLabel) event.getSource());
			Line line = Line.findLine((JLabel) event.getSource());
			//System.out.println(shape == null);
			if (shape != null) {
				shape.setSelected(true);
				selectedShapes.add(shape);
			} else if (line != null) {
				line.setSelected(true);
			} else {
				return ;
			}
			
			// Set up for moving shape
			screen = new Point(event.getXOnScreen(), event.getYOnScreen());
			initial = SwingUtilities.convertPoint((Component) event.getSource(), event.getX(), event.getY(), p);
			
			// Drawing line between shapes
			/*if ((lineBuffer.size() == 0) && drawLine) {
				int x = ((Component) event.getSource()).getWidth() / 2;
				int y = ((Component) event.getSource()).getHeight() / 2;
				beginning = SwingUtilities.convertPoint((Component) event.getSource(), x, y, p);
				
				lineBuffer.add(shape);
			} else if ((lineBuffer.size() == 1) && drawLine) {
				int x = ((Component) event.getSource()).getWidth() / 2;
				int y = ((Component) event.getSource()).getHeight() / 2;
				end = SwingUtilities.convertPoint((Component) event.getSource(), x, y, p);
				
				lineBuffer.add(shape);
				
				// Create line and add it to Line ArrayList
				Line line = new Line(beginning, end, lineBuffer.get(0), lineBuffer.get(1), "association");
				DrawPanelTwo.addLine(line);
				
				HistoryPanel.logAction("Added a line");
				lineBuffer.clear();
				
				p.revalidate();
				p.repaint();
			}*/
		}
		if (event.getSource() instanceof JPanel) {
			for (ShapeIcon shape : DrawPanelTwo.getShapes()) {
				shape.setSelected(false);
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent event) {
		if (event.getSource() instanceof JLabel) {
			ShapeIcon shape = ShapeIcon.findShape((JLabel) event.getSource());
			if (shape == null) return;
			JLabel label = shape.getShape();
			if (shape.isSelected()) {
				// Calculate new position
				int x = (int) initial.getX() + (event.getXOnScreen() - (int) screen.getX());
				int y = (int) initial.getY() + (event.getYOnScreen() - (int) screen.getY());
				//System.out.println(String.format("Point: (%d, %d)", x, y));
				//System.out.println(String.format("Screen Point: (%d, %d)", (int) screen.getX(), (int) screen.getY()));
				// Move shape
				label.setLocation(x, y);
				// Set corresponding lines
				/*for (Line line : DrawPanelTwo.getLines()) {
					if (shape == line.getHead()) {
						line.setBeginning(new Point(x + (label.getWidth() / 2), y + (label.getHeight() / 2)));
					}
					if (shape == line.getTail()) {
						line.setEnd(new Point(x + (label.getWidth() / 2), y + (label.getHeight() / 2)));
					}
				}
				linePanel.revalidate();
				linePanel.repaint();*/
			}
		}
	}
	
	public static void updatePanel(JPanel panel) {
		p = panel;
	}
	
	public static ArrayList<ShapeIcon> getSelectedShapes() {
		return selectedShapes;
	}
}