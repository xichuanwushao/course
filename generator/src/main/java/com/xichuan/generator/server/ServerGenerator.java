package com.xichuan.generator.server;


import com.xichuan.generator.util.FreemarkerUtil;

public class ServerGenerator {
    static String toPath = "generator\\src\\main\\java\\com\\xichuan\\generator\\test\\";
    public static void main(String[] args) throws Exception {
        FreemarkerUtil.initConfig("test.ftl");
        FreemarkerUtil.generator(toPath+"Test.java",null);
    }


}
