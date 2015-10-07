package com.betalife.sushibuffet.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.betalife.sushibuffet.adapter.FragmentsAdapter;
import com.betalife.sushibuffet.util.DodoroContext;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

public class MainActivity extends FragmentActivity {

	public static final String SELECTED_NAVIGATION_INDEX = "SelectedNavigationIndex";
	private ViewPager viewPager;
	private ActionBar actionBar;
	private FragmentsAdapter adapter;
	private boolean onCreate = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);

		setOnCreate(true);

		viewPager = (ViewPager) findViewById(R.id.pager);
		adapter = new FragmentsAdapter(this);
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(adapter.getCount());
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);

				adapter.refresh(arg0);
			}

		});

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// 设置ActionBar左边默认的图标是否可用
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		// 设置ActionBar标题不显示
		actionBar.setDisplayShowTitleEnabled(false);
		// 设置ActionBar自定义布局
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(R.layout.abs_layout);
		// 设置导航模式为Tab选项标签导航模式

		// actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
		// ActionBar.DISPLAY_SHOW_CUSTOM);
		View homeIcon = findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home
				: R.id.FragmentHeader);
		((View) homeIcon.getParent()).setVisibility(View.GONE);
		homeIcon.setVisibility(View.GONE);

		// actionBar.setCustomView(R.layout.abs_layout);
		// actionBar.setDisplayShowHomeEnabled(true);
		// actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));
		// View homeIcon = findViewById(android.R.id.home);
		// ((View) homeIcon.getParent()).setVisibility(View.GONE);
		TabListener tabListener = new TabListener() {

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				int position = tab.getPosition();
				viewPager.setCurrentItem(position);
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			}
		};
		int resourceId = DodoroContext.getInstance().which(R.array.tabs, R.array.tabs, R.array.takeaway_tabs);
		String[] tabIcons = getResources().getStringArray(resourceId);
		for (int i = 0; i < tabIcons.length; i++) {
			String icon = tabIcons[i];
			ActionBar.Tab tab = actionBar.newTab();
			tab.setIcon(new IconDrawable(this, IconValue.valueOf(icon)).colorRes(R.color.gold)
					.actionBarSize());
			tab.setTabListener(tabListener);
			actionBar.addTab(tab);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		int index = 0;
		Intent intent = getIntent();
		if (intent != null) {
			index = intent.getIntExtra(SELECTED_NAVIGATION_INDEX, 0);
		}
		changeTab(index);
		// adapter.refresh(index);
		adapter.notifyDataSetChanged();// 通知界面更新
	}

	public void changeTab(int index) {
		actionBar.getTabAt(index).select();
	}

	public int getTabIndex() {
		return actionBar.getSelectedNavigationIndex();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Intent intent = new Intent();
		intent.putExtra(SELECTED_NAVIGATION_INDEX, actionBar.getSelectedNavigationIndex());
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			changeTab(0);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public FragmentsAdapter getAdapter() {
		return adapter;
	}

	public boolean isOnCreate() {
		return onCreate;
	}

	public void setOnCreate(boolean onCreate) {
		this.onCreate = onCreate;
	}
}