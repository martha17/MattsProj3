package project3;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import static java.lang.Math.*;

enum LineType {
	AGGREGATION,
	INHERITANCE,
	ASSOCIATION;
}

public class Line extends Observable {
	private int x1, x2, y1, y2;
	private final double PHI = Math.toRadians(40);
	private final int BARB = 20;
	private double slope;
	private boolean selected;
	private ShapeIcon head, tail;
	private LineType lineType;
	private JLabel lineLabel;
	private static Map<JLabel, Line> lineList;
	private Shape lineGraphic;
	
	public Line() {
		lineLabel = new JLabel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.BLACK);
				Graphics2D g2d = (Graphics2D) g.create();
				
				if (selected) {
					g2d.setStroke(new BasicStroke(3));
					g.setColor(Color.BLUE);
				}
			}
		};
		lineLabel.setBounds(0, 0, 850, 375);
		lineLabel.setOpaque(false);
		
		startLineList();
		
		JPanel p = DrawPanelTwo.getBG().getBG();
		p.add(lineLabel);
		p.revalidate();
		p.repaint();
	}
	
	public Line(ShapeIcon head, ShapeIcon tail, String type) {
		this();
		this.head = head;
		this.tail = tail;
		switch (type) {
			case "association":
				lineType = LineType.ASSOCIATION; break;
			case "aggregation":
				lineType = LineType.AGGREGATION; break;
			case "inheritance":
				lineType = LineType.INHERITANCE; break;
		}
	}
	
	public Line(int x1, int y1, int x2, int y2, ShapeIcon head, ShapeIcon tail, String type) {
		this(head, tail, type);
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		calculateSlope();
	}
	
	public Line(Point beginning, Point end, ShapeIcon head, ShapeIcon tail, String type) {
		this(head, tail, type);
		this.x1 = (int) beginning.getX();
		this.x2 = (int) end.getX();
		this.y1 = (int) beginning.getY();
		this.y2 = (int) end.getY();
		calculateSlope();
	}
	
	public Line(ShapeIcon head, ShapeIcon tail, JPanel source, String type) {
		this(head, tail, type);
		Point headPoint = SwingUtilities.convertPoint((Component) head.getShape(), head.getShape().getWidth() / 2, head.getShape().getHeight() / 2, source);
		Point tailPoint = SwingUtilities.convertPoint((Component) tail.getShape(), tail.getShape().getWidth() / 2, tail.getShape().getHeight() / 2, source);
		this.x1 = (int) headPoint.getX();
		this.x2 = (int) tailPoint.getX();
		this.y1 = (int) headPoint.getY();
		this.y2 = (int) tailPoint.getY();
		calculateSlope();
	}
	
	public void draw() {
		// Get Graphics object and set its settings
		BufferedImage img = new BufferedImage(850, 375, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Get Points to draw line and set lines
        Point sw = new Point(x1, y1);
        Point ne = new Point(x2, y2);
        g2.setColor(Color.BLACK);
		
		// Draw line
		lineGraphic = new Line2D.Double(sw, ne);
        g2.draw(lineGraphic);
        switch (lineType) {
			case ASSOCIATION:
				drawArrow(g2, ne, sw, Color.black); break;
			case INHERITANCE:
				drawArrowHead(g2, ne, sw, Color.black); break;
			case AGGREGATION:
				drawDiamond(g2,sw,ne,Color.black); break;
		}
		g2.drawImage(img, 0, 0, null);
		
		// Attach image to label
		lineLabel.setIcon(new ImageIcon(img));
		
		// Add line to containers for referencing later
		lineList.put(lineLabel, this);
		
		lineLabel.revalidate();
		lineLabel.repaint();
		
		// Notify Observers
		setChanged();
		notifyObservers();
    }
	
	//Association
    private void drawArrow(Graphics2D g2, Point tip, Point tail, Color color){
        g2.setPaint(color);
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + PHI;
        for(int j = 0; j < 2; j++){
            x = tip.x - BARB * Math.cos(rho);
            y = tip.y - BARB * Math.sin(rho);
            g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
            rho = theta - PHI;
        }
    }
    //Inheritance
    private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color){
        g2.setPaint(color);
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + PHI;
        double p1x = 0, p1y = 0, p2x = 0, p2y = 0;
        for(int j = 0; j < 2; j++){  
            if(j==0){
                x = tip.x - BARB * Math.cos(rho);
                y = tip.y - BARB * Math.sin(rho);
                p1x=x;
                p1y=y;
                rho = theta - PHI;
            }
            
            if(j==1){
                x = tip.x - BARB * Math.cos(rho);
                y = tip.y - BARB * Math.sin(rho);
                p2x=x;
                p2y=y;
                rho = theta - PHI;
            }
        }
        g2.setColor(Color.WHITE);
        g2.fillPolygon(new int[] {(int)p1x, (int)p2x, (int)tip.x}, new int[] {(int)p1y, (int)p2y, (int)tip.y},3);
        g2.setColor(color);
        g2.drawPolygon(new int[] {(int)p1x, (int)p2x, (int)tip.x}, new int[] {(int)p1y, (int)p2y, (int)tip.y},3);
    }
    
    private void drawDiamond(Graphics2D g2, Point tip, Point tail, Color color){
        g2.setPaint(color);
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + PHI;
        double p1x = 0, p1y = 0, p2x = 0, p2y = 0;
        double p3x =0, p3y = 0;
        for(int j = 0; j < 2; j++){  
            if(j==0){
                x = tip.x - BARB * Math.cos(rho);
                y = tip.y - BARB * Math.sin(rho);
                p1x=x;
                p1y=y;
                rho = theta - PHI;
            }
            
            if(j==1){
                x = tip.x - BARB * Math.cos(rho);
                y = tip.y - BARB * Math.sin(rho);
                p2x=x;
                p2y=y;
                rho = theta - PHI;
            }
        }

        double d=Math.sqrt(Math.pow(((double) tail.x-tip.x),2.0)+Math.pow((double) tail.y-tip.y,2.0));
        double t=30.2/d;
        p3x=((1-t)*tip.x+t*tail.x);
        p3y=((1-t)*tip.y+t*tail.y);
        
        g2.setColor(Color.WHITE);
        g2.fillPolygon(new int[] {(int)p1x, (int)tip.x, (int)p2x, (int)p3x}, new int[] {(int)p1y, (int)tip.y, (int)p2y, (int)p3y},4);
        g2.setColor(color);
        g2.drawPolygon(new int[] {(int)p1x, (int)tip.x, (int)p2x, (int)p3x}, new int[] {(int)p1y, (int)tip.y, (int)p2y, (int)p3y},4);
    }
	
	public static boolean containsPoint(Line l, Point p){
        double d=sqrt(pow((l.x2-l.x1),2)+pow(l.y2-l.y1,2));
        double dt=.000000001;
        while(dt<=d){
            double t=dt/d;
            double x=((1-t)*l.x1+t*l.x2);
            double y=((1-t)*l.y1+t*l.y2);
            if(p.x<x+1&&p.x>x-1){
                if(p.y<y+1&&p.y>y-1){
                    return true;
                }
            }
            dt=dt+.1;
        }
        return false;
    }
	
	public static Map<JLabel, Line> getLineList() {
		return lineList;
	}
	
	public static Line findLine(JLabel label) {
		startLineList();
		for (JLabel l : lineList.keySet()) {
			if (l == label) {
				return lineList.get(label);
			}
		}
		return null;
	}
	
	private static void startLineList() {
		if (lineList == null) {
			lineList = new HashMap<JLabel, Line>();
		}
	}
	
	public JLabel getLineLabel() {
		return lineLabel;
	}
	
	public void calculateSlope() {
		if ((x2 - x1) == 0) {
			slope = 0;
		} else {
			slope = (y2 - y1) / (x2 - x1);
		}
	}
	
	public int getX1() {
		return x1;
	}
	
	public int getX2() {
		return x2;
	}
	
	public int getY1() {
		return y1;
	}
	
	public int getY2() {
		return y2;
	}
	
	public ShapeIcon getHead() {
		return head;
	}
	
	public ShapeIcon getTail() {
		return tail;
	}
	
	public void setBeginning(Point point) {
		x1 = (int) point.getX();
		y1 = (int) point.getY();
	}
	
	public void setEnd(Point point) {
		x2 = (int) point.getX();
		y2 = (int) point.getY();
	}
	
	public double getSlope() {
		return slope;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public LineType getLineType() {
		return lineType;
	}
	
}