/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.fileupload;

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
		String xxx = uploader.upload();
		
		
	}
	
}
