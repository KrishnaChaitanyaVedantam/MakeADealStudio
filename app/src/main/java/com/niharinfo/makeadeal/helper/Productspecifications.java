package com.niharinfo.makeadeal.helper;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Productspecifications {
	@Expose
	private List<SpecificatoinsData> specifications=new ArrayList<SpecificatoinsData>();

	public List<SpecificatoinsData> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<SpecificatoinsData> specifications) {
		this.specifications = specifications;
	}
}
