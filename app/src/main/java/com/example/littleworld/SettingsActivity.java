package com.example.littleworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import static android.os.FileUtils.copy;

public class SettingsActivity extends Fragment {
/**  java tai cai bu hui chong fu diao yong    **/
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    static PersonInfo myInfo;
    private int userId;
    View layout;
    private ImageView picture;
    private Uri imageUri;
    //图片
    private Bitmap bitmap;
    //保存的文件路径
    private File fileDir;
    // 声明PopupWindow
    private PopupWindow popupWindow;
    // 声明PopupWindow对应的视图
    private View popupView;
    // 声明平移动画
    private TranslateAnimation animation;
    /**    **/

    Cursor cursor;

    public SettingsActivity(int userId){
        super();
        this.userId = userId;
    }
public static void getUserInfo(final int i)
{
    Thread t=new Thread(new Runnable()
    {
        public void run()
        {
            try {
                URL url=new URL("http://192.168.0.106:8080/getuser?i="+i);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setRequestProperty("connection", "keep-alive");//设置持久连接
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if(responseCode == 200){
                    //得到响应流
                    InputStream inputStream = connection.getInputStream();
                    //将响应流转换成字符串
                    BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer tStringBuffer = new StringBuffer();
                    String sTempOneLine = new String("");
                    while ((sTempOneLine = tBufferedReader.readLine()) != null){
                        tStringBuffer.append(sTempOneLine);
                    }
                    String result= tStringBuffer.toString();
                    Log.d("user get result",result);
                    JSONObject user=new JSONObject(result);
                    myInfo.set(user);//图片路径设置为：服务器IP/图片名
                    System.out.println(myInfo);
                }
                else
                {
                    System.out.println("设置页面连接失败");
                }




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
    t.start();
}
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        layout = inflater.inflate(R.layout.settings_main, container, false);
/**
        init();//查数据库显示信息
**/
myInfo=new PersonInfo();
        /**   查数据库显示信息,和下面的函数功能一样的
        String name = new String();//保存用户名
        String intro = new String();//保存用户介绍
        cursor = DbHelper.getInstance().getUserBook(DbHelper.getInstance().getUserId());
        if(cursor.getCount()!=0){
            cursor.moveToNext();
            name = cursor.getString(1);
            intro = cursor.getString(2);
        }
        cursor.close();
        **/

        TextView userName = layout.findViewById(R.id.username);
        TextView introduction = layout.findViewById(R.id.introduction);
        ImageView head = layout.findViewById(R.id.sculpture);

        getUserInfo(DbHelper.getInstance().getUserId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(myInfo);
        String bytes = "http://192.168.0.106:8080/upload/"+myInfo.img;
        //Bitmap bitmap= BitmapFactory.decodeFile(bytes);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
        //设置头像
        Glide
                .with(this)
                .load(bytes)//加载说说路径
                .into(head);
        //head.setImageBitmap(bitmap);
        //设置用户名
        userName.setText(myInfo.name);
        //设置介绍
        if(myInfo.intro == "" || myInfo.intro == null||myInfo.intro=="null")
            introduction.setText("暂无介绍");
        else
            introduction.setText(myInfo.intro);

        /* 跳转至个人主页 */
        ImageButton btn_sculpture = layout.findViewById(R.id.sculpture);
        btn_sculpture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("is_owner",1);//本人跳转至自己主页
                intent.putExtra("user_id",userId);
                startActivity(intent);
            }
        });

        /* 编辑用户信息 */
        ImageButton btn_edit = layout.findViewById(R.id.editInfoEnter);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingEditInfo.class);
                startActivity(intent);
            }
        });

        /* 实现账号管理的功能 */
        Button btn_alterAccount = (Button) layout.findViewById(R.id.alterAccount);
        btn_alterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsAccountsActivity.class);
                startActivity(intent);
            }
        });

        /* 实现密码设置的功能 */
        Button button2 = (Button) layout.findViewById(R.id.setPassword);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsPasswordActivity.class);
                startActivity(intent);
            }
        });

        /* 实现隐私设置的功能 */
        Button button3 = (Button) layout.findViewById(R.id.setPrivacy);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsPrivacyActivity.class);
                startActivity(intent);
            }
        });

        /* 实现联系我们的功能 */
        Button button4 = (Button) layout.findViewById(R.id.contactUs);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsContactusActivity.class);
                startActivity(intent);
            }
        });
        return layout;
    }
/**
    public void init(){
        TextView userName_text = (TextView)layout.findViewById(R.id.username);
        TextView introduction_text = (TextView)layout.findViewById(R.id.introduction);
        ImageButton userImg = (ImageButton)layout.findViewById(R.id.sculpture);

        PersonInfo myInfo = DbHelper.getInstance().getUserInfo(userId);
        userName_text.setText(myInfo.name);
        if(myInfo.intro == null)
            introduction_text.setText("暂无介绍");
        else
            introduction_text.setText(myInfo.intro);
    }
**/

}

