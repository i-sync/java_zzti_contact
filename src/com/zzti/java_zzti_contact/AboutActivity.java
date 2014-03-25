package com.zzti.java_zzti_contact;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends BaseActivity {

	private TextView tvVersion;
	private Button btnUpdate;

	public AboutActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		tvVersion = (TextView) this.findViewById(R.id.tv_about_version);
		btnUpdate = (Button) this.findViewById(R.id.btn_about_update);

		String version = getResources().getString(R.string.tv_about_version)
				+ getVersionName();
		tvVersion.setText(version);

		btnUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UpdateManager manager = new UpdateManager(AboutActivity.this,
						getVersionCode());
				// ����������
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
