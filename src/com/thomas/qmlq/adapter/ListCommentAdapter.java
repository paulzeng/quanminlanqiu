package com.thomas.qmlq.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.thomas.qmlq.R;
import com.thomas.qmlq.bean.Comment;
import com.thomas.qmlq.manager.FontType;
import com.thomas.qmlq.manager.ImageManager;
import com.thomas.qmlq.manager.LogUtil;
import com.thomas.qmlq.manager.StringUtils;
import com.thomas.qmlq.view.CircleImageView;

public class ListCommentAdapter  extends BaseAdapter {
	private List<Comment> mData;
	private LayoutInflater inflater;
	private Context context;
	private ListItemClickHelp callback;

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public ListCommentAdapter(Context context, List<Comment> mData,
			ListItemClickHelp callback) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.mData = mData;
		this.callback = callback;
	}

	public void setList(List<Comment> mData){
		this.mData = mData;
		notifyDataSetChanged();
	}
	
	public void addList(Comment comment){
		mData.add(comment);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			/* 绑定相应的视图 */
			convertView = inflater
					.inflate(R.layout.item_comment_list, null);
			holder.imgViewHead = (CircleImageView) convertView
					.findViewById(R.id.iv_user_comment_img);
			holder.txtViewName = (TextView) convertView.findViewById(R.id.tv_comment_user);
			holder.txtViewContent = (TextView) convertView.findViewById(R.id.tv_comment_content);
			holder.txtViewDate = (TextView) convertView.findViewById(R.id.tv_comment_date);
			StringUtils.setTextTypeface(FontType.XIYUAN, context, holder.txtViewName);
			StringUtils.setTextTypeface(FontType.XIYUAN, context, holder.txtViewContent);
			StringUtils.setTextTypeface(FontType.XIYUAN, context, holder.txtViewDate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(mData.get(position).getUser()!=null){
			holder.txtViewName.setText(mData.get(position).getUser().getNickname());
			ImageLoader.getInstance().displayImage(
					"http://file.bmob.cn/"
							+ mData.get(position).getUser().getAvatar()
									.getUrl(), holder.imgViewHead,
					ImageManager.getUserHeadOptions());
		}
		if(mData.get(position).getContent()!=null){
			holder.txtViewContent.setText(mData.get(position).getContent());
		}
		
		// TODO 异步加载图片
		final View view = convertView;
		final int p = position;
		final int one = holder.imgViewHead.getId();
		holder.txtViewDate.setText(mData.get(position).getCreatedAt());
		holder.imgViewHead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callback.onClick(view, parent, p, one);				
			}
		});
		
		return convertView;
	}

	/**
	 * 自定义类 绑定视图
	 * 
	 * @author zwt
	 */
	final class ViewHolder {
		public CircleImageView imgViewHead;
		public TextView txtViewName, txtViewContent,txtViewDate;
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
}

