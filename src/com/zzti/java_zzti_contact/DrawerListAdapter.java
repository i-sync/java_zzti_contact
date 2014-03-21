package com.zzti.java_zzti_contact;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DrawerListAdapter extends BaseAdapter {

	private TextView tvId;
	private TextView tvName;

	private List<com.zzti.bean.Class> list;
	private Context context;

	public DrawerListAdapter(List<com.zzti.bean.Class> list, Context context) {
		// TODO Auto-generated constructor stub
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
		}

		tvId = (TextView) convertView.findViewById(R.id.tv_drawer_item_id);
		tvName = (TextView) convertView.findViewById(R.id.tv_drawer_item_name);

		tvId.setText(String.valueOf(list.get(position).getId()));
		tvName.setText(list.get(position).getName());

		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
		}

		tvId = (TextView) convertView.findViewById(R.id.tv_drawer_item_id);
		tvName = (TextView) convertView.findViewById(R.id.tv_drawer_item_name);

		tvId.setText(list.get(position).getId());
		tvName.setText(list.get(position).getName());

		return convertView;
	}

}
