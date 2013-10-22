package edu.pdx.cs.multiview.draw2d;

import java.util.List;

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.FreeformListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Publicized copy of class of same name in:
 * 
 * package org.eclipse.draw2d;
 */
public class FreeformHelper
	implements FreeformListener
{

class ChildTracker implements FigureListener {
	public void figureMoved(IFigure source) {
		invalidate();
	}
}

private FreeformFigure host;
private Rectangle freeformExtent;
private FigureListener figureListener = new ChildTracker();

public FreeformHelper(FreeformFigure host) {
	this.host = host;
}

public Rectangle getFreeformExtent() {
	if (freeformExtent != null)
		return freeformExtent;
	Rectangle r;
	List children = host.getChildren();
	for (int i = 0; i < children.size(); i++) {
		IFigure child = (IFigure)children.get(i);
		if (child instanceof FreeformFigure)
			r = ((FreeformFigure) child).getFreeformExtent();
		else
			r = child.getBounds();
		if (freeformExtent == null)
			freeformExtent = r.getCopy();
		else
			freeformExtent.union(r);
	}
	Insets insets = host.getInsets();
	if (freeformExtent == null)
		freeformExtent = new Rectangle(0, 0, insets.getWidth(), insets.getHeight());
	else {
		host.translateToParent(freeformExtent);
		freeformExtent.expand(insets);
	}
//	System.out.println("New extent calculated for " + host + " = " + freeformExtent);
	return freeformExtent;
}

public void hookChild(IFigure child) {
	invalidate();
	if (child instanceof FreeformFigure)
		((FreeformFigure)child).addFreeformListener(this);
	else
		child.addFigureListener(figureListener);
}

void invalidate() {
	freeformExtent = null;
	host.fireExtentChanged();
	if (host.getParent() != null)
		host.getParent().revalidate();
	else
		host.revalidate();
}

public void notifyFreeformExtentChanged() {
	//A childs freeform extent has changed, therefore this extent must be recalculated
	invalidate();
}

public void setFreeformBounds(Rectangle bounds) {
	host.setBounds(bounds);
	bounds = bounds.getCopy();
	host.translateFromParent(bounds);
	List children = host.getChildren();
	for (int i = 0; i < children.size(); i++) {
		IFigure child = (IFigure)children.get(i);
		if (child instanceof FreeformFigure)
			((FreeformFigure) child).setFreeformBounds(bounds);
	}
}

public void unhookChild(IFigure child) {
	invalidate();
	if (child instanceof FreeformFigure)
		((FreeformFigure)child).removeFreeformListener(this);
	else
		child.removeFigureListener(figureListener);
}

}
