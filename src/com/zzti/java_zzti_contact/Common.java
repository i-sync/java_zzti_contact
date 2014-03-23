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
	 * ��ȡ�汾��Ϣ��versionName
	 * 
	 * @return
	 */
	public String getVersionName() {
		try {
			// ��ȡpackagemanager��ʵ��
			PackageManager packageManager = getPackageManager();
			// getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * ��ȡ�汾�ţ�versionCode
	 * 
	 * @return
	 */
	public int getVersionCode() {
		try {
			// ��ȡpackagemanager��ʵ��
			PackageManager packageManager = getPackageManager();
			// getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
