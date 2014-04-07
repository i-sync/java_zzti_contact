package com.zzti.java_zzti_contact;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManager extends BaseActivity {

	private static NetworkManager instance;

	private NetworkManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static NetworkManager getInstance() {
		if (instance == null) {
			synchronized (NetworkManager.class) {
				if (instance == null) {
					instance = new NetworkManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			try {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mNetworkInfo = mConnectivityManager
						.getActiveNetworkInfo();
				if (mNetworkInfo != null) {
					//return mNetworkInfo.isAvailable();
					return mNetworkInfo.isConnected();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 判断WIFI是否连接
	 * 
	 * @param context
	 * @return
	 */
	public boolean isWIFIConnected(Context context) {
		if (context != null) {
			try {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mWiFiNetworkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (mWiFiNetworkInfo != null) {
					return mWiFiNetworkInfo.isAvailable();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 判断手机网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public boolean isMobileConnected(Context context) {
		if (context != null) {
			try {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mMobileNetworkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if (mMobileNetworkInfo != null) {
					return mMobileNetworkInfo.isAvailable();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 获取当前网络连接的类型信息
	 * 
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			try {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mNetworkInfo = mConnectivityManager
						.getActiveNetworkInfo();
				if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
					return mNetworkInfo.getType();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

}
