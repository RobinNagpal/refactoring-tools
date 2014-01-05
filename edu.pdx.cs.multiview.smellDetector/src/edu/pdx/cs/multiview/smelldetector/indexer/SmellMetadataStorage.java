package edu.pdx.cs.multiview.smelldetector.indexer;

public interface SmellMetadataStorage<T> {

	void saveSmellMetadata(String key, T value);

	T getSmellMetadata(String key);

}
