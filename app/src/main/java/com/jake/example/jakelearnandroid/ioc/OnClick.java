package com.jake.example.jakelearnandroid.ioc;


import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerName = "setOnClickListener" , listenerType = View.OnClickListener.class,callbackMethod = "onClick")
public @interface OnClick {

    int[] value();
}
