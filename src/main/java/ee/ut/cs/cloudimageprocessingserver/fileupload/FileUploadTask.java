/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.fileupload;

import ee.ut.cs.cloudimageprocessingserver.model.Resource;
import java.util.List;

/**
 *
 * @author madis
 */
public class FileUploadTask implements Runnable{
	
	FileUploader uploader;
	public FileUploadTask(FileUploader uploader) {
		this.uploader = uploader;
	}
	
	@Override
	public void run() {
		List<Resource> uploadedResource = uploader.upload();
		
		
	}
	
}
