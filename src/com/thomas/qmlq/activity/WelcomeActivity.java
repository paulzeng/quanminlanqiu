package com.thomas.qmlq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.thoams.qmlq.BaseActivity;
import com.thoams.qmlq.BaseApplication;
import com.thomas.qmlq.R;
import com.thomas.qmlq.manager.FontType;
import com.thomas.qmlq.manager.StringUtils;

public class WelcomeActivity extends BaseActivity implements OnClickListener {
	private Button btnLogin, btnRegister;
	private LinearLayout login_layout;
	private TextView tv_welcome,tv_slogon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		if (BaseApplication.mInstance.getUserInfo(this).isLogin()) {
			startActivity(new Intent(this, SplashActivity.class));
			return;
		}
		setContentView(R.layout.activity_welcome);
		findViews();
		setListener();
		init();
	}

	public void showLoginLayout() {
		login_layout.setVisibility(View.VISIBLE);
		login_layout.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.view_up));
	}

	@Override
	public void onRecvData(JsonObject response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		btnLogin = (Button) this.findViewById(R.id.btn_login);
		btnRegister = (Button) this.findViewById(R.id.btn_register);
		login_layout = (LinearLayout) findViewById(R.id.login_layout);
		login_layout.setVisibility(View.INVISIBLE);
		tv_welcome = (TextView) this.findViewById(R.id.tv_welcome);
		tv_slogon= (TextView) this.findViewById(R.id.tv_slogon);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showLoginLayout();
		StringUtils.setButtonTypeface(FontType.XIYUAN, this, btnLogin);
		StringUtils.setButtonTypeface(FontType.XIYUAN, this, btnRegister);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, tv_welcome);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, tv_slogon);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		tv_welcome.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		BaseApplication.mInstance.exit();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_login:
			startActivity(new Intent(this, LoginActivity.class));
			break;
		case R.id.btn_register:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.tv_welcome:
			startActivity(new Intent(this, MainActivity.class));
			finish();
			break;

		default:
			break;
		}
	}

}
