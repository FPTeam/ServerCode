package com.example.demo.dbobject;

public class Passage {
    private int userid,passageid,LikeNumber,CommentNumber,CollectNumber;
    private String content,imgpath,PostTime, ChangeTime , PostPlace,name,information,sex,head;


    public void setUserid(int userid) {
        this.userid = userid;
    }
    public int getUserid() {
        return userid;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent(){
        return content;
    }
    public void setPassageid(int passageid) {
        this.passageid = passageid;
    }
    public int getPassageid() {
        return passageid;
    }
    public void setCommentNumber(int CommentNumberNumber){this.CommentNumber=CommentNumber;}
    public int getCommentNumber(){return CommentNumber;}
    public void setLikeNumber(int likeNumber){this.LikeNumber=likeNumber;}
    public int getLikeNumber(){return LikeNumber;}
    public void setCollectNumber(int CollectNumber){this.CollectNumber=CollectNumber;}
    public int getCollectNumber(){return CollectNumber;}
    public void setPostTime(String postTime){this.PostTime=postTime;}
    public String getPostTime(){
        return PostTime;
    }
    public void setChangeTime(String ChangeTime){this.ChangeTime=ChangeTime;}
    public String getChangeTime(){
        return ChangeTime;
    }
    public void setPostPlace(String postPlace){this.PostPlace=postPlace;}
    public String getPostPlace(){
        return PostPlace;
    }
    public void setimgpath(String imgpath){this.imgpath=imgpath;}
    public String getimgpath(){
        return imgpath;
    }
    public void setname(String name){this.name=name;}
    public String getname(){
        return name;
    }
    public void sethead(String head){this.head=head;}
    public String gethead(){
        return head;
    }
    @Override
    public String toString() {
        return "passageid:"+passageid+",\n"+"userid:"+userid+",\n"+"content:"+content;
    }
}
