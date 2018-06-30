/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.*;
import java.io.*;

public class Background {
	private int width;
	private int height;
	private int pt;
	public static int GRID = 0;
	public static int IMAGE = 1;
	private JPanel background;
	private Color lineColor;
	private Color bgColor;
	
	public Background(int width, int height, int pt, Color lineColor, Color bgColor) {
		this.width = width;
		this.height = height;
		this.pt = pt;
		this.lineColor = lineColor;
		this.bgColor = bgColor;
		background = createGrid(pt, lineColor, bgColor);
	}
	
	public Background(int width, int height, String fileName) {
		this.width = width;
		this.height = height;
		background = createImageBackground(fileName);
	}
	
	public JPanel createGrid(int pt, Color lineColor, Color bgColor) {
		JPanel grid = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Draw background color
				g.setColor(bgColor);
				g.fillRect(1, 1, width - 2, height - 2); // Ensures panel border doesn't get colored over
				
				// Draw grid lines
				int k = 0; // Keeps track of bolder/darker lines
				int i = 0; // Horizontal lines
				int j = 0; // Vertical lines
				while (i < width) {
					g.setColor(new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), 10));
					if ((k % 4) == 0) {
						g.setColor(new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), 30));
					}
					g.drawLine(i, 0, i, height);
					i += (pt * 96) / 76; // Converts pt to px (points to pixels)
					k++;
				}
				while (j < height) {
					g.setColor(new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), 10));
					if ((k % 4) == 0) {
						g.setColor(new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), 30));
					}
					g.drawLine(0, j, width, j);
					j += (pt * 96) / 76; // Converts pt to px (points to pixels)
					k++;
				}
			}
		};
		grid.setOpaque(false);
		grid.setLayout(null);
		grid.setFocusable(true);
		grid.requestFocus();
		return grid;
	}
	
	public void changeGridlineColor(Color color) {
		lineColor = color;
		JPanel newBG = createGrid(pt, color, bgColor);
		update(background.getParent(), newBG);
		background.requestFocus();
	}
	
	public void changeGridBGColor(Color color) {
		bgColor = color;
		JPanel newBG = createGrid(pt, lineColor, color);
		update(background.getParent(), newBG);
		background.requestFocus();
	}
	
	public void changeGridSpacing(int spacing) {
		pt = spacing;
		JPanel newBG = createGrid(spacing, lineColor, bgColor);
		update(background.getParent(), newBG);
		background.requestFocus();
	}
	
	public void update(Container parent, JPanel newBG) {
		// Transfer components to new Panel
		for (Component c : DrawPanelTwo.getBGPanel().getComponents()) {
			newBG.add(c);
		}
		// Remove old panel and set references to new panel
		parent.remove(DrawPanelTwo.getBGPanel());
		DrawPanelTwo.setBGPanel(newBG);
		background = newBG;
		// Add new panel and repaint everything
		parent.add(background);
		for (Component c : background.getComponents()) {
			c.revalidate();
			c.repaint();
		}
		parent.revalidate();
		parent.repaint();
	}

	public JPanel createImageBackground(String fileName) {
		JPanel bg = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File(fileName));
				} catch (IOException e) {
					System.out.println("File not found");
				}
				
				if (image != null) {
					g.drawImage(image, 0, 0, width, height, null);
				}
			}
		};
		bg.setOpaque(false);
		bg.setLayout(null);
		bg.setFocusable(true);
		bg.requestFocus();
		return bg;
	}
	
	public void clear() {
		Map<JLabel, ShapeIcon> shapeList = ShapeIcon.getShapeList();
		for (Component c : background.getComponents()) {
			if (c instanceof JLabel) {
				ShapeIcon shape = shapeList.get(c);
				if (shape != null) {
					shape.remove(background);
				}
			}
		}
		background.revalidate();
		background.repaint();
	}
	
	public JPanel getBG() {
		return background;
	}
	
	public Color getBGColor() {
		return bgColor;
	}
	
	public Color getLineColor() {
		return lineColor;
	}
}
