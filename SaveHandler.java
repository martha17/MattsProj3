package project3;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class SaveHandler {
	public static ActionListener createSave(JFrame frame) {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser saveDiag = new JFileChooser();
				int option = saveDiag.showSaveDialog(frame);
				
				if (option == JFileChooser.APPROVE_OPTION) {
					File save = saveDiag.getSelectedFile();
					if (save.getPath().endsWith(".drw")) {
						try {
							FileOutputStream fStream = new FileOutputStream(save);
							ObjectOutputStream oStream = new ObjectOutputStream(fStream);
							oStream.writeObject(DrawPanelTwo.export());
							oStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		
		return listener;
	}
	
	public static ActionListener createLoad(JFrame frame) {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser loadDiag = new JFileChooser();
				int option = loadDiag.showOpenDialog(frame);
				
				if (option == JFileChooser.APPROVE_OPTION) {
					File load = loadDiag.getSelectedFile();
					if (load.getPath().endsWith(".drw")) {
						try {
							FileInputStream fStream = new FileInputStream(load);
							ObjectInputStream oStream = new ObjectInputStream(fStream);
							try {
								ArrayList<ArrayList> temp = (ArrayList<ArrayList>) oStream.readObject();
								DrawPanelTwo.load(temp);
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
							oStream.close();
						} catch (IOException e) {}
					}
				}
			}
		};
		
		return listener;
	}
}