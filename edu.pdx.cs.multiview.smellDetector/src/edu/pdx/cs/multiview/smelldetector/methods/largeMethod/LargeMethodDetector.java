package edu.pdx.cs.multiview.smelldetector.methods.largeMethod;

import java.util.List;

import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.smelldetector.detectors.SmellDetector;
import edu.pdx.cs.multiview.smelldetector.metadata.SmellMetadataHandler;
import edu.pdx.cs.multiview.smelldetector.ui.Flower;

public class LargeMethodDetector extends SmellDetector<LargeMethodSmellInstance> {

	public LargeMethodDetector(Flower f) {
		super(f);
	}

	@Override
	public double obviousness() {
		return 0.96;
	}

	@Override
	public LargeMethodSmellInstance calculateComplexity(List<IMethod> visibleMethods) {

		LargeMethodSmellInstance inst = new LargeMethodSmellInstance(visibleMethods);

		return inst;
	}

	@Override
	public String getName() {
		return "Large Method";
	}

	public void showDetails() {
		new LargeMethodExplanationWindow(currentSmell(), sourceViewer());
	}

	@Override
	public SmellMetadataHandler<?> getSmellMetadataHandler() {
		// TODO Auto-generated method stub
		return null;
	}
}
