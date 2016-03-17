package me.guzi.mome;

import me.guzi.mome.base.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AboutActivity extends BaseActivity implements OnClickListener {

	private TextView titleNameTv;
	private View backRl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
		initListener();
		bindData();

		titleNameTv = (TextView) findViewById(R.id.titleNameTv);
		backRl = findViewById(R.id.titleBackRl);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

		titleNameTv = (TextView) findViewById(R.id.titleNameTv);
		backRl = findViewById(R.id.titleBackRl);
	}

	@Override
	public void initListener() {
		backRl.setOnClickListener(this);
	}

	@Override
	public void bindData() {
		titleNameTv.setText("关于APP");
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == backRl.getId()) {
			finish();
		}
	}

}
