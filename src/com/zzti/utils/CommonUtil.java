package com.zzti.utils;

import java.text.DecimalFormat;

public class CommonUtil {

	public CommonUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String formetFileSize(int size) {
		DecimalFormat df = new DecimalFormat("#.00");

		if (size < 1024)
			return df.format(size) + "B";
		else if (size < 1048576) // 1048576=1024*1024
			return df.format((double) size / 1024) + "K";
		else if (size < 1073741824) // 1073741824=1024 * 1024 * 1024
			return df.format((double) size / 1048576) + "M";
		else
			return df.format((double) size / 1073741824) + "G";

	}
}
