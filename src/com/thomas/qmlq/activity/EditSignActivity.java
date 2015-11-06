package com.thomas.qmlq.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.google.gson.JsonObject;
import com.thoams.qmlq.BaseActivity;
import com.thoams.qmlq.BaseApplication;
import com.thomas.qmlq.R;
import com.thomas.qmlq.bean.User;
import com.thomas.qmlq.manager.Constant;
import com.thomas.qmlq.manager.DialogManager;
import com.thomas.qmlq.manager.FontType;
import com.thomas.qmlq.manager.StringUtils;
import com.thomas.qmlq.manager.TitleManager;
import com.thomas.qmlq.manager.ToastManager;

public class EditSignActivity extends BaseActivity implements OnClickListener {
	private EditText edtSign;
	private ImageView imgClear;
	private Dialog dialog;
	private User cUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_sign);
		init();
		findViews();
		setListener();
	}

	@Override
	public void onRecvData(JsonObject response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		TitleManager.showTitle(this, new int[] { TitleManager.BACK, }, "签名", 0,
				R.string.txt_save, this);
		edtSign = (EditText) this.findViewById(R.id.edt_sign);
		edtSign.setSelection(edtSign.getText().length());
		edtSign.setText(cUser.getSign());
		StringUtils.setEditTextTypeface(FontType.XIYUAN, this, edtSign);
		imgClear = (ImageView) this.findViewById(R.id.iv_clear);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		dialog = DialogManager.getLoadingDialog(this);
		cUser = BmobUser.getCurrentUser(this, User.class);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		imgClear.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_back_iv:
			finish();
			break;
		case R.id.iv_clear:
			edtSign.setText("");
			break;
		case R.id.title_next_tv:
			if (edtSign.getText().toString().trim().equals("")) {
				ToastManager.show(this, "请输入您的签名");
			} else {
				dialog.show();
				saveSign(edtSign.getText().toString().trim());
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 保存用户名
	 * 
	 * @param nickname
	 */
	private void saveSign(String sign) {
		User user = new User();
		user.setSign(sign);
		user.update(this, BaseApplication.mInstance.getUserInfo(this)
				.getObjectId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				dialog.dismiss();
				ToastManager.show(EditSignActivity.this, "修改成功");
				BaseApplication.mInstance.getUserInfo(EditSignActivity.this)
						.setSign(edtSign.getText().toString().trim());
				Constant.isUpdate = false;
				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				ToastManager.show(EditSignActivity.this, "修改失败");
			}
		});
	}
}
