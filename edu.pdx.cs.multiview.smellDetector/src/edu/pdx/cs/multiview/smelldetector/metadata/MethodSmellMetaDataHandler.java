package edu.pdx.cs.multiview.smelldetector.metadata;

import org.eclipse.jdt.core.IMethod;

public interface MethodSmellMetaDataHandler<T> extends SmellMetadataHandler<IMethod>{

	void saveSmellMetadata(IMethod method);

	T getSmellMetadata(String className, String methodName);

}
