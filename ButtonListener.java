package project3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ButtonListener implements ActionListener {
	private ArrayList<AbstractButton> buttons;
	private ArrayList<ShapeIcon> selectedShapes;
	private ArrayList<Line> selectedLines;
	
	public ButtonListener() {
		buttons = ButtonPanel.getButtons();
		selectedShapes = new ArrayList<ShapeIcon>();
		selectedLines = new ArrayList<Line>();
	}
	
	public void actionPerformed(ActionEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		JPanel panel = (JPanel) button.getParent();
		JTabbedPane pane = (JTabbedPane) panel.getParent();
		String text = button.getText();
		Background bg = DrawPanelTwo.getBG();
		Color fillColor, lineColor, bgColor;
		
		// Get all selectedShapes shapes
		selectedShapes = ShapeListener.getSelectedShapes();
		
		// Determine which type of operation is being done
		if (panel == pane.getComponentAt(0)) {
			shapeOperations(text, bg.getBG());
		} else if (panel == pane.getComponentAt(1)) {
			backgroundOperations(text, bg);
		} else if (panel == pane.getComponentAt(2)) {
			lineOperations(text, bg.getBG());
		}
		
		// After action is performed, clear selectedShapes shapes
		for (ShapeIcon shape : selectedShapes) {
			int index = DrawPanelTwo.getShapes().indexOf(shape);
			DrawPanelTwo.getShapes().get(index).setSelected(false);
		}
		selectedShapes.clear();
	}
	
	private void shapeOperations(String text, JPanel bg) {
		Color fillColor, lineColor, bgColor;
		if (selectedShapes.size() != 0) {
			switch (text) {
				case "Flip Horizontally":
					for (ShapeIcon shape : selectedShapes) { shape.flip("horizontal"); }
					break;
				case "Flip Vertically":
					for (ShapeIcon shape : selectedShapes) { shape.flip("vertical"); }
					break;
				case "Rotate Counter Clockwise":
					for (ShapeIcon shape : selectedShapes) { shape.rotate(-90); }
					break;
				case "Rotate Clockwise": 
					for (ShapeIcon shape : selectedShapes) { shape.rotate(90); }
					break;
				case "Change Line Color":
					lineColor = JColorChooser.showDialog(bg, text, selectedShapes.get(0).getLineColor());
					for (ShapeIcon shape : selectedShapes) { shape.color(shape.getFillColor(), lineColor); }
					break;
				case "Change Fill Color":
					fillColor = JColorChooser.showDialog(bg, text, selectedShapes.get(0).getFillColor());
					for (ShapeIcon shape : selectedShapes) { shape.color(fillColor, shape.getLineColor()); }
					break;
			}
		} else { JOptionPane.showMessageDialog(bg, "No shape selected... Please try again.", "No Shape Selection", JOptionPane.ERROR_MESSAGE); }
	}
	
	private void backgroundOperations(String text, Background bg) {
		Color fillColor, lineColor, bgColor;
		switch (text) {
			case "Change Gridline Color":
				lineColor = JColorChooser.showDialog(bg.getBG(), text, bg.getLineColor());
				bg.changeGridlineColor(lineColor);
				break;
			case "Change Grid Background Color":
				bgColor = JColorChooser.showDialog(bg.getBG(), text, bg.getBGColor());
				bg.changeGridBGColor(bgColor);
				break;
			case "Change Grid Spacing":
				String spacing = JOptionPane.showInputDialog(bg.getBG(), "Please enter an integer spacing", "Enter Spacing", JOptionPane.QUESTION_MESSAGE);
				try {
					if (Integer.parseInt(spacing) < 1) {
						throw new NumberFormatException();
					}
					bg.changeGridSpacing(Integer.parseInt(spacing));
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(bg.getBG(), "Invalid spacing... Please try again.", "Invalid Spacing", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "Clear Artboard":
				bg.clear();
				break;
		}
	}
	
	private void lineOperations(String text, JPanel bg) {
		switch (text) {
			case "Draw Line":
				if (selectedShapes.size() == 2) {
					CodeObserver o = DrawPanelTwo.getCodeObserver();
					Line line = new Line(selectedShapes.get(0), selectedShapes.get(1), bg, "inheritance");
					line.addObserver(o);
					line.draw();
				} else {JOptionPane.showMessageDialog(bg, "Please select two shapes.", "Invalid Line", JOptionPane.ERROR_MESSAGE);}
				break;
			case "Change Type":
				break;
			case "test":
				break;
		}
	}
}