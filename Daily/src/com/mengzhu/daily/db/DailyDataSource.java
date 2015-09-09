package com.mengzhu.daily.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mengzhu.daily.db.DailyDBHelper.TaskEntity;
import com.mengzhu.daily.db.DailyDBHelper.TimedEntity;
import com.mengzhu.daily.entity.Task;
import com.mengzhu.daily.entity.Timed;

public final class DailyDataSource {
	private DailyDBHelper dbHelper;
	private static DailyDataSource dailyDataSource;

	private DailyDataSource(Context context) {
		dbHelper = new DailyDBHelper(context);
	}

	/**
	 * 获得数据库
	 * 
	 * @return
	 * @throws SQLException
	 */
	public SQLiteDatabase open() throws SQLException {
		return dbHelper.getWritableDatabase();
	}

	/**
	 * 添加任务到指定位置 未完成
	 * 
	 * @param position
	 * @param task
	 * @return newid
	 */
	public long addTask(Task task) {
		SQLiteDatabase database = open();
		ContentValues params = new ContentValues();
		params.put(TaskEntity.COLUMN_COMMENT, task.getComment());
		params.put(TaskEntity.COLUMN_LEVEL, task.getLevel());
		params.put(TaskEntity.COLUMN_TIMES, task.getHours());
		params.put(TaskEntity.COLUMN_ISOPEN, task.getIsOpen());
		long id = database.insert(TaskEntity.TABLE_NAME, null, params);
		database.close();
		return id;
	}

