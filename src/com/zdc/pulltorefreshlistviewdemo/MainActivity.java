package com.zdc.pulltorefreshlistviewdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.zdc.pulltorefreshlistviewdemo.PullToRefreshListView.OnRefreshingListener;

public class MainActivity extends Activity {

	protected static final int REFRESH = 0;
	protected static final int LOADMORE = 1;
	private Context mContext;
	private PullToRefreshListView prlv;
	private ArrayList<String> strs;
	private ArrayAdapter<String> adapter;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH:
				strs.clear();
				for (int i = 0; i < 20; i++) {
					strs.add("新一批幸运观众:234792468" + i);
				}
				prlv.setOnRefreshCompleted(true);
				break;
			case LOADMORE:
				strs.add("又产生一位幸运观众:947928758972");
				prlv.setonLoadMoreCompleted(true);
				break;
			}
			adapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		prlv = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView1);
		initData();
		prlv.setOnRefreshingListener(new OnRefreshingListener() {

			@Override
			public void onRefreshing() {
				mHandler.sendEmptyMessageDelayed(REFRESH, 2000);
			}

			@Override
			public void onLoadingMore() {
				mHandler.sendEmptyMessageDelayed(LOADMORE, 2000);
			}
		});
	}

	private void initData() {
		strs = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			strs.add("幸运观众手机号为:135792468" + i);
		}
		adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
				strs);
		prlv.setAdapter(adapter);
	}
}
