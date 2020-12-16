package com.example.demo;


import org.junit.Test;

import java.lang.reflect.Type;

public class WriteTest {

    private static final String PATH = "E:\\TestPath\\WriteExcel.xlsx";

    @Test
    public void write(){
//        File file = new File("E:\\TestPath\\模板.xlsx");
//        EasyExcel.write(PATH,WritePO.class).withTemplate(file).sheet("data").doFill(WritePO.getData());
        new Shk<String,Integer>();
    }


    class Shk<T,O> {

        public Shk() {
            Type[] genericInterfaces = this.getClass().getGenericInterfaces();
            for (Type type:
            genericInterfaces) {
                System.out.println(type);
            }

        }

    }


}
