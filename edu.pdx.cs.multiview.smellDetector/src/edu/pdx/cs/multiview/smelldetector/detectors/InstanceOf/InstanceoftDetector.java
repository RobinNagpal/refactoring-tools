package edu.pdx.cs.multiview.smelldetector.detectors.InstanceOf;

import java.util.List;

import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.smelldetector.detectors.SmellDetector;
import edu.pdx.cs.multiview.smelldetector.ui.Flower;

public class InstanceoftDetector extends SmellDetector<InstanceOfInstance>{

	private ClassInstanceOfRating ratings = new ClassInstanceOfRating();
	
	public InstanceoftDetector(Flower f) {	super(f);	}
	
	@Override
	public double obviousness() {	return 0.945;		}
	
	@Override
	public InstanceOfInstance calculateComplexity(List<IMethod> visibleMethods) {
		
		for (IMethod m : visibleMethods)
			ratings.updateRatingForMethod(m);

		return new InstanceOfInstance(ratings,visibleMethods);
	}


	@Override
	public String getName() {
		return "Instance Of";
	}
	

	
	@Override
	public void showDetails() {
		//new FeatureEnvyExplanationWindow(currentSmell(),sourceViewer());
	}

}