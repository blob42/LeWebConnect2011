package com.thinkit.lewebconnect;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class Tweet {
	public static final String TWEETS_URL = "http://api.twitter.com/1/statuses/user_timeline.json?screen_name=";
	public String message;
	public String date_created;
	public String tweet_id;
	public String profile_image_url;
	
	public Tweet(String tweet_id, String message, String date_created, String profile_image_url) {
		this.message = message;
		this.date_created = date_created;
		this.tweet_id = tweet_id;
		this.profile_image_url = profile_image_url;
	}
	
	public static ArrayList<Tweet> getTweets(String username) {
		
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(TWEETS_URL + username + "&count=10");
		
		
		HttpResponse httpResponse = null;
		
//		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = null;
		
		try {
//			responseBody = client.execute(get, responseHandler);
			httpResponse = client.execute(get);
			HttpEntity httpEntity = httpResponse.getEntity();
			responseBody = EntityUtils.toString(httpEntity, "UTF-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
//			JSONObject stats = (JSONObject) new JSONTokener(responseBody).nextValue();
			JSONTokener tokener = new JSONTokener(responseBody);
			JSONArray stats = new JSONArray(tokener);
			
			for (int i =0; i < stats.length(); i++) {
				JSONObject stat = stats.getJSONObject(i);
				String tweet_id = stat.getString("id_str");
				String tweet_msg = stat.getString("text");
				String tweet_date = stat.getString("created_at");
				tweet_date = tweet_date.substring(0, 16);
				JSONObject user = stat.getJSONObject("user");
				String profile_image = user.getString("profile_image_url");
				Tweet tweet = new Tweet(tweet_id, tweet_msg, tweet_date, profile_image);
				tweets.add(tweet);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tweets;
		
	}
	
	public Bitmap getBitmap(String bitmapUrl) {
		try {
			URL url = new URL(bitmapUrl);
			return BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
}
