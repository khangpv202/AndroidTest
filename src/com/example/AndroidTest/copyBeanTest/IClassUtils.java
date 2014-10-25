package com.example.AndroidTest.copyBeanTest;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author : lent
 * @version 1.0
 * @since : 09/08/2014
 */
public interface IClassUtils
{
    List<Class> getClassByAnnotated(Context context, String packageName, Class<? extends Annotation> annotationClass)
            throws IOException, URISyntaxException, ClassNotFoundException,
            PackageManager.NameNotFoundException;

    <A extends Annotation> A deepQueryAnnotation(Class originalClass, Class<A> annotationClass);

    void deepFieldsSearch(Class concreteClass, List<Field> currentFields);

    Method getMethodByName(Class clazz, String methodName);
}
