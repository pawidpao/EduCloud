package mobacc.EduCloud.DAO;

import java.io.Serializable;

public class LessonPlansData implements Serializable{
	
	private int lp_id;
	private String title, uploader, tags, description, path, filename;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public int getLp_id() {
		return lp_id;
	}
	public void setLp_id(int lp_id) {
		this.lp_id = lp_id;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
