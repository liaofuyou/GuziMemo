package me.guzi.mome.entity;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table
public class Task {

	@Id
	private int id;
	private int imageIndex;
	private String content;
	private Date adate;
	private int type;

	public Task() {
		// TODO Auto-generated constructor stub
	}

	public Task(int id, int imageIndex, String content, Date adate, int type) {
		super();
		this.id = id;
		this.imageIndex = imageIndex;
		this.content = content;
		this.adate = adate;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAdate() {
		return adate;
	}

	public void setAdate(Date adate) {
		this.adate = adate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Task [imageIndex=" + imageIndex + ", content=" + content + ", date=" + adate + "]";
	}
}
