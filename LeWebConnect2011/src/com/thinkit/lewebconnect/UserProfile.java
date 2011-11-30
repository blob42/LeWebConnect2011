package com.thinkit.lewebconnect;
import com.thinkit.lewebconnect.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class UserProfile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.profile);
		setTitle(R.string.user_profile);
		TextView mUserName = (TextView) findViewById(R.id.profile_name);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String name = extras.getString("user_name");
			mUserName.setText(name);
		}
	}
}
