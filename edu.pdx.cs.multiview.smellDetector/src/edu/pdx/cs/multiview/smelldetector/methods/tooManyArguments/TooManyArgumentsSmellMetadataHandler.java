package edu.pdx.cs.multiview.smelldetector.methods.tooManyArguments;

import org.eclipse.jdt.core.IMethod;

import edu.pdx.cs.multiview.smelldetector.metadata.MethodSmellMetaDataHandler;

public class TooManyArgumentsSmellMetadataHandler implements MethodSmellMetaDataHandler<Integer>{


	@Override
	public void saveSmellMetadata(IMethod method) {

	}

	@Override
	public Integer getSmellMetadata(String className, String methodName) {
	
		return null;
	}

}
