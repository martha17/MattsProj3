package project3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class DrawPanelTwo extends JPanel {
	private final int PANEL_WIDTH = 850;
	private final int PANEL_HEIGHT = 375;
	private int grid_spacing = 10; // 10 pt spacing
	private static ArrayList<Line> lines = new ArrayList<Line>();
	private static ArrayList<ShapeIcon> shapes = new ArrayList<ShapeIcon>();
	private static JPanel backgroundPanel;
	private static Background background;
	private static CodeObserver observer;
	private static KeyActionListener keyListen;
	private static ShapeListener shapeListen;
	
	public DrawPanelTwo() {
		// Container that holds actual board
		JPanel container = new JPanel();
		container.setBorder(new LineBorder(Color.BLACK));
		container.setOpaque(true);
		container.setBackground(Color.WHITE);
		container.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		container.setLayout(new GridLayout(1, 1));
		
		// Margin settings
		setBorder(new EmptyBorder(0, 10, 10, 10));
		setPreferredSize(new Dimension(100, 400));
		
		// Create background
		background = new Background(PANEL_WIDTH, PANEL_HEIGHT, grid_spacing, new Color(0, 0, 0), new Color(255, 255, 255));
		backgroundPanel = background.getBG();
		container.add(backgroundPanel);

		// Handler for Drag and Drop operation
		TransferHandler transfer = DragNDropIcon.createDropHandler();
		observer = new CodeObserver();
		shapeListen = new ShapeListener(backgroundPanel);
		keyListen = new KeyActionListener(backgroundPanel);
		
		// Attach listeners for drag and drop operation and user operations
		setTransferHandler(transfer);
        attachListeners();
		
		add(container);
	}
	
	private static void attachListeners() {
		ShapeListener.updatePanel(backgroundPanel);
		backgroundPanel.addMouseListener(shapeListen);
		backgroundPanel.addMouseMotionListener(shapeListen);
		backgroundPanel.addKeyListener(keyListen);
		(new DragNDropIcon()).createDropListener(backgroundPanel, observer);
	}
	
	public static ArrayList<ArrayList> export() {
		ArrayList<ArrayList> export = new ArrayList<ArrayList>();
		export.add(lines);
		export.add(shapes);
		return export;
	}
	
	public static void load(ArrayList<ArrayList> objects) {
		backgroundPanel.removeAll();
		ArrayList<Line> tempLines = objects.get(0);
		ArrayList<ShapeIcon> tempShapes = objects.get(1);
		for (ShapeIcon shape : tempShapes) {
			shape.draw(backgroundPanel);
			shapes.add(shape);
		}
		JPanel linePanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.BLACK);
				// Loop through Line ArrayList and draw all the lines
				for (Line line : tempLines) {
					g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
					lines.add(line);
				}
			}
		};
		backgroundPanel.add(linePanel);
		backgroundPanel.revalidate();
		backgroundPanel.repaint();
	}
	
	public static MouseAdapter getShapeListener() {
		return shapeListen;
	}
	
	public static ArrayList<Line> getLines() {
		return lines;
	}
	
	public static ArrayList<ShapeIcon> getShapes() {
		return shapes;
	}
	
	public static void addShape(ShapeIcon shape) {
		shapes.add(shape);
	}
	
	public static void removeShape(ShapeIcon shape) {
		shapes.remove(shape);
	}
	
	public static void addLine(Line line) {
		lines.add(line);
	}
	
	public static void removeLine(Line line) {
		lines.remove(line);
	}
	
	public static CodeObserver getCodeObserver() {
		return observer;
	}
	
	public static Background getBG() {
		return background;
	}
	
	public static JPanel getBGPanel() {
		return backgroundPanel;
	}
	
	public static void setBGPanel(JPanel panel) {
		backgroundPanel = panel;
		attachListeners();
	}
}