package com.xichuan.generator.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class FreemarkerUtil {

    static String ftlPath = "generator\\src\\main\\java\\com\\xichuan\\generator\\ftl\\";

    static Template temp;

    public static void initConfig(String ftlName) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        temp = cfg.getTemplate(ftlName, "UTF-8");
        temp.setEncoding("UTF-8");
    }

    public static void generator(String fileName, Map<String, Object> map) throws IOException, TemplateException {
        FileWriter fw = new FileWriter(fileName);
        FileOutputStream fileoutput = new FileOutputStream(new File(fileName));
        OutputStreamWriter bw =  new OutputStreamWriter(fileoutput,"UTF-8");
        temp.process(map, bw);
        bw.flush();
        fw.close();
    }
}
