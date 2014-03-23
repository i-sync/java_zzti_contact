package com.zzti.java_zzti_contact;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Common extends BaseActivity {

	private static Common instance;

	private Common() {
		// TODO Auto-generated constructor stub
	}

	public static Common getInstance() {
		if (instance == null) {
			synchronized (Common.class) {
				if (instance == null) {
					instance = new Common();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 获取版本信息：versionName
	 * 
	 * @return
	 */
	public String getVersionName() {
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取版本号：versionCode
	 * 
	 * @return
	 */
	public int getVersionCode() {
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
