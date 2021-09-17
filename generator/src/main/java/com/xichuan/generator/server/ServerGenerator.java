package com.xichuan.generator.server;


import com.xichuan.generator.util.FreemarkerUtil;

import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {
    static String MODULE = "business";
    static String toServicePath = "server\\src\\main\\java\\com\\xichuan\\server\\service\\";
    static String toControllerPath = MODULE+"\\src\\main\\java\\com\\xichuan\\"+MODULE+"\\controller\\";
    public static void main(String[] args) throws Exception {
        String Domain = "Section";
        String domain = "section";
        String tableNameCn = "小节";
        String module = MODULE;
        Map<String,Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        map.put("tableNameCn",tableNameCn);
        map.put("module",module);
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath+Domain+"Service.java",map);

        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath+Domain+"Controller.java",map);
    }


}
