package com.betalife.sushibuffet.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.betalife.sushibuffet.adapter.AAdapter;
import com.betalife.sushibuffet.model.Diningtable;

public abstract class TableActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tables);

		TextView lbl_table_activity_title = (TextView) findViewById(R.id.lbl_table_activity_title);
		lbl_table_activity_title.setText(getTitleId());
	}

	public abstract int getTitleId();

	public void displayTables(List<Diningtable> result) {
		AAdapter<Diningtable> adapter = getAdapter();
		adapter.setList(result);
		adapter.setActivity(this);
		GridView tables = (GridView) findViewById(R.id.diningtables);
		tables.setAdapter(adapter);
	}

	public abstract AAdapter<Diningtable> getAdapter();

}
