package com.thinkit.lewebconnect;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.util.Log;

public class WebService {
	static String XML_DB_URL = "http://leweb.i-think-it.com/get/";
	static private String FILENAME = "leweb.xml"; 
	private int bufsize = 1024;
	private Context context;
	
	public WebService(Context appcontext)
	{
		Log.d("WebService", "starting web service constructor");
		this.context = appcontext;
	}
	
	
	public void GetRemoteDB() throws IOException
	{
		Log.d("WebService", "inside getremote");
		URL url = new URL(XML_DB_URL);
		InputStream input = url.openStream();
		Log.d("WebService", "Opened remote streamm starting download");
		try {
		    FileOutputStream output = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
		    try {
		        byte[] buffer = new byte[bufsize];
		        int bytesRead = 0;
				while ((bytesRead  = input.read(buffer, 0, buffer.length)) >= 0) {
					Log.d("WebService", new String(buffer, 0, bufsize));
		            output.write(buffer, 0, buffer.length);
		        }
		    } finally {
		        output.close();
		    }
		} finally {
		    input.close();
		}
	}
}
