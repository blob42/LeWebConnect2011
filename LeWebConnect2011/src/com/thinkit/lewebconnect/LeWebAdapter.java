package com.thinkit.lewebconnect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class LeWebAdapter extends ArrayAdapter<Attendee>  {
	
	private final Context context;
	private ArrayList<Attendee> users;
	
	/**
     * Lock used to modify the content of {@link #mObjects}. Any write operation
     * performed on the array should be synchronized on this lock. This lock is also
     * used by the filter (see {@link #getFilter()} to make a synchronized copy of
     * the original array of data.
     */
	private final Object mLock = new Object();
    
	private ArrayList<Attendee> originalUsers;
    private LeWebFilter filter;
    
    
	private boolean mNotifyOnChange = true;
	
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections = new String[0];
    
    
    private boolean enableSections;
    private boolean companySections;
    private boolean countrySections;
	
    
	public LeWebAdapter(Context context, ArrayList<Attendee> users, 
			boolean enableSections, boolean companySections, boolean countrySections)  {
		super(context, R.layout.rowlayout, users);
		
		Log.d("LeWebAdapter", "LeWebAdapter constructor");
		this.context = context;
		this.users = users;
		this.filter = new LeWebFilter();
		this.enableSections = enableSections;
		this.companySections = companySections;
		this.countrySections = countrySections;
		
		
		if(enableSections)
        {
//			Log.d("LeWebAdapter", "Sections enabled");
//			alphaIndexer = new HashMap<String, Integer>();
//			String firstChar;
//            for(int i = users.size() - 1; i >= 0; i--)
//            {
//                Attendee element = users.get(i);
//                if (countrySections)
//                	firstChar = element.getCountry().substring(0, 1).toUpperCase();
//                else if (companySections)
//                {
//                	Log.d("Sections", "Enabling company section, getting first letter");
//                	firstChar = element.getCompany().substring(0, 1).toUpperCase();
//                }
//                else
//                	firstChar = element.getLname().substring(0, 1).toUpperCase();
//                
//                if(firstChar.charAt(0) > 'Z' || firstChar.charAt(0) < 'A')
//                    firstChar = "A";
//
//                alphaIndexer.put(firstChar, i);
//            }
//
//            Set<String> keys = alphaIndexer.keySet();
////            Log.d("LeWebAdapter", keys.toString());
//            Iterator<String> it = keys.iterator();
//            ArrayList<String> keyList = new ArrayList<String>();
//            
//            while(it.hasNext())
//                keyList.add(it.next());
//
//            Collections.sort(keyList);
//            sections = new String[keyList.size()];
//            keyList.toArray(sections);
////            Log.d("LeWebAdapter", keyList.toString());
////            Log.d("LeWebAdapter", alphaIndexer.toString());
			buildHashIndexer();
			buildSections();
        }
	}
	
	public void buildSections() {
		
		Set<String> keys = alphaIndexer.keySet();
	    Iterator<String> it = keys.iterator();
	    ArrayList<String> keyList = new ArrayList<String>();
	    
	    while(it.hasNext())
	        keyList.add(it.next());
	
	    Collections.sort(keyList);
	    sections = new String[keyList.size()];
	    keyList.toArray(sections);
	}
	
	public void buildHashIndexer()
	{
		try {
//			Log.d("LeWebAdapter", "Building hashindexer");
			alphaIndexer = new HashMap<String, Integer>();
			String firstChar;
			for(int i = users.size() - 1; i >= 0; i--)
			{
			    Attendee element = users.get(i);
			    if (countrySections)
			    	firstChar = element.getCountry().substring(0, 1).toUpperCase();
			    else if (companySections)
			    {
			    	Log.d("Sections", "Enabling company section, getting first letter");
			    	firstChar = element.getCompany().substring(0, 1).toUpperCase();
			    }
			    else
			    	firstChar = element.getLname().substring(0, 1).toUpperCase();
			    
			    if(firstChar.charAt(0) > 'Z' || firstChar.charAt(0) < 'A')
			        firstChar = "A";

			    alphaIndexer.put(firstChar, i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void notifyDataSetChanged(){
		if (enableSections)
		{
			Log.d("notifyDataSetChanged", "...");
			buildHashIndexer();
			buildSections();	
		}
		super.notifyDataSetChanged();
		mNotifyOnChange = true;
	}
	
	@Override
    public void notifyDataSetInvalidated()
    {
		if(enableSections)
        {
			Log.d("notifyDataSetInvalidated", "...");

			buildHashIndexer();
			buildSections();
            super.notifyDataSetInvalidated();
        }
    }
	
	public void setNotifyOnChange(boolean notifyOnChange){
		mNotifyOnChange = notifyOnChange;
		
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowview = inflater.inflate(R.layout.rowlayout, parent, false);
		//Log.d("getView", "Making row ....");
		try {
				Attendee user = users.get(position);
				if (user != null)
				{
					TextView name_text = (TextView) rowview.findViewById(R.id.name_text);
					TextView company = (TextView) rowview.findViewById(R.id.company_text);
					TextView country = (TextView) rowview.findViewById(R.id.country_text);
					if (name_text != null)
						name_text.setText(user.getLname() + " " + user.getFname());
					if (company != null)
						company.setText(user.getCompany());
					if (country != null)
						country.setText(user.getCountry());
					setSocial(rowview,  user);
					if(enableSections && getSectionForPosition(position) != getSectionForPosition(position - 1))
		            {
//						Log.d("getView", "Setting Header ........ " + String.valueOf(position));
		                TextView h = (TextView) rowview.findViewById(R.id.header);
		                h.setText(sections[getSectionForPosition(position)]);
		                h.setVisibility(View.VISIBLE);
		            }
		            else
		            {
		                TextView h = (TextView) rowview.findViewById(R.id.header);
		                h.setVisibility(View.GONE);
		            }
				}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return rowview;
		}
	
	
	private void setSocial(View rowview, Attendee user) {
		// TODO Auto-generated method stub
		boolean facebook = false;
		boolean twitter = false;
		boolean linkedin = false;
		
		ImageView imageView = (ImageView) rowview.findViewById(R.id.icons);
		TextView id = (TextView) rowview.findViewById(R.id.id_text);
		TextView fb = (TextView) rowview.findViewById(R.id.fb_text);
		TextView tw = (TextView) rowview.findViewById(R.id.tw_text);
		TextView ld = (TextView) rowview.findViewById(R.id.ld_text);
		
		id.setText(String.valueOf(user.getId()));
		
		facebook =  ( user.getFacebook() != null && !user.getFacebook().equals("null") && !user.getFacebook().isEmpty());
		linkedin = ( user.getLinkedin() != null && !user.getLinkedin().equals("null") && !user.getLinkedin().isEmpty());
		twitter =  ( user.getTwitter() != null && !user.getTwitter().equals("null")  && !user.getTwitter().isEmpty());
		
		fb.setText("null");
		ld.setText("null");
		tw.setText("null");
		
		if (facebook && twitter && linkedin){
			imageView.setImageResource(R.drawable.social_all);
			fb.setText(user.getFacebook());
			tw.setText(user.getTwitter());
			ld.setText(user.getLinkedin());
		}
		else if (facebook && twitter) {
			imageView.setImageResource(R.drawable.no_linkedin);
			fb.setText(user.getFacebook());
			tw.setText(user.getTwitter());
		}
		else if (facebook && linkedin) {
			imageView.setImageResource(R.drawable.no_twitter);
			fb.setText(user.getFacebook());
			ld.setText(user.getLinkedin());
		}
		else if (twitter && linkedin) {
			imageView.setImageResource(R.drawable.no_fb);
			tw.setText(user.getTwitter());
			ld.setText(user.getLinkedin());
		}
		else if (twitter) {
			imageView.setImageResource(R.drawable.twitter);
			tw.setText(user.getTwitter());
		}
		else if (facebook) {
			imageView.setImageResource(R.drawable.fb);
			fb.setText(user.getFacebook());
		}
		else if (linkedin) {
			imageView.setImageResource(R.drawable.linkedin);
			ld.setText(user.getLinkedin());
		}
		else 
			imageView.setImageResource(R.drawable.no_all);
	}

	@Override
	public int getCount(){
		return this.users.size();
	}
		
	
    public int getPositionForSection(int section)
    {
    	try {
    		if(!enableSections) return 0;
            if (section == sections.length)
            	section--;
    		String letter = sections[section];

            return alphaIndexer.get(letter);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("getPositionForSection", String.valueOf(section));
			Log.d("getPositionForSection", "alphaindexer: " + alphaIndexer.toString());
			Log.d("getPositionForSection", "sections: " + getSections());
			//e.printStackTrace();
		}
        return 0;
    }

    public int getSectionForPosition(int position)
    {
//    	Log.d("getSectionForPosition", "recievied position : " + String.valueOf(position));
        if(!enableSections) return 0;
        int prevIndex = 0;
//        for(int i = 0; i < sections.length; i++)
//        {
//            if(getPositionForSection(i) > position && prevIndex <= position)
//            {
//                prevIndex = i ;
//                break;
//            }
//            prevIndex = i;
//        }
        
        int i = 0;
        while (i < sections.length) {
//        	Log.d("i", "------------>" + String.valueOf(i));
//        	Log.d("sectionsLength", "------------>" + String.valueOf(sections.length));
        	if (getPositionForSection(i) > position && prevIndex <= position)
        	{
        		prevIndex = i;
        		break;
        	}
        	prevIndex = i;
        	i++;
        }
        
        prevIndex--;
//        Log.d("getSectionForPosition", "Returning : " + String.valueOf(prevIndex));
        return prevIndex;
    }

    public Object[] getSections()
    {
        return sections;
    }

    @Override
    public android.widget.Filter getFilter()
    {
        if(filter == null)
            filter = new LeWebFilter();
        return filter;
    }
    
    
    // Currently filters only by Lname
    private class LeWebFilter extends android.widget.Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // NOTE: this function is *always* called from a background thread, and
            // not the UI thread.
        	Log.d("Filter", "Filter .....");
            
            FilterResults results = new FilterResults();
            
            
            if (originalUsers == null) {
            	synchronized (mLock) {
            		originalUsers = new ArrayList<Attendee>(users);
            	}
            }
            if (constraint == null || constraint.length() == 0) {
            	synchronized (mLock) {
            		ArrayList<Attendee> list = new ArrayList<Attendee>(originalUsers);
            		results.values = list;
            		results.count = list.size();
            	}
			} else {
					try {
						String constraintStr = constraint.toString().toLowerCase();
						final ArrayList<Attendee> values = originalUsers;
						final int count = values.size();
						
						final ArrayList<Attendee> newValues = new ArrayList<Attendee>();
						
						for (int i = 0; i < count; i++) {
							final Attendee value = values.get(i);
							final String valueText;
							if (companySections)
							{
								valueText = value.getCompany().toLowerCase();
							}
							else if (countrySections){
								valueText = value.getCountry().toLowerCase();
							}
							else {
								valueText = value.getLname().toLowerCase();
							}
							
							// First match against the whole, non-splitted value
							if (valueText.startsWith(constraintStr)) {
								newValues.add(value);
							} else {
								final String[] words = valueText.split(" ");
								final int wordCount = words.length;
								
								for (int k = 0; k < wordCount; k++) {
									if (words[k].startsWith(constraintStr)) {
										newValues.add(value);
										break;
									}
								}
							}
						}
						
						results.values = newValues;
						results.count = newValues.size();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
            
            	return results;
            }            
//            constraint = constraint.toString().toLowerCase();
//            if(constraint != null && constraint.toString().length() > 0)
//            {
//            	Log.d("Filter", "constraint not null with value <" + constraint.toString() + ">");
//                ArrayList<Attendee> filt = new ArrayList<Attendee>();
//                ArrayList<Attendee> lItems = new ArrayList<Attendee>();
//                synchronized (this)
//                {
//                    lItems.addAll(users);
//                }
//                for(int i = 0, l = lItems.size(); i < l; i++)
//                {
//                    Attendee m = lItems.get(i);
//                    if (countrySections && m.getCountry().toLowerCase().contains(constraint))
//                    		filt.add(m);
//                    else if (companySections && m.getCompany().toLowerCase().contains(constraint))
//                    	filt.add(m);
//                    else if (m.getLname().toLowerCase().contains(constraint))
//                        filt.add(m);
//                }
//                result.count = filt.size();
//                result.values = filt;
//            }
////            else if (constraint == null || constraint.length() == 0)
////            {
////            	ArrayList<Attendee> list = new ArrayList<Attendee>(originalUsers);
////            	result.values=list;
////            	result.count = list.size();
////            }
//            else
//            {
//                synchronized(this)
//                {
//                    result.values = users;
//                    result.count = users.size();
//                }
//            }
//            return result;

        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.
            users = (ArrayList<Attendee>)results.values;
            if (results.count > 0) {
//            	buildHashIndexer();
//    			buildSections();	
            	notifyDataSetChanged();
            } else {
//            	buildHashIndexer();
//    			buildSections();	
            	notifyDataSetInvalidated();
            }
//            notifyDataSetChanged();
//            clear();
//            for(int i = 0, l = originalUsers.size(); i < l; i++)
//                add(originalUsers.get(i));
//            notifyDataSetInvalidated();
        }
        
        
    }
}