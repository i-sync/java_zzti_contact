package com.zzti.bean;
 

import com.google.gson.reflect.TypeToken;

import android.util.Log; 

public class Common {
	
	private volatile static Common instance;

	private Common() {}

	/**
	 * get instance
	 * 
	 * @return
	 */
	public static Common getInstance() {
		if (instance == null) {
			synchronized (Common.class) {
				if (instance == null) {
					instance = new Common();
				}
			}
		}
		return instance;
	}

	/**
	 * get class list
	 * 
	 * @return
	 */
	public ListResult<Class> class_getlist() {
		//rest path
		String path = "/class/list";
		Log.i("------->>", path);
		return RestRequest.getT(path, "application/json", new TypeToken<ListResult<Class>>(){}.getType());
	}

	/**
	 * get contact list
	 * 
	 * @param data
	 * @return
	 */
	public ListResult<Contact> contact_getlist(Contact data) {
		String path = "/contact/list";
		return RestRequest.postT(path, "application/json", data,new TypeToken<ListResult<Contact>>(){}.getType());
	}

	/**
	 *get contact model by id
	 * @param data
	 * @return
	 */
	public TResult<Contact> contact_getmodel(Contact data) {
		String path = "/contact/model";
		return RestRequest.postT(path, "application/json", data, new TypeToken<TResult<Contact>>(){}.getType());
	}
	
	/**
	 * add contact
	 * @param data
	 * @return
	 */
	public Result contact_add(Contact data)
	{
		String path="/contact/add";
		return RestRequest.postT(path, "application/json", data, Result.class);
	}
	
	/**
	 * update contact
	 * @param data
	 * @return
	 */
	public Result contact_update(Contact data)
	{
		String path="/contact/update";
		return RestRequest.postT(path, "application/json", data, Result.class);
	}
}
