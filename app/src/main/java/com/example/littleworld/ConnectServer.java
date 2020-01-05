package com.example.littleworld;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectServer {
    static int userid;
    static String result;
    public static int login(final String jsonstream)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                try {
                    URL url=new URL("http://192.168.0.106:8080/login");
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.connect();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(jsonstream);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        //得到响应流
                        InputStream inputStream = connection.getInputStream();
                        //将响应流转换成字符串
                        BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuffer tStringBuffer = new StringBuffer();
                        String sTempOneLine = new String("");
                        while ((sTempOneLine = tBufferedReader.readLine()) != null){
                            tStringBuffer.append(sTempOneLine);
                        }
                        String result= tStringBuffer.toString();//返回用户号或-1
                        userid=Integer.valueOf(result);

                        Log.d("loginresult",result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return userid;
    }
    public static int logup(final String jsonstream)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                try {
                    URL url=new URL("http://192.168.0.106:8080/logup");
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.connect();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(jsonstream);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        //得到响应流
                        InputStream inputStream = connection.getInputStream();
                        //将响应流转换成字符串
                        BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuffer tStringBuffer = new StringBuffer();
                        String sTempOneLine = new String("");
                        while ((sTempOneLine = tBufferedReader.readLine()) != null){
                            tStringBuffer.append(sTempOneLine);
                        }
                        result= tStringBuffer.toString();//返回注册结果
                        userid=Integer.valueOf(result);
                        Log.d("logupresult",result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }).start();
        return userid;
    }

    public static void passageupload(final String jsonstream)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                try {
                    URL url=new URL("http://192.168.0.106:8080/sendpassage");
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.connect();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(jsonstream);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        //得到响应流
                        InputStream inputStream = connection.getInputStream();
                        //将响应流转换成字符串
                        BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuffer tStringBuffer = new StringBuffer();
                        String sTempOneLine = new String("");
                        while ((sTempOneLine = tBufferedReader.readLine()) != null){
                            tStringBuffer.append(sTempOneLine);
                        }
                        String result= tStringBuffer.toString();//返回动态上传结果
                        Log.d("passage upload result",result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /*public static void passagedownload()
    {
        new Thread(new Runnable()//之后写成Activity里的thread
        {
            public void run()
            {
                try {
                    URL url=new URL("http://192.168.0.108:8080/pdownload");
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    //connection.setRequestProperty("Content-Type", "application/json");
                    connection.connect();
                    /*BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(jsonstream);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        //得到响应流
                        InputStream inputStream = connection.getInputStream();
                        //将响应流转换成字符串
                        BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuffer tStringBuffer = new StringBuffer();
                        String sTempOneLine = new String("");
                        while ((sTempOneLine = tBufferedReader.readLine()) != null){
                            tStringBuffer.append(sTempOneLine);
                        }
                        String result= tStringBuffer.toString();//返回动态
                        // Log.d("passage upload result",result);
                        JSONArray array=new JSONArray(result);
                        DbHelper.getInstance().updatepassage();//先清空本地表
                        for(int i=0;i<array.length();i++) {
                            System.out.println(array.getJSONObject(i));
                            Passage p=new Passage(array.getJSONObject(i));//图片路径设置为：服务器IP/图片名
                            passagelist.add(p);
                            DbHelper.getInstance().insertpassage(array.getJSONObject(i));
                        }


                    }
                    else//没联网就从本地添加十条
                    {
                        for(int i=0;i<10;i++)
                        {
                            passagelist.add(DbHelper.getInstance().getpassage());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
}
