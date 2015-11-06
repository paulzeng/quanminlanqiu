package com.thoams.qmlq;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.thomas.qmlq.manager.Constant;
import com.thomas.qmlq.manager.LogUtil;
import com.thomas.qmlq.view.CustomAlertDialog;
import com.thomas.qmlq.view.CustomProgressDialog;
import com.thomas.qmlq.volley.GsonRequest;
import com.thomas.qmlq.volley.RequestManager;

public abstract class BaseActivity extends FragmentActivity {
	protected CustomAlertDialog alertDialog;
	// 加载loading框
	private CustomProgressDialog progressdialog;
	// volley消息队列
	protected RequestQueue queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initVolley(this);
		queue = RequestManager.getRequestQueue();
		// 将所有Activity加入任务栈中
		BaseApplication.allActivity.add(this);
	}

	public void initVolley(Context context) {
		RequestManager.init(context);
	}

	/**
	 * 网络请求返回处理方法<json>
	 * 
	 * @param response
	 */
	public abstract void onRecvData(JsonObject response);

	/**
	 * 初始化控件
	 */
	public abstract void findViews();

	/**
	 * 初始化数据
	 */
	public abstract void init();

	/**
	 * 设置控件监听事件
	 */
	public abstract void setListener();

	// 默认不显示加载进度框，错误提示，不能取消请求
	public static final int FLAG_DEFAULT = 0;
	// 是否显示加载进度框
	public static final int FLAG_SHOW_PROGRESS = 1;
	// 是否显示错误提示
	public static final int FLAG_SHOW_ERROR = 2;
	// 是否当关闭进度框时取消相应请求
	public static final int FLAG_CANCEL = 4;
	// 是否加密请求
	public static final int FLAG_ENCRYPT = 8;

	/**
	 * 请求JSON数据
	 * 
	 * @param url
	 *            请求接口路径
	 * @param params
	 *            请求参数
	 */
	protected <T> void request(String url, HashMap<String, String> params) {
		request(url, params, FLAG_DEFAULT);
	}

	/**
	 * 请求JSON数据
	 * 
	 * @param url
	 *            请求接口路径
	 * @param params
	 *            请求参数
	 * @param flags
	 *            FLAG_DEFAULT,FLAG_SHOW_PROGRESS,FLAG_SHOW_ERROR,FLAG_CANCEL
	 */
	public <T> void request(String url, HashMap<String, String> params,
			int flags) {
		boolean isShowProgress = (flags & FLAG_SHOW_PROGRESS) != 0;
		boolean isShowError = (flags & FLAG_SHOW_ERROR) != 0;
		boolean cancelable = (flags & FLAG_CANCEL) != 0;
		boolean isEncrypt = (flags & FLAG_ENCRYPT) != 0;

		request(url, params, isShowProgress, isShowError, cancelable, isEncrypt);
	}

	/**
	 * 请求JSON数据
	 * 
	 * @param url
	 *            请求接口路径
	 * @param params
	 *            请求参数
	 * @param isShowProgress
	 *            是否显示加载进度框
	 * @param isShowError
	 *            是否显示错误提示
	 * @param cancelable
	 *            是否当关闭进度框时取消相应请求
	 * @param isEncrypt
	 *            是否加密请求
	 */
	private <T> void request(String url, final HashMap<String, String> params,
			boolean isShowProgress, final boolean isShowError,
			boolean cancelable, final boolean isEncrypt) {
		final String sParams = params != null ? new Gson().toJson(params) : "";
		final String finalUrl = Constant.API_URL_PREFIX + url;
		LogUtil.i("volley-request", finalUrl + "[" + sParams + "]");
		final GsonRequest<JsonObject> request = new GsonRequest<JsonObject>(
				Request.Method.POST, finalUrl, JsonObject.class,
				new Response.Listener<JsonObject>() {
					@Override
					public void onResponse(JsonObject response) {
						LogUtil.e("volley-response", "[" + response.toString()
								+ "]");
						if (progressdialog != null
								&& progressdialog.isShowing())
							progressdialog.dismiss();
						try {
							if (response.get("result").getAsInt() == 0) {
								onRecvData(response);
							} else if (response.get("result").getAsInt() == 2) {
								Toast.makeText(BaseActivity.this,
										"身份认证已过期，请重新登录", Toast.LENGTH_LONG)
										.show();
							} else if (response.get("result").getAsInt() == 3) {
								Toast.makeText(BaseActivity.this,
										"身份认证不存在，请重新登录", Toast.LENGTH_LONG)
										.show();
							} else if (isShowError) {
								String code = response.get("code")
										.getAsString();
								String message = response.get("message")
										.getAsString();
								showAlertDialog("提示", code + message,
										new View.OnClickListener() {
											@Override
											public void onClick(View arg0) {
												alertDialog.cancel();
											}
										}, "确定", null, null);
							}
						} catch (Exception e) {
							if (isShowError) {
								showAlertDialog("提示", "数据解析失败",
										new View.OnClickListener() {
											@Override
											public void onClick(View arg0) {
												alertDialog.cancel();
											}
										}, "确定", null, null);
							}
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						LogUtil.i("info", volleyError.toString());
						if (progressdialog != null
								&& progressdialog.isShowing())
							progressdialog.dismiss();
						if (!isFinishing()) {
							if (isShowError) {
								showAlertDialog("提示", "数据请求失败",
										new View.OnClickListener() {
											@Override
											public void onClick(View arg0) {
												alertDialog.cancel();
											}
										}, "确定", null, null);
							}

						}
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = super.getHeaders();
				if (isEncrypt) {
					// 不指定为json，防止appserver误解析
					headers.put("Content-Type", "text/html; charset=UTF-8");
				}
				return headers;
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// TODO Auto-generated method stub
				return params;
			}
		};

		if (isShowProgress) {
			this.progressdialog = new CustomProgressDialog(this,
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							request.cancel();
						}
					}, cancelable);
			this.progressdialog.show();
		}

		RequestManager.addRequest(request);
	}

	public void showAlertDialog(String title, String msg,
			OnClickListener onPositiveListener,
			OnClickListener onNegativeListener) {
		if (alertDialog != null) {
			alertDialog.dismiss();
		}
		alertDialog = new CustomAlertDialog(this);
		alertDialog.show();
		alertDialog.setTitle(title);
		alertDialog.setMessage(msg);
		if (onPositiveListener != null) {
			alertDialog.setOnPositiveListener(onPositiveListener);
		} else {
			alertDialog.getBtn_positive().setVisibility(View.GONE);
		}
		if (onNegativeListener != null) {
			alertDialog.setOnNegativeListener(onNegativeListener);
		} else {
			alertDialog.getBtn_negative().setVisibility(View.GONE);
		}
	}

	public void showAlertDialog(String title, String msg,
			OnClickListener onPositiveListener, String positiveStr,
			String negativeStr, OnClickListener onNegativeListener) {
		if (alertDialog != null) {
			alertDialog.dismiss();
		}
		alertDialog = new CustomAlertDialog(this);
		alertDialog.show();
		alertDialog.setTitle(title);
		alertDialog.setMessage(msg);
		if (onPositiveListener != null) {
			alertDialog.setOnPositiveListener(onPositiveListener);
			if (positiveStr != null)
				alertDialog.getBtn_positive().setText(positiveStr);
		} else {
			alertDialog.getBtn_positive().setVisibility(View.GONE);
		}
		if (onNegativeListener != null) {
			alertDialog.setOnNegativeListener(onNegativeListener);
			if (negativeStr != null)
				alertDialog.getBtn_negative().setText(negativeStr);
		} else {
			alertDialog.getBtn_negative().setVisibility(View.GONE);
		}
	}

	public void showAlertDialogNormal(String title, String msg) {
		if (alertDialog != null) {
			alertDialog.dismiss();
		}
		alertDialog = new CustomAlertDialog(this);
		alertDialog.show();
		alertDialog.setTitle(title);
		alertDialog.setMessage(msg);
		alertDialog.setOnPositiveListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
		alertDialog.getBtn_negative().setVisibility(View.GONE);

	}

	public void showAlertDialogNormal(String title, String msg, boolean isNew) {
		if (alertDialog == null
				|| (alertDialog != null && !alertDialog.isShowing())) {
			if (isNew) {
				final CustomAlertDialog alertDialog = new CustomAlertDialog(
						this);
				alertDialog.show();
				alertDialog.setTitle(title);
				alertDialog.setMessage(msg);
				alertDialog.setOnPositiveListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						alertDialog.dismiss();
					}
				});
				alertDialog.getBtn_negative().setVisibility(View.GONE);
			}
		}
	}

}
