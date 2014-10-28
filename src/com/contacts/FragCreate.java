package com.contacts;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragCreate extends Fragment {
	Button btnCreate, btnCancel;
	EditText etFName, etLName;
	DBHelper dbHelper;
	OnFragmEventListener eventListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			eventListener = (OnFragmEventListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement onSomeEventListener");
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.edit, container, false);

		dbHelper = new DBHelper(getActivity());

		btnCreate = (Button) view.findViewById(R.id.btnSave);
		btnCreate.setText("Create");
		btnCreate.setOnClickListener(new OnClickListener() {
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
					etFName.setText("");
					etLName.setText("");
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					cv.put("fname", fname);
					cv.put("lname", lname);
					long rowID = db.insert("mytable", null, cv);
					Toast.makeText(getActivity(), "Created with id=" + rowID,
							Toast.LENGTH_SHORT).show();
					eventListener.event(1, "");
				}

			}
		});

		btnCancel = (Button) view.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				eventListener.event(1, "");

			}
		});

		etFName = (EditText) view.findViewById(R.id.etFName);
		etLName = (EditText) view.findViewById(R.id.etLName);

		return view;
	}
}