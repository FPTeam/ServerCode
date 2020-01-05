package com.example.littleworld;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;

public class PersonInfo {
    String name="";
    String intro="";
    String sex="" ;
    String img="";
public void set(JSONObject p) throws JSONException {
    this.name=p.getString("name");
    this.intro=p.getString("infomation");
    this.sex=p.getString("sex");
    this.img=p.getString("head");
}
    @Override
    public String toString() {
        return "name:"+name+",\n"+"head:"+img+"\n";
    }
}
