package com.thinkit.lewebconnect;


import java.util.ArrayList;
import java.util.Collections;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thinkit.lewebconnect.Attendee.LeWebByCompanyComparator;
import com.thinkit.lewebconnect.Attendee.LeWebByCountryComparator;
import com.thinkit.lewebconnect.Attendee.LeWebByLnameComparator;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.Element;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;



public class Perticipents extends ListActivity {
    /** Called when the activity is first created. */
	
	private static final int FACEBOOK = Menu.FIRST;
    private static final int TWITTER = Menu.FIRST + 1;
    private static final int LINKEDIN = Menu.FIRST + 2;
	
	private static String TAG = "Perticipents";
	private ArrayList<Attendee> users;
	private EditText filterText = null;
	ArrayAdapter<Attendee> adapter = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	
		String xml;
		xml = XMLfunctions.getXML();
		Document doc = XMLfunctions.XMLfromString(xml);
		int numResults = XMLfunctions.numResults(doc);
		//Log.d(TAG, "numResults length: " + String.valueOf(numResults));
		
		if((numResults <= 0)) {
			Toast.makeText(this, "Please make sure connection is available !", Toast.LENGTH_LONG).show();
            finish();
		}
		
		
		NodeList nodes = doc.getElementsByTagName("user");
		try {
			users = new ArrayList<Attendee>();
			//fill in the list items from the XML document
			for (int i = 0; i < nodes.getLength(); i++) {
				Node e = (Node)nodes.item(i);
				users.add(NodeToAttendee(e));
				}
			Collections.sort(users, new LeWebByLnameComparator());
			
			
			setContentView(R.layout.perticipents);
			filterText= (EditText) findViewById(R.building_list.search_box);
			filterText.addTextChangedListener(filterTextWatcher);
			
			
			ListView lv = getListView();
	        lv.setFastScrollEnabled(true);
	        lv.setTextFilterEnabled(false);
	        registerForContextMenu(lv);
			
			adapter =  new LeWebAdapter(this, users, true, false, false);
			

			setListAdapter(adapter);
			registerForContextMenu(getListView());
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    
	private TextWatcher filterTextWatcher = new TextWatcher() {
    	public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        @SuppressWarnings("unchecked")
		public void onTextChanged(CharSequence s, int start, int before,
                int count) {
        	ListView lv = getListView();
        	Log.d("onTextChanged", s.toString());
        	Log.d("getCount", String.valueOf(lv.getAdapter().getCount()));
        	adapter = (ArrayAdapter<Attendee>) lv.getAdapter();
    		adapter.getFilter().filter(s);
        	Log.d("getCount", String.valueOf(lv.getAdapter().getCount()));
        }
    };
    
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        filterText.removeTextChangedListener(filterTextWatcher);
    }
    
    
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		try {
			Intent i = new Intent(this, UserProfile.class);
			Attendee user = (Attendee) l.getItemAtPosition(position);
			Toast.makeText(this, user.getLname(), Toast.LENGTH_LONG).show();
			
			i.putExtra("user_name", user.getLinkedin());
			startActivity(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater infalter = getMenuInflater();
		infalter.inflate(R.menu.attendies, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case R.id.by_name:
	        {
	    		try {
					Collections.sort(users, new LeWebByLnameComparator());
					adapter = new LeWebAdapter(this, users, true, false, false);
					ListView lv = getListView();
					lv.setTextFilterEnabled(false);
					lv.setAdapter(adapter);
					lv.refreshDrawableState();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	Toast.makeText(this, "Filtering by Name!", Toast.LENGTH_LONG).show();
	            break;
	    	}
        case R.id.by_country:
        	{
        		try {
    				Collections.sort(users, new LeWebByCountryComparator());
    				adapter = new LeWebAdapter(this, users, true, false, true);
    				ListView lv = getListView();
					lv.setTextFilterEnabled(false);
    				lv.setAdapter(adapter);
    				lv.refreshDrawableState();
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		Toast.makeText(this, "Filtered by country.", Toast.LENGTH_LONG).show();
                break;
        	}
                
        case R.id.by_company: 
        	{
            	try {
    				Collections.sort(users, new LeWebByCompanyComparator());
    				adapter = new LeWebAdapter(this, users, true, true, false);
    				ListView lv = getListView();
					lv.setTextFilterEnabled(false);
    				lv.setAdapter(adapter);
    				lv.refreshDrawableState();
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            	Toast.makeText(this, "Filtered by company.", Toast.LENGTH_LONG).show();
                break;
        	}
		}
		return super.onOptionsItemSelected(item);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenu.ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu((ContextMenu) menu, v, menuInfo);
	    menu.add(0, FACEBOOK, 0, R.string.facebook_menu);
	    menu.add(0, TWITTER, 0, R.string.twitter_menu);
	    menu.add(0, LINKEDIN, 0, R.string.linkedin_menu);
	}

	
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case FACEBOOK:
		{
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			int id = (int) info.position;
			Toast.makeText(this, String.valueOf(id), Toast.LENGTH_LONG).show();
			return true;
		}
		case TWITTER:
			return true;
		case LINKEDIN:
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	public Attendee NodeToAttendee(Node userNode)
    {
    	NamedNodeMap attrs = userNode.getAttributes();
    	String fname = attrs.getNamedItem("fname").getTextContent();
		String lname = attrs.getNamedItem("lname").getTextContent();
		String company = attrs.getNamedItem("company").getTextContent();
		String country = attrs.getNamedItem("country").getTextContent();
		String facebook = attrs.getNamedItem("facebook").getTextContent();
		String twitter = attrs.getNamedItem("twitter").getTextContent();
		
		Attendee user = new Attendee();
		user.setFname(fname);
		user.setLname(lname);
		user.setCompany(company);
		user.setCountry(country);
		user.setFacebook(facebook);
		user.setTwitter(twitter);
		return user;
    }
 }