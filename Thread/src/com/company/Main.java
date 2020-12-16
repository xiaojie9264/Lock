package com.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    private static String writeUrl = "http://localhost:8089/test/download/wirte?index=";
    private static String loadUrl = "http://localhost:8089/test/download/load?index=";



    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"正在执行");
                    down(finalI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread"+i).start();
        }

    }

    private static void down(Integer index) throws Exception {
        InputStream is = null;
        BufferedReader br = null;
        Boolean result = null;// 返回结果字符串
        FileOutputStream fileOutputStream = null;



        is = getStream(writeUrl.concat(String.valueOf(index)));
        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        // 存放数据
        StringBuffer sbf = new StringBuffer();
        String temp = null;
        while ((temp = br.readLine()) != null) {
            sbf.append(temp);
        }
        result = new Boolean(sbf.toString());
        br.close();
        is.close();
        if (result){
            is = getStream(loadUrl.concat(String.valueOf(index)));
            byte[] buff = new byte[1024 * 100];
            fileOutputStream = new FileOutputStream("E:\\TestPath\\down\\下载"+index+".xlsx");
            BufferedInputStream brfi = new BufferedInputStream(is);
            int red ;
            while ((red = brfi.read(buff)) != -1) {
                fileOutputStream.write(buff, 0, red);
            }
            fileOutputStream.close();
            brfi.close();
            is.close();
            System.out.println("欧克");
        }

    }

    private static InputStream getStream(String urlPath) throws Exception{
        URL url = new URL(urlPath);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置连接方式：get
        connection.setRequestMethod("GET");
        // 设置连接主机服务器的超时时间：15000毫秒
        connection.setConnectTimeout(15000);
        // 设置读取远程返回的数据时间：60000毫秒
        connection.setReadTimeout(60000);
        // 发送请求
        connection.connect();
        // 通过connection连接，获取输入流
        if (connection.getResponseCode() == 200) {
            return connection.getInputStream();
        }

        connection.disconnect();// 关闭远程连接
        return null;
    }
}
