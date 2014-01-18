package edu.pdx.cs.multiview.smelldetector.detectors.featureEnvy;

import java.util.List;

import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.smelldetector.detectors.SmellDetector;
import edu.pdx.cs.multiview.smelldetector.ui.Flower;

public class FeatureEnvyDetector extends SmellDetector<EnvyInstance>{

	private ClassEnvyRating ratings = new ClassEnvyRating();
	
	public FeatureEnvyDetector(Flower f) {
		super(f);
	}

	@Override
	public double obviousness() {	return 0.5;		}

	@Override
	public EnvyInstance calculateComplexity(List<IMethod> visibleMethods) {
		for (IMethod m : visibleMethods)
			ratings.cache(m);

		return new EnvyInstance(ratings,visibleMethods);
	}
	
	/*
	 * for debugging purposes only - clears the cache
	 */
	@SuppressWarnings("unused")
	private void clear(){
		ratings.rs.clear();
	}


	@Override
	public String getName() {
		return "Feature Envy";
	}
	

	
	@Override
	public void showDetails() {
		new FeatureEnvyExplanationWindow(currentSmell(),sourceViewer());
	}

}