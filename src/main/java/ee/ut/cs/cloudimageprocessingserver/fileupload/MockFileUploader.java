/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.fileupload;

import java.io.File;
import java.util.List;

/**
 *
 * @author madis
 */
class MockFileUploader implements FileUploader {

	public MockFileUploader() {
	}

	@Override
	public FileUploader withFilesFromServletRequest() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public FileUploader withFiles(List<File> files) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public FileUploader withFile(File file) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String upload() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
