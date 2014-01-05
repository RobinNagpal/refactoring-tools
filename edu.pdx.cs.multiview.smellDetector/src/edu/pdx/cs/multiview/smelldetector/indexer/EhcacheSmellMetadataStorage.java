package edu.pdx.cs.multiview.smelldetector.indexer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

public class EhcacheSmellMetadataStorage implements SmellMetadataStorage<Object> {

	private Cache projectLevelCache;

	public EhcacheSmellMetadataStorage(String projectName) {
		String cacheName = projectName + "_smell_metadata_cache";
		projectLevelCache = CacheManager.getInstance().getCache(cacheName);
		if (projectLevelCache == null) {
			CacheManager.getInstance().addCache(cacheName);
			projectLevelCache = CacheManager.getInstance().getCache(cacheName);
		}

		CacheConfiguration config = projectLevelCache.getCacheConfiguration();
		// TODO : actual value can be decided after discussion with team and
		// more profiling
		config.setMaxEntriesLocalHeap(500);

	}

	@Override
	public void saveSmellMetadata(String key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getSmellMetadata(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
