package com.xichuan.generator.server;


import com.xichuan.generator.util.FreemarkerUtil;

import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {
    static String toPath = "generator\\src\\main\\java\\com\\xichuan\\generator\\test\\";
    static String toServicePath = "server\\src\\main\\java\\com\\xichuan\\server\\service\\";
    public static void main(String[] args) throws Exception {
        String Domain = "Section";
        String domain = "section";
        Map<String,Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath+Domain+"Service.java",map);
    }


}
