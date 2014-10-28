package com.contacts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragList extends ListFragment {

	OnFragmEventListener eventListener;
	SQLiteDatabase db;
	ArrayAdapter<String> adapter;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			eventListener = (OnFragmEventListener) activity;
			DBHelper dbHelper = new DBHelper(getActivity());
			db = dbHelper.getWritableDatabase();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement onSomeEventListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getContacts(db));
		setListAdapter(adapter);
	}

	public void update() {
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getContacts(db));
		setListAdapter(adapter);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String item = (String) getListAdapter().getItem(position);
		String selection = "fname = ? and lname = ?";
		String[] selectionArgs = item.split(" ");
		Cursor c = db.query("mytable", null, selection, selectionArgs, null,
				null, null);
		if (c.moveToFirst()) {
			eventListener.event(0, c.getString(0));
		}
	}

	private List<String> getContacts(SQLiteDatabase db) {
		Cursor c = db.query("mytable", null, null, null, null, null, null);
		List<String> contactList = new ArrayList<String>();
		if (c.moveToFirst()) {
			int nameColIndex = c.getColumnIndex("fname");
			int emailColIndex = c.getColumnIndex("lname");

			do {
				contactList.add(c.getString(nameColIndex) + " "
						+ c.getString(emailColIndex));
			} while (c.moveToNext());
		}
		c.close();
		return contactList;
	}
}