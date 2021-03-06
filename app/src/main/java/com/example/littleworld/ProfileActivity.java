package com.example.littleworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.littleworld.Adapter.ProfilePageAdapter;
import com.example.littleworld.Entity.ProfileViewPager;
import com.example.littleworld.util.ToastUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private AppBarLayout appbar;
    private ProfileViewPager viewPager;
    private FragmentManager fm;
    private TextView title; // 页面标题设置为用户名
    private TabLayout tabLayout;
    private int userId;
    private int isOwner;//1为用户自己主页，0为他人主页
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id",-1);
        isOwner = intent.getIntExtra("is_owner",-1);
        String s = String.valueOf(userId);
        if(isOwner==1)
            ToastUtil.show(getApplicationContext(),"您正在访问自己的主页");
        else
            ToastUtil.show(getApplicationContext(),"您正在访问userid="+s+"的主页");

        PersonInfo myInfo = DbHelper.getInstance().getUserInfo(userId);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(myInfo);
        appbar = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.viewPager);
        title = findViewById(R.id.title); // 页面标题设置为用户名
        title.setText(myInfo.name);

        TextView textUserName = findViewById(R.id.textUsername);
        textUserName.setText(myInfo.name);
        TextView intro = findViewById(R.id.textWhatsUp);
        ImageView head = findViewById(R.id.imageSculp);
        String bytes = "http://192.168.0.106:8080/upload/"+myInfo.img;
        //Bitmap bitmap= BitmapFactory.decodeFile(bytes);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
        //设置头像
        Glide
                .with(this)
                .load(bytes)//加载说说路径
                .into(head);
        if(myInfo.intro == "null")
            intro.setText("暂无介绍");
        else
            intro.setText(myInfo.intro);

        tabLayout = findViewById(R.id.tabs);
        fm = getSupportFragmentManager();

        // 设置状态栏用户名可见性
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {// 折叠状态
                    title.setVisibility(View.VISIBLE); // 显示用户名
                } else {// 非折叠状态
                    title.setVisibility(View.GONE); // 隐藏用户名
                }
            }
        });

        // 设置adapter
        viewPager.setAdapter(new ProfilePageAdapter(ProfileActivity.this, fm, viewPager, userId));

        // 关联tabLayout与viewPager
        tabLayout.setupWithViewPager(viewPager);

        // 设置页面切换监听器
        viewPager.addOnPageChangeListener(new ProfileViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.resetHeight(position);
                (findViewById(R.id.item_detail_container)).scrollTo(0, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        viewPager.resetHeight(0);

        // 返回至上一界面
        ImageButton backBtn=findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.this.finish();
            }
        });
    }

}