	/**
	 * 获得所有的任务， 并返回List
	 * 
	 * @return
	 */
	public List<Task> getAllTasks() {

		List<Task> tasks = new ArrayList<>();
		String queryStr = "SELECT * FROM " + TaskEntity.TABLE_NAME;
		SQLiteDatabase database = open();
		Cursor cursor = database.rawQuery(queryStr, null);
		if (cursor.moveToFirst()) {
			do {
				Task task = new Task();
				task.setId(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_ID)));
				task.setComment(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_COMMENT)));
				task.setHours(cursor.getFloat(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_TIMES)));
				task.setLevel(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_LEVEL)));
				task.setIsOpen(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_ISOPEN)));
				tasks.add(task);
			} while (cursor.moveToNext());
		}
		return tasks;
	}
	
	public int getOpenTaskCount(){

		String queryStr = "SELECT * FROM " + TaskEntity.TABLE_NAME +" where " + TaskEntity.COLUMN_ISOPEN +"=0";
		SQLiteDatabase database = open();
		Cursor cursor = database.rawQuery(queryStr, null);
		return cursor.getCount();
	}
	
	public int getAllTaskCount(){
		String queryStr = "SELECT * FROM " + TaskEntity.TABLE_NAME;
		SQLiteDatabase database = open();
		Cursor cursor = database.rawQuery(queryStr, null);
		return cursor.getCount();
	}

	/**
	 * 删除task
	 * 
	 * @param task
	 */
	public void deleteTask(Task task) {
		SQLiteDatabase database = open();
		database.delete(TaskEntity.TABLE_NAME, TaskEntity.COLUMN_ID + "=?",
				new String[] { String.valueOf(task.getId()) });
		database.close();
	}

	/**
	 * 删除小于某个时间的task
	 * 
	 * @param time
	 */
	public void clearTask(long time) {
		SQLiteDatabase database = open();
		database.delete(TaskEntity.TABLE_NAME, TaskEntity.COLUMN_TIMES + "< ?",
				new String[] { String.valueOf(time) });
		database.close();
	}

	/**
	 * 更新task信息
	 * 
	 * @param task
	 */
	public void updateTask(Task task) {
		SQLiteDatabase database = open();

		ContentValues params = new ContentValues();
		params.put(TaskEntity.COLUMN_COMMENT, task.getComment());
		params.put(TaskEntity.COLUMN_LEVEL, task.getLevel());
		params.put(TaskEntity.COLUMN_TIMES, task.getHours());
		params.put(TaskEntity.COLUMN_ISOPEN, task.getIsOpen());

		database.update(TaskEntity.TABLE_NAME, params, TaskEntity.COLUMN_ID
				+ "=?", new String[] { String.valueOf(task.getId()) });
	}

	/**
	 * 修改task位置
	 * 
	 * @param from
	 * @param to
	 */
	public void changeTaskPosition(Task from, Task to) {

		deleteTask(from);// 移除目标task

		SQLiteDatabase database = open();
		database.beginTransaction();

		String[] columns = new String[] { TaskEntity.COLUMN_ID,
				TaskEntity.COLUMN_COMMENT, TaskEntity.COLUMN_LEVEL,
				TaskEntity.COLUMN_TIMES, TaskEntity.COLUMN_ISOPEN };
		String selection = "";
		if (from.getId() < to.getId()) {// 在上面
			selection = TaskEntity.COLUMN_ID + ">?";
		} else {
			selection = TaskEntity.COLUMN_ID + ">=?";
		}

		String[] args = new String[] { to.getId() + "" };
		Cursor cursor = database.query(TaskEntity.TABLE_NAME, columns,
				selection, args, null, null, null);
		List<Task> tasks = new ArrayList<>();

		if (cursor.moveToFirst()) {
			do {
				Task task = new Task();
				task.setId(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_ID)));
				task.setComment(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_COMMENT)));
				task.setHours(cursor.getFloat(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_TIMES)));
				task.setLevel(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_LEVEL)));
				task.setIsOpen(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskEntity.COLUMN_ISOPEN)));
				tasks.add(task);
			} while (cursor.moveToNext());
		}

		// 删除选项
		for (int i = 0; i < tasks.size(); i++) {
			String delWhareStr = TaskEntity.COLUMN_ID + "="
					+ tasks.get(i).getId();
			database.delete(TaskEntity.TABLE_NAME, delWhareStr, null);
		}

		// 插入原task
		ContentValues params = new ContentValues();
		params.put(TaskEntity.COLUMN_COMMENT, from.getComment());
		params.put(TaskEntity.COLUMN_LEVEL, from.getLevel());
		params.put(TaskEntity.COLUMN_TIMES, from.getHours());
		params.put(TaskEntity.COLUMN_ISOPEN, from.getIsOpen());
		database.insert(TaskEntity.TABLE_NAME, null, params);

		// 插入被删除的tasks
		for (int i = 0; i < tasks.size(); i++) {
			params.put(TaskEntity.COLUMN_COMMENT, tasks.get(i).getComment());
			params.put(TaskEntity.COLUMN_LEVEL, tasks.get(i).getLevel());
			params.put(TaskEntity.COLUMN_TIMES, tasks.get(i).getHours());
			params.put(TaskEntity.COLUMN_ISOPEN, tasks.get(i).getIsOpen());
			database.insert(TaskEntity.TABLE_NAME, null, params);
		}

		database.setTransactionSuccessful();
		database.endTransaction();
	}

	/**
	 * 获取所有task, 并返回cursor
	 * 
	 * @return
	 */
	public Cursor getTaskCursor() {
		String queryStr = "SELECT * FROM " + TaskEntity.TABLE_NAME;
		SQLiteDatabase database = open();
		Cursor cursor = database.rawQuery(queryStr, null);
		return cursor;
	}

	/**
	 * 将cursor转化为task
	 * 
	 * @param cursor
	 * @return
	 */
	public static Task cursorToTask(Cursor cursor) {
		Task task = new Task();
		task.setId(cursor.getInt(cursor
				.getColumnIndexOrThrow(TaskEntity.COLUMN_ID)));
		task.setComment(cursor.getString(cursor
				.getColumnIndexOrThrow(TaskEntity.COLUMN_COMMENT)));
		task.setHours(cursor.getFloat(cursor
				.getColumnIndexOrThrow(TaskEntity.COLUMN_TIMES)));
		task.setLevel(cursor.getInt(cursor
				.getColumnIndexOrThrow(TaskEntity.COLUMN_LEVEL)));
		task.setIsOpen(cursor.getInt(cursor
				.getColumnIndexOrThrow(TaskEntity.COLUMN_ISOPEN)));
		return task;
	}

	/**
	 * 添加定时任务
	 * 
	 * @param timed
	 * @return
	 */
	public long addTimed(Timed timed) {

		System.out.println("datasource " + timed.getTime());
		SQLiteDatabase database = open();
		ContentValues values = new ContentValues();
		values.put(TimedEntity.COLUMN_COMMENT, timed.getComment());
		values.put(TimedEntity.COLUMN_TIME, timed.getTime());
		values.put(TimedEntity.COLUMN_ISOPEN, timed.getIsOpen());
		long newId = database.insert(TimedEntity.TABLE_NAME, null, values);
		return newId;
	}

	/**
	 * 获得所有定时任务
	 * 
	 * @return
	 */
	public List<Timed> getTimeds() {
		List<Timed> timeds = new ArrayList<>();

		SQLiteDatabase database = open();
		String queryStr = "SELECT * FROM " + TimedEntity.TABLE_NAME;
		Cursor cursor = database.rawQuery(queryStr, null);
		if (cursor.moveToFirst()) {
			do {
				Timed timed = new Timed();
				timed.setId(cursor.getInt(cursor
						.getColumnIndex(TimedEntity.COLUMN_ID)));
				timed.setComment(cursor.getString(cursor
						.getColumnIndex(TimedEntity.COLUMN_COMMENT)));
				timed.setTime(cursor.getLong(cursor
						.getColumnIndexOrThrow(TimedEntity.COLUMN_TIME)));
				timed.setIsOpen(cursor.getInt(cursor
						.getColumnIndex(TimedEntity.COLUMN_ISOPEN)));
				timeds.add(timed);
			} while (cursor.moveToNext());
		}
		return timeds;
	}
	

	/**
	 * 删除定时任务
	 * 
	 * @param timed
	 */
	public void delTimed(Timed timed) {
		SQLiteDatabase database = open();
		database.delete(TimedEntity.TABLE_NAME, TimedEntity.COLUMN_ID + "=?",
				new String[] { String.valueOf(timed.getId()) });
		database.close();
	}

	/**
	 * 清除小于某个时间的定时任务
	 * 
	 * @param time
	 */
	public void clearTimed(long time) {
		SQLiteDatabase database = open();
		database.delete(TimedEntity.TABLE_NAME, TimedEntity.COLUMN_TIME
				+ " < ?", new String[] { String.valueOf(time) });
		database.close();
	}

	/**
	 * 更新定时任务
	 * 
	 * @param timed
	 */
	public void updTimed(Timed timed) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TimedEntity.COLUMN_COMMENT, timed.getComment());
		values.put(TimedEntity.COLUMN_TIME, timed.getTime());
		values.put(TimedEntity.COLUMN_ISOPEN, timed.getIsOpen());
		String where = TimedEntity.COLUMN_ID + " = ?";
		String[] whereArgs = new String[] { String.valueOf(timed.getId()) };
		db.update(TimedEntity.TABLE_NAME, values, where, whereArgs);
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		dbHelper.close();
	}

	/**
	 * 获得实例
	 * 
	 * @param context
	 * @return
	 */
	public static DailyDataSource getInstance(Context context) {
		if (null == dailyDataSource) {
			dailyDataSource = new DailyDataSource(context);
		}

		return dailyDataSource;
	}
}
