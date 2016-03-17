package me.guzi.mome.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

	public abstract void initView();

	public abstract void initListener();

	public abstract void bindData();

	public void openActivity(Class<?> cls) {
		startActivity(new Intent(this, cls));
	}

	@SuppressLint("ShowToast")
	public void toast(Object text) {
		Toast.makeText(this, text.toString(), Toast.LENGTH_LONG).show();
	}

	public void log(Object text) {
		Log.i("MyPrint", text.toString());
	}

}
