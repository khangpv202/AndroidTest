package com.example.AndroidTest.copyBeanTest;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils to load class attribute.
 * Method getClassByAnnotated is used for most init process which mapping the model - fragment, event - result, etc..
 */
public class ClassUtils implements IClassUtils
{
// ------------------------------ FIELDS ------------------------------

    public static final String TEST_POSTFIX = "Test";

    private static IClassUtils instance;

    public static IClassUtils getInstance()
    {
        if (instance == null)
        {
            instance = new ClassUtils();
        }
        return instance;
    }

    public static void setInstance(IClassUtils iClassUtils)
    {
        instance = iClassUtils;
    }

// -------------------------- STATIC METHODS --------------------------

    @Override
    public List<Class> getClassByAnnotated(Context context, String packageName, Class<? extends Annotation> annotationClass)
            throws IOException, URISyntaxException, ClassNotFoundException,
            PackageManager.NameNotFoundException
    {
        String apkName = context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
        DexFile dexFile = new DexFile(apkName);

        PathClassLoader classLoader2 = new PathClassLoader(apkName, context.getApplicationContext().getClassLoader());
        DexClassLoader classLoader = new DexClassLoader(apkName, new ContextWrapper(context).getCacheDir().getAbsolutePath(), null, classLoader2);

        List<Class> classes = new ArrayList<Class>();
        Enumeration<String> entries = dexFile.entries();

        while (entries.hasMoreElements())
        {
            try
            {
                String entry = entries.nextElement();
                // only check items that exist in source package and not in libraries, exclude tests, etc.
                if (entry.endsWith(TEST_POSTFIX))
                { // Test is consider as postfix for test, that should be exclude from scanning
                    continue;
                }
                if (entry.startsWith(packageName))
                {

                    Class<?> entryClass = classLoader.loadClass(entry);//dexFile.loadClass(entry, classLoader);
                    if (entryClass != null)
                    {
                        Annotation[] annotations = entryClass.getAnnotations();
                        for (Annotation annotation : annotations)
                        {
                            if (annotationClass.isInstance(annotation) && !classes.contains(entryClass))
                            {
                                classes.add(entryClass);
                            }
                        }
                        // This for support 1 level inheritance annotated, if needed, reserve till Object.class
                        Class<?> superclass = entryClass.getSuperclass();
                        if (null != superclass)
                        {
                            annotations = superclass.getAnnotations();
                            for (Annotation annotation : annotations)
                            {
                                if (annotationClass.isInstance(annotation) && !classes.contains(entryClass))
                                {
                                    classes.add(entryClass);
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
            }
        }

        return classes;
    }

    @Override
    public <A extends Annotation> A deepQueryAnnotation(Class originalClass, Class<A> annotationClass)
    {
        if (originalClass != Object.class)
        {
            if (originalClass.isAnnotationPresent(annotationClass))
            {
                return (A) originalClass.getAnnotation(annotationClass);
            }
            else
            {
                return deepQueryAnnotation(originalClass.getSuperclass(), annotationClass);
            }
        }
        return null;
    }

    @Override
    public void deepFieldsSearch(Class concreteClass, List<Field> currentFields)
    {
        if (concreteClass == null)
        {
            return;
        }
        currentFields.addAll(Arrays.asList(concreteClass.getDeclaredFields()));
        if (concreteClass.getSuperclass() != Object.class)
        {
            deepFieldsSearch(concreteClass.getSuperclass(), currentFields);
        }
    }

    @Override
    public Method getMethodByName(Class clazz, String methodName)
    {
        for (Method method : clazz.getDeclaredMethods())
        {
            if (method.getName().equals(methodName))
            {
                return method;
            }
        }
        for (Method method : clazz.getMethods())
        {
            if (method.getName().equals(methodName))
            {
                return method;
            }
        }
        return null;
    }

    public static View addItemView(Context context, int layoutId)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, null);
        return view;

    }

    static public String customFormat(String pattern, double value)
    {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(value);
    }

    public static ArrayList<Integer> getNumberFromString(String time)
    {
        ArrayList<Integer> month = new ArrayList<Integer>();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(time);
        while (m.find())
        {
            month.add(Integer.valueOf(m.group()));
        }
        return month;
    }
}
