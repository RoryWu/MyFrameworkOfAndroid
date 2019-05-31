package com.jake.example.jakelearnandroid.ioc;

import android.app.Activity;
import android.view.View;

import com.jake.example.jakelearnandroid.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SuppressWarnings("ALL")
public class InjectUtils {


    private static final String METHOD_SET_CONTENT_VIEW = "setContentView";
    private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity) {

        injectContentView(activity);
        injectViewField(activity);
        injectEvents(activity);

    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) {
                        String listenerName = eventBase.listenerName();
                        Class<?> listenerType = eventBase.listenerType();
                        String callbackMethodName = eventBase.callbackMethod();

                        // 方法一：
//                        OnClick onClick = (OnClick) annotation;
//                        if (onClick != null) {
//                            int[] values = onClick.value();
//                        }
                        // 方法二：
                        try {
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            int viewsId[] = (int[]) valueMethod.invoke(annotation, null);

                            //代理模式 , 设置代理
                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);

                            handler.addMethod(callbackMethodName , method);
                            // 产生onclickLIstener 的代理对象
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),new Class<?>[]{listenerType} , handler);

                            for (int viewid : viewsId) {
                                View view = activity.findViewById(viewid);
                                Method setter = view.getClass().getDeclaredMethod(listenerName, listenerType);
                                setter.invoke(view, listener);
                            }

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static void injectViewField(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 首先拿到这个类上的属性
        // getDeclaredFields 能拿到私有的
        // getFields 不能拿到
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                int fieldID = bindView.value();
                try {
                    Method findViewByID = clazz.getMethod(METHOD_FIND_VIEW_BY_ID, int.class);
                    //执行findviewbyid 方法，并获得到控件
                    Object view  = findViewByID.invoke(activity, fieldID);
                    // 设置控件的私有属性可以访问
                    field.setAccessible(true);
                    // 设置给属性
                    field.set(activity,view);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }


        }

    }

    /**
     * 注入布局
     * @param activity
     */
    private static void injectContentView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        int layoutID = contentView.value();
        try {
//        activity.setContentView(layoutID);
            Method setContentView = clazz.getMethod(METHOD_SET_CONTENT_VIEW, int.class);
            setContentView.invoke(activity, layoutID);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
