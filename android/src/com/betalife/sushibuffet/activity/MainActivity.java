package com.betalife.sushibuffet.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

public class MainActivity extends FragmentActivity implements TabListener {

	ViewPager viewPager;
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		// requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
					Log.d("dodoro", "on PageScrollStateChanged IDLE");
				}
				if (arg0 == ViewPager.SCROLL_STATE_DRAGGING) {
					Log.d("dodoro", "OnPageScrollStateChanged Dragging");
				}
				if (arg0 == ViewPager.SCROLL_STATE_SETTLING) {
					Log.d("dodoro", "OnPageScrollStateChanged Setting");
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				// Log.d("dodoro","onPageScrolled at"+" position "+arg0+" from "+arg1+" with number of pixels = "+arg2);
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
				// Log.d("dodoro","onPageSelected at"+" position "+arg0);
			}

		});
		actionBar = getActionBar();
		if (actionBar == null) {
			Log.e("dodoro", "actionBar is null++++++++++++++");

			Log.e("dodoro", "actionBar is null++++++++++++++" + isChild());
			Window window = getWindow();
			Log.e("dodoro", "actionBar is null++++++++++++++" + !window.hasFeature(Window.FEATURE_ACTION_BAR));
		}

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setCustomView(R.layout.abs_layout);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));
		View homeIcon = findViewById(android.R.id.home);
		((View) homeIcon.getParent()).setVisibility(View.GONE);

		ActionBar.Tab tab1 = actionBar.newTab();
		// tab1.setIcon(new IconDrawable(this,
		// IconValue.fa_cutlery).colorRes(R.color.gold).actionBarSize());
		tab1.setText("123456");
		tab1.setIcon(R.drawable.logo_small);
		tab1.setTabListener(this);

		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setIcon(new IconDrawable(this, IconValue.fa_history).colorRes(R.color.gold).actionBarSize());
		tab2.setTabListener(this);

		ActionBar.Tab tab3 = actionBar.newTab();
		tab3.setIcon(new IconDrawable(this, IconValue.fa_info_circle).colorRes(R.color.gold).actionBarSize());
		tab3.setTabListener(this);

		ActionBar.Tab tab4 = actionBar.newTab();
		tab4.setIcon(new IconDrawable(this, IconValue.fa_globe).colorRes(R.color.gold).actionBarSize());
		tab4.setTabListener(this);

		ActionBar.Tab tab5 = actionBar.newTab();
		tab5.setIcon(new IconDrawable(this, IconValue.fa_cog).colorRes(R.color.gold).actionBarSize());
		tab5.setTabListener(this);

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		actionBar.addTab(tab3);
		actionBar.addTab(tab4);
		actionBar.addTab(tab5);

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Log.d("dodoro","onTabReselected at"+" position "+tab.getPosition()+" name "+tab.getText()
		// );

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// Log.d("dodoro","onTabSelected at"+" position "+tab.getPosition()+" name "+tab.getText()
		// );
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// Log.d("dodoro","onTabUnselected at"+" position "+tab.getPosition()+" name "+tab.getText()
		// );

	}

}

class MyAdapter extends FragmentPagerAdapter {

	public MyAdapter(FragmentManager fm) {
		super(fm);

		// TODO Auto-generated constructor stub

	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = null;

		if (arg0 == 0) {
			fragment = new FragmentOrderpage();
		}
		if (arg0 == 1) {
			fragment = new FragmentHistory();
		}
		if (arg0 == 2) {
			fragment = new FragmentIntroduction();
		}
		if (arg0 == 3) {
			fragment = new FragmentLang();
		}
		if (arg0 == 4) {
			fragment = new FragmentSetting();
		}

		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

}
