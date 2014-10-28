package com.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, "myDB", null, 4);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table mytable ("
				+ "id integer primary key autoincrement," + "fname text,"
				+ "lname text" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {
			db.execSQL("drop table mytable;");
			this.onCreate(db);
		}
	}
}