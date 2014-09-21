package com.betalife.sushibuffet.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends Activity {
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		actionBar = getActionBar();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
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
		View homeIcon = findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home
				: R.id.FragmentHeader);
		((View) homeIcon.getParent()).setVisibility(View.GONE);
		((View) homeIcon).setVisibility(View.GONE);
	}

}
