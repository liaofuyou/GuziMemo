package me.guzi.mome.view;

import me.guzi.mome.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * @blog http://blog.csdn.net/xiaanming
 * 
 * @author xiaanming
 * 
 */
public class SlideCutListView extends ListView {
	/**
	 * 当前滑动的ListView�?position
	 */
	private int slidePosition;
	/**
	 * 手指按下X的坐�?
	 */
	private int downY;
	/**
	 * 手指按下Y的坐�?
	 */
	private int downX;
	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	/**
	 * ListView的item
	 */
	private View itemView;
	/**
	 * 滑动�?
	 */
	private Scroller scroller;
	private static final int SNAP_VELOCITY = 600;
	/**
	 * 速度追踪对象
	 */
	private VelocityTracker velocityTracker;
	/**
	 * 是否响应滑动，默认为不响�?
	 */
	private boolean isSlide = false;
	/**
	 * 认为是用户滑动的�?小距�?
	 */
	private int mTouchSlop;
	/**
	 * 移除item后的回调接口
	 */
	private RemoveListener mRemoveListener;
	/**
	 * 用来指示item滑出屏幕的方�?,向左或�?�向�?,用一个枚举�?�来标记
	 */
	private RemoveDirection removeDirection;

	// 滑动删除方向的枚举�??
	public enum RemoveDirection {
		RIGHT, LEFT;
	}

	public SlideCutListView(Context context) {
		this(context, null);
	}

	public SlideCutListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressWarnings("deprecation")
	public SlideCutListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		scroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	/**
	 * 设置滑动删除的回调接�?
	 * 
	 * @param removeListener
	 */
	public void setRemoveListener(RemoveListener removeListener) {
		this.mRemoveListener = removeListener;
	}

	/**
	 * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事�?
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			addVelocityTracker(event);

			// 假如scroller滚动还没有结束，我们直接返回
			if (!scroller.isFinished()) {
				return super.dispatchTouchEvent(event);
			}
			downX = (int) event.getX();
			downY = (int) event.getY();

			slidePosition = pointToPosition(downX, downY);

			// 无效的position, 不做任何处理
			if (slidePosition == AdapterView.INVALID_POSITION) {
				return super.dispatchTouchEvent(event);
			}

			// 获取我们点击的item view
			itemView = getChildAt(slidePosition - getFirstVisiblePosition());
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY || (Math.abs(event.getX() - downX) > mTouchSlop && Math.abs(event.getY() - downY) < mTouchSlop)) {
				isSlide = true;

			}
			break;
		}
		case MotionEvent.ACTION_UP:
			recycleVelocityTracker();
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	/**
	 * �?右滑动，getScrollX()返回的是左边缘的距离，就是以View左边缘为原点到开始滑动的距离
	 * ，所以向右边滑动为负�?
	 */
	private void scrollRight() {
		// if(m);
		removeDirection = RemoveDirection.RIGHT;
		final int delta = (screenWidth + itemView.getScrollX());
		// 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
		scroller.startScroll(itemView.getScrollX(), 0, -delta, 0, Math.abs(delta));
		postInvalidate(); // 刷新itemView
	}

	/**
	 * 向左滑动，根据上面我们知道向左滑动为正�??
	 */
	private void scrollLeft() {
		removeDirection = RemoveDirection.LEFT;
		final int delta = (screenWidth - itemView.getScrollX());
		// 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
		scroller.startScroll(itemView.getScrollX(), 0, delta, 0, Math.abs(delta));
		postInvalidate(); // 刷新itemView
	}

	/**
	 * 根据手指滚动itemView的距离来判断是滚动到�?始位置还是向左或者向右滚�?
	 */
	private void scrollByDistanceX() {
		// 如果向左滚动的距离大于屏幕的二分之一，就让其删除
		if (itemView.getScrollX() >= screenWidth / 2) {
			scrollLeft();
		} else if (itemView.getScrollX() <= -screenWidth / 2) {
			scrollRight();
		} else {
			// 滚回到原始位�?,为了偷下懒这里是直接调用scrollTo滚动
			itemView.scrollTo(0, 0);
		}

	}

	/**
	 * 处理我们拖动ListView item的�?�辑
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (isSlide && slidePosition != AdapterView.INVALID_POSITION) {

			String str = ((TextView) itemView.findViewById(R.id.contentTv)).getText().toString();
			if (str.equals("以前") || str.equals("昨天") || str.equals("今天")) {
				return false;
			}

			requestDisallowInterceptTouchEvent(true);
			addVelocityTracker(ev);
			final int action = ev.getAction();
			int x = (int) ev.getX();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:

				MotionEvent cancelEvent = MotionEvent.obtain(ev);
				cancelEvent.setAction(MotionEvent.ACTION_CANCEL | (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
				onTouchEvent(cancelEvent);

				int deltaX = downX - x;
				downX = x;

				// 手指拖动itemView滚动, deltaX大于0向左滚动，小�?0向右�?
				itemView.scrollBy(deltaX, 0);

				return true; // 拖动的时候ListView不滚�?
			case MotionEvent.ACTION_UP:
				int velocityX = getScrollVelocity();
				if (velocityX > SNAP_VELOCITY) {
					scrollRight();
				} else if (velocityX < -SNAP_VELOCITY) {
					scrollLeft();
				} else {
					scrollByDistanceX();
				}

				recycleVelocityTracker();
				// 手指离开的时候就不响应左右滚�?
				isSlide = false;
				break;
			}
		}

		// 否则直接交给ListView来处理onTouchEvent事件
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		// 调用startScroll的时候scroller.computeScrollOffset()返回true�?
		if (scroller.computeScrollOffset()) {
			// 让ListView item根据当前的滚动偏移量进行滚动
			itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());

			postInvalidate();

			// 滚动动画结束的时候调用回调接�?
			if (scroller.isFinished()) {
				if (mRemoveListener == null) {
					throw new NullPointerException("RemoveListener is null, we should called setRemoveListener()");
				}

				itemView.scrollTo(0, 0);
				mRemoveListener.removeItem(removeDirection, slidePosition);
			}
		}
	}

	/**
	 * 添加用户的�?�度跟踪�?
	 * 
	 * @param event
	 */
	private void addVelocityTracker(MotionEvent event) {
		if (velocityTracker == null) {
			velocityTracker = VelocityTracker.obtain();
		}

		velocityTracker.addMovement(event);
	}

	/**
	 * 移除用户速度跟踪�?
	 */
	private void recycleVelocityTracker() {
		if (velocityTracker != null) {
			velocityTracker.recycle();
			velocityTracker = null;
		}
	}

	/**
	 * 获取X方向的滑动�?�度,大于0向右滑动，反之向�?
	 * 
	 * @return
	 */
	private int getScrollVelocity() {
		velocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) velocityTracker.getXVelocity();
		return velocity;
	}

	/**
	 * 
	 * 当ListView item滑出屏幕，回调这个接�?
	 * 我们�?要在回调方法removeItem()中移除该Item,然后刷新ListView
	 * 
	 * @author xiaanming
	 * 
	 */
	public interface RemoveListener {
		public void removeItem(RemoveDirection direction, int position);
	}

}
