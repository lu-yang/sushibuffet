package com.betalife.sushibuffet.dialog;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.asynctask.NewTakeawayTask;
import com.betalife.sushibuffet.model.Takeaway;

public class NewTakeawayDialog extends Dialog {

	private Activity activity;

	public NewTakeawayDialog(Activity activity) {
		super(activity);
		this.activity = activity;
		setCancelable(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_new_takeaway);

		Button ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText memoText = (EditText) findViewById(R.id.edittext_new_takeaway);
				String memo = memoText.getText().toString().trim();
				TextView errText = (TextView) findViewById(R.id.mes_new_takeaway_err);
				if (StringUtils.isEmpty(memo)) {
					errText.setVisibility(View.VISIBLE);
					return;
				}
				errText.setVisibility(View.INVISIBLE);
				Takeaway takeaway = new Takeaway();
				takeaway.setMemo(memo);
				NewTakeawayTask task = new NewTakeawayTask(activity);
				task.execute(takeaway);

				cancel();
			}
		});
	}
}
