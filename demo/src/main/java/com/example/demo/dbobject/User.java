package com.example.demo.dbobject;

public class User {
    private String name;
    private String infomation;
    private String sex;
    private String head;
    public void setName(String name){
        this.name=name;
    }
    public void setInfomation(String infomation)
    {
        this.infomation=infomation;
    }
    public void setSex(String sex){
        this.sex=sex;
    }
    public void setHead(String head){this.head=head;}
    public String getName()
    {
        return name;
    }
    public String getInfomation()
    {
        return infomation;
    }
    public String getSex()
    {
        return sex;
    }
    public String getHead(){return head;}
    @Override
    public String toString() {
        return "name:"+name+",\n"+"head:"+head+"\n";
    }
}
