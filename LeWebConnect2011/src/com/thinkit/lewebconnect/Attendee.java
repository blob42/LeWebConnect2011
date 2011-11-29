package com.thinkit.lewebconnect;

import java.util.Comparator;

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
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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


