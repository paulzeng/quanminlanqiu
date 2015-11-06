package com.thomas.qmlq.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.gson.JsonObject;
import com.thoams.qmlq.BaseActivity;
import com.thoams.qmlq.BaseApplication;
import com.thomas.qmlq.R;
import com.thomas.qmlq.fragment.DiscoverFragment;
import com.thomas.qmlq.fragment.MyFragment;
import com.thomas.qmlq.fragment.TrainFragment;
import com.thomas.qmlq.fragment.VenuesFragment;
import com.thomas.qmlq.manager.TitleManager;
import com.thomas.qmlq.manager.ToastManager;
import com.thomas.qmlq.view.ChangeColorIconWithTextView;
import com.thomas.qmlq.view.ControlScrollViewPager;

public class MainActivity extends BaseActivity implements OnPageChangeListener,
		OnClickListener {
	private ControlScrollViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

	private long mExitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
		init();
		setListener();

	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		TitleManager.showTitle(this, new int[] { TitleManager.SET },
				R.string.app_name, 0, 0, this);
		mViewPager = (ControlScrollViewPager) findViewById(R.id.id_viewpager);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		mTabs.add(new TrainFragment());
		mTabs.add(new VenuesFragment());
		mTabs.add(new DiscoverFragment());
		mTabs.add(new MyFragment());

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mTabs.get(arg0);
			}
		};

		initTabIndicator();
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	private void initTabIndicator() {
		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
		ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

		one.setIconAlpha(1.0f);
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_indicator_one:
			resetOtherTabs();
			mTabIndicator.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			resetOtherTabs();
			mTabIndicator.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			resetOtherTabs();
			mTabIndicator.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			resetOtherTabs();
			mTabIndicator.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;
		}
	}

	/**
	 * 重置其他的Tab
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}

	@Override
	public void onRecvData(JsonObject response) {
		// TODO Auto-generated method stub

	}

	/**
	 * 连续点击退出
	 * 
	 * @Title: onKeyDown
	 * @Description: TODO
	 * @Calls: TODO
	 * @CalledBy: TODO
	 * @Input:@param keyCode
	 * @Input:@param event
	 * @Input:@return
	 * @Date: 下午3:20:51
	 * 
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				ToastManager.show(this, "再按一次退出程序");
				mExitTime = System.currentTimeMillis();
			} else {
				BaseApplication.mInstance.exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
