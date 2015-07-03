package com.niharinfo.makeadeal.helper;

import com.google.gson.annotations.Expose;

/**
 * Created by chaitanya on 5/6/15.
 */
public class Details {
    @Expose
    private String attr_name;
    @Expose
    private String attr_desc;

    public String getAttr_name ()
    {
        return attr_name;
    }

    public void setAttr_name (String attr_name)
    {
        this.attr_name = attr_name;
    }

    public String getAttr_desc ()
    {
        return attr_desc;
    }

    public void setAttr_desc (String attr_desc)
    {
        this.attr_desc = attr_desc;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [attr_name = "+attr_name+", attr_desc = "+attr_desc+"]";
    }
}
