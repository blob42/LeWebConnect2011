package com.thinkit.lewebconnect;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;

public class LeWebConnect extends TabActivity{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);

        try {
			Resources res = getResources(); // Resource object to get Drawables
			TabHost tabHost = getTabHost(); // The activity TabHost
			TabHost.TabSpec spec; // Resusable TabSpec for each tab
			Intent intent; // Reusable Intent for each tab
			// Create an Intent to launch an Activity for the tab (to be reused)
			intent = new Intent().setClass(this, Perticipents.class);
			// Initialize a TabSpec for each tab and add it to the TabHost
			spec = tabHost
					.newTabSpec("perticipents")
					.setIndicator("",
							res.getDrawable(R.drawable.ic_tab_perticipents))
					.setContent(intent);
			tabHost.addTab(spec);
			// Do the same for the other tabs
			intent = new Intent().setClass(this, Search.class);
			spec = tabHost
					.newTabSpec("search")
					.setIndicator("", res.getDrawable(R.drawable.ic_tab_search))
					.setContent(intent);
			tabHost.addTab(spec);
			intent = new Intent().setClass(this, Likes.class);
			spec = tabHost
					.newTabSpec("likes")
					.setIndicator("",
							res.getDrawable(R.drawable.ic_tab_favorites))
					.setContent(intent);
			tabHost.addTab(spec);
			tabHost.setCurrentTab(0);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Error", e.toString());
			e.printStackTrace();
		}
    }
}