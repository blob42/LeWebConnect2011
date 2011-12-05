package com.thinkit.lewebconnect;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThinkIT extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.thinkit);
		
		try {
			View button = findViewById(R.id.thinkit_button);
			button.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	String url = "http://i-think-it.com/";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
			    }
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		View button = findViewById(R.id.email);
		button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		        emailIntent.setType("plain/text");
		        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"contact@i-think-it.com"});
		        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback");
		        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello guys, your app is great ... ");
		        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}
		});
		
		
		
	}
	
	

}
