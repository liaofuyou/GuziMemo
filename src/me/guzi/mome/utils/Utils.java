package me.guzi.mome.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static String getTodayStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	@SuppressWarnings("deprecation")
	public static String getYesterdayStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		date.setDate(date.getDate() - 1);
		return sdf.format(date);
	}

	@SuppressWarnings("deprecation")
	public static String getDayStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date.setDate(date.getDate());
		return sdf.format(date);
	}

	public static int compareDate(Date date) {
		String str = getDayStr(date);
		if (str.equals(getTodayStr())) {
			return 0;
		} else if (str.equals(getYesterdayStr())) {
			return 1;
		} else {
			return 2;
		}
	}
}
