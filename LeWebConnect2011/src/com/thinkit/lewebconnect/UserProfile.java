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
		
		try {
			setContentView(R.layout.profile);
			setTitle(R.string.user_profile);
			TextView mUserName = (TextView) findViewById(R.id.profile_name);
			TextView mCompany = (TextView) findViewById(R.id.profile_company);
			TextView mCountry = (TextView) findViewById(R.id.profile_country);
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				String fname = extras.getString(Attendee.FNAME);
				String lname = extras.getString(Attendee.LNAME);
				mUserName.setText(lname + " " + fname);
				mCompany.setText(extras.getString(Attendee.COMPANY));
				mCountry.setText(extras.getString(Attendee.COUNTRY));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
