package com.thinkit.lewebconnect;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.thinkit.lewebconnect.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class UserProfile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		try {
			setContentView(R.layout.profile);
			setTitle(R.string.user_profile);
			TextView mUserName = (TextView) findViewById(R.id.profile_name);
			TextView mCompany = (TextView) findViewById(R.id.profile_company);
			TextView mCountry = (TextView) findViewById(R.id.profile_country);
			final TextView mLikes = (TextView) findViewById(R.id.profile_likes);
			final Bundle extras = getIntent().getExtras();
			if (extras != null) {
				String fname = extras.getString(Attendee.FNAME);
				String lname = extras.getString(Attendee.LNAME);
				mUserName.setText(lname + " " + fname);
				mCompany.setText(extras.getString(Attendee.COMPANY));
				mCountry.setText(extras.getString(Attendee.COUNTRY));
				mLikes.setText(String.valueOf(extras.getInt(Attendee.LIKES)));
			}
			
			final Button button = (Button) findViewById(R.id.like_button);
	         button.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 incrementUser(extras.getInt(Attendee.ID));
	                 mLikes.setText(String.valueOf(extras.getInt(Attendee.LIKES) + 1));
	             }
	         });
	         
	         String twitter_name = extras.getString(Attendee.TWITTER);
	         if (twitter_name != null)
	         {
	        	 
	        	 ArrayList<Tweet> tweets = Tweet.getTweets(twitter_name);
	        	 ListView lv = (ListView) findViewById(R.id.tweets_list);
	        	 lv.setAdapter(new TweetsAdapter(this, R.layout.tweet_row, tweets));
	         }
	         
	          
	         
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void incrementUser(int id) {
		// TODO Auto-generated method stub
		try {
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet get = new HttpGet(WebService.INCREMENT_USER + String.valueOf(id));
			HttpResponse httpResponse = httpClient.execute(get);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}