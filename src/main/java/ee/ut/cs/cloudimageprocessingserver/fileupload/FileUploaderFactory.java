/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.fileupload;

/**
 *
 * @author madis
 */
public class FileUploaderFactory {
	public static FileUploader newMockFileUploader() {
		return new MockFileUploader();
	}
	
	public static FileUploader newS3FileUploader() {
		return new S3FileUploader();
	}
	
	public static FileUploader newLocalFileUploader() {
		return new LocalFileUploader();
	}
}
