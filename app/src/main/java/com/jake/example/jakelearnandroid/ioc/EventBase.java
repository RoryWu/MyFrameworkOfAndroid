package com.jake.example.jakelearnandroid.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    // btn.setOnClickListener(listener)

    /**
     * listner Type
     * @return
     */
    Class<?> listenerType();

    /**
     * listner Name
     * @return
     */
    String listenerName();

    String callbackMethod();
}
