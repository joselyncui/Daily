package com.mengzhu.daily.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DailyDBHelper extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "daily_datas.db";
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER ";
	private static final String COMMA_SEP = ",";
	
	public static final int DATABASE_VERSION = 1;
	
	public static class TaskEntity{
		public static final String TABLE_NAME="daily_task";
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_COMMENT = "comment";
		public static final String COLUMN_LEVEL = "level";
		public static final String COLUMN_TIMES = "times";
		public static final String COLUMN_ISOPEN = "isopen";
		
		public static final String CREATE_TABLE = "CREATE TABLE " 
				+ TABLE_NAME +"(" 
				+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
				+ COLUMN_COMMENT + TEXT_TYPE + COMMA_SEP
				+ COLUMN_LEVEL + INTEGER_TYPE + COMMA_SEP
				+ COLUMN_TIMES +" CHAR(8) " + COMMA_SEP
				+ COLUMN_ISOPEN + INTEGER_TYPE 
				+ " );";
	}
	
	public static class TimedEntity{
		public static final String TABLE_NAME = "daily_timed";
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_COMMENT = "comment";
		public static final String COLUMN_TIME = "time";
		public static final String COLUMN_ISOPEN = "isopen";
		
		public static final String CREATE_TABLE = "CREATE TABLE "
				+ TABLE_NAME +"("
				+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT"+COMMA_SEP
				+ COLUMN_COMMENT +TEXT_TYPE + COMMA_SEP
				+ COLUMN_TIME + INTEGER_TYPE + COMMA_SEP
				+ COLUMN_ISOPEN + INTEGER_TYPE
				+ " );";
	}

	public DailyDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TaskEntity.CREATE_TABLE);
		db.execSQL(TimedEntity.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TaskEntity.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TimedEntity.TABLE_NAME);
		onCreate(db);
	}

}
