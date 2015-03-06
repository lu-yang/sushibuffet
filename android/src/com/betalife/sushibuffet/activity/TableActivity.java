package com.betalife.sushibuffet.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.betalife.sushibuffet.adapter.AAdapter;
import com.betalife.sushibuffet.model.Diningtable;

public abstract class TableActivity extends Activity {
	protected int titleId;
	protected AAdapter<Diningtable> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tables);

		TextView lbl_table_activity_title = (TextView) findViewById(R.id.lbl_table_activity_title);
		lbl_table_activity_title.setText(titleId);
	}

	public void displayTables(List<Diningtable> result) {
		adapter.setList(result);
		GridView tables = (GridView) findViewById(R.id.diningtables);
		tables.setAdapter(adapter);
	}

}
