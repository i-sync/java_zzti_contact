package com.zzti.java_zzti_contact;

import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

import com.zzti.bean.Class;
import com.zzti.bean.ListResult;

public class MainActivity extends BaseActivity implements OnQueryTextListener {
	private long exitTime;// 退出确认
	private DrawerLayout mDrawerLayout;
	private ListView mListView;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	//private ProgressBar progressBar = Common.getInstance().getProgressBar();
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			ListResult<Class> result = (ListResult<Class>)msg.obj;
			if(result.getResult()!=1)
			{
				Log.i("---->", result.getMessage());
				Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
			}
			DrawerListAdapter adapter = new DrawerListAdapter(result.getList(), MainActivity.this);
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(new DrawerItemClickListener());
			
			selectItem(0, result.getList().get(0));
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
		
		//开启线程
		//this.addContentView(progressBar,null);
		new Thread(new LoadClassInfo()).start();
	}
	
	private class LoadClassInfo implements Runnable
	{
		@Override
		public void run() {
			ListResult<Class> result = com.zzti.bean.Common.getInstance().class_getlist();
			Message msg = Message.obtain();
			msg.obj = result;
			handler.sendMessage(msg);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.main, menu);
		// 取得id为R.id.action_search的MenuItem，由于xml文件中已经指定android:actionViewClass="android.widget.SearchView"
        // 所以通过getActionView()便可以显式转换为SearchView
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        // 注册搜索输入栏的事件监听器，来自于实现接口：android.widget.SearchView.OnQueryTextListener
        // 事件回调方法为：onQueryTextSubmit()开始搜索事件；onQueryTextChange()文本改变事件
        searchView.setOnQueryTextListener(this);
                                                  
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
		return false;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mListView);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		menu.findItem(R.id.action_search).setVisible(!drawerOpen);
		menu.findItem(R.id.action_add_contact).setVisible(!drawerOpen);
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
		case R.id.action_search:
			return true;
		case R.id.action_add_contact:
			Intent intent = new Intent(MainActivity.this,ModifyContactActivity.class);
			intent.putExtra("type", 0);//type 0为添加，1为修改
			intent.putExtra("id", 0);//添加时id为0
			startActivity(intent);
			
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
			Class data = (Class)mListView.getAdapter().getItem(position);
			selectItem(position,data);
		}
	}

	private void selectItem(int position, Class data) {
		Fragment fragment = new ContactListFragment(data);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
		if(!flag){
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
			Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT)
					.show();
			exitTime = System.currentTimeMillis();
		} else {
//			finish();
//			System.exit(0);
			BaseApplication.getInstance().AppExit();//完全退出程序
		}
	}
}
