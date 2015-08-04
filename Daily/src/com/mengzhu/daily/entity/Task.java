package com.mengzhu.daily.entity;



/**
 * Created by MGC01 on 7/7/2015.
 */
public class Task{
	public static final String GSON_KEY = "android.mengzhu.daily.entity.Task";

	public static final int OPEN = 0;
	public static final int CLOSE = OPEN + 1;
	
	private int id;
    private String comment;
    private float hours;
    private int level;
    private int isOpen;
    
    public Task() {}

    public Task( String title, float hours, int level) {
        this.comment = title;
        this.hours = hours;
        this.level = level;
        this.isOpen = OPEN;
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String title) {
		this.comment = title;
	}

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
}
