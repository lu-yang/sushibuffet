package com.betalife.sushibuffet.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

public class MainActivity extends FragmentActivity implements Callback {

	private static final String SELECTED_NAVIGATION_INDEX = "SelectedNavigationIndex";
	private ViewPager viewPager;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.pager);
		final FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);

		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);

				Fragment item = adapter.getItem(arg0);
				if (item instanceof Refreshable) {
					((Refreshable) item).refresh();
				}
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
		((View) homeIcon).setVisibility(View.GONE);

		// actionBar.setCustomView(R.layout.abs_layout);
		// actionBar.setDisplayShowHomeEnabled(true);
		// actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));
		// View homeIcon = findViewById(android.R.id.home);
		// ((View) homeIcon.getParent()).setVisibility(View.GONE);
		String[] tabIcons = getResources().getStringArray(R.array.tabs);
		String[] fragments = getResources().getStringArray(R.array.fragments);
		for (int i = 0; i < tabIcons.length; i++) {
			Fragment fragment = Fragment.instantiate(this, fragments[i]);
			adapter.addFragment(fragment);

			String icon = tabIcons[i];
			ActionBar.Tab tab = actionBar.newTab();
			tab.setIcon(new IconDrawable(this, IconValue.valueOf(icon)).colorRes(R.color.gold)
					.actionBarSize());
			tab.setTabListener(new TabListener() {

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
			});
			actionBar.addTab(tab);
			adapter.notifyDataSetChanged();// 通知界面更新
		}
		int index = 0;
		if (arg0 != null) {
			index = arg0.getInt(SELECTED_NAVIGATION_INDEX, 0);
		}
		changeTab(index);
		viewPager.setOffscreenPageLimit(adapter.getCount());
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

	private class FragmentsAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments;

		public FragmentsAdapter(FragmentManager fm) {
			super(fm);
			this.fragments = new ArrayList<Fragment>();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		public void addFragment(Fragment fragment) {
			fragments.add(fragment);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

	@Override
	public void callback() {
		Intent intent = new Intent();
		intent.setClass(this, SettingActivity.class);
		startActivity(intent);
	}
}