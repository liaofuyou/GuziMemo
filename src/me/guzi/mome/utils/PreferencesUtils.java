package me.guzi.mome.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtils {

	public static void set(Context context, String fileName, String content) {
		// 取得配置文件中的信息
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(fileName, content);
		editor.commit();
	}

	public static String get(Context context, String fileName) {
		// 取得配置文件中的信息
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sharedPreferences.getString(fileName, null);
	}
}
