/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.cloudimageprocessingserver.model;

import java.io.File;

/**
 *
 * @author madis
 */
public class VideoResource implements Resource {
	
	private String ID;
	private String title;
	private Long size;
	private Integer duration;
	private String location;
	
	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void setID(String ID) {
		this.ID = ID;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Long getSize() {
		return size;
	}

	@Override
	public void setSize(Long size) {
		this.size = size;
	}

	@Override
	public Integer getDuration() {
		return duration;
	}

	@Override
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public Resource updateWithDataFromFile(File file) {
		// FIX: update with real values as they would come from video file
		this.ID = Integer.toString(this.hashCode());
		this.setDuration(60);
		this.setTitle("Bicycle repairman but how?");
		this.setSize(file.length());
		this.location = file.getAbsolutePath();
		return this;
	}
}
