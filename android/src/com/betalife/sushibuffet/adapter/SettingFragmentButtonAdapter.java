package com.betalife.sushibuffet.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.betalife.sushibuffet.activity.R;

public class SettingFragmentButtonAdapter extends BaseAdapter {

	private List<Pair<Integer, OnClickListener>> list = new ArrayList<Pair<Integer, OnClickListener>>();
	private LayoutInflater layoutInflater;
	private Activity activity;
	private int resourceId;
	private List<Button> buttons = new ArrayList<Button>();

	public SettingFragmentButtonAdapter(Activity activity) {
		this.activity = activity;
		resourceId = R.layout.adapter_setting_button;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (layoutInflater == null) {
			layoutInflater = LayoutInflater.from(activity);
		}
		if (convertView == null) {
			convertView = layoutInflater.inflate(resourceId, parent, false);
		}

		Button button = (Button) convertView.findViewById(R.id.adapter_setting_fragment_button);
		buttons.add(button);
		Pair<Integer, OnClickListener> map = getItem(position);
		button.setOnClickListener(map.second);
		button.setText(map.first);

		return convertView;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Pair<Integer, OnClickListener> getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).hashCode();
	}

	public void addItem(Pair<Integer, OnClickListener> item) {
		list.add(item);
	}

	public void setVisibility(int visibility) {
		for (Button one : buttons) {
			one.setVisibility(visibility);
		}

	}

}
