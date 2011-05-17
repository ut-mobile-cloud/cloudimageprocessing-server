/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.fileupload;

import ee.ut.cs.cloudimageprocessingserver.model.Resource;
import ee.ut.cs.cloudimageprocessingserver.model.VideoResource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author madis
 */
public class LocalFileUploader extends AbstractFileUploader{

	@Override
	public List<Resource> upload() {
		List<Resource> resourcesList = new ArrayList<Resource>();
		for(File file : this.files) {
			Resource r = new VideoResource();
			r.updateWithDataFromFile(file);
			resourcesList.add(r);
		}
		
		return resourcesList;
	}
	
}
