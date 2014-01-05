package edu.pdx.cs.multiview.smelldetector.indexer;

import java.util.HashMap;
import java.util.Map;

public class SmellMetadataStorageFactory {

	private SmellMetadataStorageFactory() {
	}

	private static Map<String, EhcacheSmellMetadataStorage> ehchacheStoragesForDifferentProjects = new HashMap<String, EhcacheSmellMetadataStorage>();

	static SmellMetadataStorage<?> getMetaDataStorage(String projectName) {
		EhcacheSmellMetadataStorage ehcacheSmellMetadataStorage = ehchacheStoragesForDifferentProjects.get(projectName);
		if (ehcacheSmellMetadataStorage == null) {
			// Plain old singleton pattern
			synchronized (SmellMetadataStorageFactory.class) {
				if (ehcacheSmellMetadataStorage == null) {
					ehcacheSmellMetadataStorage = createNewStorage(projectName);
				}
			}
		}
		return ehcacheSmellMetadataStorage;
	}

	static private synchronized EhcacheSmellMetadataStorage createNewStorage(String projectName) {
		return new EhcacheSmellMetadataStorage(projectName);
	}

}
