package com.jake.app.jframework.app;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

//import okhttp3.Interceptor;


public final class Configurator {

    private static final HashMap<Object, Object> J_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
//    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        J_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        J_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getJFrameworkConfigs() {
        return J_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        initIcons();
        Logger.addLogAdapter(new AndroidLogAdapter());
        J_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
        Utils.init(JCore.getApplicationContext());
    }

    public final Configurator withApiHost(String host) {
        J_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withLoaderDelayed(long delayed) {
        J_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

//    public final Configurator withInterceptor(Interceptor interceptor) {
//        INTERCEPTORS.add(interceptor);
//        J_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
//        return this;
//    }
//
//    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
//        INTERCEPTORS.addAll(interceptors);
//        J_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
//        return this;
//    }

    public final Configurator withWeChatAppId(String appId) {
        J_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

//    public final Configurator withWeChatAppSecret(String appSecret) {
//        J_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
//        return this;
//    }

    @SuppressWarnings("unchecked")
    public final Configurator withActivity(Activity activity) {
        J_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Configurator withJavascriptInterface(@NonNull String name) {
        J_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

//    public Configurator withWebEvent(@NonNull String name, @NonNull Event event) {
//        final EventManager manager = EventManager.getInstance();
//        manager.addEvent(name, event);
//        return this;
//    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) J_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = J_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) J_CONFIGS.get(key);
    }
}
