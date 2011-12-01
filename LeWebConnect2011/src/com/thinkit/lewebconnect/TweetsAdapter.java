package com.thinkit.lewebconnect;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TweetsAdapter extends ArrayAdapter<Tweet> {
	private ArrayList<Tweet> tweets;
	private Context context;
	
	public TweetsAdapter(Context context, int textViewResouceID, ArrayList<Tweet> tweets) {
		super(context, textViewResouceID, tweets);
		this.tweets = tweets;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			      v = vi.inflate(R.layout.tweet_row, null);
			    }

			    Tweet tweet = tweets.get(position);
			    if (tweet != null) {
			      TextView tweet_msg = (TextView) v.findViewById(R.id.tweet_text);
			      TextView tweet_date = (TextView) v.findViewById(R.id.tweet_date);
//			      ImageView image = (ImageView) v.findViewById(R.id.avatar);
//
			      if (tweet_date != null) {
			        tweet_date.setText(tweet.date_created);
			      }

			      if(tweet_msg != null) {
			        tweet_msg.setText(tweet.message);
			      }
			        
//			      if(image != null) {
//			        image.setImageBitmap(getBitmap(tweet.image_url));
//			      }
			    }	
		return v;
		
	}
}
