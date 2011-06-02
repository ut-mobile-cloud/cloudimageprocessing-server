/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ee.ut.cs.cloudimageprocessingserver.model.VideoResource;
import ee.ut.cs.cloudimageprocessingserver.notifications.NotificationCentre;
import ee.ut.cs.cloudimageprocessingserver.performance.AsyncTestTimes;
import ee.ut.cs.cloudimageprocessingserver.performance.TestTimesManager;
import java.io.IOException;
import static ee.ut.cs.cloudimageprocessingserver.performance.TestTimesManager.timeNow;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author madis
 */
public class ProcessResourceTask implements Runnable {
	
	VideoResource resourceToProcess;
	public ProcessResourceTask(VideoResource resource) {
		this.resourceToProcess = resource;
	}
	@Override
	public void run() {
		String url = "https://s3.amazonaws.com/cloudimageprocessing/" + "input"; //+ resourceToProcess.getTitle(); // TODO: create url string from resourceToProcess
		AsyncTestTimes times = (AsyncTestTimes)TestTimesManager.sharedManager().getTimesForTestID(resourceToProcess.getID());
		times.setServerRequestToCloud(timeNow());
		try {
			// Actual processing with example file. Actual input is not 
						Process p = Runtime.getRuntime().exec("/usr/bin/java -jar /Volumes/data/src/mp.jar "
								+ "https://s3.amazonaws.com/cloudimageprocessing/input "
								+ "output "
								+ url);
			try {
				p.waitFor();
			} catch (InterruptedException ex) {
				Logger.getLogger(ProcessResourceTask.class.getName()).log(Level.SEVERE, null, ex);
			}
						System.out.println(p.getErrorStream().toString());
						
		} catch (IOException ex) {
			Logger.getLogger(ProcessResourceTask.class.getName()).log(Level.SEVERE, null, ex);
		}
			
		times.setServerResponseFromCloud(timeNow());
		System.out.println("I have times : " + times.toString());
		Gson gson = new GsonBuilder().create();
		System.err.println("BEGINNING TO PROCESS VIDEO");
		String processedResourceJson = gson.toJson(resourceToProcess, VideoResource.class);
		// Here the video processing is done, so we'll notify the client who requested this task
		System.out.println("PROCESSING DONE, NOTIFYING USER");
		times.setServerSendPushNotification(timeNow());
		NotificationCentre.getProviderForDevice("iphone")
			.withDeviceData("5324075b4c07afa9e2dbe15b74dbda2227a74d5537f0d4af24cc0aecda697f1c")
			.withMessage("Processing complete")
			.withKeyValuePairs("resourceID", resourceToProcess.getID())
			.send();
		System.out.println("NOTIFYING USER DONE");
	}
	
}
