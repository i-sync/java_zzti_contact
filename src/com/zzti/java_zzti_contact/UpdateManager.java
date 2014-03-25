package com.zzti.java_zzti_contact;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 自动更新
 * 
 * @author zhenyun
 * 
 */
public class UpdateManager {
	/* 下载xml */
	private static final int DOWNLOAD_XML = 0;
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
	HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 本地versionCode */
	private int versionCode;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private String url = "http://contact09.duapp.com/java_zzti_cloud/android/update.xml";

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_XML:
				// 检查是否更新
				checkUpdate();
				break;
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context, int versionCode) {
		this.mContext = context;
		this.versionCode = versionCode;
	}

	/**
	 * 首先下载xml,然后再解析，对比
	 */
	public void downloadXml() {
		new Thread(new downloadXmlThread()).start();
	}

	/**
	 * 检测软件更新
	 */
	private void checkUpdate() {
		int serviceCode = Integer.valueOf(mHashMap.get("version"));
		// 版本判断
		if (serviceCode > versionCode) {
			// 显示提示对话框
			showNoticeDialog();
		} else {
			Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog() {
		// 构造对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(R.string.soft_update_info);
		// 更新
		builder.setPositiveButton(R.string.soft_update_updatebtn,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 显示下载对话框
						showDownloadDialog();
					}
				});
		// 稍后更新
		builder.setNegativeButton(R.string.soft_update_later,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
		// 构造软件下载对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton(R.string.soft_update_cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 设置取消状态
						cancelUpdate = true;
					}
				});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载xml文件
	 * 
	 * @author zhenyun
	 * 
	 */
	private class downloadXmlThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				URL url = new URL(UpdateManager.this.url);// 创建version.xml的连接地址。
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				if(connection.getResponseCode()!=200)
					return ;
				InputStream is = connection.getInputStream();// 从输入流获取数据
				
				mHashMap = new HashMap<String, String>();
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(is, "UTF-8");// 设置解析的数据源
				int type = parser.getEventType();
				while (type != XmlPullParser.END_DOCUMENT) {
					switch (type) {
					case XmlPullParser.START_TAG:
						if ("version".equals(parser.getName())) {
							mHashMap.put("version", parser.nextText());
							//Log.i("version---", mHashMap.get("version"));
						} else if ("name".equals(parser.getName())) {
							mHashMap.put("name", parser.nextText());
							//Log.i("name---", mHashMap.get("name"));
						} else if ("url".equals(parser.getName())) {
							mHashMap.put("url", parser.nextText());
							//Log.i("url---", mHashMap.get("url"));
						}
						break;
					}
					type = parser.next();
				}

				mHandler.sendEmptyMessage(DOWNLOAD_XML);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 * @date 2012-4-26
	 * @blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					URL url = new URL(mHashMap.get("url"));
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, mHashMap.get("name"));
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
				else
				{
					Log.i("---->","无效存储卡");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, mHashMap.get("name"));
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}