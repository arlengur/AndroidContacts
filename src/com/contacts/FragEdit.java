package com.contacts;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragEdit extends Fragment {

	Button btnSave, btnCancel;
	EditText etFName, etLName;
	DBHelper dbHelper;
	OnFragmEventListener eventListener;
	SQLiteDatabase db;
	String id;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			eventListener = (OnFragmEventListener) activity;
			dbHelper = new DBHelper(getActivity());
			db = dbHelper.getWritableDatabase();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement onSomeEventListener");
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.edit, null);
		etFName = (EditText) view.findViewById(R.id.etFName);
		etLName = (EditText) view.findViewById(R.id.etLName);
		btnSave = (Button) view.findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (etFName.getText().toString().isEmpty()
						|| etLName.getText().toString().isEmpty()) {
					Toast.makeText(
							getActivity(),
							"Поля 'First Name' и 'Last Name' не могут быть пусты.",
							Toast.LENGTH_SHORT).show();
				} else {
					ContentValues cv = new ContentValues();
					String fname = etFName.getText().toString();
					String lname = etLName.getText().toString();
					cv.put("fname", fname);
					cv.put("lname", lname);
					int updCount = db.update("mytable", cv, "id = ?",
							new String[] { "" + id });
					Toast.makeText(getActivity(), updCount + " saved.",
							Toast.LENGTH_SHORT).show();
					etFName.setText("");
					etLName.setText("");
					eventListener.event(1, "");
				}

			}
		});

		btnCancel = (Button) view.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				etFName.setText("");
				etLName.setText("");
				eventListener.event(1, "");

			}
		});
		return view;
	}
	
	public void onStart() {
	    super.onStart();
	    fillFields();
	  }

	public void setId(String id) {
		this.id = id;
	}
	
	private void fillFields() {
		String selection = "id = ?";
		String[] selectionArgs = new String[] { id };
		Cursor c = db.query("mytable", null, selection, selectionArgs, null,
				null, null);
		if (c.moveToFirst()) {
			etFName.setText(c.getString(c.getColumnIndex("fname")));
			etLName.setText(c.getString(c.getColumnIndex("lname")));
		}
		
	}
}
