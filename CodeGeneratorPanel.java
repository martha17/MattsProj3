/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class CodeGeneratorPanel extends JPanel {
	private final int PANEL_WIDTH = 350;
	private final int PANEL_HEIGHT = 500;
	private static JTextArea text;
	
	public CodeGeneratorPanel() {
		// Margin settings
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(400, 550));
		
		JScrollPane display = createCodeDisplay();
		display.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		display.setBorder(new LineBorder(Color.BLACK));

		add(display);
	}
	
	private JScrollPane createCodeDisplay() {
		text = new JTextArea("", 30, 30);
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		text.setTabSize(4);
		return scroll;
	}
	
	public static void updateCode(String code) {
		text.setText(code);
		text.revalidate();
		text.repaint();
	}
}