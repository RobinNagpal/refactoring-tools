package edu.pdx.cs.multiview.smelldetector.detectors.InstanceOf;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.smelldetector.detectors.SmellInstance;

class InstanceOfInstance extends ClassInstanceOfRating implements SmellInstance{
	
	private Collection<IMethod> visibleMethods;
	
	public InstanceOfInstance(ClassInstanceOfRating rating, Collection<IMethod> visibleMethods){
		super.rs = rating.rs;
		this.visibleMethods = visibleMethods;
	}

	public double calculateMagnitude() {
		int instOfExprCount = 0;
		for (MethodInstanceOfRating methodRating : ratings()) {
			instOfExprCount += methodRating.smells().size();
		}

		//we add 0.5 because 1 instanceof is worth pointing out
		//we multiply by 0.5 to extend the curve: about 7 or 8 
		//			instanceof expressions produces maximum wedge size
		double magnitude = 0.5 * Math.log(instOfExprCount+0.5);
		return instOfExprCount/3;
	}

	@Override
	protected Collection<MethodInstanceOfRating> ratings() {
		
		List<MethodInstanceOfRating> ratings = new LinkedList<MethodInstanceOfRating>();
		//collect values on key predicates
		for(Map.Entry<IMethod, MethodInstanceOfRating> r : rs.entrySet())
			if(visibleMethods.contains(r.getKey()))
				ratings.add(r.getValue());
		
		return ratings;
	}
}