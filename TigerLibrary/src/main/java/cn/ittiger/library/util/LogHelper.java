package cn.ittiger.library.util;

import cn.ittiger.library.app.AppContext;

import com.orhanobut.logger.Logger;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by ylhu on 17-4-7.
 */
public final class LogHelper {
    private static final String DEFAULT_TAG = "LogHelper";

    static {
        Logger.init(DEFAULT_TAG);
    }

    public static void w(String tag, String message) {

        if(AppContext.getInstance().isDebug()) {
            Logger.t(tag).w(message);
        }
    }

    public static void d(String tag, String message) {

        if(AppContext.getInstance().isDebug()) {
            Logger.t(tag).d(message);
        }
    }

    public static void w(String message) {

        if(AppContext.getInstance().isDebug()) {
            Logger.w(message);
        }
    }

    public static void d(String message) {

        if(AppContext.getInstance().isDebug()) {
            Logger.d(message);
        }
    }

    public static void e(String message) {

        if(AppContext.getInstance().isDebug()) {
            Logger.e(message);
        }
    }

    public static void w(String message, Object... args) {

        if(AppContext.getInstance().isDebug()) {
            Logger.w(message, args);
        }
    }

    public static void d(String message, Object... args) {

        if(AppContext.getInstance().isDebug()) {
            Logger.d(message, args);
        }
    }

    public static void d(String tag, String message, Object... args) {

        if(AppContext.getInstance().isDebug()) {
            Logger.t(tag).d(message, args);
        }
    }

    public static void e(String message, Object... args) {

        if(AppContext.getInstance().isDebug()) {
            Logger.e(message, args);
        }
    }

    public static void e(Throwable throwable) {

        if(AppContext.getInstance().isDebug()) {
            Logger.e(throwable, "exception");
        }
    }

    public static void e(Throwable throwable, String message) {

        if(AppContext.getInstance().isDebug()) {
            Logger.e(throwable, message);
        }
    }

    public static void e(String tag, Throwable throwable, String message) {

        if(AppContext.getInstance().isDebug()) {
            Logger.t(tag).e(throwable, message);
        }
    }

    public static void e(Throwable throwable, String message, Object... args) {

        if(AppContext.getInstance().isDebug()) {
            Logger.e(throwable, message, args);
        }
    }

    public static void json(String json) {

        if(AppContext.getInstance().isDebug()) {
            Logger.json(json);
        }
    }

    public static String logDeviceIMEI(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            LogHelper.json(json.toString());
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }
}
