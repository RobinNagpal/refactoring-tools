package edu.pdx.cs.multiview.smelldetector.methods.tooManyArguments;

import java.util.List;

import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.smelldetector.detectors.SmellDetector;
import edu.pdx.cs.multiview.smelldetector.ui.Flower;

/**
 * Adding more arguments decreases the readability of the code and it can be an indirect sign of low cohesion.
 * One can use "Introduce Parameter Object" refactoring to avoid this code smell in some situations
 * 
 * Below are Uncle Bob's views about it:
 * "Functions should have a small number of arguments. No argument is best,followed by one, two, and three. 
 *  More than three is very questionable and should be avoided with prejudice."
 *  	
 * @author robin
 *
 */
public class TooManyArgumentsDetector extends SmellDetector<TooManyArgumentsSmellInstance> {

	public static final String TOO_MANY_ARGUMENTS_LABEL_TEXT = "Too Many Arguments";
	private static final double HIGHLY_OBVIOUS = 0.95;

	public TooManyArgumentsDetector(Flower f) {
		super(f);
	}

	/**
	 *  We think that code smell related to number of arguments is quite obvious as number of arguments can be easily counted. 
	 *  However, we think that its obviousness is less that large methods and large class as its generally neglected by the developers. 
	 *  
	 */
	@Override
	public double obviousness() {
		return HIGHLY_OBVIOUS;
	}

	@Override
	public TooManyArgumentsSmellInstance calculateComplexity(List<IMethod> visibleMethods) {
		TooManyArgumentsSmellInstance inst = new TooManyArgumentsSmellInstance();
		for (IMethod m : visibleMethods) {
			inst.put(m, inst.numberOfArguments(m));
		}
		return inst;
	}

	@Override
	public void showDetails() {
		new TooManyArgumentsExplanationWindow(currentSmell(), sourceViewer());
	}

	@Override
	public String getName() {
		return TOO_MANY_ARGUMENTS_LABEL_TEXT;
	}
}