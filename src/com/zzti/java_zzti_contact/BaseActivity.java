package com.zzti.java_zzti_contact;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//添加Activity到栈中
		BaseApplication.getInstance().addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//结束Activity 从栈中移除该 Activity
		BaseApplication.getInstance().finishActivity(this);
	}
}
