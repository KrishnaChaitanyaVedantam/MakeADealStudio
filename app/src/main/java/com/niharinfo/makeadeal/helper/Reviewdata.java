package com.niharinfo.makeadeal.helper;

import com.google.gson.annotations.Expose;

public class Reviewdata {
	@Expose
	private String summary;
	@Expose
	private String user_name;
	@Expose
	private String ratings_score;
	@Expose
	private String date;
	@Expose
	private String pros;
	@Expose
	private String heading;
	@Expose
	private String cons;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getRatings_score() {
		return ratings_score;
	}

	public void setRatings_score(String ratings_score) {
		this.ratings_score = ratings_score;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPros() {
		return pros;
	}

	public void setPros(String pros) {
		this.pros = pros;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getCons() {
		return cons;
	}

	public void setCons(String cons) {
		this.cons = cons;
	}

}
