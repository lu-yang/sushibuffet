package com.betalife.sushibuffet.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.betalife.sushibuffet.adapter.AAdapter;
import com.betalife.sushibuffet.model.Diningtable;

public class TableActivity extends FragmentActivity implements Callback {

	private FragmentSetting password;
	private FragmentTables tables;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_table);
		password = new FragmentSetting();
		AAdapter<Diningtable> adapter = (AAdapter<Diningtable>) getIntent().getSerializableExtra("adapter");
		Log.i("adapter", "" + adapter.getClass());
		adapter.setActivity(this);
		tables = new FragmentTables(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getSupportFragmentManager().beginTransaction().replace(R.id.new_tables_layout, password).commit();
	}

	@Override
	public void callback() {
		getSupportFragmentManager().beginTransaction().replace(R.id.new_tables_layout, tables).commit();
	}

}
