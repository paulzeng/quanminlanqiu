package com.thomas.qmlq.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.thoams.qmlq.BaseActivity;
import com.thomas.qmlq.R;
import com.thomas.qmlq.manager.ImageManager;
import com.thomas.qmlq.manager.LogUtil;
import com.thomas.qmlq.manager.TitleManager;

public class ImageDetailActivity extends BaseActivity implements
		OnClickListener {

	private ImageView imgview;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_detail);
		findViews();
	}

	@Override
	public void onRecvData(JsonObject response) {

	}

	@Override
	public void findViews() {
		TitleManager.showTitle(this, new int[] { TitleManager.BACK },
				"", this);
		url = getIntent().getStringExtra("url");
		imgview = (ImageView) findViewById(R.id.iv_detail_img);
		ImageAware imageAware = new ImageViewAware(imgview, false);
		ImageLoader.getInstance().displayImage(url, imageAware,
				ImageManager.getPanoOptions(), new MyImageLoadingListener());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}

	private class MyImageLoadingListener implements ImageLoadingListener {
		@Override
		public void onLoadingCancelled(String msg, View view) {
			// TODO Auto-generated method stub
			// 加载取消的时候执行
			LogUtil.e("HomeActivity", "加载取消的时候执行");
		}

		@Override
		public void onLoadingComplete(String msg, View view, Bitmap loadedImage) {
			// TODO Auto-generated method stub
			// 加载成功的时候执行
			LogUtil.e("HomeActivity", "加载成功的时候执行");
		}

		@Override
		public void onLoadingFailed(String msg, View view, FailReason failReason) {
			// TODO Auto-generated method stub
			// 加载失败的时候执行
			LogUtil.e("HomeActivity", "加载失败的时候执行");
		}

		@Override
		public void onLoadingStarted(String msg, View view) {
			// TODO Auto-generated method stub
			// 开始加载的时候执行
			LogUtil.e("HomeActivity", "开始加载的时候执行");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back_iv:
			finish();
			break;
		}
	}

}
