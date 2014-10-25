package com.example.AndroidTest.copyBeanTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khangpv on 10/25/2014.
 */
public class CopyBeanTest
{
    public static void main(String[] args)
    {
        ClassUtils classUtils = new ClassUtils();
        Manager manager = new Manager("name", 10, 5l);
        List<Field> fields = new ArrayList<Field>();
        classUtils.deepFieldsSearch(manager.getClass(), fields);
        System.out.println("size: " + fields.size());
    }

}
