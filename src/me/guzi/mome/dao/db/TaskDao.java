package me.guzi.mome.dao.db;

import java.util.ArrayList;
import java.util.List;

import me.guzi.mome.entity.Task;
import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class TaskDao {

	Context mContext;

	public TaskDao(Context mContext) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
	}

	public void add(Task task) {

		DbUtils dbUtils = DbUtils.create(mContext);
		try {
			dbUtils.save(task);
		} catch (DbException e) {
			e.printStackTrace();
		} finally {
			dbUtils.close();
		}
	}

	public void delete(int id) {
		// TODO Auto-generated method stub

		DbUtils dbUtils = DbUtils.create(mContext);
		try {
			dbUtils.deleteById(Task.class, id);
		} catch (DbException e) {
			e.printStackTrace();
		} finally {
			dbUtils.close();
		}
	}

	public List<Task> findList() {

		List<Task> mList = new ArrayList<Task>();
		DbUtils dbUtils = DbUtils.create(mContext);

		try {
			Selector selector = Selector.from(Task.class);
			mList = dbUtils.findAll(selector);
		} catch (DbException e1) {
			e1.printStackTrace();
		} finally {
			dbUtils.close();
		}

		// 如果为空
		if (mList == null) {
			mList = new ArrayList<Task>();
		}

		return mList;
	}
}
