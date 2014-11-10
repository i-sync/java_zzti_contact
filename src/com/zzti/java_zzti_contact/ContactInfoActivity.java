package com.zzti.java_zzti_contact;

import com.zzti.bean.Contact;
import com.zzti.bean.ListResult;
import com.zzti.bean.TResult;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactInfoActivity extends BaseActivity {
	private TextView tvName;
	private TextView tvClass;
	private TextView tvPhone;
	private TextView tvEmail;
	private TextView tvLiving;
	private TextView tvCompany;
	private TextView tvRemark;
	private Button btnCancel;
	
	private DialogFrag dialog;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			TResult<Contact> result = (TResult<Contact>) msg.obj;
			if (result == null) {
				Toast.makeText(ContactInfoActivity.this, "查询数据为NULL!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (result.getResult() != 1) {
				Toast.makeText(ContactInfoActivity.this, result.getMessage(),
						Toast.LENGTH_LONG).show();
				return;
			}

			Contact data = result.getT();
			tvName.setText(data.getName());
			tvClass.setText(data.getCname());
			
			//设置电话连接
			String phone = data.getPhone();
			SpannableString span1 = new SpannableString(phone);
			tvPhone.setText(span1);
			tvPhone.setAutoLinkMask(Linkify.PHONE_NUMBERS);
			tvPhone.setMovementMethod(LinkMovementMethod.getInstance());
			
			//tvPhone.setText(data.getPhone());
			//设置邮箱连接
			String email = data.getEmail().trim();
			if(!email.equals(""))
			{
				SpannableString span2 = new SpannableString(email);
				tvEmail.setText(span2);
				tvEmail.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
				tvEmail.setMovementMethod(LinkMovementMethod.getInstance());
			}
			//tvEmail.setText(data.getEmail());
			tvLiving.setText(data.getLiving());
			tvCompany.setText(data.getCompany());
			tvRemark.setText(data.getRemark());
			
			//隐藏
			dialog.dismiss();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_info);

		dialog = DialogFrag.getInstance();
		
		tvName = (TextView) this.findViewById(R.id.tv_contact_info_name);
		tvClass = (TextView) this.findViewById(R.id.tv_contact_info_class);
		tvPhone = (TextView) this.findViewById(R.id.tv_contact_info_phone);
		tvEmail = (TextView) this.findViewById(R.id.tv_contact_info_email);
		tvLiving = (TextView) this.findViewById(R.id.tv_contact_info_living);
		tvCompany = (TextView) this.findViewById(R.id.tv_contact_info_company);
		tvRemark = (TextView) this.findViewById(R.id.tv_contact_info_remark);
		btnCancel = (Button) this.findViewById(R.id.btn_contact_info_back);

		Intent intent = getIntent();
		int id = intent.getIntExtra("id", 0);
		getActionBar().setHomeButtonEnabled(true);
		
		dialog.show(getFragmentManager(), null);
		new Thread(new LoadContactInfoThread(id)).start();

		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BaseApplication.getInstance().finishActivity(
						ContactInfoActivity.class);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case android.R.id.home:
			BaseApplication.getInstance().finishActivity(
					ContactInfoActivity.class);
			return true;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 加载联系人信息
	 * @author zhenyun
	 *
	 */
	private class LoadContactInfoThread implements Runnable {
		private int id;

		public LoadContactInfoThread(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Contact data = new Contact();
			data.setId(id);
			TResult<Contact> result = com.zzti.bean.Common.getInstance()
					.contact_getmodel(data);
			Message msg = Message.obtain();
			msg.obj = result;
			handler.sendMessage(msg);
		}

	}
}
