package com.stanislav.berdnik;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {
        System.out.println("Started");
        readArgumentsAndInitProperties();
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/application-context.xml");
        Object bean = context.getBean("classForAutowiring");
        Field field = bean.getClass().getDeclaredField("autoviredValue");
        field.setAccessible(true);
        System.out.println("field.get(bean): " + field.get(bean));
        increaseNumberFromProp();
        readArgumentsAndInitProperties();
        context.refresh();
        Object beanAfterRefreshing = context.getBean("classForAutowiring");
        Field fieldAfterRefreshing = bean.getClass().getDeclaredField("autoviredValue");
        fieldAfterRefreshing.setAccessible(true);
        System.out.println("field.get(beanAfterRefreshing): " + fieldAfterRefreshing.get(beanAfterRefreshing));
        System.out.println("Finished");
    }

    private static void readArgumentsAndInitProperties() throws Exception {
        System.out.println("readArgumentsAndInitProperties started");
        System.getProperties().setProperty("settings_path_for_creator", "/home/stas/1.Privat/1.projects/tax_workers/bean-refreshing/conf/test.properties");
        System.out.println("readArgumentsAndInitProperties finished");
    }

    private static void increaseNumberFromProp() throws IOException {
        File file = new File("/home/stas/1.Privat/1.projects/tax_workers/bean-refreshing/conf/test.properties");
        String propString = new Scanner(file).useDelimiter("\\Z").next();
        String[] keyValue = propString.split("=");
        if (!file.exists()) {
            file.createNewFile();
        }
        String propName = keyValue[0];
        String propValue = keyValue[1];
        Integer propValueIntager = Integer.valueOf(propValue);
        propValueIntager++;
        String resultString = propName + "=" + propValueIntager;
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(resultString);
        bw.close();
    }
}
