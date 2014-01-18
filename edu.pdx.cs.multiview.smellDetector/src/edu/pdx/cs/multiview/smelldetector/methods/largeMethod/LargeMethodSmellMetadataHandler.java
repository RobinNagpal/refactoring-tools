package edu.pdx.cs.multiview.smelldetector.methods.largeMethod;

import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.smelldetector.metadata.MethodSmellMetaDataHandler;

public class LargeMethodSmellMetadataHandler implements MethodSmellMetaDataHandler<Integer> {

	@Override
	public void saveSmellMetadata(IMethod method) {
		
	}

	@Override
	public Integer getSmellMetadata(String className, String methodName) {

		return null;
	}

}
