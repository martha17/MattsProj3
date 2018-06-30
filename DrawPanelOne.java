package project3;

import java.awt.*;
import java.awt.dnd.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class DrawPanelOne extends JPanel {
	private static Map<ShapeIcon, String> icons;
	private static Map<JLabel, ShapeIcon> labels;
	private final int PANEL_WIDTH = 850;
	private final int PANEL_HEIGHT = 110;
	
	public DrawPanelOne() {
		// Generate shapes from icons folder
		icons = ShapeIcon.generateIcons("icons/");
		labels = new HashMap<JLabel, ShapeIcon>();
		for (ShapeIcon shape : icons.keySet()) {
			labels.put(shape.getShape(), shape);
		}
		// Container that holds the actual shapes
		JPanel container = new JPanel();
		container.setBorder(new LineBorder(Color.BLACK));
		container.setOpaque(true);
		container.setBackground(Color.WHITE);
		container.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		// Margin settings
		setBorder(new EmptyBorder(10, 10, 0, 0));
		setPreferredSize(new Dimension(100, 130));
		
		// Attach DragGestureListener to shapes
		DragGestureListener listener = DragNDropIcon.createDragListener();
		for (ShapeIcon label : icons.keySet()) {
			DragSource source = new DragSource();
			source.createDefaultDragGestureRecognizer(label.getShape(), DnDConstants.ACTION_COPY_OR_MOVE, listener);
		}
		
		// Add shapes to container
		for (ShapeIcon shape : icons.keySet()) {
			shape.draw(container);
		}
		
		add(container);
	}
	
	public static Map<JLabel, ShapeIcon> getLabels() {
		return labels;
	}
	
	public static Map<ShapeIcon, String> getIcons() {
		return icons;
	}
}