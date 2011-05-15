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
public interface FileUploader {
	FileUploader withFilesFromServletRequest();
	FileUploader withFiles(List<File>files);
	FileUploader withFile(File file);
	List<Resource> upload();
}
