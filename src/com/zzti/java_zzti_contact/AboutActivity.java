package com.zzti.java_zzti_contact;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends BaseActivity {

	private TextView tvVersion;
	private Button btnUpdate;
	private boolean isConnected;
	
	public AboutActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		// 判断网络是否连接
		isConnected = NetworkManager.getInstance().isNetworkConnected(
				AboutActivity.this);

		tvVersion = (TextView) this.findViewById(R.id.tv_about_version);
		btnUpdate = (Button) this.findViewById(R.id.btn_about_update);

		String version = getResources().getString(R.string.tv_about_version)
				+ getVersionName();
		tvVersion.setText(version);

		btnUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 首先检查网络是否可用
				if (!isConnected) {
					Toast.makeText(AboutActivity.this, "网络未连接,请检查网络！",
							Toast.LENGTH_LONG).show();
					return;
				}
				UpdateManager manager = new UpdateManager(AboutActivity.this,
						getVersionCode());
				// 检查软件更新
				manager.downloadXml();
			}
		});

		getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			BaseApplication.getInstance().finishActivity(AboutActivity.class);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

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
