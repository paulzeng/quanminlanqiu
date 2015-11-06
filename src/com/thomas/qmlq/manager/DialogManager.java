package com.thomas.qmlq.manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.thomas.qmlq.R;

public class DialogManager {
	public static Dialog getLoadingDialog(Context context) {
		return getLoadingDialog(context, R.string.dialog_loading);
	}

	public static Dialog getLoadingDialog(Context context, Object loadingTextRes) {
		final Dialog dialog = new Dialog(context, R.style.netLoadingDialog);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.custom_progress_dialog);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = getScreenWidth(context) - dpToPx(context, 100);
		window.setGravity(Gravity.CENTER_VERTICAL);
		TextView titleTxtv = (TextView) dialog.findViewById(R.id.dialog_tv);
		StringUtils.setTextTypeface(FontType.XIYUAN, context, titleTxtv);
		if (loadingTextRes instanceof String) {
			titleTxtv.setText((String) loadingTextRes);
		}
		if (loadingTextRes instanceof Integer) {
			titleTxtv.setText((Integer) loadingTextRes);
		}

		return dialog;
	}

	public static void toggleDialog(Dialog loadDialog) {
		if (loadDialog != null && loadDialog.isShowing()) {
			loadDialog.dismiss();
		}

	}

	public static int dpToPx(Context context, int dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return Math.round(dpValue * scale);
	}

	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	public static Dialog getCommWarnDialog(Context context, final OnClickListener l) {
		final Dialog dialog = new Dialog(context, R.style.float_base);
		View view = View.inflate(context, R.layout.layout_common_dialog, null);
		TextView title = (TextView) view.findViewById(R.id.warn_title_tv);
		StringUtils.setTextTypeface(FontType.XIYUAN, context, title);
		
		TextView content = (TextView) view.findViewById(R.id.warn_content_tv);
		StringUtils.setTextTypeface(FontType.XIYUAN, context, content);
		
		Button btn_sure = (Button) view.findViewById(R.id.warn_sure_bt);
		StringUtils.setButtonTypeface(FontType.XIYUAN, context, btn_sure);
		
		Button btn_cancle = (Button) view.findViewById(R.id.warn_cancle_bt); 
		StringUtils.setButtonTypeface(FontType.XIYUAN, context, btn_cancle);
		
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(true);
		view.findViewById(R.id.warn_sure_bt).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						l.onClick(v);
						dialog.dismiss();
					}
				});
		view.findViewById(R.id.warn_cancle_bt).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = getScreenWidth(context) - dpToPx(context, 50);
		window.setGravity(Gravity.CENTER_VERTICAL);

		return dialog;
	}
	

	 /**
	 * action sheet dialog
	 * 
	 * @param context
	 * @param view
	 * @return
	 */

	public static Dialog getPhotoActionSheet(Context context, OnClickListener l) {
		final Dialog dialog = new Dialog(context, R.style.action_sheet);
		View view = View.inflate(context, R.layout.layout_actionsheet_up_head,
				null);
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(true);
		Button btnCancel = (Button) view.findViewById(R.id.action_sheet_cancle_bt);
		StringUtils.setButtonTypeface(FontType.XIYUAN, context, btnCancel);
		btnCancel.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		
		Button btnCamera = (Button) view.findViewById(R.id.action_sheet_photo_camera_bt);
		StringUtils.setButtonTypeface(FontType.XIYUAN, context, btnCamera);
		btnCamera.setOnClickListener(l);
		
		Button btnAlbum = (Button) view.findViewById(R.id.action_sheet_photo_album_bt);
		StringUtils.setButtonTypeface(FontType.XIYUAN, context, btnAlbum);
		btnAlbum.setOnClickListener(l);
		
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		int screenW = getScreenWidth(context);
		lp.width = screenW;
		window.setGravity(Gravity.BOTTOM);  
		window.setWindowAnimations(R.style.action_sheet_animation);  
		return dialog;
	}
	
	 /**
		 * action sheet dialog
		 * 
		 * @param context
		 * @param view
		 * @return
		 */

		public static Dialog getSexActionSheet(Context context, OnClickListener l) {
			final Dialog dialog = new Dialog(context, R.style.action_sheet);
			View view = View.inflate(context, R.layout.layout_actionsheet_choose_sex,
					null);
			dialog.setContentView(view);
			dialog.setCanceledOnTouchOutside(true);
			Button btnCancel = (Button) view.findViewById(R.id.action_sheet_cancle_bt);
			StringUtils.setButtonTypeface(FontType.XIYUAN, context, btnCancel);
			btnCancel.setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			
			Button btnCamera = (Button) view.findViewById(R.id.action_sheet_male_bt);
			StringUtils.setButtonTypeface(FontType.XIYUAN, context, btnCamera);
			btnCamera.setOnClickListener(l);
			
			Button btnAlbum = (Button) view.findViewById(R.id.action_sheet_female_bt);
			StringUtils.setButtonTypeface(FontType.XIYUAN, context, btnAlbum);
			btnAlbum.setOnClickListener(l);
			
			Window window = dialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			int screenW = getScreenWidth(context);
			lp.width = screenW;
			window.setGravity(Gravity.BOTTOM);  
			window.setWindowAnimations(R.style.action_sheet_animation);  
			return dialog;
		}
}
