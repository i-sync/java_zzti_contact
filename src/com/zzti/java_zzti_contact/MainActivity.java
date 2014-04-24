package com.zzti.java_zzti_contact;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

import com.zzti.bean.Class;
import com.zzti.bean.ListResult;
//implements OnQueryTextListener
public class MainActivity extends BaseActivity {
	// ��Activity����
	public static final int SUB_ACTIVITY_MODIFY = 1;
	public static final int SUB_ACTIVITY_INFO = 2;
	public static final int SUB_ACTIVITY_ABOUT = 3;

	private static final int RESUME = 0;
	private static final int CLASS_LOAD = 1;
	private long exitTime;// �˳�ȷ��
	private DrawerLayout mDrawerLayout;
	private ListView mListView;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private int position;// ��ǰleftѡ��λ��
	private boolean isConnected;// ��ʶ�����Ƿ�����
	
	private DialogFrag dialog ;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CLASS_LOAD:// �༶�б����
				ListResult<Class> result = (ListResult<Class>) msg.obj;
				if (result == null) {
					Toast.makeText(MainActivity.this, "û�в�ѯ�����ݣ�",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (result.getResult() != 1) {
					Log.i("---->", result.getMessage());
					Toast.makeText(MainActivity.this, result.getMessage(),
							Toast.LENGTH_LONG).show();
				}
				DrawerListAdapter adapter = new DrawerListAdapter(
						result.getList(), MainActivity.this);
				mListView.setAdapter(adapter);
				mListView.setOnItemClickListener(new DrawerItemClickListener());

				dialog.dismiss();
				selectItem(position);
				break;
			case RESUME:// ֻ���ڱ����ˢ���б�
				if (mListView.getAdapter() == null)
					return;
				selectItem(position);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// �ж������Ƿ�����
		isConnected = NetworkManager.getInstance().isNetworkConnected(
				MainActivity.this);
		dialog = DialogFrag.getInstance();
		

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		mListView = (ListView) this.findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// �ж������Ƿ�����
		if (!isConnected) {
			Toast.makeText(MainActivity.this, "����δ����,�������磡", Toast.LENGTH_LONG)
					.show();
			return;
		}
		// �����߳�
		dialog.show(getFragmentManager(), null);
		new Thread(new LoadClassInfo()).start();
	}

	/**
	 * ��д����(����Activity���������������ڷ����д���)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SUB_ACTIVITY_MODIFY:
			if (resultCode == Activity.RESULT_OK) {
				Message msg = Message.obtain();
				msg.what = RESUME;
				handler.sendMessage(msg);
			}
			break;
		case SUB_ACTIVITY_INFO:
		case SUB_ACTIVITY_ABOUT:
			break;
		}
	}

	/**
	 * ���ذ༶�б���Ϣ
	 * 
	 * @author zhenyun
	 * 
	 */
	private class LoadClassInfo implements Runnable {
		@Override
		public void run() {
			ListResult<Class> result = com.zzti.bean.Common.getInstance()
					.class_getlist();
			Message msg = Message.obtain();
			msg.what = CLASS_LOAD;
			msg.obj = result;
			handler.sendMessage(msg);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.main, menu);
		// ȡ��idΪR.id.action_search��MenuItem������xml�ļ����Ѿ�ָ��android:actionViewClass="android.widget.SearchView"
		// ����ͨ��getActionView()�������ʽת��ΪSearchView
		// SearchView searchView =
		// (SearchView)menu.findItem(R.id.action_search).getActionView();
		// ע���������������¼���������������ʵ�ֽӿڣ�android.widget.SearchView.OnQueryTextListener
		// �¼��ص�����Ϊ��onQueryTextSubmit()��ʼ�����¼���onQueryTextChange()�ı��ı��¼�
		// searchView.setOnQueryTextListener(this);

		return super.onCreateOptionsMenu(menu);
	}

//	@Override
//	public boolean onQueryTextChange(String newText) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean onQueryTextSubmit(String query) {
//		// TODO Auto-generated method stub
//		Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
//		return false;
//	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mListView);
		menu.findItem(R.id.action_about).setVisible(!drawerOpen & isConnected);
		// menu.findItem(R.id.action_search).setVisible(!drawerOpen);
		menu.findItem(R.id.action_add_contact).setVisible(
				!drawerOpen & isConnected);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		// case R.id.action_search:
		// return true;
		case R.id.action_add_contact:
			Intent intent = new Intent(MainActivity.this,
					ModifyContactActivity.class);
			intent.putExtra("type", 0);// type 0Ϊ��ӣ�1Ϊ�޸�
			intent.putExtra("id", 0);// ���ʱidΪ0
			// startActivity(intent);
			startActivityForResult(intent, SUB_ACTIVITY_MODIFY);
			return true;
		case R.id.action_about:
			Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
			// startActivity(intent1);
			startActivityForResult(intent1, SUB_ACTIVITY_ABOUT);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// �洢����İ༶λ��
			MainActivity.this.position = position;
			// Class data = (Class) mListView.getAdapter().getItem(position);
			selectItem(position);
		}
	}

	public void selectItem(int position) {
		Class data = (Class) mListView.getAdapter().getItem(position);// ��ȡclass����
		Fragment fragment = new ContactListFragment(data.getId());
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		mListView.setItemChecked(position, true);
		setTitle(data.getName());
		mDrawerLayout.closeDrawer(mListView);
	}

	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		boolean flag = mDrawerLayout.isDrawerOpen(mListView);
		if (!flag) {
			mDrawerLayout.openDrawer(mListView);
			return;
		}
		super.onBackPressed();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// return super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(MainActivity.this, "�ٰ�һ���˳�", Toast.LENGTH_SHORT)
					.show();
			exitTime = System.currentTimeMillis();
		} else {
			BaseApplication.getInstance().AppExit();// ��ȫ�˳�����
		}
	}
}
