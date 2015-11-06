package com.thomas.qmlq.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.thomas.qmlq.R;
import com.thomas.qmlq.bean.TrainVedio;

public class ListVedioAdapter extends BaseAdapter {
	private List<TrainVedio> mData;
	private LayoutInflater inflater;
	private Context context;

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public ListVedioAdapter(Context context, List<TrainVedio> mData) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.mData = mData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			/* 绑定相应的视图 */
			convertView = inflater.inflate(R.layout.item_vedio_list, null);
			holder.imgViewVedio = (ImageView) convertView
					.findViewById(R.id.iv_vedio_img);
			holder.ImageViewPlay = (ImageView) convertView
					.findViewById(R.id.iv_play_img);
			holder.ImageViewPause = (ImageView) convertView
					.findViewById(R.id.iv_pause_img); 
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// TODO 异步加载图片 
		return convertView;
	}

	/**
	 * 自定义类 绑定视图
	 * 
	 * @author zwt
	 */
	final class ViewHolder {
		public ImageView imgViewVedio,ImageViewPlay,ImageViewPause; 
	}

}
