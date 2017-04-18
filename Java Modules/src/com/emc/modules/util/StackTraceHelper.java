package com.emc.modules.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceHelper {

	public static String convertToString(final Throwable t) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
}
