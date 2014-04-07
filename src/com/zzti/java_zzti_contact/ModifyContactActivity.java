package com.zzti.java_zzti_contact;

import java.util.List;

import com.zzti.bean.Class;
import com.zzti.bean.Common;
import com.zzti.bean.Contact;
import com.zzti.bean.ListResult;
import com.zzti.bean.Result;
import com.zzti.bean.TResult;
import com.zzti.utils.RegexUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyContactActivity extends BaseActivity {
	// 定义常量
	private final int CLASS_LOAD_FLAG = 1;
	private final int CONTACT_LOAD_FLAG = 2;
	private final int CONTACT_SAVE_FLAG = 3;

	private int id;// 操作ID
	private int type;// 操作类型：0为添加，1为修改
	private EditText etName;
	private Spinner spClass;
	private EditText etPhone;
	private EditText etEmail;
	private EditText etLiving;
	private EditText etCompany;
	private EditText etRemark;
	private Button btnSubmit;
	private Button btnCancel;
	private boolean isConnected;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CLASS_LOAD_FLAG:
				ListResult result = (ListResult<Class>) msg.obj;
				if (result == null) {
					Toast.makeText(ModifyContactActivity.this, "查询数据为NULL!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (result.getResult() != 1) {
					Toast.makeText(ModifyContactActivity.this,
							result.getMessage(), Toast.LENGTH_LONG).show();
					return;
				}
				ComplexAdapter adapter = new ComplexAdapter(
						ModifyContactActivity.this, result.getList());
				spClass.setAdapter(adapter);

				break;
			case CONTACT_LOAD_FLAG:
				TResult<Contact> result1 = (TResult<Contact>) msg.obj;
				if (result1 == null) {
					Toast.makeText(ModifyContactActivity.this, "查询数据为NULL!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (result1.getResult() != 1) {
					Toast.makeText(ModifyContactActivity.this,
							result1.getMessage(), Toast.LENGTH_LONG).show();
					return;
				}
				// 得到对象，显示
				Contact data = result1.getT();
				etName.setText(data.getName());
				for (int i = 0; spClass.getAdapter() != null
						&& i < spClass.getAdapter().getCount(); i++) {
					if ((int) spClass.getAdapter().getItemId(i) == data
							.getCid()) {
						spClass.setSelection(i, true);
						break;
					}
				}
				etPhone.setText(data.getPhone());
				etEmail.setText(data.getEmail());
				etLiving.setText(data.getLiving());
				etCompany.setText(data.getCompany());
				etRemark.setText(data.getRemark());

				break;
			case CONTACT_SAVE_FLAG:
				Result result2 = (Result) msg.obj;
				if (result2 == null) {
					Toast.makeText(ModifyContactActivity.this, "得到数据为NULL!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (result2.getResult() != 1) {
					Toast.makeText(ModifyContactActivity.this,
							result2.getMessage(), Toast.LENGTH_LONG).show();
					return;
				}
				// Intent intent = new
				// Intent(ModifyContactActivity.this,MainActivity.class);
				// 保存成功，刷新列表
				setResult(Activity.RESULT_OK);

				// 保存成功，关闭当前窗口
				BaseApplication.getInstance().finishActivity(
						ModifyContactActivity.class);

				break;
			}
		};
	};

	/**
	 * 绑定下拉列表适配器
	 * 
	 * @author zhenyun
	 * 
	 */
	private class ComplexAdapter extends BaseAdapter {

		private Context context;
		private List<Class> list;
		private TextView tvName;

		public ComplexAdapter(Context context, List<Class> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return list.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				LayoutInflater mInflater = LayoutInflater.from(context);
				convertView = mInflater.inflate(R.layout.spinner_list_item,
						null);
			}

			tvName = (TextView) convertView
					.findViewById(R.id.tv_spinner_list_item);
			tvName.setText(list.get(position).getName());
			return convertView;
		}

	}

	/*
	 * public ModifyContactActivity(int type, int id) { // TODO Auto-generated
	 * constructor stub this.type = type; this.id = id; }
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_contact);
		// 判断网络是否连接
		isConnected = NetworkManager.getInstance().isNetworkConnected(
				ModifyContactActivity.this);

		// 判断网络是否连接
		if (!isConnected) {
			Toast.makeText(ModifyContactActivity.this, "网络未连接,请检查网络！", Toast.LENGTH_LONG)
					.show();
			return;
		}
		
		Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		id = intent.getIntExtra("id", 0);

		etName = (EditText) this.findViewById(R.id.et_modify_contact_name);
		spClass = (Spinner) this.findViewById(R.id.sp_modify_contact_class);
		etPhone = (EditText) this.findViewById(R.id.et_modify_contact_phone);
		etEmail = (EditText) this.findViewById(R.id.et_modify_contact_email);
		etLiving = (EditText) this.findViewById(R.id.et_modify_contact_living);
		etCompany = (EditText) this
				.findViewById(R.id.et_modify_contact_company);
		etRemark = (EditText) this.findViewById(R.id.et_modify_contact_remark);
		btnSubmit = (Button) this.findViewById(R.id.btn_modify_contact_submit);
		btnCancel = (Button) this.findViewById(R.id.btn_modify_contact_cancel);

		// 首先加载班级列表
		new Thread(new LoadClassThread()).start();

		// 判断是添加还是修改，如果是修改，获取联系人信息
		if (type == 1) {
			setTitle(R.string.activity_contact_update);
			new Thread(new LoadContactThread()).start();
		}

		/**
		 * 保存按钮事件
		 */
		btnSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 验证数据是否正确
				String name = etName.getText().toString().trim();
				int cid = (int) spClass.getSelectedItemId();
				String phone = etPhone.getText().toString().trim();
				String email = etEmail.getText().toString().trim();
				String living = etLiving.getText().toString().trim();
				String company = etCompany.getText().toString().trim();
				String remark = etRemark.getText().toString().trim();
				boolean flag = true;
				if (name.equals("")) {
					etName.setError("姓名不能为空！");
					flag = false;
				}
				if (phone.equals("")) {
					etPhone.setError("手机号不能为空！");
					flag = false;
				} else if (!RegexUtil.isPhone(phone)) {
					etPhone.setError("请输入正确的手机号！");
					flag = false;
				}
				if (!email.equals("") && !RegexUtil.isEmail(email)) {
					etEmail.setError("请输入正确的邮箱！");
					flag = false;
				}

				// 判断是否通过
				if (!flag) {
					return;
				}

				Contact data = new Contact(id, name, cid, "", phone, email,
						living, company, remark);
				new Thread(new SaveContactThread(data)).start();
			}
		});

		/**
		 * 点击取消事件
		 */
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 设置返回值为 取消
				setResult(Activity.RESULT_CANCELED);
				BaseApplication.getInstance().finishActivity(
						ModifyContactActivity.class);
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 加载班级列表
	 * 
	 * @author zhenyun
	 * 
	 */
	private class LoadClassThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ListResult<Class> result = Common.getInstance().class_getlist();
			Message msg = Message.obtain();
			msg.what = CLASS_LOAD_FLAG;
			msg.obj = result;
			handler.sendMessage(msg);
		}
	}

	/**
	 * 根据ID，加载联系信息
	 * 
	 * @author zhenyun
	 * 
	 */
	private class LoadContactThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Contact data = new Contact();
			data.setId(id);
			TResult<Contact> result = Common.getInstance().contact_getmodel(
					data);
			Message msg = Message.obtain();
			msg.what = CONTACT_LOAD_FLAG;
			msg.obj = result;
			handler.sendMessage(msg);
		}
	}

	/**
	 * 保存联系人信息
	 * 
	 * @author zhenyun
	 * 
	 */
	private class SaveContactThread implements Runnable {
		private Contact data;

		public SaveContactThread(Contact data) {
			this.data = data;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Result result = null;
			if (type == 0)
				result = Common.getInstance().contact_add(data);
			else
				result = Common.getInstance().contact_update(data);
			Message msg = Message.obtain();
			msg.what = CONTACT_SAVE_FLAG;
			msg.obj = result;
			handler.sendMessage(msg);
		}
	}
}
