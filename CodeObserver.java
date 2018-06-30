package project3;

import java.util.*;

public class CodeObserver implements Observer {
	private String code = "";
	private ShapeIcon shape;
	private Line line;
	
	public CodeObserver() {}
	
	public void update(Observable o, Object arg) {
		String classType = "";
		String relationship = "";
		String content = "";
		int num = 0;
		if (o instanceof ShapeIcon) {
			shape = (ShapeIcon) o;
			num = DrawPanelTwo.getShapes().indexOf(shape) + 1;
			ShapeType type = shape.getShapeType();
			switch (type) {
				case CIRCLE:
					classType = "interface"; break;
				case SQUARE:
					classType = "class"; break;
				case STAR:
				case TRIANGLE:
					return ;
			}
			String newLine = String.format("public %s Icon%d %s{\n%s\n}\n", classType, num, relationship, content);
		
			if (arg.equals("add")) {
				code += newLine;
			} else if (arg.equals("remove")) {
				code = code.replace(newLine, "");
			}
			
		} else if (o instanceof Line) {
			line = (Line) o;
			ShapeIcon head = line.getHead();
			ShapeIcon tail = line.getTail();
			ShapeType[] shapeTypes = {head.getShapeType(), tail.getShapeType()};
			int[] shapeNums = {DrawPanelTwo.getShapes().indexOf(head) + 1, DrawPanelTwo.getShapes().indexOf(tail) + 1};
			LineType type = line.getLineType();
			//int numHead = DrawPanelTwo.getShapes().indexOf(head) + 1;
			//int numTail = DrawPanelTwo.getShapes().indexOf(tail) + 1;
			String newLine;
			
			String[] codeStrings = new String[2];
			for (int i = 0; i < shapeTypes.length; i++) {
				switch (shapeTypes[i]) {
					case CIRCLE:
						classType = "interface"; break;
					case SQUARE:
						classType = "class"; break;
					case STAR:
					case TRIANGLE:
						return ;
				}
				codeStrings[i] = String.format("public %s Icon%d %s{\n%s\n}\n", classType, shapeNums[i], relationship, content);
			}
			
			switch (type) {
				case AGGREGATION:
					if (shapeTypes[1] != ShapeType.CIRCLE && shapeTypes[0] != ShapeType.CIRCLE) {
						content = String.format("\tprivate Icon%d icon%d;", shapeNums[1], shapeNums[1]);
					}
					newLine = String.format("public %s Icon%d %s {\n%s\n}\n", classType, shapeNums[0], relationship, content);
					code = code.replace(codeStrings[0], newLine);
					break;
				case ASSOCIATION:
					if (shapeTypes[1] != ShapeType.CIRCLE && shapeTypes[0] != ShapeType.CIRCLE) {
						String subcontent = String.format("\t Icon%d icon%d;", shapeNums[1], shapeNums[1]);
						content = String.format("\t public Icon%d() {\n\t\t %s \n\t }", shapeNums[0], subcontent);
					}
					newLine = String.format("public %s Icon%d %s {\n%s\n}\n", classType, shapeNums[1], relationship, content);
					code = code.replace(codeStrings[0], newLine);
					
					break;
				case INHERITANCE:
					if (shapeTypes[0] == ShapeType.SQUARE && shapeTypes[1] == ShapeType.SQUARE) {
						relationship = String.format("extends Icon%d", shapeNums[1]);
					} else if (shapeTypes[1] == ShapeType.CIRCLE && shapeTypes[1] == ShapeType.SQUARE) {
						relationship = String.format("implements Icon%d", shapeNums[1]);
					}
					newLine = String.format("public %s Icon%d %s {\n%s\n}\n", classType, shapeNums[0], relationship, content);
					code = code.replace(codeStrings[0], newLine);
					System.out.println(codeStrings[0]);
					System.out.println(codeStrings[1]);
					System.out.println(newLine);
					break;
			}
		}
		
		CodeGeneratorPanel.updateCode(code);
	}
}