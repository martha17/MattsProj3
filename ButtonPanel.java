package project3;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class ButtonPanel extends JPanel {
	private final int PANEL_WIDTH = 1240;
	private final int PANEL_HEIGHT = 175;
	private static ArrayList<AbstractButton> buttons;
	private ButtonListener buttonListener;
	
	public ButtonPanel() {
		// Margin settings
		setBorder(new EmptyBorder(0, 10, 10, 10));
		setPreferredSize(new Dimension(100, 200));
		
		buttons = new ArrayList<AbstractButton>();
		createButtons();
		buttonListener = new ButtonListener();
		for (AbstractButton button : buttons) {
			button.addActionListener(buttonListener);
		}
		
		JTabbedPane pane = createTabbedPane();
		
		add(pane);
	}
	
	private void createButtons() {
		JButton rotateCWButton = new JButton("Rotate Clockwise");
		JButton rotateCCCWButton = new JButton("Rotate Counter Clockwise");
		JButton fillColorButton = new JButton("Change Fill Color");
		JButton lineColorButton = new JButton("Change Line Color");
		JButton flipVerticalButton = new JButton("Flip Vertically");
		JButton flipHorizontalButton = new JButton("Flip Horizontally");
		JButton gridSpacingButton = new JButton("Change Grid Spacing");
		JButton gridLineColorButton = new JButton("Change Gridline Color");
		JButton gridBGColorButton = new JButton("Change Grid Background Color");
		JButton clearButton = new JButton("Clear Artboard");
		JButton drawLineButton = new JButton("Draw Line");
		JButton changeLineTypeButton = new JButton("Change Type");
		//JButton testButton = new JButton("test");
		
		buttons.add(rotateCWButton);
		buttons.add(rotateCCCWButton);
		buttons.add(fillColorButton);
		buttons.add(lineColorButton);
		buttons.add(flipVerticalButton);
		buttons.add(flipHorizontalButton);
		buttons.add(gridSpacingButton);
		buttons.add(gridLineColorButton);
		buttons.add(gridBGColorButton);
		buttons.add(clearButton);
		buttons.add(drawLineButton);
		buttons.add(changeLineTypeButton);
		//buttons.add(testButton);
	}
	
	private JTabbedPane createTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		JPanel shapePanel = new JPanel();
		JPanel backgroundPanel = new JPanel();
		JPanel linePanel = new JPanel();
		
		for (AbstractButton button : buttons) {
			switch (button.getText()) {
				case "Rotate Clockwise":
				case "Rotate Counter Clockwise":
				case "Change Fill Color":
				case "Change Line Color":
				case "Flip Vertically":
				case "Flip Horizontally":
					shapePanel.add(button); break;
				case "Change Grid Spacing":
				case "Change Gridline Color":
				case "Change Grid Background Color":
				case "Clear Artboard":
					backgroundPanel.add(button); break;
				case "Draw Line":
				case "Change Type":
				case "test":
					linePanel.add(button); break;
			}
		}
		
		shapePanel.setOpaque(true);
		shapePanel.setBackground(Color.WHITE);
		
		backgroundPanel.setOpaque(true);
		backgroundPanel.setBackground(Color.WHITE);
		
		linePanel.setOpaque(true);
		linePanel.setBackground(Color.WHITE);
		
		pane.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		pane.insertTab("Shape", null, shapePanel, "", 0);
		pane.insertTab("Background", null, backgroundPanel, "", 1);
		pane.insertTab("Line", null, linePanel, "", 2);
		
		return pane;
	}
	
	public static ArrayList<AbstractButton> getButtons() {
		return buttons;
	}
}