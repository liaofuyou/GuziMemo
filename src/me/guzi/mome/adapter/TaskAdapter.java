package me.guzi.mome.adapter;

import java.util.List;

import me.guzi.mome.R;
import me.guzi.mome.base.MyBaseAdapter;
import me.guzi.mome.base.MyConfig;
import me.guzi.mome.entity.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskAdapter extends MyBaseAdapter {

	private Context mContext = null;

	private class Holder {
		TextView itemTv;
	}

	public TaskAdapter(Context context, List<Task> mList) {
		super(mList);
		this.mContext = context;
	}

	int paddingTop = 0;
	int paddingBottom = 0;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (convertView == null) {

			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task, null);
			if (paddingTop == 0) {
				paddingTop = convertView.getPaddingTop();
				paddingBottom = convertView.getPaddingBottom();
			}

			holder = new Holder();
			holder.itemTv = (TextView) convertView.findViewById(R.id.contentTv);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		Task task = (Task) mList.get(position);
		if (task.getType() == MyConfig.TYPE_DATE) {
			holder.itemTv.setBackground(null);
			holder.itemTv.setTextSize(14F);
			holder.itemTv.setTextColor(0XFF999999);
			holder.itemTv.setText(task.getContent());
			convertView.setPadding(0, 0, 0, 0);
		} else {
			if (task.getImageIndex() == 0) {
				holder.itemTv.setBackgroundResource(R.drawable.bg_message1);
			} else if (task.getImageIndex() == 1) {
				holder.itemTv.setBackgroundResource(R.drawable.bg_message2);
			} else if (task.getImageIndex() == 2) {
				holder.itemTv.setBackgroundResource(R.drawable.bg_message3);
			} else if (task.getImageIndex() == 3) {
				holder.itemTv.setBackgroundResource(R.drawable.bg_message4);
			} else if (task.getImageIndex() == 4) {
				holder.itemTv.setBackgroundResource(R.drawable.bg_message5);
			} else if (task.getImageIndex() == 5) {
				holder.itemTv.setBackgroundResource(R.drawable.bg_message6);
			} else if (task.getImageIndex() == 6) {
				holder.itemTv.setBackgroundResource(R.drawable.bg_message7);
			} else {
				holder.itemTv.setBackgroundResource(R.drawable.bg_message3);
			}
			holder.itemTv.setTextSize(16F);
			holder.itemTv.setTextColor(0XFF4d4d4d);
			holder.itemTv.setText("  " + task.getContent() + "  ");
			convertView.setPadding(0, paddingTop, 0, paddingBottom);
		}

		return convertView;
	}
}
