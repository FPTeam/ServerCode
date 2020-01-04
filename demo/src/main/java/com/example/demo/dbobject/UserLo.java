package com.example.demo.dbobject;

public class UserLo {
    private String name;
    private String password;
    private String head;
    public void setName(String name){
        this.name=name;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
    public void setHead(String head){this.head=head;}
    public String getName()
    {
        return name;
    }
    public String getPassword()
    {
        return password;
    }
    public String getHead(){return head;}
    @Override
    public String toString() {
        return "name:"+name+",\n"+"password:"+password+",\n"+"head:"+head+"\n";
    }
}
