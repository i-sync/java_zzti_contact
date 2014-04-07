package com.zzti.java_zzti_contact;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzti.bean.Common;
import com.zzti.bean.Contact;
import com.zzti.bean.ListResult;

public class ContactListFragment extends Fragment {

	// private EditText etName;
	// private EditText etPhone;
	// private Button btnSearch;
	private ListView listView;

	private int cid;// 班级ID

	public ContactListFragment(int cid) {
		this.cid = cid;
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ListResult<Contact> result = (ListResult<Contact>) msg.obj;
			if (result == null) {
				Toast.makeText(getActivity(), "数据为：null", Toast.LENGTH_LONG)
						.show();
			} else {
				if (result.getResult() != 1) {
					Log.i("---->", result.getMessage());
					Toast.makeText(getActivity(), result.getMessage(),
							Toast.LENGTH_LONG).show();
				}

				ComplexListAdapter adapter = new ComplexListAdapter(
						result.getList(), getActivity());
				listView.setAdapter(adapter);
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contact_list,
				container, false);
		// etName = (EditText) rootView.findViewById(R.id.et_contact_list_name);
		// etPhone = (EditText)
		// rootView.findViewById(R.id.et_contact_list_phone);
		// btnSearch = (Button) rootView
		// .findViewById(R.id.btn_contact_list_search);
		listView = (ListView) rootView.findViewById(R.id.lv_contact_list);

		// 加载数据
		new Thread(new LoadContactList()).start();
		// listView点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						ContactInfoActivity.class);
				intent.putExtra("id", (int) id);
				startActivity(intent);
			}
		});

		return rootView;
	}

	/**
	 * 根据班级ID加载联系列表
	 * 
	 * @author zhenyun
	 * 
	 */
	private class LoadContactList implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Contact c = new Contact();
			c.setCid(cid);

			ListResult<Contact> result = Common.getInstance()
					.contact_getlist(c);
			Message msg = Message.obtain();
			msg.obj = result;
			handler.sendMessage(msg);
		}

	}

	/**
	 * 自定义适配器
	 * 
	 * @author zhenyun
	 * 
	 */
	private class ComplexListAdapter extends BaseAdapter {
		private List<Contact> list;
		private Context context;

		public ComplexListAdapter(List<Contact> list, Context context) {
			this.list = list;
			this.context = context;
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder item = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.fragment_contact_list_item, null);
				item = new ViewHolder();
				item.name = (TextView) convertView
						.findViewById(R.id.tv_contact_list_name);
				item.phone = (TextView) convertView
						.findViewById(R.id.tv_contact_list_phone);
				item.living = (TextView) convertView
						.findViewById(R.id.tv_contact_list_living);
				// item.email = (TextView)
				// convertView.findViewById(R.id.tv_contact_list_email);
				// item.company = (TextView)
				// convertView.findViewById(R.id.tv_contact_list_company);
				item.remark = (TextView) convertView
						.findViewById(R.id.tv_contact_list_remark);
				item.btnUpdate = (Button) convertView
						.findViewById(R.id.btn_contact_list_update);

				convertView.setTag(item);
			} else {
				item = (ViewHolder) convertView.getTag();
			}

			/** 设置TextView显示的内容，即我们存放在动态数组中的数据 */
			item.name.setText(list.get(position).getName());
			// 设置点击号码进入拨号界面
			/*
			 * String phone= list.get(position).getPhone(); SpannableString span
			 * = new SpannableString(phone); item.phone.setText(span);
			 * item.phone.setMovementMethod(LinkMovementMethod.getInstance());
			 */
			item.phone.setText(list.get(position).getPhone());

			item.living.setText(list.get(position).getLiving());
			// item.email.setText(list.get(position).getEmail());
			// item.company.setText(list.get(position).getCompany());
			item.remark.setText(list.get(position).getRemark());

			/**
			 * 点击修改按钮事件
			 */
			item.btnUpdate.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							ModifyContactActivity.class);
					intent.putExtra("type", 1);// type:0为添加，1为修改
					intent.putExtra("id", (int) getItemId(position));
					// startActivity(intent);
					getActivity().startActivityForResult(intent,
							MainActivity.SUB_ACTIVITY_MODIFY);
				}
			});

			return convertView;
		}

		private class ViewHolder {
			public TextView name;
			public TextView phone;
			public TextView living;
			// public TextView email;
			// public TextView company;
			public TextView remark;
			public Button btnUpdate;

		}

	}

}
