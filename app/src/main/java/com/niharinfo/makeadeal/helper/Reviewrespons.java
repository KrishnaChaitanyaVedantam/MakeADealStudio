package com.niharinfo.makeadeal.helper;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;


public class Reviewrespons {
	@Expose
	private List<Reviewdata> key=new ArrayList<Reviewdata>();

	public List<Reviewdata> getKey() {
		return key;
	}

	public void setKey(List<Reviewdata> key) {
		this.key = key;
	}
}
