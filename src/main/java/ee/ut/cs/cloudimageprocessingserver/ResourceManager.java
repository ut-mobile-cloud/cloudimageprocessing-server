/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver;

import ee.ut.cs.cloudimageprocessingserver.model.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author madis
 */
public class ResourceManager {
	private Map<String, Resource> resources;
	private static ResourceManager instance = null;
	
	public static ResourceManager getInstance() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}
	
	private ResourceManager() {
		resources =  new ConcurrentHashMap<String, Resource>();
	}
	
	public void addResource(Resource resource) {
		this.resources.put(resource.getID(), resource);
	}
	
	public Resource resourceForKey(String key) {
		return this.resources.get(key);
	}
	
	public Collection<Resource> getAllResources() {
		return resources.values();
	}
}
