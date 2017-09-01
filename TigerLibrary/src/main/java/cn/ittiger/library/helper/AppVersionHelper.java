package cn.ittiger.library.helper;

import cn.ittiger.library.util.PreferenceHelper;
import cn.ittiger.library.app.AppContext;
import cn.ittiger.library.app.BaseApplication;

import android.content.Context;

/**
 * 如果要处理升级相关逻辑，请在应用的主ActivityonCreate中执行方法{@link #checkUpdate()}
 * 详细逻辑请继承{@link BaseApplication}并重写其{@link BaseApplication#onAppUpgrade(int, int)}方法实现相关逻辑
 *
 * App版本升级帮助类
 * @author: ylhu
 * @time: 17-5-25
 */
public class AppVersionHelper {
    // 本地文件缓存的版本号，用以处理升级逻辑。
    private static final String PREF_KEY_CACHED_VERSION_CODE = "cached_version_code";
    // 应用运行到MainActivity之后提供一个可以判断是否为首次安装的依据
    private static final String PREF_KEY_INITIAL_INSTALL = "initial_install";
    //默认版本号，第一次安装时，获取的本地缓存的版本号为0
    public static final int DEFAULT_VERSION_CODE = 0;

    public static int getCachedVersionCode() {

        return PreferenceHelper.getInt(
                PREF_KEY_CACHED_VERSION_CODE, DEFAULT_VERSION_CODE);
    }

    private static void cacheVersionCode(int versionCode) {

        PreferenceHelper.putInt(PREF_KEY_CACHED_VERSION_CODE, versionCode);
    }

    /**
     * 是否首次安装
     * @return
     */
    public static boolean isInitialInstall() {

        return PreferenceHelper.getBoolean(PREF_KEY_INITIAL_INSTALL, false);
    }

    private static void setInitialInstall(boolean initialInstall) {

        PreferenceHelper.putBoolean(PREF_KEY_INITIAL_INSTALL, initialInstall);
    }

    /**
     * 在主Activity中调用
     */
    public static void checkUpdate() {

        int currentVersionCode = Configuration.getInstance().getVersionCode();
        int cachedVersionCode = AppVersionHelper.getCachedVersionCode();
        if (currentVersionCode > cachedVersionCode) {
            if (cachedVersionCode != DEFAULT_VERSION_CODE) {
                Context context = AppContext.getInstance().getApplicationContext();
                if(context instanceof BaseApplication) {
                    ((BaseApplication) context).onAppUpgrade(cachedVersionCode, currentVersionCode);
                } else {
                    throw new IllegalStateException("application must be extends BaseApplication can use this function");
                }
            } else {
                AppVersionHelper.setInitialInstall(true);
            }
            AppVersionHelper.cacheVersionCode(currentVersionCode);
        }
    }
}
