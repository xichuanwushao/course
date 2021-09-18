package com.xichuan.generator.server;


import com.xichuan.generator.util.DbUtil;
import com.xichuan.generator.util.Field;
import com.xichuan.generator.util.FreemarkerUtil;

import java.util.*;

public class ServerGenerator {
    static String MODULE = "business";
    static String toDtoPath = "server\\src\\main\\java\\com\\xichuan\\server\\req\\";
    static String toServicePath = "server\\src\\main\\java\\com\\xichuan\\server\\service\\";
    static String toControllerPath = MODULE+"\\src\\main\\java\\com\\xichuan\\"+MODULE+"\\controller\\";
    public static void main(String[] args) throws Exception {
        String Domain = "Section";
        String domain = "section";
        String tableNameCn = "小节";
        String module = MODULE;
        List<Field> fieldList = DbUtil.getColumnByTableName(domain);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        map.put("tableNameCn",tableNameCn);
        map.put("module",module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath+Domain+"Service.java",map);

        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath+Domain+"Controller.java",map);
        // 生成dto
        FreemarkerUtil.initConfig("req.ftl");
        FreemarkerUtil.generator(toDtoPath + Domain + "Req.java", map);

    }

    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
