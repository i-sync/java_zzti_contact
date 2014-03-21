package com.zzti.java_zzti_contact;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Common extends Activity {

	private ProgressBar progressBar;
	private static Common instance ; 
	private Common() {
		// TODO Auto-generated constructor stub
		onCreate(null);
	}
	
	public static Common getInstance()
	{
		if(instance ==null)
		{
			instance = new Common();
		}
		return instance;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar);
		
		progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
		
	}
	
	public ProgressBar getProgressBar()
	{
		return this.progressBar;
	}

}
