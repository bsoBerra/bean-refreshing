package com.stanislav.berdnik;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class Main {


    public static void main(String[] args) throws Exception {
        String changeableBeanName = "changeableBean";
        String unchangeableBeanName = "unchangeableBean";
        String classForAutowiringBeanName = "classForAutowiring";

        String changeableFieldName = "changeableValue";
        String unchangeableFieldName = "unchangeableValue";

        System.out.println("Started");
        readArgumentsAndInitProperties();
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/application-context.xml");
        checkBeans(context);
        Object changeableBean = context.getBean(changeableBeanName);
        Object unchangeableBean = context.getBean(unchangeableBeanName);

        printBeanInfo(classForAutowiringBeanName, context);
        printBeanInfo(changeableBeanName, context);
        printBeanInfo(unchangeableBeanName, context);

        printFiledValue(changeableFieldName, changeableBean);
        printFiledValue(unchangeableFieldName, unchangeableBean);

        increaseNumberFromProp();

        context.refresh();
        System.out.println("AFTER REFRESHING");
        Object changeableBeanAfterRefreshing = context.getBean(changeableBeanName);
        Object unchangeableBeanAfterRefreshing = context.getBean(unchangeableBeanName);

        printBeanInfo(classForAutowiringBeanName, context);
        printBeanInfo(changeableBeanName, context);
        printBeanInfo(unchangeableBeanName, context);

        printFiledValue(changeableFieldName, changeableBeanAfterRefreshing);
        printFiledValue(unchangeableFieldName, unchangeableBeanAfterRefreshing);

//        checkBeans(context);

        System.out.println("Finished");
    }

    private static void printBeanInfo(String classForAutowiringBeanName, ConfigurableApplicationContext context) {
        BeanDefinition changeableBeanDefenition = context.getBeanFactory().getBeanDefinition(classForAutowiringBeanName);
        System.out.println("BEAN " + classForAutowiringBeanName + " INFO");
        System.out.println("changeableBeanDefenition.getBeanClassName(): " + changeableBeanDefenition.getBeanClassName());
        System.out.println("changeableBeanDefenition.hashCode(): " + changeableBeanDefenition.hashCode());
    }

    private static void printFiledValue(String fieldName, Object bean) throws NoSuchFieldException, IllegalAccessException {
        Field changeableField = bean.getClass().getDeclaredField(fieldName);
        changeableField.setAccessible(true);
        System.out.println(fieldName + ": " + changeableField.get(bean));
    }

    private static void readArgumentsAndInitProperties() throws Exception {
        System.out.println("readArgumentsAndInitProperties started");
        System.getProperties().setProperty("settings_path_for_creator", "/home/stas/1.Privat/1.projects/tax_workers/bean-refreshing/conf/test.properties");
        System.out.println("readArgumentsAndInitProperties finished");
    }

    private static void increaseNumberFromProp() throws IOException {
        File file = new File("/home/stas/1.Privat/1.projects/tax_workers/bean-refreshing/conf/test.properties");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> props = null;
            while ((line = br.readLine()) != null) {
                if(props == null) {
                    props = new ArrayList<>();
                }
                 props.add(line.trim());
            }
            assert props != null;
            String changeableProp = props.get(0);

            String resultString = increaseValueOfProp(changeableProp);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(resultString + System.getProperty("line.separator") + props.get(1));
            bw.close();
        }
    }

    private static String increaseValueOfProp(String changeableValue) {
        String[] keyValue = changeableValue.split("=");
        String propName = keyValue[0];
        String propValue = keyValue[1];
        Integer propValueIntager = Integer.valueOf(propValue);
        propValueIntager++;
        return propName + "=" + propValueIntager;
    }

    private static void checkBeans(ConfigurableApplicationContext context ) {
        Map<String,ClassForAutowiring > beans =  context.getBeanFactory().getBeansOfType(ClassForAutowiring.class);
        System.out.println("beans number:" + beans.size());

    }

}
