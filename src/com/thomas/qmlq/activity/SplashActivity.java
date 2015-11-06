package com.thomas.qmlq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.JsonObject;
import com.thoams.qmlq.BaseActivity;
import com.thomas.qmlq.R;

/**
 * 启动页面 Copyright © 2015 aite. All rights reserved.
 * 
 * @Title: SplashActivity.java
 * @author: pc @version: V1.0 @date: 2015-11-5 上午11:17:53
 * @Description: TODO
 * @Prject: SmartLife
 * @Package: com.atelen.smartlife.ui
 * @FunctionList: TODO
 * @History: TODO
 * 
 */
public class SplashActivity extends BaseActivity {

	private static final int ACTIVITY_TIMEOUT_GOTO_NEXT = 0;
	private static final int ACTIVITY_TIME = 2000;
	private Window mWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mWindow = getWindow();
		WindowManager.LayoutParams params = mWindow.getAttributes();
		params.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		mWindow.setAttributes(params);

		setContentView(R.layout.splash_activity_layout);
		mHandler.sendEmptyMessageDelayed(ACTIVITY_TIMEOUT_GOTO_NEXT,
				ACTIVITY_TIME);
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeMessages(ACTIVITY_TIMEOUT_GOTO_NEXT);
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ACTIVITY_TIMEOUT_GOTO_NEXT:
				startActivity(new Intent(SplashActivity.this,
						MainActivity.class));
				SplashActivity.this.finish();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRecvData(JsonObject response) {
		// TODO Auto-generated method stub

	}

}
