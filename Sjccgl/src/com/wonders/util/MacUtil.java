package com.wonders.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class MacUtil {

	public static String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC") > 1) {
						macAddress = str.substring(str.indexOf("=") + 2, str.length());
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}
}
