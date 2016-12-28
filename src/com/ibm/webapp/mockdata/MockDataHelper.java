package com.ibm.webapp.mockdata;

import java.io.InputStream;
import java.util.Scanner;

public class MockDataHelper {

	public static String getClaimData() {
		return readFromFile("com/ibm/webapp/mockdata/claimlist.properties");
	}

	private static String readFromFile(String path) {

		String content = "{}";
		try {
			//
			InputStream inp = MockDataHelper.class.getClassLoader()
					.getResourceAsStream(path);
			Scanner scr = new Scanner(inp);
			scr.useDelimiter("\\A");
			content = (scr.hasNext() ? scr.next() : "{}");
			scr.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return content;

	}

}
