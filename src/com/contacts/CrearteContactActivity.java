package com.contacts;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CrearteContactActivity extends FragmentActivity implements
		OnFragmEventListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}

		FragCreate fragCreate = new FragCreate();
		getSupportFragmentManager().beginTransaction()
				.add(android.R.id.content, fragCreate).commit();
	}

	@Override
	public void event(int fragmId, String id) {
		startActivity(new Intent(this, MainActivity.class));
	}
}