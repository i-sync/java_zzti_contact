package com.zzti.java_zzti_contact;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class DialogFrag extends DialogFragment {

	private ProgressBar progressBar;
	private static DialogFrag instance;
	boolean lightTheme = true;

	private DialogFrag() {
	}

	public static DialogFrag getInstance() {
		if (instance == null) {
			synchronized (DialogFrag.class) {
				if (instance == null) {
					instance = new DialogFrag();
					instance.setCancelable(false);
				}
			}
		}
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		int style = DialogFragment.STYLE_NO_TITLE;
		int theme = android.R.style.Theme_Holo_Dialog;
		if (lightTheme) {
			theme = android.R.style.Theme_Holo_Light_Dialog;
		}
		this.setStyle(style, theme);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater
				.inflate(R.layout.progressbar, container, false);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
		return rootView;
	}
}
