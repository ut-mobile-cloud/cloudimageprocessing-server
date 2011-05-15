/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.fileupload;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectResult;
import ee.ut.cs.cloudimageprocessingserver.model.Resource;
import ee.ut.cs.cloudimageprocessingserver.model.VideoResource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author madis
 */
class S3FileUploader extends AbstractFileUploader {
	
	@Override
	public List<Resource> upload() {
		List<Resource> uploadedResources = new ArrayList<Resource>();
		String DefaultS3BucketName = "cloudimageprocessing";
        AmazonS3 s3 = null;
        try {
			s3 = new AmazonS3Client(new PropertiesCredentials(
					this.getClass().getClassLoader().getResourceAsStream("AwsCredentials.properties")));
		} catch (IOException ex) {
			//Logger.getLogger(UploadPictureTask.class.getName()).log(Level.SEVERE, null, ex);
		}
        s3.createBucket(DefaultS3BucketName);
		for (File file : files) {
			Resource resource = new VideoResource();
			resource.updateWithDataFromFile(file);
			String fileName = "media-" + (Long)System.currentTimeMillis();
			PutObjectResult putObjectResult = s3.putObject(DefaultS3BucketName, fileName, file);
			resource.setID(putObjectResult.getETag());
			System.out.println("Uploaded file named : " + fileName + " to S3");
		}
		return uploadedResources;
	}
	
}
