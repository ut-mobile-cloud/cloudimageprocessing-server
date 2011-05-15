/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.fileupload;

import ee.ut.cs.cloudimageprocessingserver.model.Resource;
import java.io.File;
import java.util.List;

/**
 *
 * @author madis
 */
class MockFileUploader extends AbstractFileUploader {
	
	@Override
	public List<Resource> upload() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
