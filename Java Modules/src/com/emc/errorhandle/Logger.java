package com.emc.errorhandle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	String cause;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

	public Logger(Object cause) {
		super();
		this.cause = cause.toString();
	}

	public void logIt(Object loggedString) {
		System.out.println(cause + "::" + SDF.format(new Date()) + " ::" + loggedString);
	}
}
