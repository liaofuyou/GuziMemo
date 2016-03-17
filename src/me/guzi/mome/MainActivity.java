package me.guzi.mome;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.guzi.mome.adapter.TaskAdapter;
import me.guzi.mome.base.BaseActivity;
import me.guzi.mome.base.MyConfig;
import me.guzi.mome.ctrler.TaskCtrler;
import me.guzi.mome.entity.Task;
import me.guzi.mome.utils.PreferencesUtils;
import me.guzi.mome.view.SlideCutListView;
import me.guzi.mome.view.SlideCutListView.RemoveDirection;
import me.guzi.mome.view.SlideCutListView.RemoveListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements RemoveListener, OnClickListener {

	private List<Task> beforeTasks = new ArrayList<Task>();
	private List<Task> yesterdayTasks = new ArrayList<Task>();
	private List<Task> todayTasks = new ArrayList<Task>();
	private List<Task> showTasks = new ArrayList<Task>();

	private View aboutRl;
	private View sendRl;
	private View sloganTv;
	private EditText taskEt;
	private SlideCutListView taskLv;

	private TaskCtrler taskCtrler;
	private TaskAdapter taskAdapter;

	private int nextImageIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		taskCtrler = new TaskCtrler(this);

		firstUse();

		initView();
		initListener();
		bindData();
	}

	@Override
	public void initView() {
		taskLv = (SlideCutListView) findViewById(R.id.taskLv);

		sendRl = findViewById(R.id.sendRl);
		sloganTv = (TextView) findViewById(R.id.sloganTv);
		taskEt = (EditText) findViewById(R.id.taskEt);
		aboutRl = findViewById(R.id.titleAboutRl);
	}

	@Override
	public void initListener() {
		taskLv.setRemoveListener(this);
		sendRl.setOnClickListener(this);
		aboutRl.setOnClickListener(this);
	}

	@Override
	public void bindData() {
		// TODO Auto-generated method stub

		// 从数据库加载内容
		taskCtrler.loadList(showTasks, todayTasks, yesterdayTasks, beforeTasks);
		taskAdapter = new TaskAdapter(this, showTasks);
		taskLv.setAdapter(taskAdapter);

		if (showTasks.size() == 0) {
			sloganTv.setVisibility(View.VISIBLE);
			nextImageIndex = 0;
		} else {
			nextImageIndex = (showTasks.get(showTasks.size() - 1).getImageIndex() + 1) % 7;
		}
	}

	private void firstUse() {
		// TODO Auto-generated method stub
		String firstUse = PreferencesUtils.get(this, "firstUse");
		if (firstUse == null) {
			taskCtrler.add(new Task(0, nextImageIndex++, "欢迎使用谷子便签！！", new Date(), MyConfig.TYPE_TEXT));
			taskCtrler.add(new Task(0, nextImageIndex++, "Tip:底部可是添加便签", new Date(), MyConfig.TYPE_TEXT));
			taskCtrler.add(new Task(0, nextImageIndex++, "Tip:向右滑动可以删除", new Date(), MyConfig.TYPE_TEXT));
			PreferencesUtils.set(this, "firstUse", "true");
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == sendRl.getId()) {
			addTask();
		} else if (view.getId() == aboutRl.getId()) {
			openActivity(AboutActivity.class);
		}
	}

	private void addTask() {
		// TODO Auto-generated method stub
		String content = taskEt.getText().toString();
		if (content.length() == 0) {
			return;
		}
		if (todayTasks.size() == 0) {
			nextImageIndex = 0;
			showTasks.add(new Task(0, 0, "今天", null, MyConfig.TYPE_DATE));
		} else {
			nextImageIndex = (showTasks.get(showTasks.size() - 1).getImageIndex() + 1) % 7;
		}
		Task task = new Task(0, nextImageIndex, content, new Date(), MyConfig.TYPE_TEXT);
		todayTasks.add(task);
		showTasks.add(task);
		taskCtrler.add(task);

		// 隐藏Slogan
		sloganTv.setVisibility(View.GONE);

		// 更新界面
		taskAdapter.notifyDataSetChanged();
		taskEt.setText("");
		taskLv.setSelection(taskLv.getBottom());
	}

	// 滑动删除之后的回调方�?
	@Override
	public void removeItem(RemoveDirection direction, int position) {
		// adapter.remove(adapter.getItem(position));
		Task task = (Task) taskAdapter.getItem(position);

		// 向左还是向右�?
		switch (direction) {
		case RIGHT:
			deleteTask(task, position);
			break;
		case LEFT:
			// toast("向左删除  " + position);
			break;
		default:
			break;
		}
	}

	private void deleteTask(Task task, int position) {
		// TODO Auto-generated method stub

		// 判断是否是最后一个，如果是，就先把日期提示删�?
		if (beforeTasks.size() == 1 || yesterdayTasks.size() == 1 || todayTasks.size() == 1) {
			showTasks.remove(position - 1);
		}

		// 删除
		beforeTasks.remove(task);
		yesterdayTasks.remove(task);
		todayTasks.remove(task);
		showTasks.remove(task);
		taskCtrler.delete(task.getId());

		if (showTasks.size() == 0) {
			sloganTv.setVisibility(View.VISIBLE);
		}

		taskAdapter.notifyDataSetChanged();
	}
}
