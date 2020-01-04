package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dbhelper.DbHelper;
import com.example.demo.dbobject.*;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@ComponentScan
@RestController
public class Hello {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/login")
    public int login(@RequestBody UserLo user)
    {
        System.out.println(user);
        String username=user.getName();
        String password=user.getPassword();
        String sql = "SELECT userid FROM login WHERE name=?and password=?";
        int userid;
       try{ userid = jdbcTemplate.queryForObject(sql, new Object[]{username,password},Integer.class);}
       catch (EmptyResultDataAccessException e) {

           return -1;}
        return userid;
    }
    @RequestMapping("/logup")
    public int logup(@RequestBody UserLo user)
    {
        System.out.println(user);
        //int i=DbHelper.insertuser(user);
        String username=user.getName();
        String password=user.getPassword();
        String head=user.getHead();
        System.out.println(username);
        System.out.println(password);
        String sql = "insert into login (name,password) values(?,?)";
        int j=jdbcTemplate.update(sql, username,password);
        String sql1 = "SELECT userid FROM login WHERE name=?and password=?";
        int userid;
        userid = jdbcTemplate.queryForObject(sql1, new Object[]{username,password},Integer.class);
        String sql2 = "insert into user (userid,name,head) values(?,?,?)";
        int i=jdbcTemplate.update(sql2, userid,username,head);
        return userid;
    }
    @RequestMapping("/getuser")
    @ResponseBody
    public User getUserInfo(int i)
    {
        System.out.println(i);
        User u= (User)jdbcTemplate.queryForObject("select name,infomation,sex,head from user where userid=?",new Object[]{i},new BeanPropertyRowMapper(User.class));
        return u;
    }
    @RequestMapping("/img")
    @ResponseBody
    public String uploadAccident(@RequestParam(value = "image") MultipartFile file) throws IOException {
        System.out.println("文件的大小:"+file.getSize());
        System.out.println("文件的类型:"+file.getContentType());
        System.out.println("文件的名称:"+file.getName());
        if (file.isEmpty()){
            System.out.println("文件空");
        }
        //文件不空
        if (!file.isEmpty()){
            // 获取上传的文件名称，并结合存放路径，构建新的文件名称
            String destination="E:\\lwImage";
            String filename = file.getOriginalFilename();
            File filepath = new File(destination, filename+".jpg");
            // 判断路径是否存在，不存在则新创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();		}
            // 将上传文件保存到目标文件目录
            file.transferTo(new File(destination + File.separator + filename+".jpg"));
            return filepath.toString();
    }
        return"upimgwrong!";
    }
    @RequestMapping("/sendpassage")
    public int insertpassage(@RequestBody Passage passage) {
        System.out.println(passage);
        String sql = "insert into passage (userid,content,imgpath,PostTime,PostPlace) values(?,?,?,?,?)";
        int j = jdbcTemplate.update(sql, passage.getUserid(),passage.getContent(),passage.getimgpath(),passage.getPostTime(),passage.getPostPlace());
        return j;
    }
    @RequestMapping("/getpassage")
    @ResponseBody
    public List<Passage> ha()
    {
        List<Passage> list = jdbcTemplate.query("select * from passage,user where passage.userid=user.userid limit 10",new BeanPropertyRowMapper(Passage.class));
        if(list!=null && list.size()>0){
            return list;
        }
        else{
            return null;
        }
    }
    @RequestMapping("/getownpassage/{id}")
    @ResponseBody
    public List<Passage> ha(@PathVariable("id")int userid)
    {
        List<Passage>p = DbHelper.getopassage(userid);
        return p;
    }
}
