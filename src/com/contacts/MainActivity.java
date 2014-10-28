package com.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements
		OnFragmEventListener {

	Button btnCreate;
	boolean withDetails = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnCreate = (Button) findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createContact();
			}
		});
		withDetails = (findViewById(R.id.fdetail) != null);
		if (withDetails) {
			createContact();
		}
	}

	void showDetails(String id) {
		if (withDetails) {
			FragEdit fragEdit = new FragEdit();
			fragEdit.setId(id);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fdetail, fragEdit).commit();
		} else {
			startActivity(new Intent(this, EditContactActivity.class).putExtra(
					"id", id));
		}
	}

	void createContact() {
		if (withDetails) {
			FragCreate fragCreate = new FragCreate();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fdetail, fragCreate).commit();

		} else {
			startActivity(new Intent(this, CrearteContactActivity.class));
		}

	}

	@Override
	public void event(int fragmId, String id) {
		switch (fragmId) {
		case 0:
			showDetails(id);
			break;
		case 1:
			FragList fragList = (FragList) getSupportFragmentManager()
					.findFragmentById(R.id.flist);
			fragList.update();
			break;
		}
	}
}