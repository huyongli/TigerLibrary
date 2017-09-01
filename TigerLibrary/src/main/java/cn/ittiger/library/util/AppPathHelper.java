package cn.ittiger.library.util;

import java.io.File;

import cn.ittiger.library.app.AppContext;

/**
 * @author: ylhu
 * @time: 17-5-12
 */
public class AppPathHelper {
    /**
     * 缓存根目录，应用卸载后，数据清除
     */
    private static String sCacheDir;
    /**
     * 数据存储目录，应用卸载后，数据依然存在
     */
    private static String sDataDir;

    static {
        sCacheDir = SdCardUtil.getCacheDir(AppContext.getInstance());
        sDataDir = SdCardUtil.getRootPath();
    }

    private static String sImageCacheDir;

    public static String getCrashDir() {

        return sCacheDir + File.separator + "Crash";
    }

    public static String getCacheDir() {

        return sCacheDir;
    }


    /**
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     * @return
     */
    public static String getDataDir() {

        return sDataDir;
    }

    public static String getNetCacheDir() {

        return getCacheDir() + "/Net";
    }

    public static String getImageCacheDir() {

        return getCacheDir() + "/Image";
    }

    public static String getDBDir() {

        return getCacheDir() + "/DB";
    }
}
