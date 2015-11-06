package com.thomas.qmlq.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.thoams.qmlq.BaseActivity;
import com.thomas.qmlq.R;
import com.thomas.qmlq.bean.User;
import com.thomas.qmlq.manager.Constant;
import com.thomas.qmlq.manager.DialogManager;
import com.thomas.qmlq.manager.FontType;
import com.thomas.qmlq.manager.ImageManager;
import com.thomas.qmlq.manager.LogUtil;
import com.thomas.qmlq.manager.MediaManager;
import com.thomas.qmlq.manager.StringUtils;
import com.thomas.qmlq.manager.TitleManager;
import com.thomas.qmlq.view.CircleImageView;

@SuppressLint("HandlerLeak")
public class EditUserInfoActivity extends BaseActivity implements
		OnClickListener {
	private TextView txt_userhead, txt_nickname, tv_nickname, txt_sex, tv_sex,
			txt_city, tv_city, txt_sign, tv_sign;
	private User user;
	private CircleImageView head_iv;
	private Dialog chooseSexDialog, choosePhotoDialog, loadDialog;
	private String strSex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_userinfo);
		findViews();
		init();
		setListener();
	}

	@Override
	public void onRecvData(JsonObject response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		TitleManager.showTitle(this, new int[] { TitleManager.BACK, }, "个人资料",
				this);
		head_iv = (CircleImageView) this.findViewById(R.id.iv_user_head);
		head_iv.setOnClickListener(this);
		txt_userhead = (TextView) this.findViewById(R.id.txt_userhead);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, txt_userhead);
		txt_nickname = (TextView) this.findViewById(R.id.txt_nickname);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, txt_nickname);
		tv_nickname = (TextView) this.findViewById(R.id.tv_nickname);
		tv_nickname.setOnClickListener(this);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, tv_nickname);
		txt_sex = (TextView) this.findViewById(R.id.txt_sex);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, txt_sex);
		tv_sex = (TextView) this.findViewById(R.id.tv_sex);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, tv_sex);
		tv_sex.setOnClickListener(this);
		txt_city = (TextView) this.findViewById(R.id.txt_city);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, txt_city);
		tv_city = (TextView) this.findViewById(R.id.tv_city);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, tv_city);
		txt_sign = (TextView) this.findViewById(R.id.txt_sign);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, txt_sign);
		tv_sign = (TextView) this.findViewById(R.id.tv_sign);
		tv_sign.setOnClickListener(this);
		StringUtils.setTextTypeface(FontType.XIYUAN, this, tv_sign);
		
		choosePhotoDialog = (Dialog) DialogManager.getPhotoActionSheet(this, 
				this);
		
		chooseSexDialog = (Dialog) DialogManager.getSexActionSheet(this,  
				this);
		loadDialog = (Dialog) DialogManager.getLoadingDialog(this);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		user = BmobUser.getCurrentUser(this, User.class);
		if (user.getAvatar() != null) {
			ImageLoader.getInstance().displayImage(
					"http://file.bmob.cn/" + user.getAvatar().getUrl(),
					head_iv, ImageManager.getUserHeadOptions());
		} else {
			head_iv.setImageResource(R.drawable.login_btn_default);
		}

		tv_nickname.setText(user.getNickname());
		if (user.getSex().equals("male")) {
			tv_sex.setText("男");
		} else {
			tv_sex.setText("女");
		}
		tv_city.setText(user.getCity());
		tv_sign.setText(user.getSign());
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (Constant.isUpdate) {
			init();
			LogUtil.e("#####", "重新加载数据");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_back_iv:
			finish();
			break;
		case R.id.iv_user_head:
			choosePhotoDialog.show();
			break;
		case R.id.action_sheet_photo_camera_bt:
			MediaManager.getPhotoFromCamera(this);
			choosePhotoDialog.dismiss();
			break;
		case R.id.action_sheet_photo_album_bt:
			MediaManager.getPhotoFromAlbum(this);
			choosePhotoDialog.dismiss();
			break;
		
		case R.id.tv_sex:
			chooseSexDialog.show();
			break;
		case R.id.action_sheet_male_bt: 
			strSex = "male";
			chooseSexDialog.dismiss();
			updateSex(strSex);
			break;
		case R.id.action_sheet_female_bt: 
			strSex = "female";
			chooseSexDialog.dismiss();
			updateSex(strSex);
			break;
			
		case R.id.tv_nickname:
			startActivity(new Intent(this, EditNicknameActivity.class));
			break;
		case R.id.tv_sign:
			startActivity(new Intent(this, EditSignActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		MediaManager.onActivityResult(this, handler, requestCode, resultCode,
				data);
	}

	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constant.SHOW_PHOTO: {
				String urlPath = msg.obj.toString();
				LogUtil.e("#####", "图片地址###" + urlPath);
				ImageManager.getInstance().displayImage("file:///" + urlPath,
						head_iv, ImageManager.getUserHeadOptions());
				saveUserHead(urlPath);
				break;
			}
			}
		}
	};

	/**
	 * 上传用户头像
	 * 
	 * @param path
	 */
	private void saveUserHead(String path) {
		loadDialog.show();
		final BmobFile headFile = new BmobFile(new File(path));
		headFile.upload(this, new UploadFileListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				LogUtil.e("####", "头像上传成功。");
				Constant.isUpdate = true;
				user.setAvatar(headFile);
				user.update(EditUserInfoActivity.this, new UpdateListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						LogUtil.e("####", "更新头像成功");
						loadDialog.dismiss();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						LogUtil.e("####", "更新头像失败。");
						loadDialog.dismiss();
					}
				});
			}

			@Override
			public void onProgress(Integer arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(int arg0, String msg) {
				// TODO Auto-generated method stub
				LogUtil.e("####", "头像上传失败。" + msg);
				loadDialog.dismiss();
			}
		});
	}
	
	private void updateSex(final String str_Sex){
		user.setSex(str_Sex);
		user.update(EditUserInfoActivity.this, new UpdateListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				LogUtil.e("####", "更新性别成功"); 
				if (str_Sex.equals("male")) {
					tv_sex.setText("男");
				} else {
					tv_sex.setText("女");
				}
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				LogUtil.e("####", "更新性别失败。"); 
			}
		});
	}
	
}
