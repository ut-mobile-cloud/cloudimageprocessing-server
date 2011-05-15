
package ee.ut.cs.cloudimageprocessingserver.model;

import java.io.File;

/**
 *
 * @author madis
 */
public interface Resource {
	String getID();
	void setID(String ID);
	String getTitle();
	void setTitle(String title);
	Long getSize();
	void setSize(Long size);
	Integer getDuration();
	void setDuration(Integer duration);
	String getLocation();
	void setLocation(String location);
	Resource updateWithDataFromFile(File file);
	
}
