package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.betalife.sushibuffet.model.BaseModel;

public abstract class AAdapter<E extends BaseModel> extends BaseAdapter {

	private List<E> list;
	protected LayoutInflater layoutInflater;
	protected Activity activity;

	public AAdapter() {
		super();
	}

	public AAdapter(Activity activity) {
		this();
		setActivity(activity);
	}

	public AAdapter(Activity activity, List<E> tables) {
		this(activity);
		this.list = tables;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
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

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

}
