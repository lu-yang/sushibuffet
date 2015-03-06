package com.betalife.sushibuffet.adapter;

import org.apache.commons.lang.StringUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.activity.Refreshable;
import com.betalife.sushibuffet.util.DodoroContext;

public class FragmentsAdapter extends FragmentPagerAdapter {

	private Fragment[] fragments;
	private String[] fragmentClazzs;
	private MainActivity activity;

	public FragmentsAdapter(MainActivity activity) {
		super(activity.getSupportFragmentManager());
		this.activity = activity;
		int resourceId = DodoroContext.getInstance().which(R.array.fragments, R.array.takeaway_fragments,
				R.array.frozen_fragments);
		fragmentClazzs = activity.getResources().getStringArray(resourceId);
		this.fragments = new Fragment[fragmentClazzs.length];
	}

	@Override
	public Fragment getItem(int index) {
		if (fragments[index] == null) {
			fragments[index] = Fragment.instantiate(activity, fragmentClazzs[index]);
		}

		return fragments[index];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

	public boolean isSelected(Class<?> clazz) {
		return StringUtils.equals(clazz.getName(), fragmentClazzs[activity.getTabIndex()]);
	}

	public void refresh(int index) {
		Fragment item = getItem(index);
		if (item instanceof Refreshable) {
			((Refreshable) item).refresh();
		}
	}
}