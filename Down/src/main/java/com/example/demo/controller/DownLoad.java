package com.example.demo.controller;


import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
@RequestMapping("download")
public class DownLoad {

    ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
    // 模版路径
    private static final String PATH = "E:\\TestPath\\WriteExcel.xlsx";

    @RequestMapping("load")
    public void load(HttpServletResponse response, @RequestParam Integer index) throws IOException, InterruptedException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("数据写出" + index, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        byte[] buff = new byte[1024 * 100];
        try (OutputStream out = response.getOutputStream();
             InputStream in = new FileInputStream(PATH);
             BufferedInputStream bfr = new BufferedInputStream(in);
        ) {
            int i = 0;
            while ((i = bfr.read(buff)) != -1) {
                out.write(buff, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(500);
        rrwl.readLock().unlock();
        System.out.println(Thread.currentThread().getName() + "读完毕");

    }


    @RequestMapping("wirte")
    public Boolean wirteFile(Integer index) throws InterruptedException {
        rrwl.writeLock().lock();
        EasyExcel.write(PATH, WritePO.class).sheet("data").doWrite(WritePO.getData(index));
        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + "写完毕");
        rrwl.writeLock().unlock();
        rrwl.readLock().lock();
        return true;
    }

}
