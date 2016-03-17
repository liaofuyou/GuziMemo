package me.guzi.mome.base;

import java.util.List;

import android.widget.BaseAdapter;

public abstract class MyBaseAdapter extends BaseAdapter {

	public List<?> mList;

	public MyBaseAdapter(List<?> mList) {

		this.mList = mList;

	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public Object getItem(int index) {

		return mList.get(index);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

}
