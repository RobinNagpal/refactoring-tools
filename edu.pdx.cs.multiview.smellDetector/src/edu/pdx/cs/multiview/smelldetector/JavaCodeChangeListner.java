package edu.pdx.cs.multiview.smelldetector;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;

public class JavaCodeChangeListner implements IElementChangedListener {
	public void elementChanged(ElementChangedEvent event) {
		IJavaElementDelta delta = event.getDelta();
		if (delta != null) {
			if(delta.getElement().getElementType() == IJavaElement.COMPILATION_UNIT){
			System.out.println("delta received: ");
			System.out.println(" Compilation unit name " +delta.getElement().getPath().toString() + delta.getElement().getElementName());

			System.out.print(delta);
			}
			
		}
	}
}