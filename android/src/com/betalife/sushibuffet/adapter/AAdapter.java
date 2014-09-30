package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.betalife.sushibuffet.model.BaseModel;

public abstract class AAdapter<E extends BaseModel> extends BaseAdapter {
	private List<E> list;
	protected final LayoutInflater layoutInflater;
	protected Activity activity;

	public AAdapter(Activity activity, List<E> tables) {
		super();
		this.activity = activity;
		this.list = tables;
		this.layoutInflater = LayoutInflater.from(activity);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public E getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).getId();
	}

}
