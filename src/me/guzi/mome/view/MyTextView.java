package me.guzi.mome.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

	private int bgColor;

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, 0, 0);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyTextView(Context context) {
		this(context, null);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Bitmap mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
		canvas = new Canvas(mBitmap);
		Paint paint = new Paint();
		paint.setColor(bgColor);
		paint.setAntiAlias(true);
		paint.setDither(true);
		// paint.drawRect(mIconRect, mPaint);
		// paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		// canvas.drawBitmap(mBitmap, null, mIconRect, paint);
		// }
	}

	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}
}
