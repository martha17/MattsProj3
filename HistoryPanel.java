/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class HistoryPanel extends JPanel {
	
	private final int PANEL_WIDTH = 110;
	private final int PANEL_HEIGHT = 850;

	private static String FILENAME = "history.xml";
	
	private static final Logger logger = setUpLogger();
	private static FileHandler fileHandler;
	
	private static ArrayList<String> actions;
	
	private static JPanel container = new JPanel();
	
	public HistoryPanel() {
		// Container that holds the actual shapes
		container.setBorder(new LineBorder(Color.BLACK));
		container.setOpaque(true);
		container.setBackground(Color.WHITE);
		container.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		// Margin settings
		setBorder(new EmptyBorder(10, 10, 0, 10));
		setPreferredSize(new Dimension(100, 130));
		
		add(container);
	}
	
	public static Logger setUpLogger() {
		Logger logger = Logger.getLogger(HistoryPanel.class.getName());
		logger.setUseParentHandlers(false);
		try {
			fileHandler = new FileHandler(FILENAME);
			logger.addHandler(fileHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logger;
	}
	
	public static void logAction(String content) {
		LogRecord record = new LogRecord(Level.INFO, content);
		logger.log(record);
		parseHistoryFile();
		updatePanel();
	}
	
	private static void parseHistoryFile() {
		actions = new ArrayList<String>();
		try{
			FileReader fr = new FileReader(FILENAME);
			BufferedReader br = new BufferedReader(fr);
			String currentLine;
			
			while((currentLine = br.readLine()) != null){
				if (currentLine.contains("message")) {
					String action = currentLine.replace("  <message>", "");
					action = action.replace("</message>", "");
					actions.add(action);
				}
			}
		}
		catch (Exception e) {}
	}
	
	private static void updatePanel() {
		JPanel historyPanel = new JPanel();
		for (String action : actions) {
			JLabel label = new JLabel(action);
			historyPanel.add(label);
		}
		container.removeAll();
		container.add(historyPanel);
		container.revalidate();
		container.repaint();
	}
}