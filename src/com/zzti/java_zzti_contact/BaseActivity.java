package com.zzti.java_zzti_contact;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//���Activity��ջ��
		BaseApplication.getInstance().addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//����Activity ��ջ���Ƴ��� Activity
		BaseApplication.getInstance().finishActivity(this);
	}
}
