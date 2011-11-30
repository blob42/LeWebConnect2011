package com.thinkit.lewebconnect;

import java.util.Comparator;

import android.R.bool;

public class Attendee{
	private int id;
	private String fname;
	private String lname;
    private String company;
    private String twitter;
    private String url;
    private String linkedin;
    private String facebook;
    private String country;
    private int likes;
    private boolean has_facebook = false;
	private boolean has_twitter = false;
    private boolean has_linkedin = false;
    
    

	public static final String LNAME = "lname";
    public static final String FNAME = "fname";
    public static final String COMPANY = "company";
    public static final String TWITTER = "twitter";
    public static final String LINKEDIN = "linkedin";
    public static final String FACEBOOK = "facebook";
    public static final String COUNTRY = "country";
    public static final String LIKES = "likes";
    public static final String ID = "user_id";

    
	
    public Attendee()
	{
		
	}
    
	public Attendee(int id)
	{
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
		if ( twitter != null && !twitter.equals("null") && !twitter.isEmpty())
			setHas_twitter(true);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
		if ( linkedin != null && !linkedin.equals("null") && !linkedin.isEmpty())
			setHas_linkedin(true);
	}
	
	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
		if ( facebook != null && !facebook.equals("null") && !facebook.isEmpty())
			setHas_facebook(true);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public boolean isHas_facebook() {
		return has_facebook;
	}

	public void setHas_facebook(boolean has_facebook) {
		this.has_facebook = has_facebook;
	}

	public boolean isHas_twitter() {
		return has_twitter;
	}

	public void setHas_twitter(boolean has_twitter) {
		this.has_twitter = has_twitter;
	}

	public boolean isHas_linkedin() {
		return has_linkedin;
	}

	public void setHas_linkedin(boolean has_linkedin) {
		this.has_linkedin = has_linkedin;
	}
	
	@Override public String toString() {
		return new String(getFname() + " " + getLname());
		
	}
	
	static public class LeWebByLnameComparator implements Comparator<Attendee>
	{

		public int compare(Attendee lhs, Attendee rhs) {
			// TODO Auto-generated method stub
			return lhs.getLname().compareToIgnoreCase(rhs.getLname());
		}
		
	}
	
	static public class LeWebByCompanyComparator implements Comparator<Attendee>
	{

		public int compare(Attendee lhs, Attendee rhs) {
			// TODO Auto-generated method stub
			return lhs.getCompany().compareToIgnoreCase(rhs.getCompany());
		}
		
	}
	
	static public class LeWebByCountryComparator implements Comparator<Attendee>
	{

		public int compare(Attendee lhs, Attendee rhs) {
			// TODO Auto-generated method stub
			return lhs.getCountry().compareToIgnoreCase(rhs.getCountry());
		}
		
	}
}


