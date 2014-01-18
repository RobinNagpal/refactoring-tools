package edu.pdx.cs.multiview.smelldetector.detectors.InstanceOf;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.InstanceofExpression;

import edu.pdx.cs.multiview.smelldetector.detectors.ClassSmellRating;

class ClassInstanceOfRating extends ClassSmellRating<MethodInstanceOfRating, InstanceofExpression>{

	protected MethodInstanceOfRating createMethodRating(IMethod m) {
		return new MethodInstanceOfRating(m);
	}

	@Override
	public double calculateMagnitude() {
		return 0;
	}
}