package com.niharinfo.makeadeal.helper;

import java.util.List;

import com.google.gson.annotations.Expose;

public class SpecificatoinsData {
	
		@Expose
	    private String attr_group_name;
		@Expose
	    private List<Details> details;
	    

	    public String getAttr_group_name ()
	    {
	        return attr_group_name;
	    }

	    public void setAttr_group_name (String attr_group_name)
	    {
	        this.attr_group_name = attr_group_name;
	    }

	    public List<Details> getDetails ()
	    {
	        return details;
	    }

	    public void setDetails (List<Details> details)
	    {
	        this.details = details;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [attr_group_name = "+attr_group_name+", details = "+details+"]";
	    }
	}

			
		

