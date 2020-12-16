package com.example.demo.controller;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WritePO {

    @ExcelProperty({"姓名", "name"})
    private String name;

    @ExcelProperty({"年龄", "age"})
    private Integer age;

    @ExcelProperty({"生日", "birth"})
    @DateTimeFormat("yyyy/MM/dd")
    private Date birth;


    public static List<WritePO> getData(Integer index) {
        List<WritePO> list = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            list.add(new WritePO("T" + i, 20, new Date()));
        }
        return list;
    }

}
