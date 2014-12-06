//package com.betalife.sushibuffet.activity;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//
//public class NewTableActivity extends FragmentActivity implements Callback {
//
//	private FragmentSetting password;
//	private FragmentTables tables;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_new_table);
//		password = new FragmentSetting();
//		tables = new FragmentTables();
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		getSupportFragmentManager().beginTransaction().replace(R.id.new_tables_layout, password).commit();
//	}
//
//	@Override
//	public void callback() {
//		getSupportFragmentManager().beginTransaction().replace(R.id.new_tables_layout, tables).commit();
//	}
//
// }
