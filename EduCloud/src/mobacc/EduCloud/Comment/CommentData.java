package mobacc.EduCloud.Comment;

public class CommentData {
	
	private int id, materials_id;
	private String commentator, comment;//Commentator = email

	public String getCommentator() {
		return commentator;
	}

	public void setCommentator(String commentator) {
		this.commentator = commentator;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getMaterials_id() {
		return materials_id;
	}

	public void setMaterials_id(int materials_id) {
		this.materials_id = materials_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
