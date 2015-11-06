package com.thomas.qmlq.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.thomas.qmlq.R;

public class TrainFragment extends BaseFragment implements OnClickListener {
	private View mView;
	private ListView lstViewTrain;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_train, container, false);
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		findViews();
	}

	protected void findViews() {
		lstViewTrain = (ListView) mView.findViewById(R.id.lstViewTrain);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
