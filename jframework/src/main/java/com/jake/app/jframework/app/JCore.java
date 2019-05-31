package com.jake.app.jframework.app;

import android.content.Context;

/**
 * @author
 * @version 1.0
 * @date 19-5-31
 */
public class JCore {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getJFrameworkConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }
}
