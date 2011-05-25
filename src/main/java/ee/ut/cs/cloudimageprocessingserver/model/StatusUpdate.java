/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.model;

/**
 *
 * @author madis
 */
public class StatusUpdate {
	
	public enum Status {
		NEW, RUNNING, STOPPED, FINISHED, ERROR 
	}
	private Status status;
	private Integer progress;
	private String message;
	private String resourceID;
	
	public StatusUpdate(String resourceID) {
		this.resourceID = resourceID;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
