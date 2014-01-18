package edu.pdx.cs.multiview.smelldetector.methods.tooManyArguments;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.smelldetector.detectors.SmellInstance;

class TooManyArgumentsSmellInstance implements SmellInstance {
	private static final int MAXIMUM_CODE_SMELL_VALUE = 1;
	private static final int LEVELS_OF_SMELL = 3;
	private static final int TWO_NUMBER_OF_ARGUMENTS = 2;
	private Map<IMethod, Integer> methodToNumberOfArguments = new HashMap<IMethod, Integer>();
	private TooManyArgumentsSmellMetadataHandler metadataHandler;
	

	public TooManyArgumentsSmellInstance(List<IMethod> visibleMethods, TooManyArgumentsSmellMetadataHandler metadataHandler) {
		this.metadataHandler = metadataHandler;
		for (IMethod m : visibleMethods) {
				getMethodToNumberOfArguments().put(m, numberOfArguments(m));
		}

	}


	public int size() {
		return getMethodToNumberOfArguments().size();
	}


	/**
	 * 
	 * Two arguments are considered fine. But as the number of arguments increases more that two, it can be
	 * considered as a sign of code smell.  So, we divided the smells into three levels 
	 * 1) number of arguments <= 2  : no smell
	 * 2) number of arguments == 3  : can be a code smell, but can be a valid scenarios as well
	 * 3) number of arguments == 4  : is a code smell 
	 * 4) number of arguments >= 5  : prominent code smell
	 *  
	 */
	@Override
	public double magnitude() {
		double numberOfArgumentsExceedingThresholdOfTwo = 0;
		for (IMethod m : getMethodToNumberOfArguments().keySet()) {
			numberOfArgumentsExceedingThresholdOfTwo = Math.max((numberOfArguments(m)-TWO_NUMBER_OF_ARGUMENTS), numberOfArgumentsExceedingThresholdOfTwo);
		}

		double normalizedSeverity = numberOfArgumentsExceedingThresholdOfTwo / LEVELS_OF_SMELL;
		double severityValue = normalizedSeverity > MAXIMUM_CODE_SMELL_VALUE ? MAXIMUM_CODE_SMELL_VALUE : normalizedSeverity;
		return severityValue ;
	}

	

	public Map<IMethod, Integer> getMethodToNumberOfArguments() {
		return methodToNumberOfArguments;
	}
	
	public Integer getMaxNumberOfArguments(){
		Collection<Integer> values = getMethodToNumberOfArguments().values();
		return Collections.max(values);
	}

	private int numberOfArguments(IMethod m) {
		return m.getNumberOfParameters();
	}


}