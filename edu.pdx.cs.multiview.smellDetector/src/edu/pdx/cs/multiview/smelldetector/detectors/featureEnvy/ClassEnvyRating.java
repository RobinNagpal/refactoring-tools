package edu.pdx.cs.multiview.smelldetector.detectors.featureEnvy;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.jdt.util.MemberReference;
import edu.pdx.cs.multiview.smelldetector.detectors.ClassSmellRating;

/**
 *	I represent a set of lazily initialized feature envy smells
 *	for an entire {@link ICompilationUnit}
 */
class ClassEnvyRating extends ClassSmellRating<MethodEnvyRating, MemberReference>{

	@Override
	protected MethodEnvyRating createMethodRating(IMethod m) {
		return new MethodEnvyRating(m);
	}

	@Override
	public double calculateMagnitude() {
		return 0;
	}
}