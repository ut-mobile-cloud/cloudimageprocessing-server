/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.fileupload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author madis
 */
public abstract class AbstractFileUploader implements FileUploader {
	
	protected List<File> files;
	
	public AbstractFileUploader() {
		super();
		files = new ArrayList<File>();
	}
	@Override
	public FileUploader withFilesFromServletRequest() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public FileUploader withFiles(List<File> files) {
		this.files.addAll(files);
		return this;
	}

	@Override
	public FileUploader withFile(File file) {
		files.add(file);
		return this;
	}

	
}
