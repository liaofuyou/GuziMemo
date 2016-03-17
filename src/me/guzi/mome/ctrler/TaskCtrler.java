package me.guzi.mome.ctrler;

import java.util.List;

import me.guzi.mome.base.MyConfig;
import me.guzi.mome.dao.db.TaskDao;
import me.guzi.mome.entity.Task;
import me.guzi.mome.utils.Utils;
import android.content.Context;

public class TaskCtrler {

	Context mContext;
	TaskDao taskDao;

	public TaskCtrler(Context mContext) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		taskDao = new TaskDao(mContext);
	}

	public void add(Task task) {
		taskDao.add(task);
	}

	public void delete(int id) {
		taskDao.delete(id);
	}

	public void loadList(List<Task> showTasks, List<Task> todayTasks, List<Task> yesterdayTasks, List<Task> beforeTasks) {

		String yesterdayStr = Utils.getYesterdayStr();
		String todayStr = Utils.getTodayStr();

		// 从数据库中获取数�?
		List<Task> tasks = taskDao.findList();
		for (Task task : tasks) {
			String dateStr = Utils.getDayStr(task.getAdate());
			if (todayStr.equals(dateStr)) {
				todayTasks.add(task);
			} else if (yesterdayStr.equals(dateStr)) {
				yesterdayTasks.add(task);
			} else {
				beforeTasks.add(task);
			}

		}
		// 将数据放入ShowsTasks
		if (beforeTasks.size() != 0) {
			showTasks.add(new Task(0, 0, "以前", null, MyConfig.TYPE_DATE));
			showTasks.addAll(beforeTasks);
		}
		if (yesterdayTasks.size() != 0) {
			showTasks.add(new Task(0, 0, "昨天", null, MyConfig.TYPE_DATE));
			showTasks.addAll(yesterdayTasks);
		}
		if (todayTasks.size() != 0) {
			showTasks.add(new Task(0, 0, "今天", null, MyConfig.TYPE_DATE));
			showTasks.addAll(todayTasks);
		}
	}
}
