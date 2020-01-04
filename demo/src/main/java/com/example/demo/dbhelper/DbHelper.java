package com.example.demo.dbhelper;

import com.example.demo.dbobject.Passage;
import com.example.demo.dbobject.UserLo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@ComponentScan
public class DbHelper {
    @Autowired
    private static JdbcTemplate jdbcTemplate;
    public static int testuser(UserLo user)
    {
        String username=user.getName();
        String password=user.getPassword();
        String sql = "SELECT userid FROM login WHERE name="+username+"and password="+password;
        int userid;
        userid = jdbcTemplate.queryForObject(sql, Integer.class);
        return userid;
    }
    public static int insertuser(UserLo user)
    {
        String username=user.getName();
        String password=user.getPassword();
        System.out.println(username);
        System.out.println(password);
        String sql = "insert into login (name,password) values(?,?)";
        int i=jdbcTemplate.update(sql, username,password);
        return i;//返回插入结果
    }
    public static List<Passage> getpassage()//取十条
    {
        List<Passage> list = jdbcTemplate.query("select * from passage limit 10",new BeanPropertyRowMapper(Passage.class));
        if(list!=null && list.size()>0){
            return list;
        }
        else{
            return null;
        }
    }
    public static List<Passage> getopassage(int userid)//取十条
    {
        List<Passage> list = jdbcTemplate.query("select * from passage where userid=? limit 10",new Object[]{userid},new BeanPropertyRowMapper(Passage.class));
        if(list!=null && list.size()>0){
            return list;
        }
        else{
            return null;
        }
    }
   public static int insertpassage(Passage p)
   {
       String sql = "INSERT INTO passage VALUES (?,?,?,?,?,?,?,?,?,?)";
       int i=jdbcTemplate.update(sql,new PreparedStatementSetter() {
           public void setValues(PreparedStatement ps) throws SQLException {
               ps.setInt(2, p.getUserid());
               ps.setString(3, p.getContent());
               //...
           }
       });
       return i;//返回插入结果
   }

}
