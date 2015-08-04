package com.mengzhu.daily.entity;

public class Timed {
	public static final String GSON_KEY = "android.mengzhu.daily.entity.Timed";
	public static final int OPEN = 0;
	public static final int CLOSE = OPEN + 1;
	
	private int id;
	private String comment;
	private long time;
	private int isOpen;
	
	public Timed(){}
	
	public Timed(String comment, int time, int isOpen) {
		this.comment = comment;
		this.time = time;
		this.isOpen = isOpen;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void open(){
		this.isOpen = OPEN;
	}
	public void close(){
		this.isOpen = CLOSE;
	}

}
