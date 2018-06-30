/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;

public class DrawFrame extends JFrame {
	
	public DrawFrame() {
		// Frame Settings
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1350, 800);
		
		// Create menubar
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		
		JMenu menuFile = new JMenu("File");
		menu.add(menuFile);
		
		JMenuItem fileSave = new JMenuItem("Save");
		fileSave.addActionListener(SaveHandler.createSave(this));
		menuFile.add(fileSave);
		
		JMenuItem fileLoad = new JMenuItem("Load");
		fileLoad.addActionListener(SaveHandler.createLoad(this));
		menuFile.add(fileLoad);
	}
	
	public static void main(String[] args) {
		// Create frame object and set layout
		DrawFrame frame = new DrawFrame();
		
		// Create panel objects
		DrawPanelOne shapePanel = new DrawPanelOne();
		DrawPanelTwo workspacePanel = new DrawPanelTwo();
		//HistoryPanel historyPanel = new HistoryPanel();
		CodeGeneratorPanel codePanel = new CodeGeneratorPanel();
		ButtonPanel buttonPanel = new ButtonPanel();
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(shapePanel, BorderLayout.PAGE_START);
		centerPanel.add(workspacePanel, BorderLayout.PAGE_END);
		centerPanel.setPreferredSize(new Dimension(850, 300));
		
		// Add panels to frame
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(codePanel, BorderLayout.EAST);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
}