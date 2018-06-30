package project3;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class DragNDropIcon {
	private MouseAdapter shapeListen = DrawPanelTwo.getShapeListener();
	
	public static TransferHandler createDropHandler() {
		TransferHandler transfer = new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
				// Check if the operation is a drop and if it is an image or string
                if (!support.isDrop() || !support.isDataFlavorSupported(DataFlavor.imageFlavor) || !support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean importData(TransferSupport support) {
				// Checks if can import
                if (!canImport(support)) {
                    return false;
                }
				// Transfers icon
                Transferable transferable = support.getTransferable();
				Icon icon;
				String type;
				try {
					icon = (Icon) transferable.getTransferData(DataFlavor.imageFlavor);
					type = (String) transferable.getTransferData(DataFlavor.stringFlavor);
				} catch (Exception e) {
					return false;
				}
				if (icon == null || type == null) {
					return false;
				}
                return true;
            }
        };
		
		return transfer;
	}
	
	public static DragGestureListener createDragListener() {
		DragGestureListener listener = new DragGestureListener() {
			@Override
			public void dragGestureRecognized(DragGestureEvent dge) {
				// Get the shape and its icon
				JLabel shape = (JLabel) dge.getComponent();
				final Icon icon = shape.getIcon();
				String assignedType = "";
				for (JLabel label : DrawPanelOne.getLabels().keySet()) {
					if (shape == label) {
						ShapeIcon s = DrawPanelOne.getLabels().get(label);
						assignedType = DrawPanelOne.getIcons().get(s);
						break;
					}
				}
				final String type = assignedType;
				
				// Make Transferable object to transfer image to another panel
				Transferable transferable = new Transferable() {
					@Override
					public DataFlavor[] getTransferDataFlavors() {
						return new DataFlavor[]{DataFlavor.imageFlavor, DataFlavor.stringFlavor};
					}

					@Override
					public boolean isDataFlavorSupported(DataFlavor flavor) {
						if (!isDataFlavorSupported(flavor)) {
							return false;
						}
						return true;
					}

					@Override
					public Object getTransferData(DataFlavor flavor) {
						if (flavor == DataFlavor.imageFlavor ) {
							return icon;
						} else if (flavor == DataFlavor.stringFlavor) {
							return type;
						}
						return null;
					}
				};
				dge.startDrag(null, transferable);
			}
		};
		return listener;
	}
	
	public DropListener createDropListener(JPanel p, Observer o) {
		return new DropListener(p, o);
	}
	
	class DropListener extends DropTargetAdapter {
		private DropTarget dropTarget;
		private JPanel p;
		private Observer o;
		
		// Sets board panel as a drop target which means objects can be dropped into it
		public DropListener(JPanel panel, Observer observer) {
			p = panel;
			o = observer;
			dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
		}

		@Override
		public void drop(DropTargetDropEvent event) {
			// Get component to drop into, its location, and the icon
			DropTarget target = (DropTarget) event.getSource();
			Component ca = (Component) target.getComponent();
			Point dropPoint = ca.getMousePosition();
			Transferable tr = event.getTransferable();
			Icon icon;
			String type;
			try {
				icon = (Icon) tr.getTransferData(DataFlavor.imageFlavor);
				type = (String) tr.getTransferData(DataFlavor.stringFlavor);
				// Create a new JLabel to transfer the object onto the panel
				if (icon != null) {
					ShapeIcon shape = new ShapeIcon(icon, dropPoint, type);
					JLabel image = shape.getShape();
					image.setSize(image.getPreferredSize());
					image.addMouseListener(shapeListen);
					image.addMouseMotionListener(shapeListen);
					shape.addObserver(o);
					shape.draw(p);
					p.revalidate();
					p.repaint();
					
					event.dropComplete(true);
					
					HistoryPanel.logAction("Added a shape");
				}
				
			} catch (Exception e) {
				event.rejectDrop();
			}
		}
		
	}
}