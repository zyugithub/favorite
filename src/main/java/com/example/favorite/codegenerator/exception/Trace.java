package com.example.favorite.codegenerator.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Trace {
	public static String printStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		
		try {
		    t.printStackTrace(pw);
		    return sw.getBuffer().toString();
		} finally {
			try {
				pw.close();
				sw.close();
			} catch (IOException e) {
			}
		}
	}
}
